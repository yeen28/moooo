package kr.co.sist.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class NoticeController {
	
	@RequestMapping(value="/notice/notice_list.do",method=GET)
	public String noticeForm() {
		String jsp="notice/notice_list";
		return jsp;
	} //noticeForm

}
