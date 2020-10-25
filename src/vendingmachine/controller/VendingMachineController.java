/**
 * 
 */
package vendingmachine.controller;

import java.math.BigDecimal;

import vendingmachine.dao.VendingMachinePersistenceException;
import vendingmachine.service.VendingMachineServiceLayer;
import vendingmachine.dto.Article;
import vendingmachine.dao.ArticleCode;
import vendingmachine.ui.UserIO;
import vendingmachine.ui.UserIOConsoleImpl;
import vendingmachine.ui.VendingMachineView;
import java.util.List;

/**
 * @author Flavio Silva
 *
 */
public class VendingMachineController {
	
	private VendingMachineView view;
	private VendingMachineServiceLayer service;
	private UserIO io = new UserIOConsoleImpl(); //get rid of this somehow
	
	public VendingMachineController(VendingMachineServiceLayer service, VendingMachineView view) {
		this.service = service;
		this.view = view;
	}
	
	public void run() {
		boolean keepGoing = true;
		BigDecimal putMoney = new BigDecimal("0");
		String articleSelected = null;
		try {
			while(keepGoing) {
				
				listArticles(); // unmarshall and then list
				
				putMoney = getMoney();
				
				buyArticle();
				
				if(true) { // if money put is enough
					
					// subtract unit from inventory
					
					// give change back
					io.print("Here is your change..."); // gotta get rid of this somehow
				}
				keepGoing = io.readBoolean("Would you like to keep buying?yes/no: "); //later to be added to ServiceLayer
			}
			exitMessage();
		}catch(VendingMachinePersistenceException e) {
			view.displayErrorMessage(e.getMessage());
		}
	}
	
	private BigDecimal getMoney(){
		return view.printMenuAndGetMoney();
	}
	
	private void listArticles() throws VendingMachinePersistenceException{
		view.vmBanner();
		List<Article> articleList = service.getAllArticles();
		view.displayArticleList(articleList);
	}
	
	private void buyArticle() throws VendingMachinePersistenceException{
		view.articleChosenBanner();
		ArticleCode code = view.getArticleCode();
		Article article = service.getArticle(code);
		view.boughtArticle(article);
	}
	
	private void exitMessage() {
		view.displayExitBanner();
	}
}
