/**
 * 
 */
package vendingmachine.service;

/**
 * @author Flavio Silva
 *
 */
public class VendingMachineNoSuchArticleException extends Exception {
	
	public VendingMachineNoSuchArticleException(String message) {
		super(message);
	}
	
	public VendingMachineNoSuchArticleException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
