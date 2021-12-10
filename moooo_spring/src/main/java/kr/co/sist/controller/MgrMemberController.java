package kr.co.sist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.sql.SQLException;

import kr.co.sist.service.MgrMemberService;

@Controller
@RequestMapping("admin/")
public class MgrMemberController {

	@Autowired
	private MgrMemberService ms;
	
	/**
	 * 회원관리 폼
	 */
	@RequestMapping(value="mgr_user.do",method=GET)
	public String memberForm(Model model) {
		String jspPage="admin/mgr_user";
		
		try {
			model.addAttribute("memberList", ms.searchMember());
		} catch(DataAccessException dae) {  }
		
		return jspPage;
	} //memberForm

//	@RequestMapping(value="mgr/member_proc.do",method=GET)
//	public String memberProc(MemberVO mVO, Model model) {
//		String jspPage="";
//		return jspPage;
//	} //memberProc
	
	
	///////////////////////// 예외처리 ///////////////////////////////////
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
	
}//class
