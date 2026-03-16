package com.app.quantitymeasurement.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository;

public class QuantityMeasurementCacheRepositoryTest {

    private QuantityMeasurementCacheRepository repository;

    @BeforeEach
    void setUp() {
        File file = new File(QuantityMeasurementCacheRepository.FILE_NAME);
        if (file.exists()) {
            file.delete();
        }

        repository = new QuantityMeasurementCacheRepository();
    }

    @Test
    void givenGetInstance_WhenCalledMultipleTimes_ThenShouldReturnSameInstance() {
        QuantityMeasurementCacheRepository repo1 = QuantityMeasurementCacheRepository.getInstance();
        QuantityMeasurementCacheRepository repo2 = QuantityMeasurementCacheRepository.getInstance();

        assertSame(repo1, repo2);
    }

    @Test
    void givenEntity_WhenSaved_ThenShouldBeStoredInCache() {
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
        assertEquals(entity, result.get(0));
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
        assertTrue(result.contains(entity1));
        assertTrue(result.contains(entity2));
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
        assertTrue(result.contains(entity1));
        assertTrue(result.contains(entity3));
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
        assertTrue(result.contains(entity1));
        assertTrue(result.contains(entity3));
    }
}