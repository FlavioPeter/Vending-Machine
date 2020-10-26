/**
 * 
 */
package vendingmachine.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import vendingmachine.dao.ArticleCode;
import vendingmachine.dao.VendingMachineAuditDao;
import vendingmachine.dao.VendingMachineAuditDaoStubImpl;
import vendingmachine.dao.VendingMachineDao;
import vendingmachine.dao.VendingMachineDaoStubImpl;
import vendingmachine.dao.VendingMachinePersistenceException;
import vendingmachine.dto.Article;

/**
 * @author Flavio Silva
 *
 */
class VendingMachineServiceLayerImplTest {


	private VendingMachineServiceLayer service;
	
	public Article onlyArticle;
	
	@BeforeEach
	public void constructTest() {
		VendingMachineDao dao = new VendingMachineDaoStubImpl(onlyArticle);
	    VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();

	    service = new VendingMachineServiceLayerImpl(dao, auditDao);
	}
	
	@Test
	void testSubtractedAndAudit() throws VendingMachinePersistenceException {
		List<Article> myList = service.getAllArticles();
		System.out.println(myList.get(0).getName());
		Article onlyArticle = service.getArticle(ArticleCode.A1);
		System.out.println(onlyArticle.getName());
		
		int before = Integer.parseInt(onlyArticle.getInventory());
		String name = service.removeUnit(onlyArticle);
		int after = Integer.parseInt(onlyArticle.getInventory());
		
		assertEquals((before-1), after);
		assertTrue(name.equals("test"));
		assertTrue((myList.get(0).getName()).equals(onlyArticle.getName()));
		assertTrue(myList.get(0).getCost() == onlyArticle.getCost());
	}
	
	@Test
	void testEnoughMoneyAndChange() throws VendingMachinePersistenceException {
		List<Article> myList = service.getAllArticles();
		Article onlyArticle = service.getArticle(ArticleCode.A1);
		
		BigDecimal putMoney1 = new BigDecimal("0.50");
		BigDecimal putMoney2 = new BigDecimal("3.01");
		
		boolean enough1 = service.verifyEnoughMoney(putMoney1, onlyArticle);
		boolean enough2 = service.verifyEnoughMoney(putMoney2, onlyArticle);
		
		BigDecimal cost = new BigDecimal(onlyArticle.getCost());
		
		String change = service.getChange(putMoney2, cost);
		
		assertFalse(enough1);
		assertTrue(enough2);
		assertTrue(change.equals(" One Dollars: "+"1"+" Quarters: "+"3"+" Pennys: "+"1"+"."));
	}

}
