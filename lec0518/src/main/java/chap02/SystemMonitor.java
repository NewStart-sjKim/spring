package chap02;
//1. setPeriodTime(int) 메서드 필요
//2. setSender(SmsSender) 메서드 필요

public class SystemMonitor {
	private int time; //10
	private SmsSender sender;
	public void setPeriodTime(int time) {
		this.time = time;
	}
	public void setSender(SmsSender sender) {
		this.sender = sender;
	}
	@Override
	public String toString() {
		return "SystemMonitor [time=" + time +",sender="+sender+ "]";
	}
}
