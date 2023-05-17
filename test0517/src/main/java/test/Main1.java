package test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
/*
 [결과]
[LA] Before 메서드 실행 전 실행
프로젝트 테스트 시작
[LA] Before 메서드 실행 전 실행
[LA] AfterReturning 메서드 정상 종료 후 실행.리턴값=OracleArticleDao
[LA] After 메서드 종료 후 실행
[LA] Before 메서드 실행 전 실행
OracleArticleDao : insert() 메서드 실행됨
[LA] AfterReturning 메서드 정상 종료 후 실행.리턴값=null
[LA] After 메서드 종료 후 실행
[LA] Before 메서드 실행 전 실행
[LA] AfterReturning 메서드 정상 종료 후 실행.리턴값=OracleArticleDao
[LA] After 메서드 종료 후 실행
[LA] Before 메서드 실행 전 실행
OracleArticleDao : update() 메서드 실행됨
[LA] AfterReturning 메서드 정상 종료 후 실행.리턴값=null
[LA] After 메서드 종료 후 실행
[LA] Before 메서드 실행 전 실행
[LA] AfterReturning 메서드 정상 종료 후 실행.리턴값=OracleArticleDao
[LA] After 메서드 종료 후 실행
[LA] Before 메서드 실행 전 실행
OracleArticleDao : delete() 메서드 실행됨
[LA] AfterThrowing 메서드 예외 종료 후 실행. 예외메세지=OracleArticleDao : delete() 메서드 실행됨삭제 중 오류 발생
[LA] After 메서드 종료 후 실행
[LA] AfterThrowing 메서드 예외 종료 후 실행. 예외메세지=OracleArticleDao : delete() 메서드 실행됨삭제 중 오류 발생
[LA] After 메서드 종료 후 실행
[main] 오류 발생:OracleArticleDao : delete() 메서드 실행됨삭제 중 오류 발생
 */
public class Main1 {
	public static void main(String[] args) {
		String config = "classpath:annotation.xml";
		AbstractApplicationContext ctx = 
				new GenericXmlApplicationContext(config);
		Project proj = ctx.getBean("project", Project.class);
		try {
		   proj.test();
		}catch (Exception e) {
			System.out.println("[main] 오류 발생:" + e.getMessage());
		}
	}
}