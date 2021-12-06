package kr.co.sist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.sist.service.WantService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("view/")
public class WantController {
	
	@Autowired
	private WantService ws;

	@RequestMapping(value="want_buy/want_buy.do",method=GET)
	public String wantBuy(int category,int begin,int end,Model model) {
		String jspPage="view/want_buy/want_buy";
		
		return jspPage;
	} //wantBuy

	@RequestMapping("want_buy_detail.do")
	public String wantBuyDetail(Model model){
		String jspPage="";
		
		return jspPage;
	}
	
	@RequestMapping(value="want_sell/want_sell.do",method=GET)
	public String wantSell(int category,int begin,int end,Model model) {
		String jspPage="view/want_sell/want_sell";
		
		return jspPage;
	} //wantSell
	
}
