package test;

public class Contact {
	private String tel;
	private String fax;
	public Contact() {}
	public Contact(String tel, String fax) {
		super();
		this.tel = tel;
		this.fax = fax;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	
}
