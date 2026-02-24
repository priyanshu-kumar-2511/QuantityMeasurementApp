package com.quantitymeasurementapp;

import java.util.Scanner;

public class QuantityMeasurementApp {

    private static void printCategory() {
        System.out.println("\nChoose Category:");
        System.out.println("1. Length");
        System.out.println("2. Weight");
        System.out.println("3. Volume");
        System.out.print("Enter choice: ");
    }

    private static void printOperation() {
        System.out.println("\nChoose Operation:");
        System.out.println("1. Compare Equality");
        System.out.println("2. Convert Unit");
        System.out.println("3. Add Quantities");
        System.out.println("4. Subtract Quantities");     
        System.out.println("5. Divide Quantities");       
        System.out.print("Enter choice: ");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            printCategory();
            int category = Integer.parseInt(sc.nextLine());

            printOperation();
            int operation = Integer.parseInt(sc.nextLine());

            switch (category) {
                case 1:
                    handleLength(sc, operation);
                    break;
                case 2:
                    handleWeight(sc, operation);
                    break;
                case 3:
                    handleVolume(sc, operation);
                    break;
                default:
                    System.out.println("Invalid category selected.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }

    // ---------------- LENGTH ----------------

    private static void handleLength(Scanner sc, int operation) {
        LengthUnit[] units = LengthUnit.values();
        System.out.println("Units: 1.FEET  2.INCHES  3.YARDS  4.CENTIMETERS");
        Quantity<LengthUnit> q1 = createQuantity(sc, units);
        performOperation(sc, operation, q1, units);
    }

    // ---------------- WEIGHT ----------------

    private static void handleWeight(Scanner sc, int operation) {
        WeightUnit[] units = WeightUnit.values();
        System.out.println("Units: 1.KILOGRAM  2.GRAM  3.POUND");
        Quantity<WeightUnit> q1 = createQuantity(sc, units);
        performOperation(sc, operation, q1, units);
    }

    // ---------------- VOLUME ----------------

    private static void handleVolume(Scanner sc, int operation) {
        VolumeUnit[] units = VolumeUnit.values();
        System.out.println("Units: 1.LITRE  2.MILLILITRE  3.GALLON");
        Quantity<VolumeUnit> q1 = createQuantity(sc, units);
        performOperation(sc, operation, q1, units);
    }

    // ---------------- CREATE QUANTITY ----------------

    private static <U extends IMeasurable> Quantity<U> createQuantity(
            Scanner sc, U[] units) {

        System.out.print("Choose unit: ");
        int index = Integer.parseInt(sc.nextLine()) - 1;

        if (index < 0 || index >= units.length) {
            throw new IllegalArgumentException("Invalid unit selection.");
        }

        System.out.print("Enter value: ");
        double value = Double.parseDouble(sc.nextLine());

        return new Quantity<>(value, units[index]);
    }

    // ---------------- GENERIC OPERATION HANDLER ----------------

    private static <U extends IMeasurable> void performOperation(
            Scanner sc, int operation,
            Quantity<U> q1, U[] units) {

        if (operation == 1) {

            Quantity<U> q2 = createQuantity(sc, units);
            System.out.println("Are equal? " + q1.equals(q2));

        } else if (operation == 2) {

            System.out.print("Convert to unit: ");
            int index = Integer.parseInt(sc.nextLine()) - 1;

            if (index < 0 || index >= units.length) {
                throw new IllegalArgumentException("Invalid target unit.");
            }

            Quantity<U> converted = q1.convertTo(units[index]);
            System.out.println("Converted Result: " + converted);

        } else if (operation == 3) {

            Quantity<U> q2 = createQuantity(sc, units);
            Quantity<U> result = q1.add(q2);
            System.out.println("Addition Result: " + result);

        } else if (operation == 4) {   

            Quantity<U> q2 = createQuantity(sc, units);

            System.out.println("1. Use first unit (implicit)");
            System.out.println("2. Specify target unit");
            System.out.print("Choose option: ");
            int choice = Integer.parseInt(sc.nextLine());

            if (choice == 1) {
                Quantity<U> result = q1.subtract(q2);
                System.out.println("Subtraction Result: " + result);
            } else {
                System.out.print("Choose target unit: ");
                int index = Integer.parseInt(sc.nextLine()) - 1;

                if (index < 0 || index >= units.length) {
                    throw new IllegalArgumentException("Invalid target unit.");
                }

                Quantity<U> result = q1.subtract(q2, units[index]);
                System.out.println("Subtraction Result: " + result);
            }

        } else if (operation == 5) {   

            Quantity<U> q2 = createQuantity(sc, units);
            double result = q1.divide(q2);
            System.out.println("Division Result (ratio): " + result);

        } else {
            System.out.println("Invalid operation selected.");
        }
    }
}