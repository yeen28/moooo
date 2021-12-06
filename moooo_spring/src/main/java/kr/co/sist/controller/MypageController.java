package kr.co.sist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.sist.service.MypageService;
import kr.co.sist.vo.MemberVO;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import javax.servlet.http.HttpSession;

@Controller
public class MypageController {

	@Autowired
	private MypageService ms;
	
	private HttpSession session;
	
	// 정보변경
	@RequestMapping(value="mypage_form.do",method=GET)
	public String mypageForm(Model model) {
		String jspPage="";
		
		return jspPage;
	} //mypageForm

	@RequestMapping(value="mypage_update.do",method=GET)
	public String updateMypage(MemberVO mVO,Model model) {
		String jspPage="";
		
		return jspPage;
	} //updateMypage

	// 내가 쓴 글
	@RequestMapping(value="write_list_form.do",method=GET)
	public String writeListForm(Model model) {
		String jspPage="";
		
		return jspPage;
	}

	@RequestMapping(value="해당글로 이동.do",method=GET)
	public String move() {
		String jspPage="";
		
		return jspPage;
	}

	@RequestMapping(value="delete_write_list.do",method=GET)
	public String deleteWirteList(List<Integer> listWrite,Model model) {
		String jspPage="";
		
		return jspPage;
	}

	// 관심글
	@RequestMapping(value="interest_form.do",method=GET)
	public String interestForm(Model model) {
		String jspPage="";
		
		return jspPage;
	}

	@RequestMapping(value="interest_cancel.do",method=GET)
	public String interestCancel(List<Integer> listInterest,Model model) {
		String jspPage="";
		
		return jspPage;
	}
	
}
