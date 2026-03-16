package com.app.quantitymeasurement.repository;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.util.ConnectionPool;

public class QuantityMeasurementDatabaseRepository implements IQuantityMeasurementRepository {

	// Logger for logging database operations and errors
	private static final Logger logger = Logger.getLogger(QuantityMeasurementDatabaseRepository.class.getName());
	
	// Singleton instance of the repository
	private static QuantityMeasurementDatabaseRepository instance;
	private ConnectionPool connectionPool;
	 
	private QuantityMeasurementDatabaseRepository() throws SQLException {
		connectionPool = ConnectionPool.getInstance();
		initializeDatabase();
	}
	
	public static synchronized QuantityMeasurementDatabaseRepository getInstance() {
		if(instance == null) {
			try {
				instance = new QuantityMeasurementDatabaseRepository();
			} catch (SQLException e) {
				throw new RuntimeException("Failed to initialize database repository", e);
			}
		}
		return instance; 
	}
	
	
	private static final String INSERT_QUERY = 
			"INSERT INTO quantity_measurement_entity" + 
			"(this_value, this_unit, this_measurement_type, that_value, that_unit, " + 
			"that_measurement_type, operation, result_value, result_unit, " + 
			"result_measurement_type, result_string, is_error, error_message, " + 
			"created_at, updated_at)" + 
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
	
	private static final String SELECT_ALL_QUERY = 
			"SELECT * FROM quantity_measurement_entity ORDER BY created_at DESC";
	
	private static final String SELECT_BY_OPERATION = 
			"SELECT * FROM quantity_measurement_entity WHERE operation = ? ORDER BY created_at DESC";
	
	private static final String SELECT_BY_MEASUREMENT_TYPE =
			"SELECT * FROM quantity_measurement_entity " +
			"WHERE this_measurement_type = ? ORDER BY created_at DESC";
	
	private static final String DELETE_ALL_QUERY =
			"DELETE FROM quantity_measurement_entity";

	private static final String COUNT_QUERY =
			"SELECT COUNT(*) FROM quantity_measurement_entity";
	
	
	private void initializeDatabase() {
		 String createTableQuery =
		            "CREATE TABLE IF NOT EXISTS quantity_measurement_entity ("
		                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
		                    + "this_value DOUBLE,"
		                    + "this_unit VARCHAR(50),"
		                    + "this_measurement_type VARCHAR(50),"
		                    + "that_value DOUBLE,"
		                    + "that_unit VARCHAR(50),"
		                    + "that_measurement_type VARCHAR(50),"
		                    + "operation VARCHAR(50),"
		                    + "result_value DOUBLE,"
		                    + "result_unit VARCHAR(50),"
		                    + "result_measurement_type VARCHAR(50),"
		                    + "result_string VARCHAR(255),"
		                    + "is_error BOOLEAN,"
		                    + "error_message VARCHAR(255),"
		                    + "created_at TIMESTAMP,"
		                    + "updated_at TIMESTAMP"
		                    + ")";

	    Connection conn = null;
	    Statement stmt = null;

	    try {
	        conn = connectionPool.getConnection();
	        stmt = conn.createStatement();
	        stmt.execute(createTableQuery);

	        logger.info("Database table initialized successfully");

	    } catch(Exception e) {
	        logger.severe("Error initializing database: " + e.getMessage());
	    } finally {
	        closeResources(stmt, conn);
	    }
	}
		
	@Override
	public void save(QuantityMeasurementEntity entity) {
		 Connection conn = null;
		 PreparedStatement pstmt = null;

		 try {

		     conn = connectionPool.getConnection();
		     pstmt = conn.prepareStatement(INSERT_QUERY);

             pstmt.setDouble(1, entity.thisValue);
		     pstmt.setString(2, entity.thisUnit);
		     pstmt.setString(3, entity.thisMeasurementType);

		     pstmt.setDouble(4, entity.thatValue);
		     pstmt.setString(5, entity.thatUnit);
		     pstmt.setString(6, entity.thatMeasurementType);

		     pstmt.setString(7, entity.operation);

		     pstmt.setDouble(8, entity.resultValue);
		     pstmt.setString(9, entity.resultUnit);
		     pstmt.setString(10, entity.resultMeasurementType);

		     pstmt.setString(11, entity.resultString);
		     pstmt.setBoolean(12, entity.isError);
		     pstmt.setString(13, entity.errorMessage);
		     pstmt.executeUpdate();

		     logger.info("Measurement saved successfully");
	    } catch(SQLException e) {
	        logger.severe("Error saving measurement: " + e.getMessage());
	    } finally {
	        closeResources(pstmt, conn);
	    }
	}

