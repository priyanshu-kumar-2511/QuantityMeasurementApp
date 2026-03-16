package com.app.quantitymeasurement.entity;

import com.app.quantitymeasurement.unit.IMeasurable;

public class QuantityDTO { 
	public double value; 
	public String unit;
	public String measurementType;
	
	public QuantityDTO(double value, IMeasurable unit) {
		this.value = value;
		this.unit = unit.getUnitName();
		this.measurementType = unit.getMeasurementType();
	}
	
	public QuantityDTO(double value, String unit, String measurementType) {
		this.value = value;
		this.unit = unit;
		this.measurementType = measurementType;
	}

	public double getValue() {
		return value;
	}
	public String getUnit() {
		return unit;
	}
	public String getMeasurementType() {
		return measurementType;
	}

	@Override
	public String toString() {
		return "QuantityDTO [value: " + value + " || unit: " + unit + " || measurementType: " + measurementType + "]";
	}
	
	
	public static void main(String[] args) {
		System.out.println("Quantity DTO class"); 
	}
}