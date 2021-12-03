package kr.co.sist.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.sist.vo.MemberVO;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("user/")
public class MemberController {

	/**
	 * 로그인 폼
	 * @return
	 */
	@RequestMapping(value="login/login.do",method=GET)
	public String loginForm() {
		String page="user/login/login";
		return page;
	} //loginForm
	
	/**
	 * 로그인 처리
	 * @param mVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="login/login_proc.do",method=GET)
	public String loginProc(MemberVO mVO, Model model) {
		String page="user/login/process/login_process";
		return page;
	} //loginProc
	
	@RequestMapping(value="login/sign_up.do",method=GET)
	public String addMemberForm() {
		String page="user/login/sign_up";
		return page;
	} //loginProc
	
}
