package com.app.quantitymeasurement.util;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.app.quantitymeasurement.exception.DatabaseException;

public class ConnectionPool {
	private static final Logger logger = Logger.getLogger(ConnectionPool.class.getName());
	
	private static ConnectionPool instance;
	private List<Connection> availableConnections;
	private List<Connection> usedConnections;
	private final int poolSize;
	private final String dbUrl;
	private final String dbUsername;
	private final String dbPassword;
	private final String driverClass;
	private final String testQuery;
	
	public ConnectionPool() throws SQLException {
		ApplicationConfig config = ApplicationConfig.getInstance();
		
        this.poolSize = config.getIntProperty("db.pool-size", 5);
        this.dbUrl = config.getProperty("db.url");
        this.dbUsername = config.getProperty("db.username");
        this.dbPassword = config.getProperty("db.password");
        this.driverClass = config.getProperty("db.driver");
        this.testQuery = config.getProperty("db.hikari.connection-test-query");

        this.availableConnections = new ArrayList<>();
        this.usedConnections = new ArrayList<>();
  
        
        try {
            Class.forName(driverClass);

            for (int i = 0; i < poolSize; i++) {
                Connection connection = createConnection();
                availableConnections.add(connection);
            }

            logger.info("Connection pool initialized successfully with pool size: " + poolSize);

        } catch (ClassNotFoundException e) {
            throw new DatabaseException("Database driver class not found: " + driverClass, e);
        }
	}
	 
	
	public static synchronized ConnectionPool getInstance() throws SQLException {
		if(instance == null) {
			instance = new ConnectionPool();
		}
		return instance; 
	}
	
	private void initializeConnections() throws SQLException {
		
	}
	
	private Connection createConnection() throws SQLException {
		return DriverManager.getConnection(dbUrl, dbUsername, dbPassword); 
	}
	
	public synchronized Connection getConnection() throws SQLException {
		 try {
	         if (availableConnections.isEmpty()) {
	             throw new DatabaseException("No available database connections in the pool");
	         }

	         Connection connection = availableConnections.remove(0);

	         if (!isConnectionValid(connection)) {
	             logger.warning("Connection was invalid. Creating a new one.");
	             connection = createConnection();
	         }

	         usedConnections.add(connection);
	         logger.info("Connection acquired. Available: " + availableConnections.size()
	                 + ", Used: " + usedConnections.size());

	         return connection;

	     } catch (SQLException e) {
	         throw new DatabaseException("Failed to get connection from pool", e);
	     }
	} 
	
	public synchronized void releaseConnection(Connection connection) {
		if (connection == null) {
	        return;
	    }

	    if (usedConnections.remove(connection)) {
	        availableConnections.add(connection);
	        logger.info("Connection released back to pool. Available: " + availableConnections.size()
	                + ", Used: " + usedConnections.size());
	    }
	}
	
	public boolean isConnectionValid(Connection connection) {
		try {
            if(connection == null || connection.isClosed()) {
                return false;
            }

            try(Statement stmt = connection.createStatement()) {
                stmt.execute(testQuery);
            }
            return true;

        } catch(SQLException e) {
            return false;
        }
 	} 
	
	 public synchronized void closeAllConnections() {
	        closeConnectionList(availableConnections);
	        closeConnectionList(usedConnections);
	        availableConnections.clear(); 
	        usedConnections.clear();
	        logger.info("All connections in the pool have been closed.");
	    }
	
	public synchronized void closeConnectionList(List<Connection> connections) {
		for(Connection connection : connections) {
            try {
                if(connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch(SQLException e) {
                logger.warning("Failed to close connection: " + e.getMessage());
            } 
	    }
	}
	
	public int getAvailableConnectionCount() {
		return availableConnections.size(); 
	}
	
	public int getUsedConnectionCount() {
		return usedConnections.size(); 
	}
	
	public int getTotalConnectionCount() {
		return availableConnections.size() + usedConnections.size(); 
	}
	
	@Override
	public String toString() {
	    return "ConnectionPool{" +
	            "availableConnections=" + availableConnections.size() +
	            ", usedConnections=" + usedConnections.size() +
	            ", poolSize=" + poolSize +
	            ", dbUrl='" + dbUrl + '\'' +
	            ", dbUsername='" + dbUsername + '\'' +
	            ", driverClass='" + driverClass + '\'' +
	            ", testQuery='" + testQuery + '\'' +
	            '}';
	}
	
	public static void main(String[] args) { 
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			Connection con = pool.getConnection();
			
			logger.info("Validate connection: " + (pool.isConnectionValid(con) ? "Success" : "Failure"));
			logger.info("Available connections after acquiring 1: " + pool.getAvailableConnectionCount());
			logger.info("Used connections after acquiring 1: " + pool.getUsedConnectionCount());
			
			pool.releaseConnection(con);
			
			logger.info("Available connections after releasing 1: " + pool.getAvailableConnectionCount());
			logger.info("Used connections after releasing 1: " + pool.getUsedConnectionCount());
			pool.closeAllConnections();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
