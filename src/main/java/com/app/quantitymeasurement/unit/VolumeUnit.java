package com.app.quantitymeasurement.unit;

public enum VolumeUnit implements IMeasurable {
	LITRE(1.0),
	MILLILITRE(0.001),
	GALLON(3.78541);
	
	private final double conversionFactor;

	private VolumeUnit(double conversionFactor) {
		this.conversionFactor = conversionFactor;
	}
	
	
	public double getConversionFactor() {
		return (int)(this.conversionFactor * 10000.0) / 10000.0; 
	} 
	
	public double convertToBaseUnit(double value) {
		return value * conversionFactor; 
	}
	
	public double convertFromBaseUnit(double baseValue) {
		return baseValue / conversionFactor; 
	}

	@Override
	public String getUnitName() {
		return this.name();
	}

	@Override
	public String getMeasurementType() {
		return this.getClass().getSimpleName(); 
	}
}
