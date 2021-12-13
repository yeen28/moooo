package kr.co.sist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.sist.service.NoticeService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

@Controller
@RequestMapping("notice/")
public class NoticeController {
	
	@Autowired
	private NoticeService ns;
	
	/**
	 * 공지사항 목록
	 */
	@RequestMapping(value="notice_list.do",method=GET)
	public String noticeForm(String page, Model model) {
		String jsp="notice/notice_list";
		
		if( ns.searchNoticeList(page) == null ) {
			model.addAttribute("msg", "조회된 결과가 없습니다.");
		} else {
			model.addAttribute("selectedNotice", ns.searchNoticeList(page));
			model.addAttribute("pagination", ns.getPagination(page));
		}//end else
		
		return jsp;
	} //noticeForm

	/**
	 * 공지사항 상세페이지
	 */
	@RequestMapping(value="notice_detail.do",method=GET)
	public String noticeDetail(int notice_id,Model model) throws SQLException {
		String jspPage="notice/notice_detail";
		
		model.addAttribute("nv", ns.noticeDetail(notice_id, "user"));
		
		return jspPage;
	} //noticeDetail

	///////////////////// 예외처리 /////////////////////////////
	@ExceptionHandler(SQLException.class)
	public ModelAndView sqlErr(SQLException se) {
	ModelAndView mav=new ModelAndView();
	mav.setViewName("error/error");
	mav.addObject("se",se);
	return mav;
	} //sqlErr
	
	@ExceptionHandler(DataAccessException.class)
	public ModelAndView daErr(DataAccessException dae) {
	ModelAndView mav=new ModelAndView();
	mav.setViewName("error/error");
	mav.addObject("dae",dae);
	return mav;
	} //daErr
	
} //class
