package com.quantitymeasurementapp;

import java.util.Scanner;

public class QuantityMeasurementApp {

	private static void printUnits() {
		System.out.println("1. Feet");
		System.out.println("2. Inches");
		System.out.println("3. Yards");
		System.out.println("4. Centimeters");
		System.out.print("Enter choice: ");
	}
	
	private static LengthUnit Choice(int choice) {
		switch (choice) {
		case 1: return LengthUnit.FEET;
		case 2: return LengthUnit.INCHES;
		case 3: return LengthUnit.YARDS;
		case 4: return LengthUnit.CENTIMETERS;
		default: throw new IllegalArgumentException("Invalid unit.");
		}
	}
	
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
        	System.out.println("Choose unit for first value: ");
        	printUnits();
            int unitChoice1 = Integer.parseInt(sc.nextLine());

            System.out.print("Enter first value: ");
            double value1 = Double.parseDouble(sc.nextLine());

            System.out.println("Choose unit for second value: ");
            printUnits();
            int unitChoice2 = Integer.parseInt(sc.nextLine());

            System.out.print("Enter second value: ");
            double value2 = Double.parseDouble(sc.nextLine());

            LengthUnit unit1 = Choice(unitChoice1); 
            LengthUnit unit2 = Choice(unitChoice2);

            Length l1 = new Length(value1, unit1);
            Length l2 = new Length(value2, unit2);

            System.out.println("Are equal? " + l1.equals(l2));
 
       } catch (NumberFormatException e) {
            System.out.println("Invalid numeric input.");
       } catch (IllegalArgumentException e) {
    	   System.out.println(e.getMessage());
       }
       sc.close();    
    }
}
