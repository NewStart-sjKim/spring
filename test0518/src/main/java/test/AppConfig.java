package test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages={"test"})
public class AppConfig {
	@Bean
	public User user1() {
		User u = new User();
		Contact c = new Contact();
		u.setId("ㄱ김승준");
		c.setTel("101-2992-2931");
		c.setFax("10291-19i201");
		u.setContact(c);
		return u;
	}
	@Bean
	public User user2() {
		User u = new User();
		Contact c = new Contact();
		u.setId("dkds");
		c.setTel("11-211-246");
		c.setFax("10291-1asfaw01");
		u.setContact(c);
		return u;
	}
}
