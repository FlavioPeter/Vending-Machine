/**
 * 
 */
package vendingmachine.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * @author Flavio Silva
 *
 */
public class VendingMachineAuditDaoStubImpl implements VendingMachineAuditDao {
	
	public static final String AUDIT_TEST = "audit_test.txt";
	
	@Override
	public void writeAuditEntry(String entry) throws VendingMachinePersistenceException{
		
		PrintWriter out;
		
		try {
			out = new PrintWriter(new FileWriter(AUDIT_TEST, true));
		}catch(IOException e) {
			throw new VendingMachinePersistenceException("Could not persist audit information.", e);
		}
		
		LocalDateTime timestamp = LocalDateTime.now();
		out.println(timestamp.toString() + " : " + entry);
		out.flush();
	}
}
