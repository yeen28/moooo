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
	 * '���;��' �� ���
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
	 * �� �˻�
	 * ( �˻��� ���� ���� �� ���������̼����� �Ѿ�� �����ϱ� )
	 */
	@RequestMapping(value="want_buy/search.do", method=GET)
	public String searchBuy(String searchWord, Model model) throws SQLException {
		String jspPage="want_buy/want_buy";
		
		model.addAttribute("list", ws.searchWordBuyList(searchWord));
//		model.addAttribute("pagination", ws.getPagination(0, page));
		
		return jspPage;
	} //search

	/**
	 * '���;��' ��������
	 */
	@RequestMapping(value="want_buy/want_buy_detail.do",method=GET)
	public String wantBuyDetail(int buy_id,Model model){
		String jspPage="want_buy/want_buy_detail";
		
		model.addAttribute("buy",ws.getWantBuyDetail(buy_id));
		
		return jspPage;
	} //wantBuyDetail
	
	/**
	 * '���;��' �� �߰� �Ǵ� ���� ��
	 */
	@RequestMapping(value="want_buy/wb_write.do",method={ GET, POST })
	public String wantBuyWriteForm(HttpServletRequest request,HttpSession session,WantBuyVO wVO,Model model) throws DataAccessException, SQLException {
		String jspPage="want_buy/wb_write";
		
		if(wVO.getBuy_id() != 0) { // �����ϴ� ���
			model.addAttribute("buy",ws.getWantBuyDetail(wVO.getBuy_id()));
		} //end if
		model.addAttribute("categoryList", ms.getCategory());
		
		return jspPage;
	} //wantBuyWriteForm
	
	/**
	 * �� �߰� �Ǵ� ���� ó��
	 */
	@RequestMapping(value="want_buy/wb_write_proc.do",method=POST)
	public String wantBuyWriteProc(HttpServletRequest request,HttpSession session,WantBuyVO wVO,Model model) throws DataAccessException {
		String jspPage="want_buy/wb_write";
		
		String ip_addr=request.getRemoteAddr();
		String session_id=(String)session.getAttribute("user_id");
		wVO.setIp_addr(ip_addr);
		wVO.setUser_id(session_id);
		
		if(wVO.getBuy_id() != 0) { // �߰��ϴ� ���
			if( ws.updateBuy(wVO) ) { // �� ���� ����
				model.addAttribute("url", "want_buy.do?buy_id="+wVO.getBuy_id());
			} else { //�� ���� ����
				System.out.println("�� ���� ����");
			}//end else
		} else { // �����ϴ� ���
			ws.addBuy(wVO); // ���ܰ� �߻����� ������ ������ �귯��.
			model.addAttribute("url", "want_buy.do");
		}//end else
		model.addAttribute("msg", "�Ⱦƿ� ���� ��ϵƽ��ϴ�.");
		
		return jspPage;
	} //wantBuyWriteProc
	
	/**
	 * �� ����
	 */
	@RequestMapping(value="want_buy/want_buy_delete.do",method=GET)
	public String wantBuyDelete(int buy_id, HttpSession session, Model model) throws SQLException {
		String jspPage="want_buy/want_buy_delete";
		
		ws.deleteBuy(buy_id, (String)session.getAttribute("user_id"));
		
		return jspPage;
	} //wantBuyDelete
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * '�Ⱦƿ�' �� ���
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
	 * �� �˻�
	 * ( �˻��� ���� ���� �� ���������̼����� �Ѿ�� �����ϱ� )
	 */
	@RequestMapping(value="want_sell/search.do", method=GET)
	public String searchSell(String searchWord, Model model) throws SQLException {
		String jspPage="want_sell/want_sell";
		
		model.addAttribute("list", ws.searchWordSellList(searchWord));
//		model.addAttribute("pagination", ws.getPagination(0, page));
		
		return jspPage;
	} //search
	
	/**
	 * '�Ⱦƿ�' ��������
	 */
	@RequestMapping(value="want_sell/want_sell_detail.do",method=GET)
	public String wantSellDetail(int sell_id, HttpSession session, Model model){
		String jspPage="want_sell/want_sell_detail";
		
		model.addAttribute("sell",ws.getWantSellDetail(sell_id));
		model.addAttribute( "isInterest", ws.isInterest(sell_id, (String)session.getAttribute("user_id")) );
		
		return jspPage;
	} //wantSellDetail
	
	/**
	 * '�Ⱦƿ�' �� �߰� �Ǵ� ���� ��
	 */
	@RequestMapping(value="want_sell/ws_write.do",method= { GET, POST })
	public String wantSellWriteForm(HttpServletRequest request,HttpSession session,WantSellVO wVO,Model model) 
				throws DataAccessException, SQLException {
		String jspPage="want_sell/ws_write";
		
		if(wVO.getSell_id() != 0) { // �����ϴ� ���
			model.addAttribute("sell",ws.getWantSellDetail(wVO.getSell_id()));
		} //end if
		model.addAttribute("categoryList", ms.getCategory());
		
		return jspPage;
	} //wantSellWriteForm
	
	/**
	 * �� �߰� �Ǵ� ���� ó��
	 */
	@RequestMapping(value="want_sell/ws_write_proc.do",method=POST)
	public String wantSellWriteProc(HttpServletRequest request,HttpSession session,WantSellVO wVO,Model model) throws DataAccessException {
		String jspPage="want_sell/ws_write";
		
		String ip_addr=request.getRemoteAddr();
		String session_id=(String)session.getAttribute("user_id");
		wVO.setIp_addr(ip_addr);
		wVO.setUser_id(session_id);
		
		if(wVO.getSell_id() != 0) { // �����ϴ� ���
			if( ws.updateSell(wVO) ) { // �� ���� ����
				model.addAttribute("url", "want_sell.do?sell_id="+wVO.getSell_id());
			} else { //�� ���� ����
				System.out.println("�� ���� ����");
			}//end else
		} else { // �߰��ϴ� ���
			ws.addSell(wVO); // ���ܰ� �߻����� ������ ������ �귯��.
			model.addAttribute("url", "want_sell.do");
		}
		model.addAttribute("msg", "�Ⱦƿ� ���� ��ϵƽ��ϴ�.");
		
		return jspPage;
	} //wantSellWriteProc
	
	/**
	 * �� ����
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
	 * �Ű����� ���
	 */
	@RequestMapping(value="report.do",method=GET)
	public String reportReason(Model model) throws SQLException {
		String jspPage="report/report";
		
		model.addAttribute("reportList", ws.getReportReasonList());
		
		return jspPage;
	} //reportReason
	
	/**
	 * �Ű�ó��
	 */
	@RequestMapping(value="want_sell/report_proc.do",method=GET)
	public String reportProc(ReportVO rVO, HttpSession session, String sell_id, Model model) throws SQLException {
		String jspPage="redirect:/want_sell/want_sell_detail.do?sell_id="+sell_id;
		
		rVO.setUser_id((String)session.getAttribute("user_id"));
		
		model.addAttribute("url", "/want_sell/want_sell_detail.do?sell_id=");
		if( 	ws.chkBeforeReport(rVO) ) { //�Ű��� �� ����
			model.addAttribute("msg", "�̹� �Ű��߽��ϴ�.");
			return jspPage;
		} //end if
		
		if( ws.reportProc(rVO) ) { //����
			model.addAttribute("msg", "�Ű�Ǿ����ϴ�.");
		} else { //����
			model.addAttribute("msg", "����.");
			//jspPage="error/error";
		} //end else
		
		return jspPage;
	} //reportProc
	
	/**
	 * ���ɱ۷� ���� | ����
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
		
		if( "y".equals(interest) ) { //���ɱۿ��� �����ϱ�
			ws.updateInterest(code, (String)session.getAttribute("user_id"), "remove");
			
		} //end if
		
		if( "n".equals(interest) ) { //���ɱ۷� �����ϱ�
			ws.updateInterest(code, (String)session.getAttribute("user_id"), "add");
		} //end else
		
		return jspPage;
	} //interestProc
	
	
	/////////////////////// ���� ó�� ////////////////////////////
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
