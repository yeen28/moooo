package kr.co.sist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.sist.service.NoticeService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("notice/")
public class NoticeController {
	
	@Autowired
	private NoticeService ns;
	
	@RequestMapping(value="notice_list.do",method=GET)
	public String noticeForm(String page, Model model) {
		String jsp="notice/notice_list";
		
		int nowPage=ns.nowPage(page);
//		int totalCount=ns.searchAllCnt();
//		int blockPage=ns.blockPage();
//		int start=ns.startNum(nowPage, blockPage);
//		int end=ns.endNum(start, blockPage);
		
		if( ns.searchNoticeList(page) == null ) {
			model.addAttribute("msg", "조회된 결과가 없습니다.");
		} else {
			model.addAttribute("selectedNotice", ns.searchNoticeList(page));
			model.addAttribute("nowPage", nowPage);
			model.addAttribute("LastPage", nowPage);
			model.addAttribute("start", nowPage);
			model.addAttribute("end", nowPage);
		}//end else
		
		return jsp;
	} //noticeForm

	@RequestMapping(value="notice_detail.do",method=GET)
	public String noticeDetail(int notice_id,Model model) {
		String jsp="";
		
		return jsp;
	} //noticeDetail
	
} //class
