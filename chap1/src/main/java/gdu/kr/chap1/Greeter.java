package gdu.kr.chap1;

public class Greeter {
	private String format; // "%s을 배웁니다." 저장
	public String greet(String guest) { //Main1에서 호출함
		//guest : "스프링" 저장
		return String.format(format,guest); //스프릥을 배웁니다. 리턴
	}
	//format : "%s을 배웁니다." => xml에서 주입.
	public void setFormat(String format) {
		this.format = format;
	}
}
