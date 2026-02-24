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
                case 1 -> handleLength(sc, operation);
                case 2 -> handleWeight(sc, operation);
                case 3 -> handleVolume(sc, operation);
                default -> System.out.println("Invalid category selected.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric input.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }

    // ---------------- LENGTH ----------------

    private static void handleLength(Scanner sc, int operation) {
        LengthUnit[] units = LengthUnit.values();
        printUnits(units);
        Quantity<LengthUnit> q1 = createQuantity(sc, units);
        performOperation(sc, operation, q1, units);
    }

    // ---------------- WEIGHT ----------------

    private static void handleWeight(Scanner sc, int operation) {
        WeightUnit[] units = WeightUnit.values();
        printUnits(units);
        Quantity<WeightUnit> q1 = createQuantity(sc, units);
        performOperation(sc, operation, q1, units);
    }

    // ---------------- VOLUME ----------------

    private static void handleVolume(Scanner sc, int operation) {
        VolumeUnit[] units = VolumeUnit.values();
        printUnits(units);
        Quantity<VolumeUnit> q1 = createQuantity(sc, units);
        performOperation(sc, operation, q1, units);
    }

    // ---------------- PRINT UNITS GENERIC ----------------

    private static <U extends IMeasurable> void printUnits(U[] units) {
        System.out.println("Available Units:");
        for (int i = 0; i < units.length; i++) {
            System.out.println((i + 1) + ". " + units[i].getUnitName());
        }
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

        switch (operation) {

            case 1 -> {
                Quantity<U> q2 = createQuantity(sc, units);
                System.out.println("Are equal? " + q1.equals(q2));
            }

            case 2 -> {
                System.out.print("Convert to unit: ");
                int index = Integer.parseInt(sc.nextLine()) - 1;

                if (index < 0 || index >= units.length)
                    throw new IllegalArgumentException("Invalid target unit.");

                Quantity<U> converted = q1.convertTo(units[index]);
                System.out.println("Converted Result: " + converted);
            }

            case 3 -> {
                Quantity<U> q2 = createQuantity(sc, units);
                Quantity<U> result = q1.add(q2);
                System.out.println("Addition Result: " + result);
            }

            case 4 -> {
                Quantity<U> q2 = createQuantity(sc, units);

                System.out.println("1. Use first unit (implicit)");
                System.out.println("2. Specify target unit");
                System.out.print("Choose option: ");

                int choice = Integer.parseInt(sc.nextLine());

                if (choice == 1) {
                    Quantity<U> result = q1.subtract(q2);
                    System.out.println("Subtraction Result: " + result);
                } else if (choice == 2) {

                    System.out.print("Choose target unit: ");
                    int index = Integer.parseInt(sc.nextLine()) - 1;

                    if (index < 0 || index >= units.length)
                        throw new IllegalArgumentException("Invalid target unit.");

                    Quantity<U> result = q1.subtract(q2, units[index]);
                    System.out.println("Subtraction Result: " + result);
                } else {
                    System.out.println("Invalid option selected.");
                }
            }

            case 5 -> {
                Quantity<U> q2 = createQuantity(sc, units);
                double result = q1.divide(q2);
                System.out.println("Division Result (ratio): " + result);
            }

            default -> System.out.println("Invalid operation selected.");
        }
    }
}