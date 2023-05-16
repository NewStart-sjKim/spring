package test;

import org.springframework.context.support.GenericXmlApplicationContext;
//classpath:applicationContext.xml 완성시키기
public class XmlMain {
	public static void main(String[] args) {
		GenericXmlApplicationContext ctx =
        new GenericXmlApplicationContext("classpath:applicationContext.xml");
	    WriteArticleService articleService = 
	    		ctx.getBean("writeArticleService",WriteArticleService.class);
	    articleService.write(new Article());
	}
}
