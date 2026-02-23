# QuantityMeasurementApp

## 🗓 Day 1 – UC1: Feet measurement equality
*(Date: 17-Feb-2026)*

- Creating Feet class which is responsible for checking the equality of two numerical values
measured in feet in the Quantity Measurement Application.
- Creating JUnit test cases : 
  - testEquality_SameValue()
  - testEquality_DifferentValue()
  - testEquality_NullComparison()
  - testEquality_NonNumericInput()
  - testEquality_SameReference()

🔗 *Code Link:*  
[Day 1 – UC1: Feet measurement equality](https://github.com/priyanshu-kumar-2511/QuantityMeasurementApp/tree/feature/UC1-FeetEquality/src)

## 🗓 Day 2 – UC2: Feet and Inches measurement equality
*(Date: 18-Feb-2026)*

- Creating Inches class which is responsible for checking the equality of two numerical values
measured in feet in the Quantity Measurement Application.
- Creating JUnit test cases : 
  - testEquality_SameValue()
  - testEquality_DifferentValue()
  - testEquality_NullComparison()
  - testEquality_NonNumericInput()
  - testEquality_SameReference()

🔗 *Code Link:*  
[Day 2 – UC2: Feet and Inches measurement equality](https://github.com/priyanshu-kumar-2511/QuantityMeasurementApp/tree/feature/UC2-InchEquality/src)

## 🗓 Day 3 – UC3: Generic Quantity Class equality
*(Date: 19-Feb-2026)*

- Main Flow
  - User inputs two numerical values with their respective unit types.
  - The Quantity Length class validates the input values to ensure they are numeric.
  - The Quantity Length class validates the unit type against supported units.
  - Both values are converted to a common base unit (e.g., feet) using conversion factors.
  - The converted values are compared for equality.
  - The result of the comparison is returned to the user.
- Creating JUnit test cases : 
  - testEquality_FeetToFeet_SameValue()
  - testEquality_InchToInch_SameValue()
  - testEquality_NullComparison()
  - testEquality_InchToFeet_EquivalentValue()
  - testEquality_FeetToFeet_DifferentValue()
  - testEquality_InchToInch_DifferentValue()

🔗 *Code Link:*  
[Day 3 – UC2: Feet and Inches measurement equality](https://github.com/priyanshu-kumar-2511/QuantityMeasurementApp/tree/feature/UC3-GenericLength/src)

## 🗓 Day 4 – UC4: Extended Unit Support
*(Date: 20-Feb-2026)*

- Main Flow
  - Users input two numerical values with their respective unit types (feet, inches, yards or cms).
  - The Quantity Length class validates the input values to ensure they are numeric.
  - The QuantityLength class validates the unit type against supported units (feet, inches, yards, cms).
  - Both values are converted to a common base unit (in or feet) using conversion factors.
  - The converted values are compared for equality.
  - The result of the comparison is returned to the user.
- Creating JUnit test cases : 
  - testEquality_YardToYard_SameValue()
  - testEquality_YardToYard_DifferentValue()
  - testEquality_YardToFeet_EquivalentValue()
  - testEquality_FeetToYard_EquivalentValue()
  - testEquality_YardToInches_EquivalentValue()
  - testEquality_InchesToYard_EquivalentValue()

🔗 *Code Link:*  
[Day 4 – UC4: Extended Unit Support](https://github.com/priyanshu-kumar-2511/QuantityMeasurementApp/tree/feature/UC4-YardEquality/src)

## 🗓 Day 5 – UC5: Unit-to-Unit Conversion
*(Date: 21-Feb-2026)*

- Main Flow
  - Client calls Quantity Length.convert(value, sourceUnit, targetUnit) or uses an instance method to request conversion.
  - The method validates:
    - value is a finite number (Double.isFinite or equivalent).
    - sourceUnit and targetUnit are non-null and members of LengthUnit.
  - Convert the input value to the common base unit (e.g., feet) using sourceUnit.getConversionFactor().
  - Convert from the base unit to the target unit by dividing by targetUnit.getConversionFactor() (or multiplying by appropriate reciprocal).
  - Apply optional rounding or precision handling (caller-specified or a default epsilon).
  - Return the converted numeric value to the caller.

- Creating JUnit test cases : 
  - testConversion_FeetToInches()
  - testConversion_InchesToFeet()
  - testConversion_YardsToInches()
  - testConversion_InchesToYards()
  - testConversion_CentimetersToInches() 
  - testConversion_FeatToYard()
  - testConversion_RoundTrip_PreservesValue()
  - testConversion_ZeroValue()

🔗 *Code Link:*  
[Day 5 – UC5: Unit-to-Unit Conversion](https://github.com/priyanshu-kumar-2511/QuantityMeasurementApp/tree/feature/UC5-UnitConversion/src)


