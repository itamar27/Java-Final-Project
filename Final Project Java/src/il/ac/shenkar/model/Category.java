/**
 * @author Barak Daniel - 204594329
 * @author Itamar Yarden - 204289987
 */

package il.ac.shenkar.model;

import java.util.Objects;

/**
 * This class represent a category for the CostItems.
 * It only has name as value, and id for the DB as members.
 */
public class Category {
    private String name;
    private int id;

    /**
     * The main constructor for Category, sets both name and id
     * @param name
     * @param id
     */
    public Category(String name, int id){
        setName(name);
        setId(id);
    }

    /**
     * This constructor isn't getting id as parameter, which mean it hasn't been stored
     * in the DB yet.
     * This constructor uses the main constructor with value of -1 as id.
     * @param name
     */
    public Category(String name) {
        this(name, -1);
    }

    /**
     * Set methods for the class members
     */
    public void setName(String name) {
        this.name = name;
    }
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get methods for the class members
     */
    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }

    /**
     * Overriding methods
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id == category.id && Objects.equals(name, category.name);
    }
    @Override
    public String toString() {
        return name;
    }
}
