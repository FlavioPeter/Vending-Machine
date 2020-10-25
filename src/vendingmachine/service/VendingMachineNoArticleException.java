/**
 * 
 */
package vendingmachine.service;

/**
 * @author Flavio Silva
 *
 */
public class VendingMachineNoArticleException extends Exception {
	
	public VendingMachineNoArticleException(String message) {
		super(message);
	}
	
	public VendingMachineNoArticleException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
