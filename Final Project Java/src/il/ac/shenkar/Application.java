package il.ac.shenkar;

import il.ac.shenkar.Model.CostManagerException;
import il.ac.shenkar.Model.DerbyDBModel;
import il.ac.shenkar.Model.IModel;
import il.ac.shenkar.View.IView;
import il.ac.shenkar.View.View;
import il.ac.shenkar.ViewModel.IViewModel;
import il.ac.shenkar.ViewModel.ViewModel;

public class Application {
    public static void main(String args[]) {
        try {
            //creating the application components
            IModel model = new DerbyDBModel();
            IView view = new View();
            IViewModel vm = new ViewModel();

            //connecting the components with each other
            view.setViewModel(vm);
            vm.setModel(model);
            vm.setView(view);

        } catch (CostManagerException e) {
            System.out.println(e.getMessage());
        }

    }
}
