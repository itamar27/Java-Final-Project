package Model;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public interface IModel {

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
