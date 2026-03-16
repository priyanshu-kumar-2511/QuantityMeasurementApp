package com.app.quantitymeasurement.repository;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.logging.*;
import java.util.ArrayList;
import java.util.List;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

class AppendableObjectOutputStream extends ObjectOutputStream {

	public AppendableObjectOutputStream(OutputStream out) throws IOException {
		super(out);
	}
	
	@Override
	protected void writeStreamHeader() throws IOException {
		File file = new File(QuantityMeasurementCacheRepository.FILE_NAME);
		
		if(!file.exists() || file.length() == 0) {
			super.writeStreamHeader();
		} else {
			reset();
		}
	}
}


public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {
	
	// File where data will be stored
    public static final String FILE_NAME = "quantity_measurement_repo.ser";

    // List to store entities in memory
    List<QuantityMeasurementEntity> quantityMeasurementEntityCache;

	public Object lastSavedEntity;

    // Singleton instance
    private static QuantityMeasurementCacheRepository instance;

    private static final Logger logger = Logger.getLogger(QuantityMeasurementCacheRepository.class.getName());
 
    public QuantityMeasurementCacheRepository() {
        quantityMeasurementEntityCache = new ArrayList<>();

        // Load existing data from disk
        loadFromDisk();
        
        logger.info("QuantityMeasurementCacheRepository initialized with " + quantityMeasurementEntityCache.size() + " entities loaded from disk");
    }

    // Get single instance of repository
    public static QuantityMeasurementCacheRepository getInstance() {
        if (instance == null) {
            instance = new QuantityMeasurementCacheRepository();
        }
        return instance;
    }

    // Save entity in memory and disk
    @Override
    public void save(QuantityMeasurementEntity entity) {

        // Add entity to memory cache
        quantityMeasurementEntityCache.add(entity);

        // Save entity to disk
        saveToDisk(entity);
    }

    // Return all stored entities
    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {

        // Return cached list
        return quantityMeasurementEntityCache;
    }

    // Save entity to file
    private void saveToDisk(QuantityMeasurementEntity entity) {

        try {
            FileOutputStream fos = new FileOutputStream(FILE_NAME, true);
            AppendableObjectOutputStream oos = new AppendableObjectOutputStream(fos);

            // Write object to file
            oos.writeObject(entity);

            oos.close();
            fos.close();

        } catch (IOException e) {
            System.err.println("Error saving entity: " + e.getMessage());
        }
    }

    // Load entities from disk when repository starts
    private void loadFromDisk() {

        File file = new File(FILE_NAME);

        // If file does not exist, nothing to load
        if (!file.exists()) {
            return;
        }

        try {
            FileInputStream fis = new FileInputStream(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);

            while (true) {
                try {
                    QuantityMeasurementEntity entity =
                            (QuantityMeasurementEntity) ois.readObject();

                    // Add entity to cache
                    quantityMeasurementEntityCache.add(entity);

                } catch (EOFException e) {
                    // End of file reached
                    break;
                }
            }

            ois.close();
            fis.close();

        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(
                    "Error loading quantity measurement entities: " +
                            ex.getMessage()
            );
        }
    }

    @Override
    public int getTotalCount() {
        return quantityMeasurementEntityCache.size();
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
        List<QuantityMeasurementEntity> result = new ArrayList<>();

        for (QuantityMeasurementEntity entity : quantityMeasurementEntityCache) {
            if (entity != null &&
                entity.operation != null &&
                entity.operation.equalsIgnoreCase(operation)) {
                result.add(entity);
            }
        }

        return result;
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType) {
        List<QuantityMeasurementEntity> result = new ArrayList<>();

        for (QuantityMeasurementEntity entity : quantityMeasurementEntityCache) {
            if (entity != null &&
                entity.thisMeasurementType != null &&
                entity.thisMeasurementType.equalsIgnoreCase(measurementType)) {
                result.add(entity);
            }
        }

        return result;
    }

}