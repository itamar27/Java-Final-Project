package View;


import Model.CostItem;
import ViewModel.IViewModel;

import java.util.List;

public interface IView {

    //will be implemented after the ViewModel implementation


    //public void displayPieChart();

    public void displayCostItemTable(List<CostItem> cs);
    public void showMessage(String message);
    public void setViewModel(IViewModel vm);

}