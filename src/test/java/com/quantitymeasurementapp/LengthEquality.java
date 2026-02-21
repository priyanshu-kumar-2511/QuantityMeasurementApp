package com.quantitymeasurementapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LengthEquality {

    @Test
    void testEquality_FeetToFeet_SameValue() {
        assertTrue(new Length(1.0, LengthUnit.FEET).equals(new Length(1.0, LengthUnit.FEET)));
    }

    @Test
    void testEquality_InchToInch_SameValue() {
        assertTrue(new Length(5.0, LengthUnit.INCHES).equals(new Length(5.0, LengthUnit.INCHES)));
    }

    @Test
    void testEquality_FeetToInch_EquivalentValue() {
        assertTrue(new Length(1.0, LengthUnit.FEET).equals(new Length(12.0, LengthUnit.INCHES)));
    }

    @Test
    void testEquality_DifferentValue() {
        assertFalse(new Length(1.0, LengthUnit.FEET).equals(new Length(2.0, LengthUnit.FEET)));
    }

    @Test
    void testEquality_NullComparison() {
        assertFalse(new Length(1.0, LengthUnit.FEET).equals(null));
    }

    @Test
    void testEquality_SameReference() {
        Length l = new Length(1.0, LengthUnit.FEET);
        assertTrue(l.equals(l));
    }
}
