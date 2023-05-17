package test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ComponentScan(basePackages = {"test"})
public class AppCtx {
	@Bean
	public WriteArticleService writeArticleService() {
		WriteArticleService obj = new WriteArticleServiceImpl(articleDao());
		return obj;
	}
	@Bean
	public ArticleDao articleDao() {
		ArticleDao obj = new MariadbArticleDao();
		return obj;
	}
}
