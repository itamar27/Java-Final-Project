package ViewModel;


import Model.Category;
import Model.IModel;
import View.IView;
import Model.CostItem;

public interface IViewModel {

    public void setView(IView view);
    public void setModel(IModel model);

    public void addCategory(Category category);
    public void addCostItem(CostItem item);
    public void getCostsForChart(String dateFrom, String dateTo);
    public void getCostsForPie(String dateFrom, String dateTo);

    public String[] getCategories();
    public String[] getCurrencies();

}


