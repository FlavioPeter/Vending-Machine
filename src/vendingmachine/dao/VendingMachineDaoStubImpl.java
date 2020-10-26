/**
 * 
 */
package vendingmachine.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import vendingmachine.dto.Article;

/**
 * @author Flavio Silva
 *
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao {
	
	public Article onlyArticle;

    public VendingMachineDaoStubImpl(Article onlyArticle){
         this.onlyArticle = new Article("test", new BigDecimal("1.25"), 5);
     }

    @Override
	public List<Article> getAllArticles() throws VendingMachinePersistenceException{
    	List<Article> articleList = new ArrayList<>();
        articleList.add(onlyArticle);
        return articleList;
	}
	
	@Override
	public Article getArticle(ArticleCode code) throws VendingMachinePersistenceException{
		if(code == ArticleCode.A1) {
			return onlyArticle;
		}else {
			return null;
		}
	}

	@Override
	public void updateInventory() throws VendingMachinePersistenceException {
		// do nothing
	}

}
