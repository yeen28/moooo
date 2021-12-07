package kr.co.sist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.sist.service.AdminLoginService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("mgr/")
public class AdminLoginController {
	
	@Autowired(required = false)
	private AdminLoginService als;
	
	@RequestMapping(value="main_form.do",method=GET)
	public String mainForm() {
		return "";
	} //mainForm

//	@RequestMapping(value="login_form.do",method=GET)
//	public String loginForm() {
//		
//	}
//
//	@RequestMapping(value="login_proc.do",method=GET)
//	public String loginProc(AdminLoginVO aVO,Model model) {
//		
//	}
//
//	@RequestMapping(value="change_pass_form.do",method=GET)
//	public String changePassForm() {
//		
//	}
//
//	@RequestMapping(value="change_pass_proc.do",method=GET)
//	public String changePassProc(AdminLoginVO aVO, Model model) {
//		
//	}
	
} //class
