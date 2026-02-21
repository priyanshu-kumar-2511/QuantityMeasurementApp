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

- Creating Inchs class which is responsible for checking the equality of two numerical values
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

