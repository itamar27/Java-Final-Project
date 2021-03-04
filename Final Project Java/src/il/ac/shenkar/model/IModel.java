/**
 * @author Barak Daniel - 204594329
 * @author Itamar Yarden - 204289987
 */

package il.ac.shenkar.model;

import java.util.List;

/**
 * IModel interface defines the method that needs to be implemented for a model.
 * This model interface is part of the MVVM architecture, every method will
 * be provided to the Model in order to work with the ViewModel.
 */
public interface IModel {
    //Methods to create db and connection to it
    public void createConnection() throws CostManagerException;
    public void createDB() throws CostManagerException;

    //Methods for cost items queries from db
    public void addCostItem(CostItem item) throws CostManagerException;
    public List<CostItem> getCostItemsBetweenDates(String fromDate, String toDate) throws CostManagerException;
    public List<Pair> getCategorySumBetweenDates(String fromDate, String toDate) throws CostManagerException;

    //Methods for categories queries from db
    public void addCategory(Category category) throws CostManagerException;
    public List<Category> getAllCategory() throws CostManagerException;
}
