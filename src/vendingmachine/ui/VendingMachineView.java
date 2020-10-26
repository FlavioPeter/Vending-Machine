/**
 * 
 */
package vendingmachine.ui;

import java.math.BigDecimal;
import java.util.List;

import vendingmachine.dto.Article;
import vendingmachine.dao.ArticleCode;

/**
 * @author Flavio Silva
 *
 */
public class VendingMachineView {

	private UserIO io;
	
	public VendingMachineView(UserIO io) {
		this.io = io;
	}
	
	public BigDecimal printMenuAndGetMoney() {
		return io.readBigDecimal("How much money will you put?: "); // This will go to putMoney
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
	
	public void returnBoughtArticle(String name) {// removeUnit as input // goes inside controller.boughtArticle
		io.print("You bought "+name);
	}
	
	public void displayExitBanner() {
		io.print("Thanks for buying!!! Good bye...");
	}
	
	public void displayErrorMessage(String errorMsg) {
		io.print("=== ERROR ===");
		io.readString(errorMsg);
	}
	
	public boolean getBuyMore() {
		return io.readBoolean("Would you like to keep buying?yes/no: ");
	}
	
	public ArticleCode getArticleCodeChoice() {
		return io.readArticleCode("Which article would you like based on the code?: ");
	}
	
	public void notEnoughMoney() {
		io.print("Sorry, but you didn't put enough money.");
	}
	
	public void displayChange(String change) {
		io.print("Here is your change: "+change);
	}
	
	public void youBought(String purchasedArticle) {
		io.readString("You bought: "+purchasedArticle+". \nPress enter to continue...");
	}
	
	public void errorMessage() {
		io.readString("You typed an invalid code. Please press enter to restart...");
	}
}
