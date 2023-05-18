package chap02;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import chap02.SystemMonitor;

public class Main2 {
	public static void main(String[] args) {
//1. ApplicationContext(컨테이너) 객체 생성
	ApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);
//2. chap02.SystemMonitor 객체를 컨테이너에서 조회하여 monitor 변수 저장하기
//3. monitor 콘솔 출력하기
		SystemMonitor m = ctx.getBean("monitor",SystemMonitor.class);
		System.out.println(m.toString());
		//스프링 없이 객체 생성하기
		SystemMonitor m1 = new SystemMonitor();
		m1.setPeriodTime(10);
		SmsSender s = new SmsSender();
		m1.setSender(s);
		System.out.println(s);

	}
}
