package com.app.quantitymeasurement.repository;

import java.util.List;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

public interface IQuantityMeasurementRepository {
	int getTotalCount();
	void save(QuantityMeasurementEntity entity);
	List<QuantityMeasurementEntity> getAllMeasurements();
	List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation);
	List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType);
}
