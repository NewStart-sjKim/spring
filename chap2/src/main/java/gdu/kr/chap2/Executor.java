package gdu.kr.chap2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // "executor" 이름으로 컨테이너에 저장
public class Executor { //worker 변수에 Worker 객체가 주입된 상태로 객체 생성
	@Autowired //컨테이너의 객체 중 자료형이 Worker인 객체를 주입
	private Worker worker;
	public void addUnit(WorkUnit unit) {
		worker.work(unit);
	}
}
