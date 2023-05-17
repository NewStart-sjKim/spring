package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration //xml 대체하는 환경설정 java 클래스 
//annotation 패키지에 속한 클래스 중 @Component 클래스의 객체 생성
@ComponentScan(basePackages= {"annotation"}) 
@EnableAspectJAutoProxy //AOP 설정. AOP관련 어노테이션 인식
public class AppCtx {

}
