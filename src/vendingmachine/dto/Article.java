/**
 * 
 */
package vendingmachine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Flavio Silva
 *
 */

public class Article {
	
	private String name;
	private BigDecimal cost;
	private int inventory;
	
	public Article(String name, BigDecimal cost, int inventory) {
		// String name, BigDecimal(String cost), Integer.parseInt(String inventory) 
		this.name = name;
		this.cost = cost.setScale(2, RoundingMode.HALF_UP);
		this.inventory = inventory;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getCost() {
		return cost.toString();
	}
	
	public String getInventory() {
		return String.valueOf(inventory);
	}
	
	public void inventorySubtract() {
		this.inventory--;
	}
}
