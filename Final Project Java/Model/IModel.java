package Model;

import java.sql.SQLException;
import java.util.List;

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
    public CostItem[] getCostItemsBetweenDates(String fromDate, String toDate) throws CostManagerException;

    /*
     * Category DB execution
     */

    public void addCategory(Category category) throws CostManagerException;
    public List<Category> getAllCategory() throws CostManagerException;
}
