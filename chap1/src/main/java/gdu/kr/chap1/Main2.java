package gdu.kr.chap1;

import org.springframework.context.support.GenericXmlApplicationContext;

public class Main2 {
	public static void main(String[] args) {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationContext.xml");
		//m : gdu.kr.chap1.MessageKr 객체 저장 => Message 형으로 형변환 가능
		Message m = ctx.getBean("message",Message.class);
		m.sayHello("홍길동"); //gdu.kr.chap1.MessageKr.sayHello(String) 메서드 호출
	}
}
