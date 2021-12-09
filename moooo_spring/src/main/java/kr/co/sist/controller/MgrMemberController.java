package kr.co.sist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.sql.SQLException;

import kr.co.sist.service.MgrMemberService;

@Controller
public class MgrMemberController {

	@Autowired
	private MgrMemberService ms;
	
	@RequestMapping(value="mgr/member_form.do",method=GET)
	public String memberForm(Model model) throws SQLException {
		String jspPage="admin/mgr_user";
		
		model.addAttribute("memberList", ms.searchMember());
		
		return jspPage;
	} //memberForm

//	@RequestMapping(value="mgr/member_proc.do",method=GET)
//	public String memberProc(MemberVO mVO, Model model) {
//		String jspPage="";
//		return jspPage;
//	} //memberProc
	
	@ExceptionHandler(SQLException.class)
	public ModelAndView sqlErr(SQLException se) {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("error/error");
		mav.addObject("se",se);
		return mav;
	} //sqlErr
	
}//class
