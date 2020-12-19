package Model;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public interface IModel {
    /*
     * Pair class for return values
     */

    public class Pair {

        public String name;
        public Double amount;

        public Pair(String name, Double amount) {
            this.name = name;
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


    /*
     * DB general methods
     */

    public void createConnection() throws CostManagerException;

    public void createDB() throws CostManagerException, SQLException;


    /*
     * Cost item DB execution
     */

    public void addCostItem(CostItem item) throws CostManagerException;

    public List<CostItem> getCostItemsBetweenDates(String fromDate, String toDate) throws CostManagerException;

    public List<Pair> getCategorySumBetweenDates(String fromDate, String toDate) throws CostManagerException;

    /*
     * Category DB execution
     */

    public void addCategory(Category category) throws CostManagerException;

    public List<Category> getAllCategory() throws CostManagerException;
}
