/**
 * 
 */
package vendingmachine.app;

import vendingmachine.controller.VendingMachineController;
import vendingmachine.dao.VendingMachineDao;
import vendingmachine.dao.VendingMachineDaoFileImpl;
import vendingmachine.ui.UserIO;
import vendingmachine.ui.UserIOConsoleImpl;
import vendingmachine.ui.VendingMachineView;

/**
 * @author Flavio Silva
 *
 */
public class App {

	public static void main(String[] args) {
		UserIO myIO = new UserIOConsoleImpl();
		VendingMachineView myView = new VendingMachineView(myIO);
		VendingMachineDao myDao = new VendingMachineDaoFileImpl();
		VendingMachineController controller = new VendingMachineController(myDao, myView);
		controller.run();
	}

}
