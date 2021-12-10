package kr.co.sist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import kr.co.sist.service.MemberService;
import kr.co.sist.vo.MemberVO;
import kr.co.sist.vo.UpdateUserPassVO;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

@Controller
@RequestMapping("user/login/")
@SessionAttributes("user_id")
public class MemberController {

	@Autowired
	private MemberService ms;
	
	/**
	 * 로그인 폼
	 */
	@RequestMapping(value="login.do",method=GET)
	public String loginForm() {
		String jspPage="user/login/login";
		return jspPage;
	} //loginForm
	
	/**
	 * 로그인 처리
	 */
	@RequestMapping(value="login_proc.do",method=POST)
	public String loginProc(MemberVO mVO, Model model) {
		String page="user/login/process/login_process";
		
		if( ms.loginProc(mVO) ) { //로그인 성공
			model.addAttribute("user_id",mVO.getUser_id());
			
		} else { //로그인 실패
			model.addAttribute("msg", "아이디, 비밀번호를 확인해주세요.");
		} //end else
		
		return page;
	} //loginProc
	
	/**
	 * 로그아웃 (세션 삭제)
	 */
	@RequestMapping(value="logout_proc.do",method=GET)
	public String logoutProc(SessionStatus ss,MemberVO mVO, Model model) {
		if( !ss.isComplete() ) {
			ss.setComplete(); //세션 삭제
		}//end if
		
		return "redirect:/index.do";
	} //logoutProc
	
	/**
	 * 회원가입 폼
	 */
	@RequestMapping(value="sign_up.do",method=GET)
	public String signUpForm() {
		String page="user/login/sign_up";
		return page;
	} //signUpForm
	
	/**
	 * 아이디 중복확인
	 */
	@RequestMapping(value="id_dup.do",method=GET)
	public String idDuplication(String id, Model model) {
		String page="user/login/id_dup";
		return page;
	} //idDuplication
	
	/**
	 * 회원가입 처리
	 */
	@RequestMapping(value="sign_up_proc.do",method=GET)
	public String signUpProc(MemberVO mVO, Model model) {
		String page="user/login/sign_up";
		
		if( ms.signUpProc(mVO) ) { //회원가입 성공
			model.addAttribute("nickname",mVO.getNickname());
		} else { //회원가입 실패
			page="redirect:/common/error/error.jsp";
		}//end else
		
		return page;
	} //signUpProc
	
	/**
	 * 아이디/비밀번호 찾기 폼
	 */
	@RequestMapping(value="find_form.do",method=GET)
	public String findForm() {
		String page="user/login/find";
		return page;
	} //findForm
	
	/**
	 * 아이디 찾기 처리
	 */
	@RequestMapping(value="find_id_proc.do",method=POST)
	public String findId(MemberVO mVO, Model model) {
		String page="user/login/process/find_id_process";
		
		try {
			mVO.setUser_id( ms.findIdProc(mVO) );
			model.addAttribute("mVO", mVO);
		} catch(DataAccessException dae) {  }
		
		return page;
	} //findId
	
	/**
	 * 비밀번호 찾기 처리
	 */
	@RequestMapping(value="find_pass_proc.do",method=POST)
	public String findPass(MemberVO mVO, Model model) throws SQLException {
		String page="user/login/process/find_pass_process";
		
		try {
			mVO.setPass( ms.findPassProc(mVO) );
			model.addAttribute("mVO", mVO);
		} catch(DataAccessException dae) {  }
		
		return page;
	} //findPass
	
	/**
	 * 비밀번호 변경 폼
	 */
	@RequestMapping(value="change_pass_form.do",method=GET)
	public String changePassForm() {
		String page="user/login/change_password";
		return page;
	} //changePassForm
	
	/**
	 * 비밀번호 변경 처리
	 */
	@RequestMapping(value="change_pass_proc.do",method=GET)
	public String changePassProc(UpdateUserPassVO uVO, Model model) throws SQLException {
		String page="user/login/process/change_pass_process";
		
		if( ms.changePass(uVO) ) { //비밀번호 변경 성공
			model.addAttribute("msg", "비밀번호를 변경했습니다.");
		}
		
		return page;
	} //changePassProc
	
	/**
	 * 회원탈퇴 폼
	 */
	@RequestMapping(value="leave_form.do",method=GET)
	public String leaveForm(HttpSession session, Model model) throws SQLException {
		String jspPage="";
		
		// 세션이 있는지 확인
		if(session.getAttribute("user_id") == null) { 
			jspPage="redirect:/index.do";
		} else {
			jspPage="user/login/leave";
			model.addAttribute("nickname", ms.getUserNickname((String)session.getAttribute("user_id")));
		} //end else
		
		return jspPage;
	} //leaveForm
	
	/**
	 * 회원탈퇴 처리
	 */
	@RequestMapping(value="leave_proc.do",method=GET)
	public String leaveProc() {
		String page="user/login/process/leave_process";
		return page;
	} //leaveProc
	
	///////////////////// 예외처리 /////////////////////////////
	@ExceptionHandler(SQLException.class)
	public ModelAndView sqlErr(SQLException se) {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("error/error");
		mav.addObject("se",se);
		return mav;
	} //sqlErr
	
	@ExceptionHandler(DataAccessException.class)
	public ModelAndView daErr(DataAccessException dae) {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("error/error");
		mav.addObject("dae",dae);
		return mav;
	} //daErr
	
} //class
