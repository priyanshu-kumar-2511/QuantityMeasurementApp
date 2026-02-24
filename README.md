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

## 🗓 Day 6 – UC6: Addition of Two Length Units
*(Date: 22-Feb-2026)*

- Main Flow
  - Client calls Quantity Length.add(length1, length2, targetUnit) or uses an instance method to add two length measurements.
  - The method validates:
    - Both length1 and length2 are non-null and have valid LengthUnits.
    - All values are finite numbers (Double.isFinite or equivalent).
  - Convert both length1 and length2 to a common base unit (feet).
  - Add the converted values.
  - Return a new Quantity Length object (or numeric value) representing the result in the unit of first operand.

- Creating JUnit test cases : 
  - testAddition_SameUnit_FeetPlusFeet()
  - testAddition_SameUnit_InchPlusInch()
  - testAddition_CrossUnit_FeetPlusInches()
  - testAddition_CrossUnit_InchPlusFeet()
  - testAddition_CrossUnit_YardPlusFeet() 
  - testAddition_CrossUnit_CentimeterPlusInch()
  - testAddition_Commutativity()
  - testAddition_WithZero()
  - testAddition_NegativeValues()
  - testAddition_NullSecondOperand()

🔗 *Code Link:*  
[Day 6 – UC6: Addition of Two Length Units](https://github.com/priyanshu-kumar-2511/QuantityMeasurementApp/tree/feature/UC6-UnitAddition/src)

## UC7: Addition with Target Unit

- Main Flow
  - Client calls Quantity Length.add(length1, length2, targetUnit) with an explicit target unit parameter.
  - The method validates:
    - Both length1 and length2 are non-null and have valid LengthUnits.
    - targetUnit is non-null and a valid LengthUnit.
    - All values are finite numbers (Double.isFinite or equivalent).
  - Convert both length1 and length2 to a common base unit (feet).
  - Add the converted values.
  - Convert the sum from the base unit to the explicitly specified targetUnit.
  - Return a new Quantity Length object representing the result in the target unit.

- Creating JUnit test cases : 
  - testAddition_ExplicitTargetUnit_Feet()
  - testAddition_ExplicitTargetUnit_Inches() 
  - testAddition_ExplicitTargetUnit_Yards()
  - testAddition_ExplicitTargetUnit_Centimeters() 
  - testAddition_ExplicitTargetUnit_SameAsFirstOperand() 
  - testAddition_ExplicitTargetUnit_SameAsSecondOperand()
  - testAddition_ExplicitTargetUnit_Commutativity()
  - testAddition_ExplicitTargetUnit_WithZero() 
  - testAddition_ExplicitTargetUnit_NegativeValues()
  - testAddition_ExplicitTargetUnit_NullTargetUnit()

🔗 *Code Link:*  
[Day 6 – UC7: Addition with Target Unit](https://github.com/priyanshu-kumar-2511/QuantityMeasurementApp/tree/feature/UC7-TargetUnitAddition/src)

## UC8: Refactoring Unit Enum to Standalone with Conversion Responsibility

- Main Flow
  - Enum Refactoring:
    - Move LengthUnit from inside QuantityLength to a standalone top-level class.
    - Add conversion responsibility to LengthUnit: methods to convert from base unit and to base unit.
  - Unit Conversion Logic:
    - Implement convertToBaseUnit(double value) method in LengthUnit to convert a value in this unit to feet (base unit).
    - Implement convertFromBaseUnit(double baseValue) method in LengthUnit to convert a base unit value (feet) to this unit.
  - QuantityLength Simplification:
    - Remove internal conversion logic from QuantityLength.
    - Delegate all conversion operations to the unit's conversion methods.
    - QuantityLength now focuses solely on value comparison and arithmetic logic.
  - Backward Compatibility:
    - All existing test cases from UC1–UC7 pass without modification.
    - Client code continues to work with the same public API.
  - Scalability Pattern:
    - The refactored design establishes a pattern for future measurement categories.
    - New units (WeightUnit, VolumeUnit, TemperatureUnit) can follow the same extraction and responsibility pattern.

- Creating JUnit test cases : 
  - testLengthUnitEnum_FeetConstant() 
  - testLengthUnitEnum_InchesConstant() 
  - testLengthUnitEnum_YardsConstant()
  - testLengthUnitEnum_CentimetersConstant() 
  - testConvertToBaseUnit_FeetToFeet()
  - testConvertToBaseUnit_InchesToFeet() 
  - testConvertToBaseUnit_YardsToFeet()
  - testConvertToBaseUnit_CentimetersToFeet() 
  - testConvertFromBaseUnit_FeetToFeet()
  - testConvertFromBaseUnit_FeetToInches() 

🔗 *Code Link:*  
[Day 6 – UC8: Refactoring Unit Enum to Standalone with Conversion Responsibility](https://github.com/priyanshu-kumar-2511/QuantityMeasurementApp/tree/feature/UC8-StandaloneUnit/src)

## UC9: Weight Measurement Equality, Conversion, and Addition (Kilogram, Gram, Pound)

- Main Flow
  - Equality Comparison:
    - User inputs two numerical values with their respective weight unit types.
    - QuantityWeight class validates the input values to ensure they are numeric and units are valid.
    - Both values are converted to the common base unit (kilogram) using WeightUnit conversion methods.
    - The converted values are compared for equality using the overridden equals() method.
    - The result of the comparison (true or false) is returned.
  - Unit Conversion:
    - User inputs a numerical value, source unit, and target unit.
    - QuantityWeight.convertTo(targetUnit) converts the measurement to the target unit.
    - The method normalizes through the base unit (kilogram) and applies appropriate conversion factors.
    - A new QuantityWeight object is returned with the converted value and target unit.
  - Addition Operations:
    - User inputs two QuantityWeight objects and optionally a target unit.
    - Both measurements are converted to the base unit (kilogram).
    - The converted values are summed.
    - The result is converted to the target unit (either first operand's unit or explicitly specified unit).
    - A new QuantityWeight object representing the sum is returned.


- Creating JUnit test cases : 
  - testEquality_KilogramToKilogram_SameValue()
  - testEquality_KilogramToKilogram_DifferentValue() 
  - testEquality_KilogramToGram_EquivalentValue() 
  - testEquality_GramToKilogram_EquivalentValue() 
  - testEquality_WeightVsLength_Incompatible()
  - testEquality_NullComparison() 
  - testEquality_SameReference()
  - testEquality_NullUnit() 
  - testEquality_TransitiveProperty()
  - testEquality_ZeroValue()  

🔗 *Code Link:*  
[Day 6 – UC9: Weight Measurement Equality](https://github.com/priyanshu-kumar-2511/QuantityMeasurementApp/tree/feature/UC9-WeightMeasurement/src)

## 🗓 Day 7 – UC10: Generic Quantity Class with IMeasurable Interface
*(Date: 23-Feb-2026)*

- Main Flow
  - Introduced Measurable interface.
  - Refactored LengthUnit and WeightUnit to implement interface.
  - Created generic class Quantity<U extends IMeasurable>.
  - Removed duplicate Quantity classes.
  - Prevented cross-category comparison using unit.getClass().
  - Used Double.compare() for equality.
  - Rounded conversion results to 2 decimal places.
  - Simplified QuantityMeasurementApp using generic methods.
  - Restored DRY and SRP principles.

- Creating JUnit test cases :
  - testIMeasurableInterface_LengthUnitImplementation()
  - testIMeasurableInterface_WeightUnitImplementation()
  - testGenericQuantity_LengthOperations_Equality()
  - testGenericQuantity_WeightOperations_Equality()
  - testGenericQuantity_LengthOperations_Conversion()
  - testGenericQuantity_WeightOperations_Addition()
  - testCrossCategoryPrevention_LengthVsWeight()
  - testGenericQuantity_ConstructorValidation_NullUnit()
  - testGenericQuantity_ConstructorValidation_InvalidValue()
  - testHashCode_GenericQuantity_Consistency()

🔗 *Code Link:*  
[Day 7 – UC10: Generic Quantity Class with IMeasurable Interface](https://github.com/priyanshu-kumar-2511/QuantityMeasurementApp/tree/feature/UC10-GenericQuantity/src)
