package kr.co.sist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.sist.service.MainService;
import kr.co.sist.service.WantService;
import kr.co.sist.vo.WantBuyVO;
import kr.co.sist.vo.WantSellVO;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

@Controller
public class WantController {
	
	@Autowired
	private WantService ws;
	@Autowired
	private MainService ms;

	/**
	 * '사고싶어요' 글 목록
	 */
	@RequestMapping(value="want_buy/want_buy.do",method=GET)
	public String wantBuy(String category,String page,Model model) {
		String jspPage="want_buy/want_buy";
		//int nowPage=ws.nowPage(page);
		
		int categoryNum=0;
		try {
			categoryNum=Integer.parseInt(category);
		}catch(NumberFormatException nfe) {
			categoryNum=0;
		}
		
		ws.searchBuyList(categoryNum, page);
		model.addAttribute("list",ws.searchBuyList(categoryNum, page));
		
		return jspPage;
	} //wantBuy

	/**
	 * '사고싶어요' 상세페이지
	 */
	@RequestMapping(value="want_buy/want_buy_detail.do",method=GET)
	public String wantBuyDetail(int buy_id,Model model){
		String jspPage="want_buy/want_buy_detail";
		
		model.addAttribute("buy",ws.getWantBuyDetail(buy_id));
		
		return jspPage;
	} //wantBuyDetail
	
	/**
	 * '사고싶어요' 글 추가 또는 수정 폼
	 */
	@RequestMapping(value="want_buy/wb_write.do",method=GET)
	public String wantBuyWriteForm(HttpServletRequest request,HttpSession session,WantBuyVO wVO,Model model) throws DataAccessException, SQLException {
		String jspPage="want_buy/wb_write";
		
		if(wVO.getBuy_id() != 0) { // 수정하는 경우
			model.addAttribute("buy",ws.getWantBuyDetail(wVO.getBuy_id()));
		} //end if
		model.addAttribute("categoryList", ms.getCategory());
		
		return jspPage;
	} //wantBuyWriteForm
	
	/**
	 * 글 추가 또는 수정 처리
	 */
	@RequestMapping(value="want_buy/wb_write_proc.do",method=POST)
	public String wantBuyWriteProc(HttpServletRequest request,HttpSession session,WantBuyVO wVO,Model model) throws DataAccessException {
		String jspPage="want_buy/wb_write";
		
		String ip_addr=request.getRemoteAddr();
		String session_id=(String)session.getAttribute("user_id");
		wVO.setIp_addr(ip_addr);
		wVO.setUser_id(session_id);
		
		if(wVO.getBuy_id() == 0) { // 추가하는 경우
			ws.addBuy(wVO); // 예외가 발생하지 않으면 밑으로 흘러감.
			model.addAttribute("url", "want_buy.do");
		} else { // 수정하는 경우
			if( ws.updateBuy(wVO) ) { // 글 수정 성공
				model.addAttribute("url", "want_buy.do?buy_id="+wVO.getBuy_id());
			} else { //글 수정 실패
				System.out.println("글 수정 실패");
			}//end else
		}
		model.addAttribute("msg", "팔아요 글이 등록됐습니다.");
		
		return jspPage;
	} //wantBuyWriteProc
	
	/**
	 * 글 삭제
	 */
	@RequestMapping(value="want_buy/want_buy_delete.do",method=GET)
	public String wantBuyDelete(int buy_id, HttpSession session, Model model) throws SQLException {
		String jspPage="want_buy/want_buy_delete";
		
		ws.deleteBuy(buy_id, (String)session.getAttribute("user_id"));
		
		return jspPage;
	} //wantBuyDelete
	
	/**
	 * '팔아요' 글 목록
	 */
	@RequestMapping(value="want_sell/want_sell.do",method=GET)
	public String wantSell(String category,String page,Model model) {
		String jspPage="want_sell/want_sell";
		
		int categoryNum=0;
		try {
			categoryNum=Integer.parseInt(category);
		}catch(NumberFormatException nfe) {
			categoryNum=0;
		}
		
		ws.searchSellList(categoryNum, page);
		model.addAttribute("list",ws.searchSellList(categoryNum, page));
		
		return jspPage;
	} //wantSell
	
