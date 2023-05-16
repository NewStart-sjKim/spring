package gdu.kr.chap2;

import java.util.List;

public class BuildRunner {
	private String path; 
	public void setPath(String path) {
		this.path = path;
	}
	public void build(List<String> srcdirs, String bindir) {
		String info = "프로젝트 경로:" + path + "\n";
		for(String dir : srcdirs) {
			info += "소스파일 경로:" + dir + "\n";
		}
		info += "클래스 파일 경로 :" + bindir + "\n";
		System.out.println(info);
	}
}
