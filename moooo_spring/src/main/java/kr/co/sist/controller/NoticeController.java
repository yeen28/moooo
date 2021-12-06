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
	public String noticeForm(int begin,int end,Model model) {
		String jsp="notice/notice_list";
		return jsp;
	} //noticeForm

	@RequestMapping(value="notice_detail.do",method=GET)
	public String noticeDetail(int notice_id,Model model) {
		String jsp="";
		
		return jsp;
	} //noticeDetail
	
} //class
