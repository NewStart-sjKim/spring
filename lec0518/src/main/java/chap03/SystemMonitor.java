package chap03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("Monitor")
public class SystemMonitor {
	@Value("10")
	private int time; 
	@Autowired
	private SmsSender sender;
	@Override
	public String toString() {
		return "SystemMonitor [time=" + time +",sender="+sender+ "]";
	}
}
