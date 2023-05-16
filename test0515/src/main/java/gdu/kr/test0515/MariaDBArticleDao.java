package gdu.kr.test0515;

import org.springframework.stereotype.Component;

//@Component
public class MariaDBArticleDao implements ArticleDao{
	public void insert() {
		System.out.println("MariaDBArticleDao.insert() 메서드 호출");
	}
}
