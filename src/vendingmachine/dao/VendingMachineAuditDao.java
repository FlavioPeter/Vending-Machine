/**
 * 
 */
package vendingmachine.dao;

/**
 * @author Flavio Silva
 *
 */
public interface VendingMachineAuditDao {

	public void writeAuditEntry(String entry) throws VendingMachinePersistenceException;
	
}
