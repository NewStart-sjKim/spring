package xml;

public class UpdateTraceAdvice {
	public void traceReturn(Object result,String id, UpdateInfo info) {
		System.out.println
				("[TA] 정보 수정 결과:" + result + ",대상회원:" + id + ",수정정보:" + info);
	}
}
