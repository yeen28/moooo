package kr.co.sist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.sist.service.MainService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class MainController {
	
	@Autowired
	private MainService ms;
	
	@RequestMapping(value="/index.do",method=GET)
	public String index(HttpSession session, Model model) {// throws SQLException {
		
		try {
		model.addAttribute("listBuyTitle", ms.getWantBuyTitle());
		model.addAttribute("listSellTitle", ms.getWantSellTitle());
		model.addAttribute("howToUse", ms.getHowToUse());
		
		String user_id=(String)session.getAttribute("user_id");
		if(ms.isThereSession(user_id)) { //세션이 있으면 true
			model.addAttribute("phone", ms.getUserPhone(user_id));
		} //end if
		
		}catch(SQLException se) {}
		
		return "index";
	} //index
	
	@RequestMapping(value="/layout/side_left.do",method=GET)
	public String sideLeft(Model model) throws SQLException {
		model.addAttribute("listCategory", ms.getCategory());
		return "layout/side_left";
	} //sideLeft
	
	@ExceptionHandler(SQLException.class)
	public ModelAndView sqlErr(SQLException se) {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("exception/sql_err");
		mav.addObject("se", se);
		
		return mav;
	} //sqlErr
	
}//class
