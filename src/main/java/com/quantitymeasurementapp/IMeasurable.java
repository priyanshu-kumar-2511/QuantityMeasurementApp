package com.quantitymeasurementapp;

public interface IMeasurable {

    double getConversionFactor();

    String getUnitName();

    default double convertToBaseUnit(double value) {
        return value * getConversionFactor();
    }

    default double convertFromBaseUnit(double baseValue) {
        return baseValue / getConversionFactor();
    }
}