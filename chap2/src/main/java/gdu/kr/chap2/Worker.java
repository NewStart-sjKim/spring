package gdu.kr.chap2;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component //"worker 이름으로 컨테이너에 저장
@Scope(value="prototype",proxyMode=ScopedProxyMode.TARGET_CLASS)//일회용 객체로 설정
public class Worker {
	public void work(WorkUnit unit) {
		System.out.println(this + ":work:" + unit);
	}
}
