package gdu.kr.chap2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Main2 {
	public static void main(String[] args) {
		ApplicationContext ctx = new GenericXmlApplicationContext("classpath:annotation.xml");
		Project pro = ctx.getBean("project",Project.class);
		pro.build();
	}
}
