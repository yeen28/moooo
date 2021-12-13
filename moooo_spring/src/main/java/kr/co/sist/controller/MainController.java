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
import org.springframework.dao.DataAccessException;

@Controller
public class MainController {
	
	@Autowired(required = false)
	private MainService ms;
	
	/**
	 * 사용자 메인화면
	 */
	@RequestMapping(value="/index.do",method=GET)
	public String index(Model model)  throws SQLException {
		
		model.addAttribute("listBuyTitle", ms.getWantBuyTitle());
		model.addAttribute("listSellTitle", ms.getWantSellTitle());
		model.addAttribute("howToUse", ms.getHowToUse());
		
		return "index";
	} //index
	
	/**
	 * 왼쪽 메뉴 카테고리 보여주기
	 */
	@RequestMapping(value="/layout/side_left.do",method=GET)
	public String sideLeft(HttpSession session, Model model) throws SQLException {
		model.addAttribute("listCategory", ms.getCategory());
		
		String user_id=(String)session.getAttribute("user_id");
		if( !"".equals(user_id)) { //세션이 있으면(로그인 했으면)
			try {
				model.addAttribute("nickname", ms.getUserNickname(user_id));
			}catch(DataAccessException dae) { }
		} //end if
		
		return "layout/side_left";
	} //sideLeft
	
	@RequestMapping(value="/about/about.do", method=GET)
	public void about() {
	}
	
	
	////////////////////// 예외처리 /////////////////////////////
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