	/**
	 * '팔아요' 상세페이지
	 */
	@RequestMapping(value="want_sell/want_sell_detail.do",method=GET)
	public String wantSellDetail(int sell_id,Model model){
		String jspPage="want_sell/want_sell_detail";
		
		model.addAttribute("sell",ws.getWantSellDetail(sell_id));
		
		return jspPage;
	} //wantSellDetail
	
	/**
	 * '팔아요' 글 추가 또는 수정 폼
	 */
	@RequestMapping(value="want_sell/ws_write.do",method=GET)
	public String wantSellWriteForm(HttpServletRequest request,HttpSession session,WantSellVO wVO,Model model) throws DataAccessException, SQLException {
		String jspPage="want_sell/ws_write";
		
		if(wVO.getSell_id() != 0) { // 수정하는 경우
			model.addAttribute("sell",ws.getWantSellDetail(wVO.getSell_id()));
		} //end if
		model.addAttribute("categoryList", ms.getCategory());
		
		return jspPage;
	} //wantSellWriteForm
	
	/**
	 * 글 추가 또는 수정 처리
	 */
	@RequestMapping(value="want_sell/ws_write_proc.do",method=POST)
	public String wantSellWriteProc(HttpServletRequest request,HttpSession session,WantSellVO wVO,Model model) throws DataAccessException {
		String jspPage="want_sell/ws_write";
		
		String ip_addr=request.getRemoteAddr();
		String session_id=(String)session.getAttribute("user_id");
		wVO.setIp_addr(ip_addr);
		wVO.setUser_id(session_id);
		
		if(wVO.getSell_id() == 0) { // 추가하는 경우
			ws.addSell(wVO); // 예외가 발생하지 않으면 밑으로 흘러감.
			model.addAttribute("url", "want_sell.do");
		} else { // 수정하는 경우
			if( ws.updateSell(wVO) ) { // 글 수정 성공
				model.addAttribute("url", "want_sell.do?sell_id="+wVO.getSell_id());
			} else { //글 수정 실패
				System.out.println("글 수정 실패");
			}//end else
		}
		model.addAttribute("msg", "팔아요 글이 등록됐습니다.");
		
		return jspPage;
	} //wantSellWriteProc
	
	/**
	 * 글 삭제
	 */
	@RequestMapping(value="want_sell/want_sell_delete.do",method=GET)
	public String wantSellDelete(int sell_id, HttpSession session, Model model) throws SQLException {
		String jspPage="want_sell/want_sell_delete";
		
		ws.deleteSell(sell_id, (String)session.getAttribute("user_id"));
		
		return jspPage;
	} //wantSellDelete
	
	/**
	 * 신고이유 얻기
	 */
	@RequestMapping(value="report.do",method=GET)
	public String reportReason(Model model) throws SQLException {
		String jspPage="report/report";
		
		model.addAttribute("reportList", ws.getReportReasonList());
		
		return jspPage;
	} //reportReason
	
	/**
	 * 신고처리
	 */
	@RequestMapping(value="want_sell/report_proc.do",method=GET)
	public String reportProc(Model model) throws SQLException {
		String jspPage="redirect:/want_sell/want_sell.do";
		
		model.addAttribute("msg", "신고되었습니다.");
		
		return jspPage;
	} //reportProc
	
	
	/////////////////////// 예외 처리 ////////////////////////////
	@ExceptionHandler(DataAccessException.class)
	public ModelAndView daeErr( DataAccessException dae ) {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("error/error");
		mav.addObject("dae", dae);
		return mav;
	} //daeErr
	
	@ExceptionHandler(SQLException.class)
	public ModelAndView sqlErr( SQLException se ) {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("error/error");
		mav.addObject("se", se);
		return mav;
	} //sqlErr
	
} //class
