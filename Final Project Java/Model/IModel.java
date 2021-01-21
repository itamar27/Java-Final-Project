package Model;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public interface IModel {

    public void createConnection() throws CostManagerException;
    public void createDB() throws CostManagerException;


    /**
     * Cost item DB execution
     *
     * API -
     * @Post (addCostItem)  -  add new cost item to the DB.
     * @Get (getCostItemBetweenDates) - get list of cost items between different dates.
     * @Get (getCategorySumBetweenDates) - get list of sums of categories between dates
     */

    public void addCostItem(CostItem item) throws CostManagerException;
    public List<CostItem> getCostItemsBetweenDates(String fromDate, String toDate) throws CostManagerException;
    public List<Pair> getCategorySumBetweenDates(String fromDate, String toDate) throws CostManagerException;

    /**
     * Category DB execution
     *
     * @Post (addCategory) - add new category to  DB.
     * @Get (getAllCategory) - get all categories from DB.s
     */

    public void addCategory(Category category) throws CostManagerException;
    public List<Category> getAllCategory() throws CostManagerException;
}
