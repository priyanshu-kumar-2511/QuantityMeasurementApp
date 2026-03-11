package com.quantitymeasurementapp;

import com.quantitymeasurementapp.controller.QuantityMeasurementController;
import com.quantitymeasurementapp.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        QuantityMeasurementServiceImpl service =
                new QuantityMeasurementServiceImpl();

        QuantityMeasurementController controller =
                new QuantityMeasurementController(service);

        controller.startApplication();
    }
}