/**
 * 
 */
package vendingmachine.service;

/**
 * @author Flavio Silva
 *
 */
public class VendingMachineNotEnoughMoneyException extends Exception {

	public VendingMachineNotEnoughMoneyException(String message) {
		super(message);
	}
	
	public VendingMachineNotEnoughMoneyException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
