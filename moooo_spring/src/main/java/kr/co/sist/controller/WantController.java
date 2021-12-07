package kr.co.sist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.sist.service.WantService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class WantController {
	
	@Autowired
	private WantService ws;

	@RequestMapping(value="want_buy/want_buy.do",method=GET)
//	public String wantBuy(@RequestParam(value="category",defaultValue = "0")String category,String page,Model model) {
	public String wantBuy(String paramCategory,String page,Model model) {
		String jspPage="want_buy/want_buy";
		int nowPage=ws.nowPage(page);
		
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

	@RequestMapping("want_buy/want_buy_detail.do")
	public String wantBuyDetail(int buy_id,Model model){
		String jspPage="want_buy/want_buy_detail";
		
		model.addAttribute("buy",ws.getWantBuyDetail(buy_id));
		
		return jspPage;
	} //wantBuyDetail
	
	@RequestMapping("want_buy/wb_write.do")
	public String wantBuyWrite(@RequestParam(value="buy_id",defaultValue = "0")String buy_id,Model model){
		String jspPage="want_buy/wb_write";
		
		int buyId=Integer.parseInt(buy_id);
		
		if(buyId == 0) {
			if( ws.addBuy() ) {
				
			}
		} else {
			//model.addAttribute("buy",ws.getWantBuyDetail(buyId));
		}
		
		return jspPage;
	} //wantBuyWrite
	
	@RequestMapping(value="want_sell/want_sell.do",method=GET)
	public String wantSell(int category,int begin,int end,Model model) {
		String jspPage="want_sell/want_sell";
		
		return jspPage;
	} //wantSell
	
}
