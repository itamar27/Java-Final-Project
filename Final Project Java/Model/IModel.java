package Model;

import java.sql.SQLException;

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
    public void deleteCostItem(CostItem item) throws CostManagerException;

    /*
     * Category DB execution
     */

    public void addCategory(Category category) throws CostManagerException;
    public Category[] getAllCategory() throws CostManagerException;
}
