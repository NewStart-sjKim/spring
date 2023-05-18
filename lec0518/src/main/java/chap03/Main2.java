package chap03;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main2 {
	public static void main(String[] args) {
//1. ApplicationContext(컨테이너) 객체 생성
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx2.class);
//2. chap02.SystemMonitor 객체를 컨테이너에서 조회하여 monitor 변수 저장하기
//3. monitor 콘솔 출력하기
		SystemMonitor m = ctx.getBean("Monitor",SystemMonitor.class);
		System.out.println(m);
		
	}
}
