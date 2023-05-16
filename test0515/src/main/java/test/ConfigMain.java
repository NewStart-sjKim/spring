package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//AppCtx 만들기
public class ConfigMain {
	public static void main(String[] args) {
		  ApplicationContext ctx = 
				   new AnnotationConfigApplicationContext(AppCtx.class);
		WriteArticleService articleService = 
				ctx.getBean("writeArticleService", WriteArticleService.class);
		articleService.write(new Article());
	}
}
