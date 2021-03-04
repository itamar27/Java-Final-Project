/**
 * @author Barak Daniel - 204594329
 * @author Itamar Yarden - 204289987
 */

package il.ac.shenkar.viewmodel;

import il.ac.shenkar.view.IView;
import il.ac.shenkar.model.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class is used to get data from the IModel and then update the IView with data we got.
 * In order to keep the View working without interupting it, we use a ExcutorService and for each
 * method we will submit a new thread into the pool.
 */
public class ViewModel implements IViewModel {
    private IModel model;
    private IView view;
    private ExecutorService pool;

    /**
     * Sets the ExecutorService to work with 10 threads.
     */
    public ViewModel() {
        setPool(Executors.newFixedThreadPool(10));
    }

    @Override
    public void setPool(ExecutorService pool) { this.pool = pool; }

    @Override
    public void setView(IView view) {
        this.view = view;
    }

    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

    /**
     * When called, ask the IModel to add a new category to our db.
     * In case of either success or failure the user will be displayed with
     * a relevant message.
     * @param category - the category name to add to our db.
     */
    @Override
    public void addCategory(Category category) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                String message = "";
                try {
                    //Add the category to db by calling the model
                    model.addCategory(category);
                    //If the operation was done successfully show this message
                    message = "Category: " + category.toString() + "  was added successfully";
                } catch (CostManagerException e) {
                    //If we couldn't add the category to the db, show this message
                    message = "Error adding " + category.toString() + " category";
                } finally {
                    view.showMessage(message);
                }
            }
        });
    }

    /**
     * When called, ask the IModel to add a new cost item to our db.
     * In case of either success or failure the user will be displayed with
     * a relevant message.
     * @param item - the CostItem to add to our db.
     */
    @Override
    public void addCostItem(CostItem item) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                String message = "";
                try {
                    //Add the cost item to db by calling the model
                    model.addCostItem(item);
                    //If the operation was done successfully show this message
                    message = "New cost: " + item.toString() + "  was added successfully";
                } catch (CostManagerException e) {
                    //If we couldn't add the cost item to the db, show this message
                    message = "Error with cost item:  " + item.toString();
                } finally {
                    view.showMessage(message);
                }
            }
        });
    }

    /**
     * When called, ask the IModel to return all the costs between two dates in our db.
     * In case of either success or failure the user will be displayed with
     * a relevant message.
     * @param dateFrom - get only costs from this date.
     * @param dateTo - get only costs to this date.
     */
    @Override
    public void getCostsForTable(String dateFrom, String dateTo) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    //Get list of cost items between to dates from the db by calling the model
                    List<CostItem> tableInfo = model.getCostItemsBetweenDates(dateFrom, dateTo);
                    //If the operation was done successfully show the table
                    view.displayCostItemTable(tableInfo);
                } catch (CostManagerException e) {
                    //If we couldn't get the data from the db, show the error message
                    view.showMessage(e.getMessage());
                }
            }
        });
    }

    /**
     * When called, ask the IModel to return all categories and their sums between two dates in our db.
     * In case of either success or failure the user will be displayed with
     * a relevant message.
     * @param dateFrom - get only costs from this date.
     * @param dateTo - get only costs to this date.
     */
    @Override
    public void getCostsForPieChart(String dateFrom, String dateTo) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    //Get list of pairs containing (category name, sum)
                    List<Pair> pieChartInfo = model.getCategorySumBetweenDates(dateFrom, dateTo);
                    //Make arrays in order to pass them to the view
                    String[] catNames = new String[pieChartInfo.size()];
                    double[] sums = new double[pieChartInfo.size()];
                    //Fill the arrays with values of the pairs
                    int i = 0;
                    for (Pair item : pieChartInfo) {
                        catNames[i] = item.name;
                        sums[i] = item.amount;
                        i++;
                    }
                    //If the operation was done successfully show the pie chart
                    view.displayCategoriesChart(catNames, sums);
                } catch (CostManagerException e) {
                    //If we couldn't get the data from the db, show the error message
                    view.showMessage(e.getMessage());
                }
            }
        });
    }

    /**
     * When called, ask the IModel to return all the categories in our db.
     * In case of either success or failure the user will be displayed with
     * a relevant message.
     */
    @Override
    public void getCategories() {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    //Get list of all existing categories in our db by calling the model
                    List<Category> categoryList = model.getAllCategory();
                    //Creating array in order to send it to the view
                    String[] categoriesNames = new String[categoryList.size()];
                    //Fill the array with all the category names in the list
                    for (int i = 0; i < categoryList.size(); i++) {
                        categoriesNames[i] = categoryList.get(i).getName();
                    }
                    //If the operation was done send the categories names to the view
                    view.displayCategoriesSelect(categoriesNames);
                } catch (CostManagerException e) {
                    //If we couldn't get the data from the db, show the error message
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    /**
     * When called, return all the Currencies name values in our Currency Enum.
     */
    @Override
    public void getCurrencies() {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                //Use the Currency Enum to get all the names values and send them to the view
                view.displayCurrenciesSelect(Arrays.toString(Currency.values()).replaceAll("^.|.$", "").split(", "));
            }
        });
    }
}