package kr.co.sist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import kr.co.sist.service.MemberService;
import kr.co.sist.vo.MemberVO;
import kr.co.sist.vo.UpdatePassVO;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("user/login/")
@SessionAttributes("user_id")
public class MemberController {

	@Autowired
	private MemberService ms;
	
	/**
	 * 로그인 폼
	 * @return
	 */
	@RequestMapping(value="login.do",method=GET)
	public String loginForm() {
		String jspPage="user/login/login";
		return jspPage;
	} //loginForm
	
	/**
	 * 로그인 처리
	 * @param mVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="login_proc.do",method=GET)
	public String loginProc(MemberVO mVO, Model model) {
		String page="user/login/process/login_process";
		
		if( ms.loginProc(mVO) ) { //로그인 성공
			model.addAttribute("user_id",mVO.getUser_id());
			
		} else { //로그인 실패
			model.addAttribute("msg", "아이디, 비밀번호를 확인해주세요.");
		} //end else
		
		return page;
	} //loginProc
	
	@RequestMapping(value="logout_proc.do",method=GET)
	public String logoutProc(SessionStatus ss,MemberVO mVO, Model model) {
		if( !ss.isComplete() ) {
			ss.setComplete(); //세션 삭제
		}//end if
		
		return "redirect:../../index.do";
	} //logoutProc
	
	@RequestMapping(value="sign_up.do",method=GET)
	public String signUpForm() {
		String page="user/login/sign_up";
		return page;
	} //signUpForm
	
	@RequestMapping(value="id_dup.do",method=GET)
	public String idDuplication(String id, Model model) {
		String page="user/login/id_dup";
		return page;
	} //idDuplication
	
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
	
	@RequestMapping(value="find_form.do",method=GET)
	public String findForm() {
		String page="user/login/find";
		return page;
	} //findForm
	
	@RequestMapping(value="find_id_proc.do",method=GET)
	public String findId(MemberVO mVO, Model model) {
		String page="user/login/process/find_id_process";
		return page;
	} //findId
	
	@RequestMapping(value="find_pass_proc.do",method=GET)
	public String findPass(MemberVO mVO, Model model) {
		String page="user/login/process/find_pass_process";
		return page;
	} //findPass
	
	@RequestMapping(value="change_pass_form.do",method=GET)
	public String changePassForm() {
		String page="user/login/change_password";
		return page;
	} //changePassForm
	
	@RequestMapping(value="change_pass_proc.do",method=GET)
	public String changePassProc(UpdatePassVO uVO, Model model) {
		String page="user/login/process/change_pass_process";
		return page;
	} //changePassProc
	
	@RequestMapping(value="leave_form.do",method=GET)
	public String leaveForm(HttpSession session) {
		String jspPage="";
		
		// 세션이 있는지 확인
		if(session.getAttribute("user_id") == null) { 
			jspPage="../../index";
		} else {
			jspPage="user/login/leave";
		}
		
		return jspPage;
	} //leaveForm
	
	@RequestMapping(value="leave_proc.do",method=GET)
	public String leaveProc() {
		String page="user/login/process/leave_process";
		return page;
	} //leaveProc
	
} //class
