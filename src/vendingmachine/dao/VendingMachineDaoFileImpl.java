/**
 * 
 */
package vendingmachine.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vendingmachine.dto.Article;
import vendingmachine.service.ArticleCode;

/**
 * @author Flavio Silva
 *
 */
public class VendingMachineDaoFileImpl implements VendingMachineDao {
	
	private Map<String, Article> articles = new HashMap<>();
	
	@Override
	public List<Article> getAllArticles(){
		return new ArrayList<Article>(articles.values());
	}
	
	@Override
	public Article getArticle(ArticleCode code) {
		return articles.get(code);
	}
	
	@Override
	public String removeUnit(Article article) { // goes inside controller.boughtArticle
		article.inventorySubtract();
		return article.getName();
	}
}
