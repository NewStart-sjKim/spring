package gdu.kr.chap2.javaconfig;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import gdu.kr.chap2.BuildRunner;
import gdu.kr.chap2.Camera;
import gdu.kr.chap2.DisplayMode;
import gdu.kr.chap2.InfraredRaySensor;
import gdu.kr.chap2.Project;

@Configuration //컨테이너 설정 클래스
//gdu.kr.chap2 패키지중 @Component인 클래스 객체 생성
//<context:component-scan ..../>
@ComponentScan(basePackages = {"gdu.kr.chap2"}) 
public class AppCtx {
	@Bean //객체화. 메서드의 이름으로 객체가 저장됨.
	public Camera camera1() { //<bean id="camera1".../>
		Camera c = new Camera();
		c.setNumber(1);
		return c;
	}
	@Bean
	public Camera camera2() {
		Camera c = new Camera();
		c.setNumber(2);
		return c;
	}
	@Bean
	public Camera camera3() {
		Camera c = new Camera();
		c.setNumber(3);
		return c;
	}
	@Bean
	public Camera camera4() {
		Camera c = new Camera();
		c.setNumber(4);
		return c;
	}
	@Bean
	@Qualifier("intrusionDetection")
	public InfraredRaySensor windowSensor() {
		return new InfraredRaySensor("창센서");
	}
	@Bean
	@Qualifier("intrusionDetection")
	public InfraredRaySensor doorSensor() {
		return new InfraredRaySensor("현관센서");
	}
	@Bean
	public InfraredRaySensor lampSensor() {
		return new InfraredRaySensor("전등센서");
	}
	@Bean
	public DisplayMode displayMode() {
		DisplayMode d = new DisplayMode();
		d.setType("GRID");
		return d;
	}
	@Bean
	public Project project() {
		Project p = new Project();
		p.setBindir("bin");
		p.setClasspath("src/");
		List<String> list = Arrays.asList("src/","srcResources/");
		p.setSrcdirs(list);
		p.setBuild(runner());
		return p;
	}
	@Bean
	public BuildRunner runner() {
		BuildRunner b = new BuildRunner();
		b.setPath("c:/setup/");
		return b;
	}
}
/*
 * ========= 일반 자바 클래스에서 사용되는 어노테이션
 * @Component : 객체 생성. 객체 주입이 완료.
 * @Autowired : 객체 주입. => 주입되는 객체가 없는경우 : 오류 발생
 * 				객체가 없어도 허용 : @Autowired(required=false)
 * 				@Resource(name="이름") : 이름을 가진 객체 주입
 * 				@Required			  : 객체 주입 필수 
 * ========= 환경 설정 자바 클래스에서 사용되는 어노테이션
 * @Scope     : 일회성 객체 생성
 * @Configuration : 횐경 설정 자바 클래스 xml 대체 클래스
 * @ComponentScan : 객체 생성을 위 한 패키지 scan 설정
 * @Bean 		  : <bean id=""..../> 환경 설정 자바 클래스에서 메서드에 사용됨.
 * 					리턴타입에 해당하는 객체를 생성. 메서드의 이름으로 객체를 저장
 */
