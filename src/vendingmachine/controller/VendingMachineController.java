/**
 * 
 */
package vendingmachine.controller;

import java.math.BigDecimal;

import vendingmachine.dao.VendingMachinePersistenceException;
import vendingmachine.service.VendingMachineNoArticleException;
import vendingmachine.service.VendingMachineNoSuchArticleException;
import vendingmachine.service.VendingMachineNotEnoughMoneyException;
import vendingmachine.service.VendingMachineServiceLayer;
import vendingmachine.dto.Article;
import vendingmachine.dao.ArticleCode;
import vendingmachine.ui.VendingMachineView;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Flavio Silva
 *
 */
@Component
public class VendingMachineController {
	
	private VendingMachineView view;
	private VendingMachineServiceLayer service;
	
	@Autowired
	public VendingMachineController(VendingMachineServiceLayer service, VendingMachineView view) {
		this.service = service;
		this.view = view;
	}
	
	public void run() {
		boolean keepGoing = true;
		Article article = null;
		BigDecimal putMoney = new BigDecimal("0");
		try {
			while(keepGoing) {
				try {
					listArticles(); // unmarshall and then list
					
					putMoney = getMoney(); // money put by user
					
					article = buyArticle(); // select article based on listArticles()
					
					boughtArticle(putMoney ,article); // did you buy? was the money enough?
					
					keepGoing = buyMore(); // Will you buy more?
				} catch(VendingMachineNoSuchArticleException | VendingMachineNoArticleException |VendingMachineNotEnoughMoneyException e) {
					promptError(e);
				}
				
			}
			exitMessage(); // Good Bye
		}catch(VendingMachinePersistenceException e) {
			promptError(e); // If you get here, something went wrong
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
		ArticleCode code = view.getArticleCodeChoice();
		Article article = service.getArticle(code);
		service.validateThereIsArticle(article);
		return article;
	}
	
	private void boughtArticle(BigDecimal putMoney, Article article) throws VendingMachinePersistenceException, VendingMachineNotEnoughMoneyException{
		service.verifyEnoughMoney(putMoney, article);	
		// subtract unit from inventory
		String change = service.getChange(putMoney, (new BigDecimal(article.getCost())));// create
		view.displayChange(change); 
		String purchasedArticle = service.removeUnit(article);
		view.articleChosenBanner();
		view.youBought(purchasedArticle);
	}
	
	private void exitMessage() {
		view.displayExitBanner();
	}
	
	private boolean buyMore() {
		return view.getBuyMore();
	}
	
	private void promptError(Exception e) {
		view.displayErrorMessage(e.getMessage());
	}
}
