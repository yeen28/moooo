package kr.co.sist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.co.sist.service.MainService;
import kr.co.sist.service.WantService;
import kr.co.sist.vo.ReportVO;
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
		
		int categoryNum=0;
		try {
			categoryNum=Integer.parseInt(category);
		}catch(NumberFormatException nfe) {
			categoryNum=0;
		}
		
		ws.searchBuyList(categoryNum, page);
		model.addAttribute("list",ws.searchBuyList(categoryNum, page));
		model.addAttribute("pagination", ws.getPagination(category, page));
		
		return jspPage;
	} //wantBuy
	
	/**
	 * 글 검색
	 * ( 검색된 글이 많을 때 페이지네이션으로 넘어가게 구현하기 )
	 */
	@RequestMapping(value="want_buy/search.do", method=GET)
	public String searchBuy(String searchWord, Model model) throws SQLException {
		String jspPage="want_buy/want_buy";
		
		model.addAttribute("list", ws.searchWordBuyList(searchWord));
//		model.addAttribute("pagination", ws.getPagination(0, page));
		
		return jspPage;
	} //search

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
	@RequestMapping(value="want_buy/wb_write.do",method={ GET, POST })
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
		
		if(wVO.getBuy_id() != 0) { // 추가하는 경우
			if( ws.updateBuy(wVO) ) { // 글 수정 성공
				model.addAttribute("url", "want_buy.do?buy_id="+wVO.getBuy_id());
			} else { //글 수정 실패
				System.out.println("글 수정 실패");
			}//end else
		} else { // 수정하는 경우
			ws.addBuy(wVO); // 예외가 발생하지 않으면 밑으로 흘러감.
			model.addAttribute("url", "want_buy.do");
		}//end else
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
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
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
		model.addAttribute("pagination", ws.getPagination(category, page));
		
		return jspPage;
	} //wantSell
	
	/**
	 * 글 검색
	 * ( 검색된 글이 많을 때 페이지네이션으로 넘어가게 구현하기 )
	 */
	@RequestMapping(value="want_sell/search.do", method=GET)
	public String searchSell(String searchWord, Model model) throws SQLException {
		String jspPage="want_sell/want_sell";
		
		model.addAttribute("list", ws.searchWordSellList(searchWord));
//		model.addAttribute("pagination", ws.getPagination(0, page));
		
		return jspPage;
	} //search
	
	/**
	 * '팔아요' 상세페이지
	 */
	@RequestMapping(value="want_sell/want_sell_detail.do",method=GET)
	public String wantSellDetail(int sell_id, HttpSession session, Model model){
		String jspPage="want_sell/want_sell_detail";
		
		model.addAttribute("sell",ws.getWantSellDetail(sell_id));
		model.addAttribute( "isInterest", ws.isInterest(sell_id, (String)session.getAttribute("user_id")) );
		
		return jspPage;
	} //wantSellDetail
	
	/**
	 * '팔아요' 글 추가 또는 수정 폼
	 */
	@RequestMapping(value="want_sell/ws_write.do",method= { GET, POST })
	public String wantSellWriteForm(HttpServletRequest request,HttpSession session,WantSellVO wVO,Model model) 
				throws DataAccessException, SQLException {
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
		
		if(wVO.getSell_id() != 0) { // 수정하는 경우
			if( ws.updateSell(wVO) ) { // 글 수정 성공
				model.addAttribute("url", "want_sell.do?sell_id="+wVO.getSell_id());
			} else { //글 수정 실패
				System.out.println("글 수정 실패");
			}//end else
		} else { // 추가하는 경우
			ws.addSell(wVO); // 예외가 발생하지 않으면 밑으로 흘러감.
			model.addAttribute("url", "want_sell.do");
		}
		model.addAttribute("msg", "팔아요 글이 등록됐습니다.");
		
		return jspPage;
	} //wantSellWriteProc
	
	/**
	 * 글 삭제
	 */
	@RequestMapping(value="want_sell/want_sell_delete.do",method=GET)
	public String wantSellDelete(@RequestParam(value="sell_id", defaultValue = "0")String sell_id, HttpSession session, Model model) throws SQLException {
		String jspPage="want_sell/want_sell_delete";
		
		int code=0;
		try {
			code=Integer.parseInt(sell_id);
		} catch(NumberFormatException nfe) {
			code=0;
		} //end catch
		
		ws.deleteSell(code, (String)session.getAttribute("user_id"));
		
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
	public String reportProc(ReportVO rVO, HttpSession session, String sell_id, Model model) throws SQLException {
		String jspPage="redirect:/want_sell/want_sell_detail.do?sell_id="+sell_id;
		
		rVO.setUser_id((String)session.getAttribute("user_id"));
		
		model.addAttribute("url", "/want_sell/want_sell_detail.do?sell_id=");
		if( 	ws.chkBeforeReport(rVO) ) { //신고할 수 없음
			model.addAttribute("msg", "이미 신고했습니다.");
			return jspPage;
		} //end if
		
		if( ws.reportProc(rVO) ) { //성공
			model.addAttribute("msg", "신고되었습니다.");
		} else { //실패
			model.addAttribute("msg", "실패.");
			//jspPage="error/error";
		} //end else
		
		return jspPage;
	} //reportProc
	
	/**
	 * 관심글로 설정 | 해제
	 */
	@RequestMapping(value="/want_sell/interest.do",method=GET)
	public String interestProc(@RequestParam(value="sell_id",defaultValue = "0")String sell_id, HttpSession session, String interest) {
		int code=0;
		try {
			code=Integer.parseInt(sell_id);
		} catch(NumberFormatException nfe) {
			code=0;
		} //end catch
		
		String jspPage="redirect:/want_sell/want_sell_detail.do?sell_id="+sell_id;
		
		if( "y".equals(interest) ) { //관심글에서 해제하기
			ws.updateInterest(code, (String)session.getAttribute("user_id"), "remove");
			
		} //end if
		
		if( "n".equals(interest) ) { //관심글로 설정하기
			ws.updateInterest(code, (String)session.getAttribute("user_id"), "add");
		} //end else
		
		return jspPage;
	} //interestProc
	
	
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
