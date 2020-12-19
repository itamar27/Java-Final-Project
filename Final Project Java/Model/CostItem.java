package Model;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class CostItem {

    private int id;
    private Category category;
    private double amount;
    private Currency currency;
    private String description;
    private LocalDate date;


    /**
     *
     * This constructor builds a CostItem object from input.
     * @params description - A short text describing the cost reason of spending.
     *
     * @params  amount - The total amount of the cost
     *
     * @params currency - The currency that was being used during the purchase time.
     *
     * @params Category - An instantiation  of an object that represents the category that
     *                    the Cost belonged to.
     */
    public CostItem(Category category, double amount, Currency currency,  String description,String date) throws CostManagerException{

        if(amount < 0)
            throw new CostManagerException("Not a valid amount");
        this.date = createDateFromString(date);
        this.description = description;
        this.amount = amount;
        this.currency = currency;
        this.id  = -1;
        this.category = category;
    }

    /**
     *
     * This constructor builds a CostItem object from a database load.
     * @params description - A short text describing the cost reason of spending.
     *
     * @params  amount - The total amount of the cost
     *
     * @params currency - The currency that was being used during the purchase time.
     *
     * @params Category - An instantiation  of an object that represents the category that
     *                    the Cost belonged to.
     *
     * @params id - A variable to hold the Cost id from DB.
     */

    public CostItem(int id, Category category, double amount, Currency currency,  String description,String date) throws CostManagerException {

        if(amount < 0)
            throw new CostManagerException("Not a valid amount");
        this.date = createDateFromString(date);
        this.description = description;
        this.amount= amount;
        this.currency = currency;
        this.id  = id;
        this.category = category;
    }

    /**
     *
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

    public LocalDate getDate() {return date;}

    private LocalDate createDateFromString(String dateTime){

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
        return "CostItem {" +
                "id=" + id +
                "," + category +
                ", amount=" + amount +
                ", currency=" + currency +
                ", description='" + description +
                "', date="+ date +
                '}';
    }
}
