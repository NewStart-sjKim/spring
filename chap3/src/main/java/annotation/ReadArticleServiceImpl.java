package annotation;

import org.springframework.stereotype.Component;

import xml.Article;
@Component("readArticleService")
public class ReadArticleServiceImpl implements ReadArticleService{
	public Article getArticleAndReadCnt(int id) throws Exception{
		System.out.println("getArticleAndReadCnt(" + id + ")호출");
		if(id == 0) {
			throw new Exception("id는 0이안됨"); //예외 발생
		}
		return new Article(id); //new Article(1) 리턴
	}
}
