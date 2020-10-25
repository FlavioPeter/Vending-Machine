/**
 * 
 */
package vendingmachine.service;

import java.util.ArrayList;
import java.util.List;

import vendingmachine.dao.ArticleCode;
import vendingmachine.dao.VendingMachineAuditDao;
import vendingmachine.dao.VendingMachineDao;
import vendingmachine.dao.VendingMachinePersistenceException;
import vendingmachine.dto.Article;

/**
 * @author Flavio Silva
 *
 */
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {
	
	VendingMachineDao dao;
	VendingMachineAuditDao auditDao;
	
	public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao) {
		this.dao = dao;
		this.auditDao = auditDao;
	}
	
	@Override
	public List<Article> getAllArticles() throws VendingMachinePersistenceException{
		return dao.getAllArticles();
	}
	
	@Override
	public Article getArticle(ArticleCode code) throws VendingMachinePersistenceException{
		return dao.getArticle(code);
	}
	
	@Override
	public Article removeUnit(ArticleCode code) throws VendingMachinePersistenceException{
		Article purchasedArticle = dao.removeUnit(code);
		// Write to audit log
		auditDao.writeAuditEntry("Article " + purchasedArticle.getName() + " was purchased");
		return purchasedArticle;
	}
	
	
	// Validate that the article exists and/or if there is still any in stock
	private void validateThereIsArticle(Article article) throws 
			VendingMachineNoSuchArticleException, 
			VendingMachineNoArticleException {
		if(article==null) {
			throw new VendingMachineNoSuchArticleException("ERROR: That option does not exist.");
		}else if(Integer.parseInt(article.getInventory())==0) {
			throw new VendingMachineNoArticleException("ERROR: This article is out of stock.");
		}
	}
}
