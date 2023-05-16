package gdu.kr.chap2;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class HomeController {
	private AlarmDevice alarmDevice;
	private Viewer viewer;
	@Autowired//Camera 객체 중 camera1 인 객체 주입
	private Camera camera1; // number 변수의 값이 1 Camera 객체 주입
	@Autowired
	private Camera camera2; // number 변수의 값이 1 Camera 객체 주입
	@Autowired
	private Camera camera3;
	//@Autowired
	@Resource(name="camera4") //camera4 이름을 가진 객체 주입.
	private Camera camera4;
	@Autowired(required=false) //주입되는 객체 (Recoder 객체)가 없어도 허용.
	private Recoder recoder;   //recoder	 = null로 저장됨
	
	private List<InfraredRaySensor> sensors;
	
	@Autowired
	//AlarmDevice 객체(SmsAlarmDevice 객체), Viewer 객체 주입, Viewer(SmartPhoneView 객체) 객체 주입
	public void prepare(AlarmDevice alarmDevice, Viewer viewer) {
		this.alarmDevice = alarmDevice; //SmsAlarmDevice 객체
		this.viewer = viewer;			//SmartPhoneView 객체
	}
	@PostConstruct //객체 생성 완료 후 호출되는 메서드 
				   //객체 주입 완료 된 경우 : 객체 생성 완료 상태
	public void init() {
		System.out.println("init()메서드 호출");
		viewer.add(camera1);
		viewer.add(camera2);
		viewer.add(camera3);
		viewer.add(camera4);
		viewer.draw();
		System.out.println("기록장치:" + recoder);
	}
	@Autowired //
	@Qualifier("intrusionDetection") //별명
	//InfraredRaySensor 객체 중 별명이  intrusionDetection 객체들만 주입
	//sensors : 창센서와 , 현관센서 객체 저장
	public void setSensors(List<InfraredRaySensor> sensors) {
		System.out.println("setSensors() 메서드 호출");
		this.sensors = sensors;
		for (InfraredRaySensor s : sensors) {
			System.out.println(s.getName());
		}
	}
	public void checkSensorAndAlarm() { //main에서 호출되는 함수
		for(InfraredRaySensor s : sensors) {
			if(s.isObjectFound()) {
				alarmDevice.alarm(s.getName());
			}
		}
	}
	
}
