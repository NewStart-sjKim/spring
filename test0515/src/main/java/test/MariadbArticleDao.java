package test;

import org.springframework.stereotype.Component;

@Component
public class MariadbArticleDao implements ArticleDao{

	public void insert() {
		System.out.println("mariadb에 저장합니다.");		
	}

}
