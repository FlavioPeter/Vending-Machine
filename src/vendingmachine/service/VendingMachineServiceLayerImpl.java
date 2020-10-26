/**
 * 
 */
package vendingmachine.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
	public String removeUnit(Article article) throws VendingMachinePersistenceException{
		article.inventorySubtract();
		dao.updateInventory();
		String purchasedArticle = article.getName();
		// Write to audit log
		auditDao.writeAuditEntry("Article " + purchasedArticle + " was purchased");
		return purchasedArticle;
	}
	
	
	// Validate that the article exists and/or if there is still any in stock
	public void validateThereIsArticle(Article article) throws 
			VendingMachineNoSuchArticleException, 
			VendingMachineNoArticleException {
		if(article==null) {
			throw new VendingMachineNoSuchArticleException("ERROR: That option does not exist.");
		}else if(Integer.parseInt(article.getInventory())==0) {
			throw new VendingMachineNoArticleException("ERROR: This article is out of stock.");
		}
	}
	
	public boolean verifyEnoughMoney(BigDecimal putMoney, Article article){
		if(putMoney.compareTo(new BigDecimal(article.getCost())) >= 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public String getChange(BigDecimal putMoney, BigDecimal cost) {
		BigDecimal oneDollar = new BigDecimal("1").setScale(2);
		BigDecimal remain = (putMoney.subtract(cost)).setScale(2, RoundingMode.HALF_UP);
		BigDecimal oneDollarChange = remain.divide(oneDollar, 0, RoundingMode.DOWN);
		
		BigDecimal quarter = new BigDecimal("0.25").setScale(2);
		remain = remain.subtract(oneDollarChange.multiply(quarter)).setScale(2, RoundingMode.HALF_UP);
		BigDecimal quarterChange = remain.divide(quarter, 0, RoundingMode.DOWN);
		
		BigDecimal penny = new BigDecimal("0.01").setScale(2);
		BigDecimal pennyChange = remain.divide(penny, 0, RoundingMode.DOWN);
		
		String change = " One Dollars: "+oneDollarChange.toString()+" Quarters: "+quarterChange.toString()+" Pennys: "+pennyChange.toString()+".";
		return change;
	}
}
