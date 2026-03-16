package com.app.quantitymeasurement.controller;

import java.util.logging.Logger;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;

public class QuantityMeasurementController {
	private IQuantityMeasurementService quantityMeasurementService;
	
	// Private Logger for logging information in the controller
	private static final Logger logger = Logger.getLogger(QuantityMeasurementController.class.getName());

	public QuantityMeasurementController(IQuantityMeasurementService quantityMeasurementService) {
		this.quantityMeasurementService = quantityMeasurementService;
		logger.info("QuantityMeasurementController intialized with service: " + quantityMeasurementService); 
	}
	
	
	// Comparison
	public boolean performComparison(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
	    return quantityMeasurementService.compare(thisQuantityDTO, thatQuantityDTO);
	}

	// Conversion
	public QuantityDTO performConversion(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
	    return quantityMeasurementService.convert(thisQuantityDTO, thatQuantityDTO);
	}
	
	
	// Addition
	public QuantityDTO performAddition(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
	    return quantityMeasurementService.add(thisQuantityDTO, thatQuantityDTO);
	}

	public QuantityDTO performAddition(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO) {
	    return quantityMeasurementService.add(thisQuantityDTO, thatQuantityDTO, targetUnitDTO);
	}

	
	// Subtraction
	public QuantityDTO performSubtraction(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
	    return quantityMeasurementService.subtract(thisQuantityDTO, thatQuantityDTO);
	}

	public QuantityDTO performSubtraction(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO) {
	    return quantityMeasurementService.subtract(thisQuantityDTO, thatQuantityDTO, targetUnitDTO);
	}

	
	// Division
	public QuantityDTO performDivision(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
	    return quantityMeasurementService.divide(thisQuantityDTO, thatQuantityDTO);
	}
	
	
	// Private Handler method
	public void handleResult(QuantityDTO q1, QuantityDTO q2, QuantityDTO result, String operator) {
	    System.out.println(q1.getValue() + " " + q1.getUnit() + " " + operator + " "
	                     + q2.getValue() + " " + q2.getUnit() + " = "
	                     + result.getValue() + " " + result.getUnit()); 
	}
	
	
	public static void main(String[] args) {
		System.out.println("Quantity Measurement Controller class"); 
	}
}