package gdu.kr.chap2.javaconfig;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import gdu.kr.chap2.Project;
public class Main4 {
	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);
		Project pro = ctx.getBean("project",Project.class);
		pro.build();
		
	}
}
