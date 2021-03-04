/**
 * @author Barak Daniel - 204594329
 * @author Itamar Yarden - 204289987
 */

package il.ac.shenkar.model.tests;

import il.ac.shenkar.model.Category;
import il.ac.shenkar.model.CostItem;
import il.ac.shenkar.model.CostManagerException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import il.ac.shenkar.model.Currency;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for all the CostItem class functionality
 */
class CostItemTest {

    private CostItem costItem = null;
    private Category category = null;

    @BeforeEach
    void setUp() throws CostManagerException {
        category = new Category("shopping", 1);
        costItem = new CostItem(category, 550, Currency.ILS, "shoe shopping", "2020-11-12");
    }

    @AfterEach
    void tearDown() {
        category = null;
        costItem = null;
    }

    @Test
    void getId() {
        int expected = -1;
        int actual = costItem.getId();
        assertEquals(expected, actual);
    }

    @Test
    void getCategory() {
        Category expected = new Category("shopping", 1);
        Category actual = costItem.getCategory();
        assertEquals(expected, actual);

    }

    @Test
    void getAmount() {
        double expected = 550;
        double actual  = costItem.getAmount();
        assertEquals(expected, actual);
    }

    @Test
    void getCurrency() {
        assertEquals(Currency.ILS, costItem.getCurrency());
    }

    @Test
    void getDescription() {
        String expected = "shoe shopping";
        String actual = costItem.getDescription();
        assertEquals(expected, actual);
    }

    @Test
    void getDate() {

        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Parsing the date
        LocalDate expected = LocalDate.parse("2020-11-12", inputFormat);
        LocalDate actual = costItem.getDate();

        assertEquals(expected,actual);
    }

    @Test
    void testToString() {

        String expected = "CostItem {" +
                "id=-1"  +
                "," + "Category={ " +
                "name='shopping'" +
                ", id=" + 1 +
                '}' +
                ", amount=550.0"  +
                ", currency=ILS" +
                ", description='shoe shopping'" +
                ", date=2020-11-12" +
                '}';
        String actual = costItem.toString();
        assertEquals(expected,actual);
    }
}