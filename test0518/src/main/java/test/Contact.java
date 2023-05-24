package test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Contact {
	@Value("1111-2222")
	private String tel;
	@Value("1111-3333")
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