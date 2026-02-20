package com.quantitymeasurementapp;

import java.util.Scanner;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("Choose unit (1 = Feet, 2 = Inches): ");
            int choice = Integer.parseInt(sc.nextLine());

            System.out.print("Enter first value: ");
            double value1 = Double.parseDouble(sc.nextLine());

            System.out.print("Enter second value: ");
            double value2 = Double.parseDouble(sc.nextLine());

            boolean result;

            if (choice == 1) {
                Feet f1 = new Feet(value1);
                Feet f2 = new Feet(value2);
                result = f1.equals(f2);
            } else if (choice == 2) {
                Inches i1 = new Inches(value1);
                Inches i2 = new Inches(value2);
                result = i1.equals(i2);
            } else {
                System.out.println("Invalid unit choice.");
                return;
            }

            System.out.println("Are equal? " + result);

        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric input.");
        }
    sc.close();    
    }
}
