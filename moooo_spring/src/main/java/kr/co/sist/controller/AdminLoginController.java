package kr.co.sist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
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
@RequestMapping("mgr/")
@SessionAttributes("admin_id")
public class AdminLoginController {
	
	@Autowired(required = false)
	private AdminLoginService als;
	
	/**
	 * 로그인 화면 ( 관리자 접속시 첫 화면 )
	 * @return
	 */
	@RequestMapping(value="login_form.do",method=GET)
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
	public String loginProc(AdminLoginVO aVO,Model model) throws SQLException {
		String jspPage="";
		
		if( als.procLogin(aVO) ) {  //로그인 성공
			model.addAttribute("admin_id", aVO.getAdmin_id());
			jspPage="redirect:admin/main.do";
			
		} else { //로그인 실패
			model.addAttribute("msg", "아이디, 비밀번호를 확인해주세요.");
			jspPage="admin/login";
		}//end else
		
		return jspPage;
	} //loginProc

	@RequestMapping(value="main_form.do",method=GET)
	public String mainForm(HttpSession session, Model model) {
		// session이 있으면 jsp를 보여주고,
		// session이 없으면 redirect로 login페이지로 이동
		String jspPage="admin/main";
		return jspPage;
	} //mainForm

	@RequestMapping(value="change_pass_form.do",method=GET)
	public String changePassForm() {
		String jspPage="admin/change_pass";
		return jspPage;
	} //changePassForm

	@RequestMapping(value="change_pass_proc.do",method=GET)
	public String changePassProc(UpdateAdminPassVO uVO, Model model) throws DataAccessException {
		String jspPage="admin/change_pass";
		
		if( als.changePass(uVO) ) { //비밀번호 변경 성공
			
		} else { // 실패
			
		} //end else
		
		return jspPage;
	} //changePassProc
	
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
