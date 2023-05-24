package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import exception.LoginException;
import logic.Cart;
import logic.Sale;
import logic.ShopService;
import logic.User;

@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	private ShopService service;
	
	@GetMapping("*") //설정되지 않은 모든 요청시 호출되는 메서드
	public ModelAndView join() {
		ModelAndView mav = new ModelAndView();
		mav.addObject(new User());
		return mav;
	}
	@PostMapping("join")
	public ModelAndView userAdd(@Valid User user, BindingResult bresult) {
		ModelAndView mav = new ModelAndView();
		if(bresult.hasErrors()) {
			mav.getModel().putAll(bresult.getModel());
			//reject 메서드 : global error에 추가
			bresult.reject("error.input.user");
			bresult.reject("error.input.check");
			return mav;
		}
		//정상 입력값 : 회원가입하기 => db의 useraccount 테이블에 저장
		try {
			service.userInsert(user);
			mav.addObject("user",user);
		}catch(DataIntegrityViolationException e) {
			//DataIntegrityViolationException : db에서  중복 key 오류시 발생되는 예외 객체
			e.printStackTrace();
			bresult.reject("error.duplicate.user"); //global 오류 등록
			mav.getModel().putAll(bresult.getModel());
			return mav;
		}
		mav.setViewName("redirect:login");
		return mav;
	}
	@PostMapping("login")
	public ModelAndView login(@Valid User user, BindingResult bresult, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User dbUser;
		if(bresult.hasErrors()) {
			mav.getModel().putAll(bresult.getModel());
			bresult.reject("error.login.input");
			return mav;
		}
		// 1. userid 맞는 User를 db에서 조회하기
		try {
			dbUser = service.selectUserOne(user.getUserid());
		} catch(EmptyResultDataAccessException e) { //조회된 데이터가 없는 경우 발생 예외
			e.printStackTrace();
			bresult.reject("error.login.id"); //아이디를 확안하세요
			mav.getModel().putAll(bresult.getModel());
			return mav;
		}
		//2. 비밀번호 검증 : 
		// 		일치 : session.setAttribute("loginUser",dbUser) => 로그인 정보
		//		불일치: 비밀번호를 확인하세요. 출력(error.login.password)
		
		//3. mypage로 페이지 이동 => 404 오류 발생(임시)
		if (user.getPassword().equals(dbUser.getPassword())) {
			 session.setAttribute("loginUser",dbUser);
			mav.setViewName("redirect:mypage?userid="+user.getUserid());
		} else {
			bresult.reject("error.login.password"); // 비밀번호를 확인하세요
			mav.getModel().putAll(bresult.getModel());
		}
		return mav;
	}
	/*
	 * AOP 설정하기 :
	 *  UserLoginAspect 클래스의 userIdCheck 메서드로 구현하기.
	 * 1. pointcut : UserController 클래스의 idCheck로 시작하는 메서드이고,
	 * 				 마지막 매개변수의 userid,session인 경우
	 * 2. 로그인 여부 검증
	 * 		- 로그인이 안된경우 로그인 후 거래하세요. 메세지 출력. login페이지 호출
	 * 3. admin이 아니면서, 로그인 아이디와 파라미터 userid값이 다른 경우
	 * 		- 본인만 거래 가능합니다. 메세지 출력. item/list 페이지 호출
	 */
	@RequestMapping("mypage")
	public ModelAndView idCheckMypage(String userid,HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = service.selectUserOne(userid);
		List<Sale> salelist = service.salelist(userid);
		mav.addObject("user", user); //회원정보데이터
		mav.addObject("salelist", salelist);  //주문목록
		return mav;
	}	
	//로그아웃
	//로그아웃 후 login 페이지로 이동
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:login";
	}
	//로그인 상태, 본인정보만 조회 검증=> AOP클래스(UserLoginAspect.userIdCheck() 검증)
	@GetMapping({"update","delete"})
	public ModelAndView idCheckUser(String userid, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = service.selectUserOne(userid);
		mav.addObject("user",user);
		return mav;
	}
	@PostMapping("update")
	public ModelAndView idCheckUpdate(@Valid User user, BindingResult bresult, String userid, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		//입력검증
		if(bresult.hasErrors()) {
			mav.getModel().putAll(bresult.getModel());
			bresult.reject("error.update.user");
			return mav;
		}
		User loginUser = (User)session.getAttribute("loginUser");
		if(!loginUser.getPassword().equals(user.getPassword())) {
			mav.getModel().putAll(bresult.getModel());
			bresult.reject("error.login.password");
			return mav;
		}
		//비밀번호 일치 => 데이터 수정
		try {
			service.userUpdate(user);
			if(loginUser.getUserid().equals(user.getUserid()))
			session.setAttribute("loginUser",user);
			mav.setViewName("redirect:mypage?userid=" + user.getUserid());
		}catch(Exception e) {
			e.printStackTrace();
			/*
			 * bresult.reject("error.duplicate.user"); //global 오류 등록
			 * mav.getModel().putAll(bresult.getModel()); return mav;
			 */
			throw new LoginException
			("고객 정보 수정 실패","update?userid=" + user.getUserid());
		}
		return mav;
	}
	/*
	 * 탈퇴 검증
	 * UserLoginAspect.userIdCheck() 메서드 실행 하도록 설정
	 * 
	 * 회원탈퇴
	 * 1. 파라미터 정보 저장.(userid, password)
	 * 		- 관리자인 경우 탈퇴 불가
	 * 2. 비밀번호 검증 => 로그인된 비밀번호 검증
	 * 		본인탈퇴 : 본인 비밀번호
	 * 		관리자가 타인 탈퇴 : 관리자 비밀번호
	 * 3. 비밀번호 불일치
	 * 		메세지 출력 후 delete 페이지 이동
	 * 4. 비밀번호 일치
	 * 		db에서 해당 사용자정보 삭제하기
	 * 		본인탈퇴 : 로그아웃, login 페이지 이동
	 * 		관리자탈퇴 : admin/list 페이지 이동 => 404 오류 발생
	 */
	@PostMapping("delete")
	public String idCheckDelete(String password, String userid, HttpSession session) {
		//관리자 탈퇴 불가
		if(userid.equals("admin"))
			throw new LoginException("관리자 탈퇴는 불가합니다.","mypage?userid=" + userid);
		//비밀번호 검증 : 로그인된 정보
		User loginUser = (User)session.getAttribute("loginUser");
		//password : 입력된 비밀번호
		//loginUser.getPassword() : 로그인 사용자의 비밀번호
		if(!password.equals(loginUser.getPassword())) {
			throw new LoginException("비밀번호를 확인하세요." ,"delete?userid=" + userid);
		}
		//비밀번호 일치 : 고객정보 제거
		try {
			service.userDelete(userid);
		}catch(DataIntegrityViolationException e) {
			throw new LoginException
			("주문정보가 존재합니다. 탈퇴 불가 합니다. 관리자 연락 요망","mypage?userid=" + userid);
		}catch(Exception e) {
			e.printStackTrace();
			throw new LoginException
			("탈퇴시 오류발생","delete?userid=" + userid);
		}
		//탈퇴 성공 : 회원정보를 제거 된 상태
		if(loginUser.getUserid().equals("admin")) { //관리자가 강제 탈퇴
			return "redirect:../admin/list";
		}else{				//본인 탈퇴.
			session.invalidate();
			return "redirect:login";
		}
	}
	/*
	 * UserLoginAspect. loginCheck() : UserController.loginCheck*(..)인 메서드
	 * 								   마지막 매개변수 HttpSession인 메서드
	 * 1. 로그인 검증 => AOP 클래스.
	 * 2. 파라미터값을 저장( 매개변수이용 )
	 * 3. 현재비밀번호와 입력된 비밀번호 검증
	 * 	  불일치 : 오류 메세지 출력. password 페이지 이동
	 * 4. 일치 : db 수정
	 * 5. 성공 : 로그인정보 변경, mypage 페이지 이동
	 * 	  실패 : 오류 메세지 출력. password 페이지 이동
	 */
	@PostMapping("password")
	public String passUpdate(String password , String chgpass, HttpSession session) {
		//비밀번호 검증
		User loginUser = (User)session.getAttribute("loginUser");
		if(!loginUser.getPassword().equals(password)) {
			throw new LoginException("불일치","password?userid="+loginUser.getUserid());
		}
		try {
			service.passUpdate(loginUser.getUserid(),chgpass);
			loginUser.setPassword(chgpass);
		}catch (Exception e){
			throw new LoginException
			("오류발생 관리자 연락요망","password?userid=" + loginUser.getUserid());
		}
		return "redirect:mypage?userid=" + loginUser.getUserid();
	}
}
