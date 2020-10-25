/**
 * 
 */
package vendingmachine.dao;

import java.util.List;

import vendingmachine.dto.Article;
import vendingmachine.service.ArticleCode;

/**
 * @author Flavio Silva
 *
 */
public interface VendingMachineDao {

	
	/**
	 * Returns a list of all Article objects extracted from unmarshalled file
	 * 
	 * @return List<Article> 
	 */
	List<Article> getAllArticles() throws VendingMachineDaoException;
	
	/**
	 * Returns the article object associated with given name of the article
	 * Returns null if the article doesn't exist
	 * 
	 * @param code corresponding to article
	 * @return the article object associated with the respective code attributed to it,
	 * null if the code isn't assigned to any product
	 */
	Article getArticle(ArticleCode code) throws VendingMachineDaoException;
	
	/**
	 * Removes 1 unit from inventory for the purchased article
	 * 
	 * @param code
	 * @return name of the article that had a unit removed
	 */
	String removeUnit(ArticleCode code) throws VendingMachineDaoException;
}
