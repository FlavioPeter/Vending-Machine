/**
 * 
 */
package vendingmachine.controller;

import java.math.BigDecimal;

import vendingmachine.dao.VendingMachineDao;
import vendingmachine.dao.VendingMachineDaoException;
import vendingmachine.dao.VendingMachineDaoFileImpl;
import vendingmachine.dto.Article;
import vendingmachine.service.ArticleCode;
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
	private VendingMachineDao dao;
	private UserIO io = new UserIOConsoleImpl(); //get rid of this somehow
	
	public VendingMachineController(VendingMachineDao dao, VendingMachineView view) {
		this.dao = dao;
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
		}catch(VendingMachineDaoException e) {
			view.displayErrorMessage(e.getMessage());
		}
	}
	
	private BigDecimal getMoney(){
		return view.printMenuAndGetMoney();
	}
	
	private void listArticles() throws VendingMachineDaoException{
		view.vmBanner();
		List<Article> articleList = dao.getAllArticles();
		view.displayArticleList(articleList);
	}
	
	private void buyArticle() throws VendingMachineDaoException{
		view.articleChosenBanner();
		ArticleCode code = view.getArticleCode();
		Article article = dao.getArticle(code);
		view.boughtArticle(article);
	}
	
	private void exitMessage() {
		view.displayExitBanner();
	}
}
