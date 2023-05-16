package gdu.kr.chap2;

public class InfraredRaySensor {
	private String name; //생성자에서 주입
	private boolean objectFound; //기본값 : false 
	//생성자
	public InfraredRaySensor(String name) {
		this.name = name;
	}
	public void foundObject() {
		objectFound = true;
	}
	public String getName() {
		return name;
	}
	public boolean isObjectFound() {
		return objectFound;
	}
	@Override
	public String toString() {
		return "InfraredRaySensor [name=" + name + ", objectFound=" + objectFound + "]";
	}
	
}
