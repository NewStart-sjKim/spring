package xml;

public class LoggingAdvice {
	public void before() {
		System.out.println("[LA]메서드 실행 전 전처리 수행 기능");
	}
	public void afterReturning(Object ret) {// ret : 핵심메서드의 리턴값
		System.out.println("[LA]메서드 정상 처리 후 수행 함. ret=" + ret);
	}
	public void afterThrowing(Throwable ex) { //ex : 핵심메서드에서 발생된 예외 객체
		System.out.println("[LA]메서드 예외 발생 후 수행 함. 발생 예외=" + ex.getMessage());
	}
	public void afterFinally() {
		System.out.println("[LA]메서드 실행 후 수행 함.");
	}
}
