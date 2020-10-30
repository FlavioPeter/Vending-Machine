/**
 * 
 */
package vendingmachine;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import vendingmachine.controller.VendingMachineController;
import vendingmachine.service.VendingMachineNoArticleException;
import vendingmachine.service.VendingMachineNoSuchArticleException;

/**
 * @author Flavio Silva
 *
 */
public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws VendingMachineNoSuchArticleException, VendingMachineNoArticleException {
		/*
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
		*/
		

		AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("vendingmachine");
        //System.out.println(appContext);
        
        appContext.refresh();
	        
	    VendingMachineController controller = appContext.getBean("vendingMachineController", VendingMachineController.class);
	    controller.run();
	}

}
