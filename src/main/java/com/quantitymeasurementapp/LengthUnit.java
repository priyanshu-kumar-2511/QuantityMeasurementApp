package com.quantitymeasurementapp;

public enum LengthUnit {

    FEET(12.0),     // 1 foot = 12 inches
    INCHES(1.0);    // base unit = inch

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }
}
