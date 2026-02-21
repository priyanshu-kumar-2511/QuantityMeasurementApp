package com.quantitymeasurementapp;

import java.util.Scanner;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
        	System.out.println("Choose unit for first value (1=Feet, 2=Inches): ");
            int unitChoice1 = Integer.parseInt(sc.nextLine());

            System.out.print("Enter first value: ");
            double value1 = Double.parseDouble(sc.nextLine());

            System.out.println("Choose unit for second value (1=Feet, 2=Inches): ");
            int unitChoice2 = Integer.parseInt(sc.nextLine());

            System.out.print("Enter second value: ");
            double value2 = Double.parseDouble(sc.nextLine());

            LengthUnit unit1 = (unitChoice1 == 1) ? LengthUnit.FEET : LengthUnit.INCHES;
            LengthUnit unit2 = (unitChoice2 == 1) ? LengthUnit.FEET : LengthUnit.INCHES;

            Length l1 = new Length(value1, unit1);
            Length l2 = new Length(value2, unit2);

            System.out.println("Are equal? " + l1.equals(l2));
 
       } catch (NumberFormatException e) {
            System.out.println("Invalid numeric input.");
       }
       sc.close();    
    }
}
