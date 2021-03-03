package il.ac.shenkar.model;

import java.util.Objects;

/**
 *
 * @class
 * Category - This class represents categories that the user wants to define for his costs.
 *
 * @member
 * name - the name of the category.
 * id - the id representation for the category in the DB.
 *
 * @method
 * setName - set the name of the category to new one.
 * getName - return the name of the category.
 * setId - set the id of the category to a new one.
 * getId - return the id of the category.
 * equals - return true if the given category is equal to *this* category.
 * toString - return string representation of the category.
 *
 */

public class Category {

    private String name;
    private int id;

    /**
     *
     * This constructor builds a Category class from a user input.
     * @params name - should be the name of the category
     *
     */

    public Category(String name) {
        setName(name);
        setId(-1);
    }

    /**
     *
     * This constructor builds a Category class from a database load of values.
     * @params
     * name - should be the name of the category.
     * id - the id of the category that is read from the data base.
     *
     */
    public Category(String name, int id){
        setName(name);
        setId(id);
    }

    /**
     *
     * This method sets the name of the category.
     * @params
     * name - should be the name of the category.
     *
     */
    public void setName(String name) {
        this.name = name;
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

    /**
     *
     * This method returns the name of the category.
     *
     */
    public String getName() {
        return name;
    }

    /**
     *
     * This method returns the id of the category.
     *
     */
    public int getId() {
        return id;
    }


    /**
     *
     * This method returns true if the given object as parameter is equal to our category.
     * @params
     * o - Object to check if is equal to this.
     *
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id == category.id && Objects.equals(name, category.name);
    }


    /**
     *
     * This method returns the string representation of the category.
     *
     */
    @Override
    public String toString() {
        return name;
    }
}
