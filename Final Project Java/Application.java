import Model.CostManagerException;
import Model.DerbyDBModel;
import Model.IModel;
import View.IView;
import View.View;
import ViewModel.IViewModel;
import ViewModel.ViewModel;

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
