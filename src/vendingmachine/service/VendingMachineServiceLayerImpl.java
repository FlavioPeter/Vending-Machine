/**
 * 
 */
package vendingmachine.service;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import vendingmachine.dao.ArticleCode;
import vendingmachine.dao.VendingMachineAuditDao;
import vendingmachine.dao.VendingMachineDao;
import vendingmachine.dao.VendingMachinePersistenceException;
import vendingmachine.dto.Article;

/**
 * @author Flavio Silva
 *
 */
@Component
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {
	
	VendingMachineDao dao;
	VendingMachineAuditDao auditDao;
	
	@Autowired
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
	
	public void verifyEnoughMoney(BigDecimal putMoney, Article article) throws VendingMachineNotEnoughMoneyException{
		if(putMoney.compareTo(new BigDecimal(article.getCost())) >= 0) {
		}else {
			throw new VendingMachineNotEnoughMoneyException("ERROR: Not enough money put.");
		}
	}
	
	public String getChange(BigDecimal putMoney, BigDecimal cost) {
		BigDecimal oneDollar = new BigDecimal("1").setScale(2);
		BigDecimal remain = (putMoney.subtract(cost)).setScale(2, RoundingMode.HALF_UP);
		BigDecimal oneDollarChange = remain.divide(oneDollar, 0, RoundingMode.DOWN);
		
		BigDecimal quarter = new BigDecimal("0.25").setScale(2);
		remain = remain.subtract(oneDollarChange.multiply(oneDollar)).setScale(2, RoundingMode.HALF_UP);
		BigDecimal quarterChange = remain.divide(quarter, 0, RoundingMode.DOWN);
		
		BigDecimal dime = new BigDecimal("0.10").setScale(2);
		remain = remain.subtract(quarterChange.multiply(quarter)).setScale(2, RoundingMode.HALF_UP);
		BigDecimal dimeChange = remain.divide(dime, 0, RoundingMode.DOWN);
		
		BigDecimal nickel = new BigDecimal("0.05").setScale(2);
		remain = remain.subtract(dimeChange.multiply(dime)).setScale(2, RoundingMode.HALF_UP);
		BigDecimal nickelChange = remain.divide(nickel, 0, RoundingMode.DOWN);
		
		BigDecimal penny = new BigDecimal("0.01").setScale(2);
		remain = remain.subtract(nickelChange.multiply(nickel)).setScale(2, RoundingMode.HALF_UP);
		BigDecimal pennyChange = remain.divide(penny, 0, RoundingMode.DOWN);
		
		String change = " One Dollars: "+oneDollarChange.toString()+" Quarters: "+quarterChange.toString()+" Dimes: "+dimeChange.toString()+" Nickels: "+nickelChange.toString()+" Pennys: "+pennyChange.toString()+".";
		return change;
	}
}
