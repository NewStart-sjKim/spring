package spring;

public class WriteImpl {
	private ArticleDao articleDao;
	public WriteImpl(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}
	public void write() {
		System.out.println("WriteImpl.write 메서드 호출");
		articleDao.insert();
	}
}
