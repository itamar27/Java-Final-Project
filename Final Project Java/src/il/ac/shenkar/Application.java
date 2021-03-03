package il.ac.shenkar;

import il.ac.shenkar.model.CostManagerException;
import il.ac.shenkar.model.DerbyDBModel;
import il.ac.shenkar.model.IModel;
import il.ac.shenkar.view.IView;
import il.ac.shenkar.view.View;
import il.ac.shenkar.viewmodel.IViewModel;
import il.ac.shenkar.viewmodel.ViewModel;

/**
 * Application is the class that runs our Cost Manager application,
 *
 * @author Barak Daniel
 * @author Itamar Yarden
 *
 * @version 1.0 - 03/03/2021
 *
 */

public class Application {
    /**
     * When we run Application class this main method will connect
     * all the application components and start the program.
     * @param args no need to send any args
     */
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
