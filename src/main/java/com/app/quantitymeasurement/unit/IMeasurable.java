package com.app.quantitymeasurement.unit;

import com.app.quantitymeasurement.unit.IMeasurable;

public interface IMeasurable { 
	public String getUnitName();
	
	public double getConversionFactor();
	
	public double convertToBaseUnit(double value);
	 
	public double convertFromBaseUnit(double baseValue);

	public String getMeasurementType(); 
}
