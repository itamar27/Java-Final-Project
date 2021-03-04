package il.ac.shenkar.viewmodel;


import il.ac.shenkar.model.Category;
import il.ac.shenkar.model.IModel;
import il.ac.shenkar.view.IView;
import il.ac.shenkar.model.CostItem;

import java.util.concurrent.ExecutorService;


/**
 * IViewModel interface defines the method that needs to be implemented for a ViewModel.
 * This viewmodel interface is part of the MVVM architecture, every method will
 * be provided to the View in order to work with the Model.
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


