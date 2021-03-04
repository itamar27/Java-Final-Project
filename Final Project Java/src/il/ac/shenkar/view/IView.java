/**
 * @author Barak Daniel - 204594329
 * @author Itamar Yarden - 204289987
 */

package il.ac.shenkar.view;


import il.ac.shenkar.model.CostItem;
import il.ac.shenkar.viewmodel.IViewModel;

import java.util.List;

/**
 * IView interface defines the method that needs to be implemented for a view.
 * This view interface is part of the MVVM architecture, every method will
 * be provided to the ViewModel in order to work with the View.
 */
public interface IView {

    public void displayCostItemTable(List<CostItem> cs);
    public void displayCategoriesChart(String[] catNames, double[] sums);
    public void displayCategoriesSelect(String[] catNames);
    public void displayCurrenciesSelect(String[] currencies);

    public void showMessage(String message);
    public void setViewModel(IViewModel vm);

}