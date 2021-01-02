package View;


import ViewModel.IViewModel;

import java.util.Map;

public interface IView {

    //will be implemented after the ViewModel implementation
    //public void displayPieChart(Map map);
    //public void displayCostItemTable(CostItem[] cs);

    public void setViewModel(IViewModel vm);

}