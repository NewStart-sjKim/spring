package controller;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import exception.LoginException;
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
}
