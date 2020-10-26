/**
 * 
 */
package vendingmachine.controller;

import java.math.BigDecimal;

import vendingmachine.dao.VendingMachinePersistenceException;
import vendingmachine.service.VendingMachineNoArticleException;
import vendingmachine.service.VendingMachineNoSuchArticleException;
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
	
	public VendingMachineController(VendingMachineServiceLayer service, VendingMachineView view) {
		this.service = service;
		this.view = view;
	}
	
	public void run() throws VendingMachineNoSuchArticleException, VendingMachineNoArticleException {
		boolean keepGoing = true;
		Article article = null;
		BigDecimal putMoney = new BigDecimal("0");
		try {
			while(keepGoing) {
				
				listArticles(); // unmarshall and then list
				
				putMoney = getMoney(); // money put by user
				
				article = buyArticle(); // select article based on listArticles()
				
				boughtArticle(putMoney ,article); // did you buy? was the money enough?
				
				keepGoing = buyMore(); // Will you buy more?
			}
			exitMessage(); // Good Bye
		}catch(VendingMachinePersistenceException e) {
			view.displayErrorMessage(e.getMessage()); // If you get here, something went wrong
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
	
	private Article buyArticle() throws VendingMachinePersistenceException, VendingMachineNoSuchArticleException, VendingMachineNoArticleException{
		view.articleChosenBanner();
		ArticleCode code = view.getArticleCodeChoice();
		Article article = service.getArticle(code);
		service.validateThereIsArticle(article);
		return article;
	}
	
	private void boughtArticle(BigDecimal putMoney, Article article) throws VendingMachinePersistenceException{
		boolean enough = service.verifyEnoughMoney(putMoney, article);
		if(enough) { // if money put is enough
			
			// subtract unit from inventory
			String change = service.getChange(putMoney, (new BigDecimal(article.getCost())));// create
			view.displayChange(change); 
			String purchasedArticle = service.removeUnit(article);
			view.youBought(purchasedArticle);
		} else {
			view.notEnoughMoney();
		}
	}
	
	private void exitMessage() {
		view.displayExitBanner();
	}
	
	private boolean buyMore() {
		return view.getBuyMore();
	}
}
