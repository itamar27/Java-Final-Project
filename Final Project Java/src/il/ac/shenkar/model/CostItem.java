/**
 * @author Barak Daniel - 204594329
 * @author Itamar Yarden - 204289987
 */

package il.ac.shenkar.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * This class represent the main object of the system,
 * it hold all the important data the DB has to save inorder to display to the user.
 * This class support functionality of creating a new CostItem object,
 * setting the values, string interpolation, and comparing between the different cost items.
 */
public class CostItem {

    private int id;
    private Category category;
    private double amount;
    private Currency currency;
    private String description;
    private LocalDate date;

    /**
     * This is the main constructor where we set the values of all the members of the class
     * @param id
     * @param category
     * @param amount
     * @param currency
     * @param description
     * @param date
     * @throws CostManagerException
     */
    public CostItem(int id, Category category, double amount, Currency currency, String description, String date) throws CostManagerException {
        setDate(createDateFromString(date));
        setDescription(description);
        setAmount(amount);
        setCurrency(currency);
        setId(id);
        setCategory(category);
    }

    /**
     * This constructor don't need an id value, which means the cost hasn't been stored in the DB yet.
     * It uses the main constructor with value of -1 for id.
     * @param category
     * @param amount
     * @param currency
     * @param description
     * @param date
     * @throws CostManagerException
     */
    public CostItem(Category category, double amount, Currency currency, String description, String date) throws CostManagerException {
        this(-1, category, amount, currency, description, date);
    }

    /**
     * All set methods of the members
     */

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setAmount(double amount) throws CostManagerException {
        if (amount < 0) {
            throw new CostManagerException("Not a valid amount");
        }
        this.amount = amount;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * All get methods of the members.
     */

    public int getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    /**
     * In order to work with the right format of the date member,
     * this private method parsing the sent date in the right format.
     * @param dateTime
     * @return
     */
    private LocalDate createDateFromString(String dateTime) {

        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Parsing the date
        LocalDate date = LocalDate.parse(dateTime, inputFormat);

        return date;
    }

    /**
     * Over riding class object methods.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CostItem costItem = (CostItem) o;
        return id == costItem.id &&
                Double.compare(costItem.amount, amount) == 0 &&
                category.equals(costItem.category) &&
                currency == costItem.currency &&
                description.equals(costItem.description) &&
                date.equals(costItem.date);
    }

    @Override
    public String toString() {
        return category.toString() + " with amount of " + amount;
    }
}
