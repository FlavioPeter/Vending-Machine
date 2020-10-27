/**
 * 
 */
package vendingmachine.service;

import java.math.BigDecimal;
import java.util.List;

import vendingmachine.dao.ArticleCode;
import vendingmachine.dao.VendingMachinePersistenceException;
import vendingmachine.dto.Article;

/**
 * @author Flavio Silva
 *
 */
public interface VendingMachineServiceLayer {

	
	/**
	 * Returns a list of all Article objects extracted from unmarshalled file
	 * 
	 * @return List<Article> 
	 */
	List<Article> getAllArticles() throws 
			VendingMachinePersistenceException;
	
	/**
	 * Returns the article object associated with given name of the article
	 * Returns null if the article doesn't exist
	 * 
	 * @param code corresponding to article
	 * @return the article object associated with the respective code attributed to it,
	 * null if the code isn't assigned to any product
	 */
	Article getArticle(ArticleCode code) throws 
			VendingMachinePersistenceException;
	
	/**
	 * Removes 1 unit from inventory for the purchased article
	 * 
	 * @param code
	 * @return name of the article that had a unit removed
	 */
	String removeUnit(Article article) throws VendingMachinePersistenceException;
	
	void validateThereIsArticle(Article article) throws 
			VendingMachineNoSuchArticleException, 
			VendingMachineNoArticleException;
	
	void verifyEnoughMoney(BigDecimal putMoney, Article article) throws VendingMachineNotEnoughMoneyException;
	
	String getChange(BigDecimal putMoney, BigDecimal cost) ;
}
