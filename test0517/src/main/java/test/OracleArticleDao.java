package test;

import org.springframework.stereotype.Component;

@Component
public class OracleArticleDao implements ArticleDao {
	public void insert(String str)  throws Exception {
		System.out.println(str);
	}

	@Override
	public void update(String str)  throws Exception {
		System.out.println(str);
	}

	@Override
	public void delete(String str) throws Exception{
		System.out.println(str);
		throw new Exception(str+"삭제 중 오류 발생");
	}

	@Override
	public String toString() {
		return "OracleArticleDao";
	}
}