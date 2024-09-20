package com.stockcharting.app.entity.test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.stockcharting.app.entity.StockEntity;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class StockEntityTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        // Initialize the Validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidStockEntity() {
        StockEntity stockEntity = new StockEntity(1L, "Tesla", 1000.0, "NASDAQ", "TSLA", 10, 1);

        Set<ConstraintViolation<StockEntity>> violations = validator.validate(stockEntity);

        // No violations should occur for a valid entity
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidStockEntity_NameIsBlank() {
        StockEntity stockEntity = new StockEntity(1L, "", 1000.0, "NASDAQ", "TSLA", 10, 1);

        Set<ConstraintViolation<StockEntity>> violations = validator.validate(stockEntity);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        // Assert that the violation message is related to the blank name
        ConstraintViolation<StockEntity> violation = violations.iterator().next();
        assertEquals("Name cannot be blank", violation.getMessage());
    }

    @Test
    public void testInvalidStockEntity_PriceIsNull() {
        StockEntity stockEntity = new StockEntity(1L, "Tesla", null, "NASDAQ", "TSLA", 10, 1);

        Set<ConstraintViolation<StockEntity>> violations = validator.validate(stockEntity);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        // Assert that the violation message is related to the null price
        ConstraintViolation<StockEntity> violation = violations.iterator().next();
        assertEquals("Price cannot be null", violation.getMessage());
    }

    @Test
    public void testInvalidStockEntity_PriceIsNegative() {
        StockEntity stockEntity = new StockEntity(1L, "Tesla", -1000.0, "NASDAQ", "TSLA", 10, 1);

        Set<ConstraintViolation<StockEntity>> violations = validator.validate(stockEntity);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        // Assert that the violation message is related to the negative price
        ConstraintViolation<StockEntity> violation = violations.iterator().next();
        assertEquals("Price must be positive", violation.getMessage());
    }

    @Test
    public void testInvalidStockEntity_ExchangeIsBlank() {
        StockEntity stockEntity = new StockEntity(1L, "Tesla", 1000.0, "", "TSLA", 10, 1);

        Set<ConstraintViolation<StockEntity>> violations = validator.validate(stockEntity);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        // Assert that the violation message is related to the blank exchange
        ConstraintViolation<StockEntity> violation = violations.iterator().next();
        assertEquals("Exchange cannot be blank", violation.getMessage());
    }

    @Test
    public void testInvalidStockEntity_SymbolIsBlank() {
        StockEntity stockEntity = new StockEntity(1L, "Tesla", 1000.0, "NASDAQ", "", 10, 1);

        Set<ConstraintViolation<StockEntity>> violations = validator.validate(stockEntity);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        // Assert that the violation message is related to the blank symbol
        ConstraintViolation<StockEntity> violation = violations.iterator().next();
        assertEquals("Symbol cannot be blank", violation.getMessage());
    }
}
