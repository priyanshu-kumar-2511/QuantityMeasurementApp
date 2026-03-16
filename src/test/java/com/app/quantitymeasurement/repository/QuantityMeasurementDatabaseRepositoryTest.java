package com.app.quantitymeasurement.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;

public class QuantityMeasurementDatabaseRepositoryTest {

    private QuantityMeasurementDatabaseRepository repository;

    @BeforeEach
    void setUp() {
        repository = QuantityMeasurementDatabaseRepository.getInstance();
        repository.deleteAll();
    }

    @Test
    void givenGetInstance_WhenCalledMultipleTimes_ThenShouldReturnSameInstance() {
        QuantityMeasurementDatabaseRepository repo1 = QuantityMeasurementDatabaseRepository.getInstance();
        QuantityMeasurementDatabaseRepository repo2 = QuantityMeasurementDatabaseRepository.getInstance();

        assertSame(repo1, repo2);
    }

    @Test
    void givenEntity_WhenSaved_ThenShouldBeStoredInDatabase() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                2.0, "FEET", "LengthUnit",
                24.0, "INCHES", "LengthUnit",
                "ADD",
                4.0, "FEET", "LengthUnit"
        );

        repository.save(entity);

        List<QuantityMeasurementEntity> result = repository.getAllMeasurements();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ADD", result.get(0).operation);
        assertEquals(4.0, result.get(0).resultValue);
        assertEquals("FEET", result.get(0).resultUnit);
        assertEquals("LengthUnit", result.get(0).resultMeasurementType);
    }

    @Test
    void givenMultipleEntities_WhenSaved_ThenGetAllMeasurementsShouldReturnAllEntities() {
        QuantityMeasurementEntity entity1 = new QuantityMeasurementEntity(
                1.0, "FEET", "LengthUnit",
                12.0, "INCHES", "LengthUnit",
                "COMPARISON",
                1.0, "BOOLEAN", "BOOLEAN"
        );

        QuantityMeasurementEntity entity2 = new QuantityMeasurementEntity(
                2.0, "LITRE", "VolumeUnit",
                500.0, "MILLILITRE", "VolumeUnit",
                "ADD",
                2.5, "LITRE", "VolumeUnit"
        );

        repository.save(entity1);
        repository.save(entity2);

        List<QuantityMeasurementEntity> result = repository.getAllMeasurements();

        assertEquals(2, result.size());
    }

    @Test
    void givenSavedEntities_WhenGetTotalCountCalled_ThenShouldReturnCorrectCount() {
        repository.save(new QuantityMeasurementEntity(
                2.0, "FEET", "LengthUnit",
                24.0, "INCHES", "LengthUnit",
                "ADD",
                4.0, "FEET", "LengthUnit"
        ));

        repository.save(new QuantityMeasurementEntity(
                5.0, "FEET", "LengthUnit",
                12.0, "INCHES", "LengthUnit",
                "SUBTRACT",
                4.0, "FEET", "LengthUnit"
        ));

        assertEquals(2, repository.getTotalCount());
    }

    @Test
    void givenSavedEntities_WhenGetMeasurementsByOperationCalled_ThenShouldReturnMatchingEntitiesOnly() {
        QuantityMeasurementEntity entity1 = new QuantityMeasurementEntity(
                2.0, "FEET", "LengthUnit",
                24.0, "INCHES", "LengthUnit",
                "ADD",
                4.0, "FEET", "LengthUnit"
        );

        QuantityMeasurementEntity entity2 = new QuantityMeasurementEntity(
                5.0, "FEET", "LengthUnit",
                12.0, "INCHES", "LengthUnit",
                "SUBTRACT",
                4.0, "FEET", "LengthUnit"
        );

        QuantityMeasurementEntity entity3 = new QuantityMeasurementEntity(
                1.0, "FEET", "LengthUnit",
                12.0, "INCHES", "LengthUnit",
                "ADD",
                2.0, "FEET", "LengthUnit"
        );

        repository.save(entity1);
        repository.save(entity2);
        repository.save(entity3);

        List<QuantityMeasurementEntity> result = repository.getMeasurementsByOperation("ADD");

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(entity -> "ADD".equals(entity.operation)));
    }

    @Test
    void givenSavedEntities_WhenGetMeasurementsByTypeCalled_ThenShouldReturnMatchingMeasurementTypeOnly() {
        QuantityMeasurementEntity entity1 = new QuantityMeasurementEntity(
                2.0, "FEET", "LengthUnit",
                24.0, "INCHES", "LengthUnit",
                "ADD",
                4.0, "FEET", "LengthUnit"
        );

        QuantityMeasurementEntity entity2 = new QuantityMeasurementEntity(
                2.0, "LITRE", "VolumeUnit",
                500.0, "MILLILITRE", "VolumeUnit",
                "ADD",
                2.5, "LITRE", "VolumeUnit"
        );

        QuantityMeasurementEntity entity3 = new QuantityMeasurementEntity(
                5.0, "YARDS", "LengthUnit",
                3.0, "FEET", "LengthUnit",
                "SUBTRACT",
                4.0, "YARDS", "LengthUnit"
        );

        repository.save(entity1);
        repository.save(entity2);
        repository.save(entity3);

        List<QuantityMeasurementEntity> result = repository.getMeasurementsByType("LengthUnit");

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(entity -> "LengthUnit".equals(entity.thisMeasurementType)));
    }

    @Test
    void givenSavedEntities_WhenDeleteAllCalled_ThenDatabaseShouldBeEmpty() {
        repository.save(new QuantityMeasurementEntity(
                2.0, "FEET", "LengthUnit",
                24.0, "INCHES", "LengthUnit",
                "ADD",
                4.0, "FEET", "LengthUnit"
        ));

        repository.save(new QuantityMeasurementEntity(
                5.0, "FEET", "LengthUnit",
                12.0, "INCHES", "LengthUnit",
                "SUBTRACT",
                4.0, "FEET", "LengthUnit"
        ));

        assertEquals(2, repository.getTotalCount());

        repository.deleteAll();

        assertEquals(0, repository.getTotalCount());
        assertTrue(repository.getAllMeasurements().isEmpty());
    }
}