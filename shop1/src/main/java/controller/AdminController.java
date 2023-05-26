package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import exception.LoginException;
import logic.Mail;
import logic.ShopService;
/*
 * AdminController의 모든 메서드들은 반드시 관리자로 로그인 해야만 실행 가능함.
 * AOP 설정 : AdminLoginAspect 클래스. adminCheck 메서드
 *    1. 로그아웃상태 : 로그인 하세요. login페이지 이동
 *    2. 관리자 로그인이 아닌 경우 : 관리자만 가능한 거래입니다. mypage 이동
 * 
 */
import logic.User;

@Controller
@RequestMapping("admin")
public class AdminController {
	@Autowired
	private ShopService service;
	@RequestMapping("list")
	public ModelAndView userlist(String sort, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		//list : db에 등록된 모든 회원정보 저장 목록
		List<User> list = service.userlist(); //전체 회원목록
		mav.addObject("list", list);
		if(sort != null ) {
			switch (sort){
				/*Collections.sort(list,new Comparator<User>(){
				 * @Override
				 * public int compare(User u1, User u2){
				 * 	reutrn u1.getUserid().compareTo(u2.getUserid());
				 * }
				 */
				case "10" : Collections.sort(list,(u1,u2)->u1.getUserid().compareTo(u2.getUserid()));
				break;
				case "11" : Collections.sort(list,(u1,u2)->u2.getUserid().compareTo(u1.getUserid()));
				break;
				case "20" : Collections.sort(list,(n1,n2)->n1.getUsername().compareTo(n2.getUsername()));
				break;
				case "21" : Collections.sort(list,(n1,n2)->n2.getUsername().compareTo(n1.getUsername()));
				break;
				case "30" : Collections.sort(list,(p1,p2)->p1.getPhoneno().compareTo(p2.getPhoneno()));
				break;
				case "31" : Collections.sort(list,(p1,p2)->p2.getPhoneno().compareTo(p1.getPhoneno()));
				break;
				case "40" : Collections.sort(list,(u1,u2)->u1.getBirthday().compareTo(u2.getBirthday()));
				break;
				case "41" : Collections.sort(list,(u1,u2)->u2.getBirthday().compareTo(u1.getBirthday()));
				break;
				case "50" : Collections.sort(list,(e1,e2)->e1.getEmail().compareTo(e2.getEmail()));
				break;
				case "51" : Collections.sort(list,(e1,e2)->e2.getEmail().compareTo(e1.getEmail()));
				break;
			}
		}
		mav.addObject("lilst",list);
		return mav;
	}
	@RequestMapping("mailForm")
	public ModelAndView mailform(String[] idchks, HttpSession session) {
		//String[] idchks : idchks 파라미터의 값 여러개 가능. request.getParamaterValues("파라미터")
		ModelAndView mav = new ModelAndView("admin/mail");
		if(idchks == null || idchks.length == 0) {
			throw new LoginException("메일을 보낼 대상을 선택하세요","list");
		}
		List<User> list = service.getUserList(idchks);
		mav.addObject("list",list);
		return mav;
	}
	@RequestMapping("mail")
	public ModelAndView mail (Mail mail,HttpSession session) {
		ModelAndView mav = new ModelAndView("alert");
		Properties prop = new Properties();
		try {
			//mail.properties : resources 폴더에 생성
			//java, resources 폴더의 내용은 : WEB-INF/classes에 복사됨
			FileInputStream fis = new FileInputStream
					(session.getServletContext().getRealPath("/")+"/WEB-INF/classes/mail.properties");
			prop.load(fis);
			prop.put("mail.smtp.user",mail.getNaverid());
		} catch (IOException e) {
			e.printStackTrace();
		}
		mailSend(mail,prop);
		mav.addObject("message","메일 전송이 완료 되었습니다.");
		mav.addObject("url","list");
		return mav;
	}
	private boolean mailSend(Mail mail, Properties prop) {
		MyAuthenticator auth = new MyAuthenticator(mail.getNaverid(),mail.getNaverpw());
		Session session = Session.getInstance(prop,auth);
		MimeMessage msg =new MimeMessage(session);
		try {
			//보내는 메일
			msg.setFrom(new InternetAddress(mail.getNaverid() + "@naver.com"));
			//받는메일 정보
			List<InternetAddress> addrs = new ArrayList<InternetAddress>();
			String[] emails = mail.getRecipient().split(",");
			for(String email : emails) {
				try {
					addrs.add(new InternetAddress (new String(email.getBytes("utf-8"),"8859_1")));
				} catch (UnsupportedEncodingException ue) {
					ue.printStackTrace();
				}
			}
			InternetAddress[] arr = new InternetAddress[emails.length];
			for(int i=0;i<addrs.size();i++) {
				arr[i] = addrs.get(i);
			}
			msg.setRecipients(Message.RecipientType.TO,arr); //수신메일 설정
			msg.setSentDate(new Date());	//전송일자
			msg.setSubject(mail.getTitle()); //제목
			MimeMultipart multipart = new MimeMultipart(); //s내용,첨부파일...
			MimeBodyPart message = new MimeBodyPart();
			message.setContent(mail.getContents(),mail.getMtype());
			multipart.addBodyPart(message);
			//첨부된 파일의 내용 중 한개
			for(MultipartFile mf : mail.getFile1()) {
				if((mf != null) && (!mf.isEmpty())) {
					multipart.addBodyPart(bodyPart(mf));
				}
			}
			msg.setContent(multipart);
			//msg : 메일 전체 객체 (전송메일주소, 수신메일주소들, 제목, 전송일자, 내용, 첨부파일들)
			Transport.send(msg);
			return true;
		} catch(MessagingException me) {
			me.printStackTrace();
		}
		return false;
	}
	private BodyPart bodyPart(MultipartFile mf) {
		MimeBodyPart body = new MimeBodyPart();
		String orgFile = mf.getOriginalFilename();
		String path = "c:/mailupload/";
		File f1 = new File(path);
		if(!f1.exists()) f1.mkdirs();
		File f2 = new File(path + orgFile);
		try {
			mf.transferTo(f2); //파일업로드
			body.attachFile(f2); //이메일 첨부
			body.setFileName(new String(orgFile.getBytes("UTF-8"),"8859_1")); //첨부된 파일의 파일명
		} catch (Exception e) {
			e.printStackTrace();
		}
		return body;
	}
	//AdminControoler 클래스의 내부 클래스로 구현
	private final class MyAuthenticator extends Authenticator{
		private String id;
		private String pw;
		public MyAuthenticator(String id, String pw) {
			this.id = id;
			this.pw = pw;
		}
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(id,pw);
		}
	}
}
