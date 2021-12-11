package kr.co.sist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import kr.co.sist.service.MgrHowToService;
import kr.co.sist.service.MgrMemberService;
import kr.co.sist.service.NoticeService;
import kr.co.sist.vo.NoticeVO;

@Controller
@RequestMapping("admin/")
public class MgrController {

	@Autowired
	private MgrMemberService ms;
	@Autowired
	private MgrHowToService mhts;
	@Autowired
	private NoticeService ns;
	
	/**
	 * ȸ������ ��
	 */
	@RequestMapping(value="mgr_user.do",method=GET)
	public String memberForm(Model model) {
		String jspPage="admin/mgr_user";
		
		try {
			model.addAttribute("memberList", ms.searchMember());
		} catch(DataAccessException dae) {  }
		
		return jspPage;
	} //memberForm

//	@RequestMapping(value="mgr/member_proc.do",method=GET)
//	public String memberProc(MemberVO mVO, Model model) {
//		String jspPage="";
//		return jspPage;
//	} //memberProc
	
	/**
	 * �̿��� ��
	 */
	@RequestMapping(value="mgr_how_to.do", method=GET)
	public String howToForm(Model model) {
		String jspPage="admin/mgr_how_to";
		
		model.addAttribute("comments", mhts.getHowToUse());
		
		return jspPage;
	} //howToForm

	/**
	 * �̿��� ���� ��
	 */
	@RequestMapping(value="mgr_how_to_edit.do",method=GET)
	public String updateHowTo(String comments, Model model) throws SQLException {
		String jspPage="admin/mgr_how_to_edit";
		
		model.addAttribute("comments", mhts.getHowToUse());
		
		return jspPage;
	} //updateHowTo
	
	/**
	 * �̿��� ���� ó��
	 */
	@RequestMapping(value="mgr_how_to_edit_proc.do",method=POST)
	public String updateHowToProc(String comments, Model model) throws SQLException {
		String jspPage="";
		
		if( mhts.editHowToUse(comments) ) { //����
			model.addAttribute("msg", "���� �����߽��ϴ�.");
			jspPage="admin/mgr_how_to";
		} else { //����
			jspPage="/error/error";
		}//end else
		
		return jspPage;
	} //updateHowToProc
	
	/**
	 * �������� ����Ʈ
	 */
	@RequestMapping(value="mgr_notice.do", method=GET)
	public String noticeForm(String page, Model model) {
		String jspPage="admin/mgr_notice";
		
		int nowPage=ns.nowPage(page);
//			int totalCount=ns.searchAllCnt();
//			int blockPage=ns.blockPage();
//			int start=ns.startNum(nowPage, blockPage);
//			int end=ns.endNum(start, blockPage);
		
		model.addAttribute("noticeList", ns.searchNoticeList(page));
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("LastPage", nowPage);
		model.addAttribute("start", nowPage);
		model.addAttribute("end", nowPage);
		
		return jspPage;
	} //noticeForm
	
	/**
	 * �������� ��������
	 */
	@RequestMapping(value="mgr_notice_detail.do",method=GET)
	public String noticeDetail(@RequestParam(value = "notice_id", defaultValue = "0")int notice_id, Model model) throws SQLException {
		String jspPage="admin/mgr_notice_detail";
		
		model.addAttribute("notice", ns.noticeDetail(notice_id, "admin"));
		
		return jspPage;
	} //noticeDetail
	
	/**
	 * �������� �߰� �Ǵ� ���� ��
	 */
	@RequestMapping(value="notice_edit_form.do", method=GET)
	public String noticeEditForm(NoticeVO nVO, String control, Model model) throws SQLException {
		String jspPage="admin/mgr_notice_write";
		
		model.addAttribute("notice", ns.noticeDetail(nVO.getNotice_id(), "admin"));
		
		return jspPage;
	} //noticeEditForm
	
	/**
	 * �������� �߰� �Ǵ� ���� ó��
	 */
	@RequestMapping(value="notice_edit_proc.do", method=POST)
	public String noticeEditProc(NoticeVO nVO, String control, Model model) throws SQLException {
		String jspPage="admin/mgr_notice_write";
		
		if( "edit".equals(control) ) { //�����ϴ� ���
			if( ns.editNotice(nVO) ) { //����
				model.addAttribute("msg", "��ϵƽ��ϴ�.");
				model.addAttribute("url", "mgr_notice_detail.do?notice_id="+nVO.getNotice_id());
			} else {
				jspPage="/error/error";
			} //end else
		} else { //�߰��ϴ� ���
			ns.addNotice(nVO);
			model.addAttribute("msg", "��ϵƽ��ϴ�.");
			model.addAttribute("url", "mgr_notice.do");
		} //end else
		model.addAttribute("notice", ns.noticeDetail(nVO.getNotice_id(), "admin"));
		
		return jspPage;
	} //noticeEditForm
	
	/**
	 * �������� ����
	 */
	@RequestMapping(value="mgr_notice_delete.do", method=GET)
	public String noticeDelete(int notice_id, String writer, HttpSession session, Model model) throws SQLException {
		String jspPage="";
		
		//�ۼ��ڰ� �´��� Ȯ��
		if( writer.equals((String)session.getAttribute("admin_id")) ){ //��ġ
			if( ns.deleteNotice(notice_id, writer) ) { //����
				model.addAttribute("msg","�����߽��ϴ�.");
				jspPage="redirect:/admin/mgr_notice.do";
			}
		} else { //����ġ
			model.addAttribute("msg","�ۼ��ڸ� ���� �����մϴ�.");
			jspPage="redirect:/admin/mgr_notice_detail.do?notice_id="+notice_id;
		} //end else
		
		return jspPage;
	} //noticeDelete
	
	///////////////////// ����ó�� /////////////////////////////
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
