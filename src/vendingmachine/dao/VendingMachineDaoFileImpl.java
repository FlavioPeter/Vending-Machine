/**
 * 
 */
package vendingmachine.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import vendingmachine.dto.Article;

/**
 * @author Flavio Silva
 *
 */
public class VendingMachineDaoFileImpl implements VendingMachineDao {
	
	public static final String INVENTORY_FILE = "inventory.txt";
	public static final String DELIMITER = "::";
	
	private Map<ArticleCode, Article> articles = new TreeMap<>();
	
	private Article unmarshallArticle(String articleAsText) {
		// articleAsText is expecting a line read in from our file.
		// For example, it might look like this:
		// <name>::<cost>::<inventory>
		// 
		// We then split that line on our DELIMITER - which we are using as ::
		// Leaving us with an array of Strings, stored in articleTokens.
		// Which should look like this:
		// ______________________________________________________
		//|      |      |           |
		//|<name>|<cost>|<inventory>|
		//|      |      |           |
		// ------------------------------------------------------
		//   [0]   [1]       [2]          [3]
		String[] articleTokens = articleAsText.split(DELIMITER);
		
		// Given the pattern above, the name is in index 0 of the newly created String array
		String name = articleTokens[0];
		// Index 1
		BigDecimal cost = new BigDecimal(articleTokens[1]);
		// Index 2
		int inventory = Integer.parseInt(articleTokens[2]);
		
		// return constructed Article object
		return new Article(name, cost, inventory);
	}
	
	private void loadArticle() throws VendingMachinePersistenceException{
		Scanner scanner;
		ArticleCode[] codeArray = ArticleCode.values(); // code to be used as key for selecting and article
		int i = 0; // index of the code to be assigned to article of each line read
		
		try {
			// Create scanner for reading the file
			scanner = new Scanner(new BufferedReader(new FileReader(INVENTORY_FILE)));
		}catch(FileNotFoundException e){
			throw new VendingMachinePersistenceException("-_- Could not load article into memory.", e);
		}
		// currentLine holds the most recent line read from the file
		String currentLine;
		// currentArticle holds the most recent article unmarshalled
		Article currentArticle;
		// Go through INVENTORY_FILE line by line, decoding each line into a
		// Article object by calling the unmarshallArticle method.
		// Process while we have more lines in the file
		while(scanner.hasNextLine()) {
			// get the next line in the file
			currentLine = scanner.nextLine();
			// unmarshall the line into a Student
			currentArticle = unmarshallArticle(currentLine);
			
			// We are going to use ArticleCode enums as a map key for our article object.
			// Put currentArticle into the map using Enum code as the key
			articles.put(codeArray[i], currentArticle);
			i++;
		}
		// close scanner
		scanner.close();
	}
	
	private String marshallArticle(Article anArticle) {
		// We need to turn and Article object into a line of text for our file.
		// For example, we need an in memory object to end up like this:
		// name::cost::inventory
		
		// It's not a complicated process. Just get out each property,
		// and concatenate with our DELIMITER as a kind of spacer.
		
		// Start with name
		String articleAsText = anArticle.getName() + DELIMITER;
		
		// then cost
		articleAsText += anArticle.getCost() + DELIMITER;
		
		// and finally inventory (without DELIMITER as it's the last property
		articleAsText += anArticle.getInventory();
		
		// And now we have an Article successfully turned into a line of text
		return articleAsText;
	}
	
	private void writeArticle(Map<ArticleCode, Article> articles) throws VendingMachinePersistenceException, IOException {
		PrintWriter scanner;
		try {
			scanner = new PrintWriter(new FileWriter(INVENTORY_FILE));
		}catch(FileNotFoundException e) {
			throw new VendingMachinePersistenceException("-_- Could not load article data into memory.", e);
		}
		for(Map.Entry<ArticleCode, Article> thisArticle : articles.entrySet()) {
			scanner.println(marshallArticle(thisArticle.getValue()));
		}
		scanner.close();
	}
	
	@Override
	public List<Article> getAllArticles() throws VendingMachinePersistenceException{
		loadArticle();
		return new ArrayList<Article>(articles.values());
	}
	
	@Override
	public Article getArticle(ArticleCode code) throws VendingMachinePersistenceException{
		loadArticle();
		return articles.get(code);
	}
	
	@Override
	public Article removeUnit(ArticleCode code) throws VendingMachinePersistenceException{ // goes inside controller.boughtArticle
		loadArticle();
		Article subtractedArticle = articles.get(code);
		subtractedArticle.inventorySubtract();
		try {
			writeArticle(articles);
		}catch(IOException e) {
			throw new VendingMachinePersistenceException("-_- Could not load article data into memory.", e);
		}
		return subtractedArticle;
	}
}
