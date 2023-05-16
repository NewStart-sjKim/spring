package test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("writeArticleService")
public class WriteArticleServiceImpl implements WriteArticleService{
    private ArticleDao dao; 
    @Autowired
    public WriteArticleServiceImpl(ArticleDao dao) {
    	this.dao = dao;
    }
	public void write(Article article) {
		dao.insert();		
	}
}