	@Override
	public List<QuantityMeasurementEntity> getAllMeasurements() {
		Connection con = null;
		PreparedStatement statement = null;
		
		List<QuantityMeasurementEntity> result = new ArrayList<>();
		
		try {
			con = connectionPool.getConnection();
			statement = con.prepareStatement(SELECT_ALL_QUERY);
			
			ResultSet res = statement.executeQuery();
			
			while(res.next()) {
				result.add(mapResultSetToEntity(res));
			}
		}
		catch(SQLException e) {
			logger.severe("Error saving measurement: " + e.getMessage());
		}
		finally {
	        closeResources(statement, con);
	    }
		return result;
	}
	
	
	@Override
	public int getTotalCount() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

	    try {
	        conn = connectionPool.getConnection();
	        stmt = conn.createStatement();
	        rs = stmt.executeQuery(COUNT_QUERY);

	        if(rs.next()) {
	            return rs.getInt(1);
	        }
	    } catch (SQLException e) {
	        logger.severe("Error counting measurements: " + e.getMessage());
	    } finally {
	        closeResources(rs, stmt, conn);
	    }
	    return 0; 
	}
	
	public void deleteAll() {
		 Connection conn = null;
		 Statement stmt = null;

		 try {
		     conn = connectionPool.getConnection();
		     stmt = conn.createStatement();
		     stmt.executeUpdate(DELETE_ALL_QUERY);

		     logger.info("All measurements deleted");

		 } catch (SQLException e) {
		     logger.severe("Error deleting measurements: " + e.getMessage());
		 } finally {
		     closeResources(stmt, conn);
		 }
	} 
	
	public String getPoolStatistics() {
		 return "Available Connections: "
		            + connectionPool.getAvailableConnectionCount()
		            + ", Used Connections: "
		            + connectionPool.getUsedConnectionCount()
		            + ", Total: "
		            + connectionPool.getTotalConnectionCount();
	}
	
	private QuantityMeasurementEntity mapResultSetToEntity(ResultSet rs) {
		try {
		    QuantityMeasurementEntity entity = new QuantityMeasurementEntity(rs.getDouble("this_value"), rs.getString("this_unit"), rs.getString("this_measurement_type"), rs.getDouble("that_value"), rs.getString("that_unit"), rs.getString("that_measurement_type"), rs.getString("operation"), rs.getDouble("result_value"), rs.getString("result_unit"), rs.getString("result_measurement_type"), rs.getString("result_string"), rs.getBoolean("is_error"), rs.getString("error_message"));
		    return entity;

		} catch (SQLException e) {
		    logger.severe("Error mapping result set: " + e.getMessage());
		    return null;
		}
	}
	
	private void closeResources(ResultSet rs, Statement statement, Connection conn) {
		try {
	        if(rs != null) rs.close();
	        if(statement != null) statement.close(); 

	        if(conn != null) connectionPool.releaseConnection(conn);
	    } catch(Exception e) {
	        logger.warning("Error closing resources: " + e.getMessage());
	    }
	}
	
	private void closeResources(Statement statement, Connection conn) {
		try {
	        if(statement != null) statement.close();
	        if(conn != null) connectionPool.releaseConnection(conn);
	    } catch(Exception e) {
	        logger.warning("Error closing resources: " + e.getMessage());
	    } 
	}
	
	public static void main(String[] args) {
		
	}

	@Override
	public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    List<QuantityMeasurementEntity> result = new ArrayList<>();

	    try {
	        conn = connectionPool.getConnection();
	        pstmt = conn.prepareStatement(SELECT_BY_OPERATION);
	        pstmt.setString(1, operation);

	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            QuantityMeasurementEntity entity = mapResultSetToEntity(rs);
	            if (entity != null) {
	                result.add(entity);
	            }
	        }

	    } catch (SQLException e) {
	        logger.severe("Error fetching measurements by operation: " + e.getMessage());
	    } finally {
	        closeResources(rs, pstmt, conn);
	    }

	    return result;
	}

	@Override
	public List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    List<QuantityMeasurementEntity> result = new ArrayList<>();

	    try {
	        conn = connectionPool.getConnection();
	        pstmt = conn.prepareStatement(SELECT_BY_MEASUREMENT_TYPE);
	        pstmt.setString(1, measurementType);

	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            QuantityMeasurementEntity entity = mapResultSetToEntity(rs);
	            if (entity != null) {
	                result.add(entity);
	            }
	        }

	    } catch (SQLException e) {
	        logger.severe("Error fetching measurements by measurement type: " + e.getMessage());
	    } finally {
	        closeResources(rs, pstmt, conn);
	    }

	    return result;
	}
	
}