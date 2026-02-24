package com.quantitymeasurementapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GenericQuantityTest {

    @Test
    void testLengthEquality() {
        Quantity<LengthUnit> feet = new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> inches = new Quantity<>(12.0, LengthUnit.INCHES);

        assertTrue(feet.equals(inches));
    }

    @Test
    void testWeightEquality() {
        Quantity<WeightUnit> kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> gram = new Quantity<>(1000.0, WeightUnit.GRAM);

        assertTrue(kg.equals(gram));
    }

    @Test
    void testCrossCategoryPrevention() {
        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertFalse(length.equals(weight));
    }

    @Test
    void testLengthAddition() {
        Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.FEET)
                        .add(new Quantity<>(12.0, LengthUnit.INCHES), LengthUnit.FEET);

        assertEquals(2.0, result.getValue());
    }

    @Test
    void testWeightAddition() {
        Quantity<WeightUnit> result = new Quantity<>(1.0, WeightUnit.KILOGRAM)
                        .add(new Quantity<>(1000.0, WeightUnit.GRAM), WeightUnit.KILOGRAM);

        assertEquals(2.0, result.getValue());
    }
}