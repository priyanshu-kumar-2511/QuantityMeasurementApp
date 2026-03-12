package com.quantitymeasurementapp.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.quantitymeasurementapp.service.QuantityMeasurementServiceImpl;

class ControllerTest {

    @Test
    void shouldCreateControllerInstance() {

        QuantityMeasurementServiceImpl service =
                new QuantityMeasurementServiceImpl();

        QuantityMeasurementController controller =
                new QuantityMeasurementController(service);

        assertNotNull(controller);
    }
}