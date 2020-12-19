package Model.tests;

import Model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DerbyDBModelTest {

    DerbyDBModel db = null;

    @BeforeEach
    void setUp() throws CostManagerException {
        db = new DerbyDBModel();
    }

    @AfterEach
    void tearDown() {
        db = null;
    }

    @Test
    void derbyDBModelRelease() {
        String expectedException = "Error closing DB";

        try {
            db.DerbyDBModelRelease();
        } catch (CostManagerException e) {
            assertEquals(e.getMessage(), expectedException);
        }
    }

    @Test
    void createConnection() {
        String expectedException = "Could not create a connection to derbyDB";

        try {
            db.createConnection();
        } catch (CostManagerException e) {
            assertEquals(e.getMessage(), expectedException);
        }
    }

    @Test
    void createDB() {
        String expectedException = "Error with creating a table statement";

        try {
            db.createConnection();
        } catch (CostManagerException e) {
            assertEquals(e.getMessage(), expectedException);
        }
    }

    @Test
    void addCostItem() {
        String expectedException = "Could not add new CostItem";

        try {
            db.createConnection();
        } catch (CostManagerException e) {
            assertEquals(e.getMessage(), expectedException);
        }
    }

    @Test
    void getCostItemsBetweenDates() throws CostManagerException {
        List<CostItem> expectedList = new ArrayList<>();
        expectedList.add(new CostItem(2, new Category("Shopping"), 4.0, Currency.NZD, "Very cheap", "2021-12-14"));
        expectedList.add(new CostItem(102, new Category("Shopping"), 4.0, Currency.NZD, "Very cheap", "2021-12-14"));
        expectedList.add(new CostItem(302, new Category("Shopping"), 4.0, Currency.NZD, "Very cheap", "2021-12-14"));
        expectedList.add(new CostItem(402, new Category("Food"), 4.0, Currency.NZD, "Very cheap", "2021-12-14"));
        List<CostItem> actualList = db.getCostItemsBetweenDates("2021-12-14", "2021-12-14");

        assertEquals(expectedList, actualList);
    }

    @Test
    void getCategorySumBetweenDates() throws CostManagerException {
        List<Pair> expectedList = new ArrayList<>();
        expectedList.add(new Pair("Food", 24.2));
        expectedList.add(new Pair("Shopping", 76.8));
        List<Pair> actualList = db.getCategorySumBetweenDates("1990-01-01", "2021-12-31");

        assertEquals(expectedList, actualList);
    }

    @Test
    void addCategory() {
        String expectedException = "Could not add new category";
        try {
            db.addCategory(new Category("Shopping"));
        } catch (CostManagerException e) {
            assertEquals(e.getMessage(), expectedException);
        }
    }
}