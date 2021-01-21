package ViewModel;


import Model.Category;
import Model.IModel;
import View.IView;
import Model.CostItem;


/**
 *  Interface to set functionality for every ViewModel that will implement it.
 *
 */
public interface IViewModel {

    public void setView(IView view);
    public void setModel(IModel model);

    public void addCategory(Category category);
    public void addCostItem(CostItem item);
    public void getCostsForTable(String dateFrom, String dateTo);
    public void getCostsForPieChart(String dateFrom, String dateTo);

    public String[] getCategories();
    public String[] getCurrencies();

}


