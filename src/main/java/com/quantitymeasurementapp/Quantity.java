package com.quantitymeasurementapp;

import java.util.Objects;

public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    private static final double EPS = 1e-6;

    public Quantity(double value, U unit) {
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Value must be finite");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    private double toBase() {
        return unit.convertToBaseUnit(value);
    }

    // ---------------- EQUALITY ----------------

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Quantity<?> other = (Quantity<?>) obj;

        if (this.unit.getClass() != other.unit.getClass())
            return false;

        return Math.abs(this.toBase() - other.toBase()) < EPS;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Math.round(toBase() / EPS));
    }

    // ---------------- CONVERSION ----------------

    public Quantity<U> convertTo(U targetUnit) {

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        if (!targetUnit.getClass().equals(unit.getClass()))
            throw new IllegalArgumentException("Target unit must be same category");

        double base = toBase();
        double converted = targetUnit.convertFromBaseUnit(base);

        return new Quantity<>(converted, targetUnit);
    }

    // ---------------- ADDITION ----------------

    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {

        validateOperation(other, targetUnit);

        double resultBase = this.toBase() + other.toBase();
        double result = targetUnit.convertFromBaseUnit(resultBase);

        result = round(result);

        return new Quantity<>(result, targetUnit);
    }

    // ---------------- SUBTRACTION (UC12) ----------------

    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {

        validateOperation(other, targetUnit);

        double resultBase = this.toBase() - other.toBase();
        double result = targetUnit.convertFromBaseUnit(resultBase);

        result = round(result);

        return new Quantity<>(result, targetUnit);
    }

    // ---------------- DIVISION (UC12) ----------------

    public double divide(Quantity<U> other) {

        if (other == null)
            throw new IllegalArgumentException("Other quantity cannot be null");

        if (this.unit.getClass() != other.unit.getClass())
            throw new IllegalArgumentException("Cross-category division not allowed");

        double divisor = other.toBase();

        if (divisor == 0)
            throw new ArithmeticException("Division by zero");

        return this.toBase() / divisor;
    }

    // ---------------- COMMON VALIDATION ----------------

    private void validateOperation(Quantity<U> other, U targetUnit) {

        if (other == null)
            throw new IllegalArgumentException("Other quantity cannot be null");

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        if (this.unit.getClass() != other.unit.getClass())
            throw new IllegalArgumentException("Cross-category operation not allowed");
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", value, unit);
    }
}