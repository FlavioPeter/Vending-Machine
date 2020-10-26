/**
 * 
 */
package vendingmachine.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

import vendingmachine.dao.ArticleCode;

/**
 * @author Flavio Silva
 *
 */
public class UserIOConsoleImpl implements UserIO {
	
	private static Scanner input = new Scanner(System.in); 
	
	public void print(String message) {
		System.out.println(message);
	}
	
	public String readString(String prompt) {
		print(prompt);
		String ans = input.nextLine();
		return ans;
	}
	
	public int readInt(String prompt) {
		int ans;
		while(true) {
			try {
				print(prompt);
				ans = Integer.parseInt(input.nextLine());
				break;
			}catch(Exception e) {
				print("That was not an Integer.");
			}
		}
		return ans;
	}
	
	public int readInt(String prompt, int min, int max) {
		int ans = min-1;
		while(!(ans>=min && ans<=max)) {
			try {
				print(prompt);
				ans = Integer.parseInt(input.nextLine());
			}catch(Exception e) {
				print("That was not an Integer within the correct range.");
			}
		}
		return ans;
	}
	
	public double readDouble(String prompt) {
		double ans;
		while(true) {
			try {
				print(prompt);
				ans = Double.parseDouble(input.nextLine());
				break;
			}catch(Exception e) {
				print("That was not a Double.");
			}
		}
		return ans;
	}
	
	public double readDouble(String prompt, double min, double max) {
		double ans = min-1;
		while(!(ans>=min && ans<=max)) {
			try {
				print(prompt);
				ans = Double.parseDouble(input.nextLine());
			}catch(Exception e) {
				print("That was not a Double within range.");
			}
		}
		return ans;
	}
	
	public float readFloat(String prompt) {
		float ans;
		while(true) {
			try {
				print(prompt);
				ans = Float.parseFloat(input.nextLine());
				break;
			}catch(Exception e) {
				print("That was not a Float.");
			}
		}
		return ans;
	}
	
	public float readFloat(String prompt, float min, float max) {
		float ans = min - 1;
		while(!(ans>=min && ans<=max)) {
			try {
				print(prompt);
				ans = Float.parseFloat(input.nextLine());
			}catch(Exception e) {
				print("That was not a Float within range.");
			}
		}
		return ans;
	}
	
	public long readLong(String prompt) {
		long ans;
		while(true) {
			try {
				print(prompt);
				ans = Long.parseLong(input.nextLine());
				break;
			}catch(Exception e) {
				print("That was not a Long.");
			}
		}
		return ans;
	}
	
	public long readLong(String prompt, long min, long max) {
		long ans = min - 1;
		while(!(ans>=min && ans<=max)) {
			try {
				print(prompt);
				ans = Long.parseLong(input.nextLine());
			}catch(Exception e) {
				print("That was not a Long within range.");
			}
		}
		return ans;
	}
	
	public BigDecimal readBigDecimal(String prompt) {
		BigDecimal ans;
		while(true) {
			try {
				print(prompt);
				ans = new BigDecimal(input.nextLine());
				ans = ans.setScale(2, RoundingMode.HALF_UP);
				break;
			}catch(Exception e) {
				print("That was not a BigDecimal.");
			}
		}
		return ans;
	}
	
	public boolean readBoolean(String prompt) {
		boolean ans = false;
		print(prompt);
		String answer = input.nextLine();
		if(answer.equals("yes")) {
			ans = true;
		}
		return ans;
	}
	
	public ArticleCode readArticleCode(String prompt) {
		ArticleCode ans;
		print(prompt);
		String answer = input.nextLine();
		ans = ArticleCode.valueOf(answer);
		for (ArticleCode code : ArticleCode.values()) {
		    if (code == ans) {
		        return code;
		    }
		}
		return ArticleCode.NONE;
	}
}
