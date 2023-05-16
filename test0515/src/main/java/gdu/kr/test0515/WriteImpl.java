package gdu.kr.test0515;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WriteImpl {
	@Autowired
	private ArticleDao dao;
	public WriteImpl(ArticleDao dao) { //생성자
		this.dao = dao;
	}
	public void write() {
		System.out.println("WriteImpo.write 메서드 호출");
		dao.insert();
	}
}
