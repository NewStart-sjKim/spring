package main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import spring.WriteImpl;
import xml.Article;
import xml.ReadArticleService;

public class Main2 {
	public static void main(String[] args) {
		String[] config = {"di.xml","aop2.xml"};
		ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
		//ReadArticleServiceImpl객체
		ReadArticleService service = ctx.getBean("readArticleService",ReadArticleService.class); 
		try {
			Article a1 = service.getArticleAndReadCnt(1);
			Article a2 = service.getArticleAndReadCnt(1);
			System.out.println("[main] a1 == a2 : " + (a1 == a2)) ;
			service.getArticleAndReadCnt(0);
		} catch(Exception e) {
			System.out.println("[main] " + e.getMessage());
		}
	}
}
