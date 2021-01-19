package ViewModel;

import Model.Category;
import Model.CostItem;
import Model.CostManagerException;
import Model.IModel;
import View.IView;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
                try {
                    model.addCategory(category);
                    // Maybe add a message window in view to say the item was added.
                } catch (CostManagerException e) {
                    // At the moment a console message, maybe error message should
                    // be displayed for the user.
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    @Override
    public void addCostItem(CostItem item) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.addCostItem(item);
                    // Maybe add a message window in view to say the item was added.
                } catch (CostManagerException e) {
                    // At the moment a console message, maybe error message should
                    // be displayed for the user.
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getCostsForChart(String dateFrom, String dateTo) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.getCostItemsBetweenDates(dateFrom, dateTo);
                    // Maybe add a message window in view to say the item was added.
                } catch (CostManagerException e) {
                    // At the moment a console message, maybe error message should
                    // be displayed for the user.
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getCostsForPie(String dateFrom, String dateTo) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.getCategorySumBetweenDates(dateFrom, dateTo);
                    // Maybe add a message window in view to say the item was added.
                } catch (CostManagerException e) {
                    // At the moment a console message, maybe error message should
                    // be displayed for the user.
                    System.out.println(e.getMessage());
                }
            }
        });
    }
}
