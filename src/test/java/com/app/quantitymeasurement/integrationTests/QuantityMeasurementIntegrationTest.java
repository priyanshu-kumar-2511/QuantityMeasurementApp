package com.app.quantitymeasurement.integrationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementIntegrationTest {

    private QuantityMeasurementController controller;
    private QuantityMeasurementDatabaseRepository repository;
    private QuantityMeasurementServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = QuantityMeasurementDatabaseRepository.getInstance();
        repository.deleteAll();

        service = new QuantityMeasurementServiceImpl(repository);
        controller = new QuantityMeasurementController(service);
    }

    @Test
    void givenEqualLengthQuantities_WhenCompared_ThenShouldReturnTrueAndSaveToDatabase() {
        QuantityDTO dto1 = new QuantityDTO(1.0, "FEET", "LengthUnit");
        QuantityDTO dto2 = new QuantityDTO(12.0, "INCHES", "LengthUnit");

        boolean result = controller.performComparison(dto1, dto2);

        assertTrue(result);

        List<QuantityMeasurementEntity> allMeasurements = repository.getAllMeasurements();
        assertNotNull(allMeasurements);
        assertEquals(1, allMeasurements.size());

        QuantityMeasurementEntity savedEntity = allMeasurements.get(0);
        assertEquals("COMPARISON", savedEntity.operation);
        assertEquals(1.0, savedEntity.thisValue);
        assertEquals("FEET", savedEntity.thisUnit);
        assertEquals(12.0, savedEntity.thatValue);
        assertEquals("INCHES", savedEntity.thatUnit);
        assertEquals(1.0, savedEntity.resultValue);
        assertEquals("BOOLEAN", savedEntity.resultUnit);
        assertEquals("BOOLEAN", savedEntity.resultMeasurementType);
    }

    @Test
    void givenTwoLengthQuantities_WhenAdded_ThenShouldReturnCorrectResultAndSaveToDatabase() {
        QuantityDTO dto1 = new QuantityDTO(2.0, "FEET", "LengthUnit");
        QuantityDTO dto2 = new QuantityDTO(24.0, "INCHES", "LengthUnit");

        QuantityDTO result = controller.performAddition(dto1, dto2);

        assertNotNull(result);
        assertEquals(4.0, result.getValue());
        assertEquals("FEET", result.getUnit());
        assertEquals("LengthUnit", result.getMeasurementType());

        List<QuantityMeasurementEntity> allMeasurements = repository.getAllMeasurements();
        assertEquals(1, allMeasurements.size());

        QuantityMeasurementEntity savedEntity = allMeasurements.get(0);
        assertEquals("ADD", savedEntity.operation);
        assertEquals(4.0, savedEntity.resultValue);
        assertEquals("FEET", savedEntity.resultUnit);
        assertEquals("LengthUnit", savedEntity.resultMeasurementType);
    }

    @Test
    void givenTwoLengthQuantitiesAndTargetUnit_WhenAdded_ThenShouldReturnCorrectTargetResultAndSaveToDatabase() {
        QuantityDTO dto1 = new QuantityDTO(2.0, "FEET", "LengthUnit");
        QuantityDTO dto2 = new QuantityDTO(24.0, "INCHES", "LengthUnit");
        QuantityDTO target = new QuantityDTO(0.0, "INCHES", "LengthUnit");

        QuantityDTO result = controller.performAddition(dto1, dto2, target);

        assertNotNull(result);
        assertEquals(48.0, result.getValue());
        assertEquals("INCHES", result.getUnit());
        assertEquals("LengthUnit", result.getMeasurementType());

        List<QuantityMeasurementEntity> allMeasurements = repository.getAllMeasurements();
        assertEquals(1, allMeasurements.size());

        QuantityMeasurementEntity savedEntity = allMeasurements.get(0);
        assertEquals("ADD", savedEntity.operation);
        assertEquals(48.0, savedEntity.resultValue);
        assertEquals("INCHES", savedEntity.resultUnit);
        assertEquals("LengthUnit", savedEntity.resultMeasurementType);
    }

    @Test
    void givenTwoLengthQuantities_WhenSubtracted_ThenShouldReturnCorrectResultAndSaveToDatabase() {
        QuantityDTO dto1 = new QuantityDTO(5.0, "FEET", "LengthUnit");
        QuantityDTO dto2 = new QuantityDTO(12.0, "INCHES", "LengthUnit");

        QuantityDTO result = controller.performSubtraction(dto1, dto2);

        assertNotNull(result);
        assertEquals(4.0, result.getValue());
        assertEquals("FEET", result.getUnit());
        assertEquals("LengthUnit", result.getMeasurementType());

        List<QuantityMeasurementEntity> allMeasurements = repository.getAllMeasurements();
        assertEquals(1, allMeasurements.size());

        QuantityMeasurementEntity savedEntity = allMeasurements.get(0);
        assertEquals("SUBTRACT", savedEntity.operation);
        assertEquals(4.0, savedEntity.resultValue);
        assertEquals("FEET", savedEntity.resultUnit);
        assertEquals("LengthUnit", savedEntity.resultMeasurementType);
    }

    @Test
    void givenTwoLengthQuantitiesAndTargetUnit_WhenSubtracted_ThenShouldReturnCorrectTargetResultAndSaveToDatabase() {
        QuantityDTO dto1 = new QuantityDTO(5.0, "FEET", "LengthUnit");
        QuantityDTO dto2 = new QuantityDTO(12.0, "INCHES", "LengthUnit");
        QuantityDTO target = new QuantityDTO(0.0, "INCHES", "LengthUnit");

        QuantityDTO result = controller.performSubtraction(dto1, dto2, target);

        assertNotNull(result);
        assertEquals(48.0, result.getValue());
        assertEquals("INCHES", result.getUnit());
        assertEquals("LengthUnit", result.getMeasurementType());

        List<QuantityMeasurementEntity> allMeasurements = repository.getAllMeasurements();
        assertEquals(1, allMeasurements.size());

        QuantityMeasurementEntity savedEntity = allMeasurements.get(0);
        assertEquals("SUBTRACT", savedEntity.operation);
        assertEquals(48.0, savedEntity.resultValue);
        assertEquals("INCHES", savedEntity.resultUnit);
        assertEquals("LengthUnit", savedEntity.resultMeasurementType);
    }

    @Test
    void givenLengthQuantityAndTargetUnit_WhenConverted_ThenShouldReturnCorrectResultAndSaveToDatabase() {
        QuantityDTO dto1 = new QuantityDTO(1.0, "FEET", "LengthUnit");
        QuantityDTO target = new QuantityDTO(0.0, "INCHES", "LengthUnit");

        QuantityDTO result = controller.performConversion(dto1, target);

        assertNotNull(result);
        assertEquals(12.0, result.getValue());
        assertEquals("INCHES", result.getUnit());
        assertEquals("LengthUnit", result.getMeasurementType());

        List<QuantityMeasurementEntity> allMeasurements = repository.getAllMeasurements();
        assertEquals(1, allMeasurements.size());

        QuantityMeasurementEntity savedEntity = allMeasurements.get(0);
        assertEquals("CONVERSION", savedEntity.operation);
        assertEquals(12.0, savedEntity.resultValue);
        assertEquals("INCHES", savedEntity.resultUnit);
        assertEquals("LengthUnit", savedEntity.resultMeasurementType);
    }

    @Test
    void givenTwoLengthQuantities_WhenDivided_ThenShouldReturnCorrectResult() {
        QuantityDTO dto1 = new QuantityDTO(24.0, "INCHES", "LengthUnit");
        QuantityDTO dto2 = new QuantityDTO(12.0, "INCHES", "LengthUnit");

        QuantityDTO result = controller.performDivision(dto1, dto2);

        assertNotNull(result);
        assertEquals(2.0, result.getValue());

        // Current project design returns QuantityDTO with same unit,
        // though logically division result should be unitless.
        assertEquals("INCHES", result.getUnit());
        assertEquals("LengthUnit", result.getMeasurementType());
    }

    @Test
    void givenMultipleOperations_WhenSaved_ThenRepositoryCountShouldIncrease() {
        QuantityDTO dto1 = new QuantityDTO(1.0, "FEET", "LengthUnit");
        QuantityDTO dto2 = new QuantityDTO(12.0, "INCHES", "LengthUnit");
        QuantityDTO target = new QuantityDTO(0.0, "INCHES", "LengthUnit");

        controller.performComparison(dto1, dto2);
        controller.performConversion(dto1, target);
        controller.performAddition(dto1, dto2);
        controller.performSubtraction(new QuantityDTO(5.0, "FEET", "LengthUnit"), dto2);

        int totalCount = repository.getTotalCount();

        assertEquals(4, totalCount);
    }
}