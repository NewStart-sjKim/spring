package gdu.kr.test0515;

import org.springframework.stereotype.Component;

//@Component
public class OracleArticleDao implements ArticleDao{
	public void insert() {
		System.out.println("OracleArticleDao.insert() 메서드 호출");
	}
}
