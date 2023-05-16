package gdu.kr.chap2;

import java.util.List;

public class Project {
	private List<String> srcdirs; //["src/","srcResources/"]
	private String bindir;		  // "bin"
	private BuildRunner build;	  //BuildRunner 객체. path="c:/setup/"
	private String classpath;	  //"src/"
	public void build() {
		build.build(srcdirs, bindir);
	}
	//getter, setter
	public List<String> getSrcdirs() { 
		System.out.println("srcdirs:" + srcdirs);
		return srcdirs;
	}
	public void setSrcdirs(List<String> srcdirs) {
		this.srcdirs = srcdirs;
	}
	public String getBindir() {
		return bindir;
	}
	public void setBindir(String bindir) {
		this.bindir = bindir;
	}
	public BuildRunner getBuild() {
		return build;
	}
	public void setBuild(BuildRunner build) {
		this.build = build;
	}
	public String getClasspath() {
		return classpath;
	}
	public void setClasspath(String classpath) {
		this.classpath = classpath;
	}
}
