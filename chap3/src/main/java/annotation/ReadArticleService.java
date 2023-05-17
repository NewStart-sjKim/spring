package annotation;

import xml.Article;

public interface ReadArticleService {
	Article getArticleAndReadCnt(int id) throws Exception;
}
