package il.ac.shenkar.ViewModel;


import il.ac.shenkar.Model.Category;
import il.ac.shenkar.Model.IModel;
import il.ac.shenkar.View.IView;
import il.ac.shenkar.Model.CostItem;

import java.util.concurrent.ExecutorService;


/**
 *  Interface to set functionality for every ViewModel that will implement it.
 *
 */
public interface IViewModel {

    public void setPool(ExecutorService pool);
    public void setView(IView view);
    public void setModel(IModel model);

    public void addCategory(Category category);
    public void addCostItem(CostItem item);
    public void getCostsForTable(String dateFrom, String dateTo);
    public void getCostsForPieChart(String dateFrom, String dateTo);

    public void getCategories();
    public void getCurrencies();

}


