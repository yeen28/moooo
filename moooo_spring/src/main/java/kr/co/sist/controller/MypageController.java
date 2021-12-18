package kr.co.sist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.sist.service.MypageService;
import kr.co.sist.service.WantService;
import kr.co.sist.vo.MemberVO;
import kr.co.sist.vo.WantBuyVO;
import kr.co.sist.vo.WantSellVO;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("user/mypage/")
public class MypageController {

	@Autowired
	private MypageService ms;
	@Autowired
	private WantService ws;
	
	/**
	 * ��������
	 */
	@RequestMapping(value="mypage_form.do",method=GET)
	public String mypageForm(HttpSession session,Model model) throws SQLException {
		String jspPage="user/mypage/mypage";
		
		String user_id=(String)session.getAttribute("user_id");

		if(user_id == null || user_id =="") {
			return "user/login/login";
		} //end if
		
		MemberVO mVO=ms.getMypageInfo(user_id);
		model.addAttribute("mVO", mVO);
		
		return jspPage;
	} //mypageForm

	/**
	 * ���������� ������Ʈ ó��
	 */
	@RequestMapping(value="mypage_update.do",method= POST)
	public String updateMypage(HttpServletRequest request, MemberVO mVO,Model model) throws IOException, SQLException {
		String jspPage="redirect:/user/mypage/mypage_form.do";
		
		ms.updateMypage(request, mVO);
		
		model.addAttribute("msg", "���������߽��ϴ�.");
		model.addAttribute("url", "/user/mypage/mypage_form.do");
		
		return jspPage;
	} //updateMypage

	/**
	 * ���� �� �� List
	 */
	@RequestMapping(value="ajax_sub/write_list_form.do",method=POST)
	public String writeListForm(HttpSession session, Model model) throws SQLException {
		String jspPage="user/mypage/ajax_sub/sell_buy_list";

		String user_id=(String)session.getAttribute("user_id");
		
		if(user_id == null || user_id =="") {
			return "user/login/login";
		} //end if
		
		List<WantBuyVO> buyList=ms.getWriteListBuy(user_id);
		model.addAttribute("buyList", buyList);
		List<WantSellVO> sellList=ms.getWriteListSell(user_id);
		model.addAttribute("sellList", sellList);
		
		return jspPage;
	} //writeListForm

	/**
	 * ���ɱ� List
	 */
	@RequestMapping(value="ajax_sub/interest_list.do",method=POST)
	public String interestList(HttpSession session, Model model) throws SQLException {
		String jspPage="user/mypage/ajax_sub/interest_list";
		
		String user_id=(String)session.getAttribute("user_id");
		List<WantSellVO> interestList=ms.getInterestList(user_id);
		model.addAttribute("interestList", interestList);
		
		return jspPage;
	} //interestList

//	/**
//	 * ���ɱ� ����
//	 */
//	@RequestMapping(value="/interest_cancel.do",method=GET)
//	public String interestProc(@RequestParam(value="sell_id",defaultValue = "0")String sell_id, HttpSession session) {
//		int code=0;
//		try {
//			code=Integer.parseInt(sell_id);
//		} catch(NumberFormatException nfe) {
//			code=0;
//		} //end catch
//		
//		String jspPage="redirect:/user/mypage/mypage_form.do";
//		
//		//���ɱۿ��� �����ϱ�
//		ws.updateInterest(code, (String)session.getAttribute("user_id"), "remove");
//		
//		return jspPage;
//	} //interestProc
	
	
	//////////////////////////////////////////////////////////////////////////////////
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
