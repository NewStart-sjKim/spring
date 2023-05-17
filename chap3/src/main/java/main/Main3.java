package main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import annotation.ReadArticleService;
import xml.Article;
import annotation.Member;
import annotation.MemberService;
import xml.UpdateInfo;

public class Main3 {
	public static void main(String[] args) {
		String[] config = {"annotation.xml"};
		ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
		ReadArticleService service = ctx.getBean("readArticleService",ReadArticleService.class);
		try {
			Article a1 = service.getArticleAndReadCnt(1);
			Article a2 = service.getArticleAndReadCnt(1);
			System.out.println("[main] a1 == a2 : " + (a1 == a2)) ;
			service.getArticleAndReadCnt(0);
		} catch(Exception e) {
			System.out.println("[main] " + e.getMessage());
		}
		System.out.println("\n UpdateMemberInfoTraceAspect 연습");
		MemberService ms = ctx.getBean("memberService",MemberService.class);
		ms.regist(new Member());
		ms.update("hong",new UpdateInfo());
		ms.delete("hong2", "test",new UpdateInfo());
	}
}
