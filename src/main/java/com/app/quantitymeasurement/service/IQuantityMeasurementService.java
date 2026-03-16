package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.entity.QuantityDTO;

public interface IQuantityMeasurementService {
	
	public boolean compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
	
	public QuantityDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO thaQuantityDTO);
	
	public QuantityDTO add(QuantityDTO thiQuantityDTO, QuantityDTO tahtQuantityDTO);
	
	public QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thaQuantityDTO, QuantityDTO targetUnitDTO);
	
	public QuantityDTO subtract(QuantityDTO thiQuantityDTO, QuantityDTO thaQuantityDTO);
	
	public QuantityDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thaQuantityDTO, QuantityDTO targetUnitDTO);

	public QuantityDTO divide(QuantityDTO thiQuantityDTO, QuantityDTO thaQuantityDTO);

	
	public static void main(String[] args) {
		System.out.println("IQuantityMeasurementService Interface");
	}
}   
