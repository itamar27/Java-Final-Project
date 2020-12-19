package Model.tests;

import Model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Model.Currency;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CostItemTest {

    private CostItem costItem = null;
    private Category category = null;

    @BeforeEach
    void setUp() throws CostManagerException {
        category = new Category("Shopping", 1);
        costItem = new CostItem(category, 550, Currency.ILS, "Shoe shopping", "2020-11-12");
    }

    @AfterEach
    void tearDown() {
        category = null;
        costItem = null;
    }

    @Test
    void getId() {
    }

    @Test
    void getCategory() {
    }

    @Test
    void getAmount() {
    }

    @Test
    void getCurrency() {
    }

    @Test
    void getDescription() {
    }

    @Test
    void getDate() {
    }

    @Test
    void testToString() {
    }
}