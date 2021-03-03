package il.ac.shenkar.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @class CostItem - This class represents cost of the user.
 * @members
 * id - the id representation for the CostItem in the DB.
 * category - the category of this cost.
 * amount - the amount that the user spent on the cost.
 * currency - the currency which the user used for the cost.
 * description - a short description of the user's cost.
 * date - the date of that cost.
 * @methods
 * setName() - set the name of the category to new one.
 * getName() - return the name of the category.
 * setId() - set the id of the category to a new one.
 * getId() - return the id of the category.
 * equals() - return true if the given category is equal to *this* category.
 * toString() - return string representation of the category.
 */

public class CostItem {

    private int id;
    private Category category;
    private double amount;
    private Currency currency;
    private String description;
    private LocalDate date;


    /**
     * This constructor builds a CostItem object from input.
     *
     * @params
     * date - The date that the cost was made.
     * description - A short text describing the cost reason of spending.
     * amount - The total amount of the cost
     * currency - The currency that was being used during the purchase time.
     * Category - An instantiation  of an object that represents the category that the Cost belonged to.
     */
    public CostItem(Category category, double amount, Currency currency, String description, String date) throws CostManagerException {

        if (amount < 0)
            throw new CostManagerException("Not a valid amount");
        setDate(createDateFromString(date));
        setDescription(description);
        setAmount(amount);
        setCurrency(currency);
        setId(-1);
        setCategory(category);
    }

    /**
     * This constructor builds a CostItem object from a database load.
     *
     * @params
     * description - A short text describing the cost reason of spending.
     * amount - The total amount of the cost
     * currency - The currency that was being used during the purchase time.
     * Category - An instantiation  of an object that represents the category that the Cost belonged to.
     * id - A variable to hold the Cost id from DB.
     * date - the date that the cost was made.
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
     *
     * This method sets the id of the category.
     * @params
     * id - should be the id of the category.
     *
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
     * All get function to class members.
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
