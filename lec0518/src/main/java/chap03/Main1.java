package chap03;

import org.springframework.context.support.GenericXmlApplicationContext;

public class Main1 {
	public static void main(String[] args) {
//1. ApplicationContext(컨테이너) 객체 생성
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationContext2.xml");
//2. chap02.SystemMonitor 객체를 컨테이너에서 조회하여 monitor 변수 저장하기
//3. monitor 콘솔 출력하기
		SystemMonitor m = ctx.getBean("Monitor",SystemMonitor.class);
		System.out.println(m);
		
	}
}
