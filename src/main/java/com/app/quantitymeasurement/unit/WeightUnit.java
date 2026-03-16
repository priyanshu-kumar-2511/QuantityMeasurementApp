package com.app.quantitymeasurement.unit;

public enum WeightUnit implements IMeasurable {
	KILOGRAM(1.0),
	GRAM(0.001),
	POUND(0.453592),
	MILLIGRAM(0.000001);
	
	private final double conversionFactor;

	private WeightUnit(double conversionFactor) {
		this.conversionFactor = conversionFactor;
	}
	
	public double getConversionFactor() {
		return conversionFactor; 
	}
			
	public double convertToBaseUnit(double value) {
		return round(value * this.conversionFactor);
	}
	
	public double convertFromBaseUnit(double baseValue) {
		return round(baseValue / this.conversionFactor); 
	}
	
	private double round(double value) {
		return Math.round(value * 100.0) / 100.0; 
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
