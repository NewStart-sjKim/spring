package gdu.kr.test0515;

import org.springframework.context.support.GenericXmlApplicationContext;

/*
 * [결과]
   WriteImpo.write 메서드 호출
   MariaDBArticleDao.insert() 메서드 호출
 */
public class Main1 {
	public static void main(String[] args) {
		GenericXmlApplicationContext ctx = 
  new GenericXmlApplicationContext("classpath:applicationContext.xml");
		WriteImpl w = ctx.getBean("write",WriteImpl.class);
		w.write();
	}
}
