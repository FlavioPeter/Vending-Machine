/**
 * 
 */
package vendingmachine.app;

import vendingmachine.controller.VendingMachineController;
import vendingmachine.dao.VendingMachineAuditDao;
import vendingmachine.dao.VendingMachineAuditDaoFileImpl;
import vendingmachine.dao.VendingMachineDao;
import vendingmachine.dao.VendingMachineDaoFileImpl;
import vendingmachine.service.VendingMachineServiceLayer;
import vendingmachine.service.VendingMachineServiceLayerImpl;
import vendingmachine.ui.UserIO;
import vendingmachine.ui.UserIOConsoleImpl;
import vendingmachine.ui.VendingMachineView;

/**
 * @author Flavio Silva
 *
 */
public class App {

	public static void main(String[] args) {
		// Instantiate the UserIO implementation
		UserIO myIO = new UserIOConsoleImpl();
		// Instantiate the View and wire the UserIO implementation into it
		VendingMachineView myView = new VendingMachineView(myIO);
		// Instantiate the DAO
		VendingMachineDao myDao = new VendingMachineDaoFileImpl();
		// Instantiate the Audit DAO
		VendingMachineAuditDao myAuditDao = new VendingMachineAuditDaoFileImpl();
		// Instantiate the Service Layer and wire the DAO and Audit DAO into it
		VendingMachineServiceLayer myService = new VendingMachineServiceLayerImpl(myDao, myAuditDao);
		// Instantiate the controller and wire Service Layer into it
		VendingMachineController controller = new VendingMachineController(myService, myView);
		// Kick off the controller
		controller.run();
	}

}
