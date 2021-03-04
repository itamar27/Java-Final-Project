/**
 * @author Barak Daniel - 204594329
 * @author Itamar Yarden - 204289987
 */

package il.ac.shenkar.model.tests;

import il.ac.shenkar.model.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for all the Category class functionality
 */
class CategoryTest {

    Category category;

    @BeforeEach
    void setUp() {
        category = new Category("Shopping");
    }

    @AfterEach
    void tearDown() {
        category = null;
    }

    @Test
    void getName() {
        String expected = "Shopping";
        String actual = category.getName();
        assertEquals(expected, actual);
    }

    @Test
    void getId() {
        int expected = -1;
        int actual = category.getId();
        assertEquals(expected, actual, 0);
    }

    @Test
    void testToString() {
        String expected = "Category={ " +
                "name='Shopping'" +
                ", id=" + -1 +
                '}';
        String actual = category.toString();
        assertEquals(expected, actual);
    }
}