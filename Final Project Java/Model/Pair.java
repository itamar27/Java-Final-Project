package Model;

/*
 * Pair class for return values
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

