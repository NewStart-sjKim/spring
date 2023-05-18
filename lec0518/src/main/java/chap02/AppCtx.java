package chap02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//자바 config를 사용하여 객체 주입!
@Configuration
public class AppCtx {
	@Bean // SysyemMonitor 객체 생성하여 monitor이름으로 컨테이너에 저장해 (메서드명이 이름표)
	public SystemMonitor monitor() {
		SystemMonitor s = new SystemMonitor();
		s.setPeriodTime(10);
		s.setSender(sender());
		return s;
	}
	@Bean
	public SmsSender sender() {
		return new SmsSender();
	}
}
