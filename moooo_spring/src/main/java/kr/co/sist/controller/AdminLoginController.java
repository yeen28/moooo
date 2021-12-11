package kr.co.sist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import kr.co.sist.service.AdminLoginService;
import kr.co.sist.vo.AdminLoginVO;
import kr.co.sist.vo.UpdateAdminPassVO;

@Controller
@RequestMapping("admin/")
@SessionAttributes("admin_id")
public class AdminLoginController {
	
	@Autowired(required = false)
	private AdminLoginService als;
	
	/**
	 * 로그인 화면 ( 관리자 접속시 첫 화면 )
	 * @return
	 */
	@RequestMapping(value="login.do",method=GET)
	public String loginForm() {
		String jspPage="admin/login";
		return jspPage;
	} //loginForm
	
	/**
	 * 로그인 처리
	 * @param aVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="login_proc.do",method=POST)
	public String loginProc(AdminLoginVO aVO,Model model) {
		String jspPage="";
		
		if( als.procLogin(aVO) ) {  //로그인 성공
			model.addAttribute("admin_id", aVO.getAdmin_id());
			jspPage="admin/main";
			
		} else { //로그인 실패
			model.addAttribute("msg", "아이디, 비밀번호를 확인해주세요.");
			jspPage="admin/login";
		}//end else
		
		return jspPage;
	} //loginProc
	
	/**
	 * 로그아웃 (세션 삭제)
	 */
	@RequestMapping(value="logout.do",method=GET)
	public String logout(SessionStatus ss, Model model) {
		if( !ss.isComplete() ) {
			ss.setComplete(); //세션 삭제
		}//end if
		
		return "admin/login";
	} //logoutProc

	/**
	 * 관리자 메인화면 폼
	 */
	@RequestMapping(value="main_form.do",method=GET)
	public String mainForm(HttpSession session, Model model) {
		String jspPage="";
		
		// session이 있으면 메인화면을 보여주고
		if(session.getAttribute("admin_id") != null) {
			jspPage="admin/main";
		} else {
			// session이 없으면 login페이지로 이동
			jspPage="admin/login";
		} //end else
		
		return jspPage;
	} //mainForm

	/**
	 * 비밀번호 변경 폼
	 */
	@RequestMapping(value="change_pass.do",method=GET)
	public String changePassForm() {
		String jspPage="admin/change_pass";
		return jspPage;
	} //changePassForm

	/**
	 * 비밀번호 변경 처리
	 */
	@RequestMapping(value="change_pass_proc.do",method=GET)
	public String changePassProc(UpdateAdminPassVO uVO, Model model) throws DataAccessException, SQLException {
		String jspPage="admin/change_pass";
		
		if( als.changePass(uVO) ) { //비밀번호 변경 성공
			model.addAttribute("msg", "비밀번호가 변경됐습니다.");
		} else { // 실패
			model.addAttribute("msg", "비밀번호를 확인해주세요.");
		} //end else
		
		return jspPage;
	} //changePassProc
	
	
	////////////////////////////////// 예외처리 //////////////////////////////////////////////
	@ExceptionHandler(SQLException.class)
	public ModelAndView seErr(SQLException se) {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("error/error"); //에러가 발생하면 만들어둔 에러페이지를 보여줌.
		mav.addObject("se", se); //jsp로 에러메시지 전달
		return mav;
	} //seErr
	
	@ExceptionHandler(DataAccessException.class)
	public ModelAndView daeErr(DataAccessException dae) {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("error/error"); //에러가 발생하면 만들어둔 에러페이지를 보여줌.
		mav.addObject("dae", dae); //jsp로 에러메시지 전달
		return mav;
	} //daeErr
	
} //class
