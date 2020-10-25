/**
 * 
 */
package vendingmachine.ui;

import java.math.BigDecimal;
import java.util.List;

import vendingmachine.dao.VendingMachineDao;
import vendingmachine.dao.VendingMachineDaoFileImpl;
import vendingmachine.dto.Article;
import vendingmachine.dao.ArticleCode;

/**
 * @author Flavio Silva
 *
 */
public class VendingMachineView {
	
	private VendingMachineDao dao = new VendingMachineDaoFileImpl(); //get rid of this some how
	private UserIO io;
	
	public VendingMachineView(UserIO io) {
		this.io = io;
	}
	
	public BigDecimal printMenuAndGetMoney() {
		io.print("HERES ARE THE ARTICLES UNMARSHALLED FROM TXT FILE"); //displayArticleList
		
		return io.readBigDecimal("How much money will you put?: ");
	}
	
	public void vmBanner() { // Display banner before displayArticleList
		io.print("=== Vending Machine ===");
	}
	
	public void purchaseSuccess(String name) { // Which article was purchased with success
		io.print(name + " purchased with success. Enjoy!");
	}
	
	public void displayArticleList(List<Article> articleList) {
		for(int i=0 ; i<articleList.size() ; i++) {
			String articleInfo = String.format(ArticleCode.values()[i]+"\n%s\n%s\n%s\n\n", 
					articleList.get(i).getName(),
					articleList.get(i).getCost(),
					articleList.get(i).getInventory());
			io.print(articleInfo);
		}
	}
	
	public void articleChosenBanner() {
		io.print("=== Article Chosen ===");
	}
	
	public ArticleCode getArticleCode() {
		return ArticleCode.valueOf(io.readString("Please enter the article code (e.g: A1, B3, C2, etc): "));
	}
	
	public void boughtArticle(Article article) {
		if(article != null) {
			io.print(article.getName());
			io.print(article.getCost());
			io.print(article.getInventory());
			String name = dao.removeUnit(article); // gotta fix this
			returnBoughtArticle(name);
		}else {
			io.print("No such article...");
		}
		io.readString("Please hit enter to continue and order something else.");
	}
	
	public void returnBoughtArticle(String name) {// removeUnit as input // goes inside controller.boughtArticle
		io.print("You bought "+name);
	}
	
	public void displayExitBanner() {
		io.print("Thanks for buying!!! Good bye...");
	}
	
	public void displayErrorMessage(String errorMsg) {
		io.print("=== ERROR ===");
		io.print(errorMsg);
	}
}
