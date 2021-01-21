package ViewModel;

import Model.*;
import View.IView;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/*
 * class implementation for the IViewModel interface
 * This module is a center piece in the MVVM architecture, it receives processed data from the view module and sending it for the model to be stored.
 * and receives unprocessed data from the model and deliver is processed to the view model to be shown to the user.
 * @Methods ViewModel() - constructor that initiates the thread poll for every action.
 *          SetView() - setting the view module of the app.
 *          setModel() - setting the model module of the app.
 *          addCategory() - adding a new category to DB, threaded function.
 *          addCostItem() - adding a new cost item to DB, threaded function.
 *          getCostForPie() - sending dates from view to the model for receiving table to show in pieChart
 *          getCostForTable() - sending dates from view to the model for receiving table to show in table.
 *          getCategories() - getting all the updated categories from the model
 *          getCurrencies() - returning al Currency enum members.
 */

public class ViewModel implements IViewModel {
    private IModel model;
    private IView view;
    private ExecutorService pool;

    public ViewModel() {
        pool = Executors.newFixedThreadPool(10);
    }

    @Override
    public void setView(IView view) {
        this.view = view;
    }

    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

    @Override
    public void addCategory(Category category) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                String message = "";
                try {
                    model.addCategory(category);
                    message = "Category: " + category.toString() + "  was added successfully";
                } catch (CostManagerException e) {
                    message = "Error adding " + category.toString() + " category";
                } finally {
                    view.showMessage(message);
                }
            }
        });
    }

    @Override
    public void addCostItem(CostItem item) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                String message = "";
                try {
                    model.addCostItem(item);
                    message = "New cost: " + item.toString() + "  was added successfully";
                } catch (CostManagerException e) {
                    message = "Error with cost item:  " + item.toString();
                } finally {
                    view.showMessage(message);
                }
            }
        });
    }

    @Override
    public void getCostsForTable(String dateFrom, String dateTo) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<CostItem> tableInfo = model.getCostItemsBetweenDates(dateFrom, dateTo);
                    view.displayCostItemTable(tableInfo);

                } catch (CostManagerException e) {
                    view.showMessage(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getCostsForPieChart(String dateFrom, String dateTo) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.getCategorySumBetweenDates(dateFrom, dateTo);
                } catch (CostManagerException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    @Override
    public String[] getCategories() {
        try {
            List<Category> categoryList = model.getAllCategory();
            String[] categoriesNames = new String[categoryList.size()];
            for (int i = 0; i < categoryList.size(); i++) {
                categoriesNames[i] = categoryList.get(i).getName();
            }
            return categoriesNames;
        } catch (CostManagerException e) {
            System.out.println(e.getMessage());
        }
        return new String[0];
    }

    public String[] getCurrencies() {
        return Arrays.toString(Currency.values()).replaceAll("^.|.$", "").split(", ");
    }
}