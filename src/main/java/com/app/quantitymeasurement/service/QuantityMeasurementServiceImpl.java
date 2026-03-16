package com.app.quantitymeasurement.service;

import java.util.logging.Logger;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.entity.QuantityModel;
import com.app.quantitymeasurement.exception.CategoryMismatchException;
import com.app.quantitymeasurement.exception.InvalidUnitMeasurementException;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.quantity.Quantity;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.unit.IMeasurable;
import com.app.quantitymeasurement.unit.*;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

	private IQuantityMeasurementRepository repository;
	
	// Private Logger for logging information in the controller
	private static final Logger logger = Logger.getLogger(QuantityMeasurementServiceImpl.class.getName());
 
	public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
		this.repository = repository;
		logger.info("QuantityMeasurementServiceImpl init with repository: " + repository.getClass().getName()); 
	}
	
	private enum Operation {
		COMPARISON, CONVERSION, ADD, SUBTRACT, DIVIDE;  
	}
		
	
	@Override
	public boolean compare(QuantityDTO dto1, QuantityDTO dto2) {
	    validate(dto1, dto2);
		
		QuantityModel<IMeasurable> model1 = convertToQuantityModel(dto1);
		QuantityModel<IMeasurable> model2 = convertToQuantityModel(dto2);
		
	    Quantity<IMeasurable> q1 = new Quantity<>(model1.getValue(), model1.getUnit());
	    Quantity<IMeasurable> q2 = new Quantity<>(model2.getValue(), model2.getUnit());
	    
	    boolean result = q1.equals(q2);

	    QuantityMeasurementEntity entity = new QuantityMeasurementEntity(dto1.getValue(), dto1.getUnit(), dto1.getMeasurementType(), dto2.getValue(), dto2.getUnit(), dto2.getMeasurementType(), Operation.COMPARISON.name(), result ? 1.0 : 0.0, "BOOLEAN", "BOOLEAN");
	    
	    repository.save(entity);
	   	    	    
	    return result;
	}

	@Override
	public QuantityDTO convert(QuantityDTO dto1, QuantityDTO targetDTO) {
	    validate(dto1, targetDTO);
		
		QuantityModel<IMeasurable> model1 = convertToQuantityModel(dto1);
		QuantityModel<IMeasurable> model2 = convertToQuantityModel(targetDTO); 

	    Quantity<IMeasurable> q1 = new Quantity<>(model1.getValue(), model1.getUnit());
	    Quantity<IMeasurable> targetQuantity = new Quantity<>(model2.getValue(), model2.getUnit());

	    Quantity<IMeasurable> result = q1.convertTo(targetQuantity.getUnit());
	    
	   saveOperation(dto1, targetDTO, Operation.CONVERSION, result); 

	    return convertToDTO(result);
	}

	@Override
	public QuantityDTO add(QuantityDTO dto1, QuantityDTO dto2) {
		return add(dto1, dto2, dto1); 
	}

	@Override
	public QuantityDTO add(QuantityDTO dto1, QuantityDTO dto2, QuantityDTO targetDTO) {
		validate(dto1, dto2, targetDTO); 

		QuantityModel<IMeasurable> model1 = convertToQuantityModel(dto1);
		QuantityModel<IMeasurable> model2 = convertToQuantityModel(dto2);
		QuantityModel<IMeasurable> targetModel = convertToQuantityModel(targetDTO);

	    Quantity<IMeasurable> q1 = new Quantity<>(model1.getValue(), model1.getUnit());
	    Quantity<IMeasurable> q2 = new Quantity<>(model2.getValue(), model2.getUnit());
	    
	    
	    Quantity<IMeasurable> result = q1.add(q2, targetModel.getUnit());
	    		
		saveOperation(dto1, dto2, Operation.ADD, result); 
		 
		return convertToDTO(result); 
	} 

	@Override
	public QuantityDTO subtract(QuantityDTO dto1, QuantityDTO dto2) {
		return subtract(dto1, dto2, dto1); 
	}

	@Override
	public QuantityDTO subtract(QuantityDTO dto1, QuantityDTO dto2, QuantityDTO targetDTO) {
	    validate(dto1, dto2, targetDTO);
		
		QuantityModel<IMeasurable> model1 = convertToQuantityModel(dto1);
		QuantityModel<IMeasurable> model2 = convertToQuantityModel(dto2);
		QuantityModel<IMeasurable> targetModel = convertToQuantityModel(targetDTO);


	    Quantity<IMeasurable> q1 = new Quantity<>(model1.getValue(), model1.getUnit());
	    Quantity<IMeasurable> q2 = new Quantity<>(model2.getValue(), model2.getUnit());

	    Quantity<IMeasurable> result = q1.subtract(q2, targetModel.getUnit());
	    
	    saveOperation(dto1, dto2, Operation.SUBTRACT, result);

	    return convertToDTO(result);
	}

	@Override
	public QuantityDTO divide(QuantityDTO dto1, QuantityDTO dto2) {
	    validate(dto1, dto2);

		QuantityModel<IMeasurable> model1 = convertToQuantityModel(dto1);
		QuantityModel<IMeasurable> model2 = convertToQuantityModel(dto2);  

	    Quantity<IMeasurable> q1 = new Quantity<>(model1.getValue(), model1.getUnit());
	    Quantity<IMeasurable> q2 = new Quantity<>(model2.getValue(), model2.getUnit());
		
	    double result = q1.divide(q2);
	    
	    return new QuantityDTO(result, q1.getUnit()); 
	}

	
	// Helper method for addition
	private void validate(QuantityDTO dto1, QuantityDTO dto2) {
		validate(dto1, dto2, dto1); 
	}
	
	private void validate(QuantityDTO dto1, QuantityDTO dto2, QuantityDTO targetDTO) {
		if(dto1 == null || dto2 == null || targetDTO == null) {
			throw new QuantityMeasurementException("QuantityDTO objects can't be null!!!");
		}
		
		if((!dto1.getMeasurementType().equals(dto2.getMeasurementType())) || (!dto1.getMeasurementType().equals(targetDTO.getMeasurementType()))) {
			throw new CategoryMismatchException("Operation not possible on two different measurement types!!!");
		}
		
		if(!Double.isFinite(dto1.getValue()) || !Double.isFinite(dto2.getValue()) || !Double.isFinite(targetDTO.getValue())) {
			throw new QuantityMeasurementException("Values must be finite numbers!!!"); 
		}
	} 
	
	// Helper method to convert QuantityDTO object to QuantityModel object
	public QuantityModel<IMeasurable> convertToQuantityModel(QuantityDTO dto) {
		if(dto == null) {
            throw new QuantityMeasurementException("QuantityDTO object can't be null!!!");
		}
		
		String measurementType = dto.getMeasurementType();
		String unitName = dto.getUnit();
		IMeasurable unit;
		
		switch(measurementType) {
			case "LengthUnit":
				unit = LengthUnit.valueOf(unitName);
				break;
				
			case "WeightUnit":
				unit = WeightUnit.valueOf(unitName);
				break;
				
			case "VolumeUnit":
				unit = VolumeUnit.valueOf(unitName);
				break;
				
			case "TemperatureUnit":
				unit = TemperatureUnit.valueOf(unitName);
				break;
				 
			default:
				throw new InvalidUnitMeasurementException("Invalid measurement type or unit!!!");
		}
		
		return new QuantityModel<IMeasurable>(dto.getValue(), unit); 
	}
	
	private void saveOperation(QuantityDTO dto1, QuantityDTO dto2, Operation arithmetic, Quantity<IMeasurable> result) {
	    QuantityModel<IMeasurable> resultModel = new QuantityModel<>(result.getValue(), result.getUnit());

	    QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
            dto1.getValue(), dto1.getUnit(), dto1.getMeasurementType(),
            dto2.getValue(), dto2.getUnit(), dto2.getMeasurementType(),
            arithmetic.name(),
            resultModel.getValue(),
            resultModel.getUnit().getUnitName(),
            resultModel.getUnit().getMeasurementType()
	    ); 

	    repository.save(entity);
	}
	
	private QuantityDTO convertToDTO(Quantity<IMeasurable> result) {
	    return new QuantityDTO(result.getValue(), result.getUnit());
	}
}
