package com.quantitymeasurementapp;

import java.util.Objects;

public final class Length {

    private final double value;
    private final LengthUnit unit;

    private static final double EPS = 1E-6;
    
    public Length(double value, LengthUnit unit) {

    	if (!Double.isFinite(value)) {
    		throw new IllegalArgumentException("Value must be finite");
    	}
    	
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        this.value = value;
        this.unit = unit;
    }

    // Getters
    
    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }
    
    // Base Conversion
    
    private double toBaseUnit() {
        return value * unit.getConversionFactor();
    }

    // static conversion
    
    public static double convert(double value, LengthUnit source, LengthUnit target) {
    	if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be finite.");
        }

        if (source == null || target == null) {
            throw new IllegalArgumentException("Units must not be null.");
        }

        double valueInBase = value * source.getConversionFactor();
        return valueInBase / target.getConversionFactor();
    }
    
    // instance conversion
    
    public Length convertTo(LengthUnit targetUnit) {

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit must not be null.");
        }

        double convertedValue = convert(this.value, this.unit, targetUnit);
        return new Length(convertedValue, targetUnit);
    }
    
    // Addition 

    public Length add(Length other) {

        if (other == null) {
            throw new IllegalArgumentException("Second operand cannot be null.");
        }

        if (!Double.isFinite(other.value)) {
            throw new IllegalArgumentException("Other value must be finite.");
        }

        double thisBase = this.toBaseUnit();
        double otherBase = other.toBaseUnit();

        double sumBase = thisBase + otherBase;

        double resultValue = sumBase / this.unit.getConversionFactor();

        return new Length(resultValue, this.unit);
    }
    
    // Equality
    
    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Length other = (Length) obj;

        return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPS;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Math.round(toBaseUnit() / EPS));
    }
    
    @Override
    public String toString() {
    	return String.format("%.2f %", value, unit);
    }
}
