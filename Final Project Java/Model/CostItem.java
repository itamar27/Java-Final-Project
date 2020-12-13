package Model;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CostItem {

    private int id;
    private Category category;
    private double sum;
    private Currency currency;
    private String description;
    private LocalDate date;


    /**
     *
     * This constructor builds a CostItem object from input.
     * @params description - A short text describing the cost reason of spending.
     *
     * @params  sum - The total sum of the cost
     *
     * @params currency - The currency that was being used during the purchase time.
     *
     * @params Category - An instantiation  of an object that represents the category that
     *                    the Cost belonged to.
     */
    public CostItem(String description, double sum, Currency currency, Category category, LocalDate date) throws CostManagerException{

        if(sum < 0)
            throw new CostManagerException("Not a valid sum");
        this.date = date;
        this.description = description;
        this.sum = sum;
        this.currency = currency;
        this.id  = -1;
        this.category = category;
    }

    /**
     *
     * This constructor builds a CostItem object from a database load.
     * @params description - A short text describing the cost reason of spending.
     *
     * @params  sum - The total sum of the cost
     *
     * @params currency - The currency that was being used during the purchase time.
     *
     * @params Category - An instantiation  of an object that represents the category that
     *                    the Cost belonged to.
     *
     * @params id - A variable to hold the Cost id from DB.
     */

    public CostItem(String description, double sum, Currency currency, Category category, int id, LocalDate date) throws CostManagerException {

        if(sum < 0)
            throw new CostManagerException("Not a valid sum");
        this.date = date;
        this.description = description;
        this.sum = sum;
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

    public double getSum() {
        return sum;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getDescription() {
        return description;
    }

    /**
     *
     * Over riding class object methods.
     */

    @Override
    public String toString() {


        return "CostItem {" +
                "id=" + id +
                ", category=" + category +
                ", sum=" + sum +
                ", currency=" + currency +
                ", description='" + description +
                ", date="+ date +
                '}';
    }
}
