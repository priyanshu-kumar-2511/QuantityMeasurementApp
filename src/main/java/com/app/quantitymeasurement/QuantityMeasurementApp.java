package com.app.quantitymeasurement;

import java.util.logging.Logger;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementApp {
	
	// Singleton instance
	private static QuantityMeasurementApp instance;
	
	QuantityMeasurementController controller;
	private IQuantityMeasurementRepository repository;
	private QuantityMeasurementServiceImpl service;
	
	private static final Logger logger = Logger.getLogger(QuantityMeasurementApp.class.getName());

	private QuantityMeasurementApp() {
		this.repository = QuantityMeasurementDatabaseRepository.getInstance();
		this.service = new QuantityMeasurementServiceImpl(this.repository);
		this.controller = new QuantityMeasurementController(service);
		logger.info("Quantity Measurement Application initialized with Database Repository");
	}
		
	// Return a single entity of QuantityMeasurementApp
	public static QuantityMeasurementApp getInstance() {
		if(instance == null) {
			instance = new QuantityMeasurementApp();
		}
		return instance;
	}
		
	
	public static void main(String[] args) {
		QuantityMeasurementController controller = getInstance().controller;
				
		// Testing Comparisons of Two QuantityDTOs
		QuantityDTO q1 = new QuantityDTO(2.0, "FEET", "LengthUnit");
		QuantityDTO q2 = new QuantityDTO(24.0, "INCHES", "LengthUnit");
		
		boolean comparisonResult = controller.performComparison(q1, q2);
		logger.info(comparisonResult + " (1 Foot = 12 Inches)");
				
		
		// ---------------- Addition ----------------
	    QuantityDTO addQ1 = new QuantityDTO(2.0, "FEET", "LengthUnit");
	    QuantityDTO addQ2 = new QuantityDTO(24.0, "INCHES", "LengthUnit");

	    QuantityDTO additionResult = controller.performAddition(addQ1, addQ2);
	    logger.info("Addition Result: " + additionResult);

	    
	    // Addition with target unit
	    QuantityDTO additionTarget = new QuantityDTO(0.0, "INCHES", "LengthUnit");
	    
	    QuantityDTO additionWithTargetResult = controller.performAddition(addQ1, addQ2, additionTarget);
	    logger.info("Addition Result in Target Unit: " + additionWithTargetResult);

	    
	    // ---------------- Subtraction ----------------
	    QuantityDTO subQ1 = new QuantityDTO(5.0, "FEET", "LengthUnit");
	    QuantityDTO subQ2 = new QuantityDTO(12.0, "INCHES", "LengthUnit");

	    QuantityDTO subtractionResult = controller.performSubtraction(subQ1, subQ2);
	    logger.info("Subtraction Result: " + subtractionResult);

	    
	    // Subtraction with target unit
	    QuantityDTO subtractionTarget = new QuantityDTO(0.0, "INCHES", "LengthUnit");
	   
	    QuantityDTO subtractionWithTargetResult = controller.performSubtraction(subQ1, subQ2, subtractionTarget);
	    logger.info("Subtraction Result in Target Unit: " + subtractionWithTargetResult);

	    
	    // ---------------- Division ----------------
	    QuantityDTO divQ1 = new QuantityDTO(24.0, "INCHES", "LengthUnit");
	    QuantityDTO divQ2 = new QuantityDTO(12.0, "INCHES", "LengthUnit");

	    QuantityDTO divisionResult = controller.performDivision(divQ1, divQ2);
	    logger.info("Division Result: " + divisionResult);
	}
}
