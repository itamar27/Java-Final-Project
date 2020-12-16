package Model.tests;

import Model.Category;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    Category category;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        category = new Category("Shopping");
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        category = null;
    }

    @org.junit.jupiter.api.Test
    void getName() {
        String expected = "Shopping";
        String actual = category.getName();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getId() {
        int expected = -1;
        int actual = category.getId();
        assertEquals(expected, actual, 0);
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        String expected = "Category={ " +
                "name='Shopping'" +
                ", id=" + -1 +
                '}';
        String actual = category.toString();
        assertEquals(expected, actual);
    }
}