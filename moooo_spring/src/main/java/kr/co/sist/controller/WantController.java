package kr.co.sist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.co.sist.service.WantService;
import kr.co.sist.vo.WantBuyVO;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

@Controller
public class WantController {
	
	@Autowired
	private WantService ws;

	@RequestMapping(value="want_buy/want_buy.do",method=GET)
//	public String wantBuy(@RequestParam(value="category",defaultValue = "0")String category,String page,Model model) {
	public String wantBuy(String paramCategory,String page,Model model) {
		String jspPage="want_buy/want_buy";
		//int nowPage=ws.nowPage(page);
		
		int category=0;
		try {
			category=Integer.parseInt(paramCategory);
		}catch(NumberFormatException nfe) {
			category=0;
		}
		
		ws.searchBuyList(category, page);
		model.addAttribute("list",ws.searchBuyList(category, page));
		
		return jspPage;
	} //wantBuy

	@RequestMapping(value="want_buy/want_buy_detail.do",method=GET)
	public String wantBuyDetail(int buy_id,Model model){
		String jspPage="want_buy/want_buy_detail";
		
		model.addAttribute("buy",ws.getWantBuyDetail(buy_id));
		
		return jspPage;
	} //wantBuyDetail
	
	@RequestMapping(value="want_buy/wb_write.do",method=GET)
	public String wantBuyWriteForm(HttpServletRequest request,HttpSession session,WantBuyVO wVO,Model model) throws DataAccessException {
		String jspPage="want_buy/wb_write";
		
		if(wVO.getBuy_id() != 0) { // 수정하는 경우
			model.addAttribute("buy",ws.getWantBuyDetail(wVO.getBuy_id()));
		} //end if
		
		return jspPage;
	} //wantBuyWriteForm
	
	@RequestMapping(value="want_buy/wb_write_proc.do",method=POST)
//	public String wantBuyWrite(HttpServletRequest request,HttpSession session,@RequestParam(value="buy_id",defaultValue = "0")String buy_id,Model model){
	public String wantBuyWriteProc(HttpServletRequest request,HttpSession session,WantBuyVO wVO,Model model) throws DataAccessException {
		String jspPage="want_buy/wb_write";
		
//		int buyId=Integer.parseInt(buy_id);
		
		String ip_addr=request.getRemoteAddr();
		String session_id=(String)session.getAttribute("user_id");
		wVO.setIp_addr(ip_addr);
		wVO.setUser_id(session_id);
		
		if(wVO.getBuy_id() == 0) { // 추가하는 경우
			ws.addBuy(wVO); // 예외가 발생하지 않으면 밑으로 흘러감.
			model.addAttribute("url", "want_buy.do");
		} else { // 수정하는 경우
			if( ws.updateBuy(wVO) ) { // 글 수정 성공
			//model.addAttribute("buy",ws.getWantBuyDetail(buyId));
			} else { //글 수정 실패
				
			}
		}
		model.addAttribute("msg", "팔아요 글이 등록됐습니다.");
		
		return jspPage;
	} //wantBuyWrite
	
	@RequestMapping(value="want_sell/want_sell.do",method=GET)
	public String wantSell(int category,int begin,int end,Model model) {
		String jspPage="want_sell/want_sell";
		
		return jspPage;
	} //wantSell
	
	@ExceptionHandler(DataAccessException.class)
	public ModelAndView daeErr( DataAccessException dae ) {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("error/error");
		mav.addObject("dae", dae);
		return mav;
	} //daeErr
	
}
