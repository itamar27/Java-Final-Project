/**
 * @author Barak Daniel - 204594329
 * @author Itamar Yarden - 204289987
 */

package il.ac.shenkar.model;

/**
 * This class is used to hold a name and amount of pair of values.
 * We use it to pass the data from and to the Model.
 * In order to provide the required functionality we override toString() and equals()
 */
public class Pair {
    public String name;
    public Double amount;

    public Pair(String name, Double amount) {
        setName(name);
        setAmount(amount);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "name = " + name +
                ", Sum amount = " + amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return name.equals(pair.name) &&
                amount.equals(pair.amount);
    }
}

