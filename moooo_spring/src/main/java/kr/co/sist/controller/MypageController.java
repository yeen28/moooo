package kr.co.sist.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.sist.service.MypageService;
import kr.co.sist.vo.MemberVO;
import kr.co.sist.vo.WantBuyVO;
import kr.co.sist.vo.WantSellVO;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("user/mypage/")
public class MypageController {

	@Autowired
	private MypageService ms;
	
	/**
	 * 정보변경
	 */
	@RequestMapping(value="mypage_form.do",method=GET)
	public String mypageForm(HttpSession session,Model model) throws SQLException {
		String jspPage="user/mypage/mypage";
		
		String user_id=(String)session.getAttribute("user_id");
//		JSONObject json=new JSONObject();
//		json.put("msg", name+"님 안녕하세요.");
//		model.addAttribute("jsonData", json.toJSONString());
		MemberVO mVO=ms.getMypageInfo(user_id);
		model.addAttribute("mVO", mVO);
		
		return jspPage;
	} //mypageForm

	@RequestMapping(value="mypage_update.do",method=GET)
	public String updateMypage(MemberVO mVO,Model model) {
		String jspPage="";
		
		return jspPage;
	} //updateMypage

	/**
	 * 내가 쓴 글 List
	 */
	@RequestMapping(value="ajax_sub/write_list_form.do",method=POST)
	public String writeListForm(HttpSession session, Model model) throws SQLException {
		String jspPage="user/mypage/ajax_sub/sell_buy_list";

		String user_id=(String)session.getAttribute("user_id");
		List<WantBuyVO> buyList=ms.getWriteListBuy(user_id);
		model.addAttribute("buyList", buyList);
		List<WantSellVO> sellList=ms.getWriteListSell(user_id);
		model.addAttribute("sellList", sellList);
		
		return jspPage;
	} //writeListForm

//	@RequestMapping(value="해당글로 이동.do",method=POST)
//	public String move() {
//		String jspPage="";
//		
//		return jspPage;
//	}

	@RequestMapping(value="delete_write_list.do",method=GET)
	public String deleteWirteList(List<Integer> listWrite,Model model) {
		String jspPage="";
		
		return jspPage;
	}

	/**
	 * 관심글 List
	 */
	@RequestMapping(value="ajax_sub/interest_list.do",method=POST)
	public String interestList(HttpSession session, Model model) throws SQLException {
		String jspPage="user/mypage/ajax_sub/interest_list";
		
		String user_id=(String)session.getAttribute("user_id");
		List<WantSellVO> interestList=ms.getInterestList(user_id);
		model.addAttribute("interestList", interestList);
		
		return jspPage;
	} //interestList

	@RequestMapping(value="interest_cancel.do",method=GET)
	public String interestCancel(List<Integer> listInterest,Model model) {
		String jspPage="";
		
		return jspPage;
	}
	
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
	
}
