package gdu.kr.chap1;

import org.springframework.context.support.GenericXmlApplicationContext;

public class Main1 {
	public static void main(String[] args) {
		/*
		 * 컨테이너 : 객체들을 저장하고있는 객체
		 * 		1. greeter 이름으로 gdu.kr.chap1.Greeter 객체 저장
		 * 						  foramt 변수 : "%s을 배웁니다." 설정됨
		 */
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationContext.xml");
		//g : 컨테이너에 저장된 greeter 객체
		Greeter g = ctx.getBean("greeter",Greeter.class);
		System.out.println(g.greet("스프링")); //greet 메서드 호출
		
		//기존방식 코딩
		Greeter g2 = new Greeter();
		g2.setFormat("%s을 배웁니다.");
		System.out.println(g.greet("스프링"));
	}
}
