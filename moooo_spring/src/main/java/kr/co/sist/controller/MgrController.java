package kr.co.sist.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	 * 관리자 메인화면 폼
	 */
	@RequestMapping(value="main.do",method=GET)
	public String mainForm(HttpSession session, Model model) throws SQLException {
		String jspPage="admin/main";
		
		if(session.getAttribute("admin_id") == null) {
			// session이 없으면 login페이지로 이동
			return "redirect:/admin/login.do";
		} //end if
		
		model.addAttribute( "memList", ms.searchReportedMember() );
		
		return jspPage;
	} //mainForm
	
	/**
	 * 회원관리 폼
	 */
	@RequestMapping(value="mgr_user.do",method=GET)
	public String memberForm(String page, Model model) throws SQLException {
		String jspPage="admin/mgr_user";
		
		try {
			model.addAttribute("memberList", ms.searchMember());
		} catch(DataAccessException dae) {  }
		model.addAttribute("pagination", ms.getPagination(page));
		
		return jspPage;
	} //memberForm
	
	/**
	 * 회원 상세페이지
	 */
	@RequestMapping(value="mgr_user_detail.do",method=GET)
	public String memberDetail(String user_id, Model model) throws SQLException {
		String jspPage="admin/mgr_user_detail";
		
		if( ms.searchMemberDetail(user_id) == null ) {
			return "error/error";
		} //end if
		
		model.addAttribute("member", ms.searchMemberDetail(user_id));
		// 신고이유 얻기
		
		return jspPage;
	} //memberDetail
	
	/**
	 * 신고 이유 보여주기
	 */
	@RequestMapping(value="report_detail.do", method=GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String memberReportDetail(String reported_user_id, Model model) throws SQLException {
		String jsonObj=ms.searchReportReason(reported_user_id);
		return jsonObj;
	} //memberReportDetail

	/**
	 * 회원삭제 처리
	 */
	@RequestMapping(value="delete_member.do",method=GET)
	public String leaveProc(String user_id, HttpSession session, Model model) {
		String page="redirect:admin/mgr_user.do";
		
		if( ms.deleteMember(user_id) ) { //성공
			model.addAttribute("flag", true);
		} else { //실패
			model.addAttribute("flag", false);
		} //end else
		
		return page;
	} //leaveProc
	
	/**
	 * 이용방법 폼
	 */
	@RequestMapping(value="mgr_how_to.do", method=GET)
	public String howToForm(Model model) {
		String jspPage="admin/mgr_how_to";
		
		model.addAttribute("comments", mhts.getHowToUse());
		
		return jspPage;
	} //howToForm

	/**
	 * 이용방법 수정 폼
	 */
	@RequestMapping(value="mgr_how_to_edit.do",method=GET)
	public String updateHowTo(String comments, Model model) throws SQLException {
		String jspPage="admin/mgr_how_to_edit";
		
		model.addAttribute("comments", mhts.getHowToUse());
		
		return jspPage;
	} //updateHowTo
	
	/**
	 * 이용방법 수정 처리
	 */
	@RequestMapping(value="mgr_how_to_edit_proc.do",method=POST)
	public String updateHowToProc(String comments, Model model) throws SQLException {
		String jspPage="";
		
		if( mhts.editHowToUse(comments) ) { //성공
			model.addAttribute("msg", "글을 수정했습니다.");
			jspPage="admin/mgr_how_to";
		} else { //실패
			jspPage="/error/error";
		}//end else
		
		return jspPage;
	} //updateHowToProc
	
	/**
	 * 공지사항 리스트
	 */
	@RequestMapping(value="mgr_notice.do", method=GET)
	public String noticeForm(String page, Model model) {
		String jspPage="admin/mgr_notice";
		
		model.addAttribute("noticeList", ns.searchNoticeList(page));
		model.addAttribute("noticeListSize", ns.searchAllCnt());
		model.addAttribute("pagination", ns.getPagination(page));
		
		return jspPage;
	} //noticeForm
	
	/**
	 * 공지사항 상세페이지
	 */
	@RequestMapping(value="mgr_notice_detail.do",method=GET)
	public String noticeDetail(@RequestParam(value = "notice_id", defaultValue = "0")int notice_id, Model model) throws SQLException {
		String jspPage="admin/mgr_notice_detail";
		
		model.addAttribute("notice", ns.noticeDetail(notice_id, "admin"));
		
		return jspPage;
	} //noticeDetail
	
	/**
	 * 공지사항 추가 또는 수정 폼
	 */
	@RequestMapping(value="notice_edit_form.do", method=GET)
	public String noticeEditForm(NoticeVO nVO, String control, Model model) throws SQLException {
		String jspPage="admin/mgr_notice_write";
		
		model.addAttribute("notice", ns.noticeDetail(nVO.getNotice_id(), "admin"));
		
		return jspPage;
	} //noticeEditForm
	
	/**
	 * 이미지 서버로 업로드
	 */
	@RequestMapping(value="uploadSummernoteImgFile.do",method=POST)
	public String uploadSummernoteImgFile(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 이미지 업로드할 경로
		String uploadPath = "저장될 외부파일 경로";//D:/dev/git/moooo/moooo_spring/src/main/webapp/upload";
		//String uploadPath = "D:/dev/moooo/upload"; //서버에서 경로
	    int size = 10 * 1024 * 1024;  // 업로드 사이즈 제한 10M 이하
		
		String fileName = ""; // 파일명
		
		try{
	        // 파일업로드 및 업로드 후 파일명 가져옴
			MultipartRequest multi = new MultipartRequest(request, uploadPath, size, "utf-8", new DefaultFileRenamePolicy());
			Enumeration files = multi.getFileNames();
			String file = (String)files.nextElement(); 
			fileName = multi.getFilesystemName(file); 
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	    // 업로드된 경로와 파일명을 통해 이미지의 경로를 생성
		uploadPath = "/upload/" + fileName;
		
	    // 생성된 경로를 JSON 형식으로 보내주기 위한 설정
		JSONObject jobj = new JSONObject();
		jobj.put("url", uploadPath);
		
		response.setContentType("application/json"); // 데이터 타입을 json으로 설정하기 위한 세팅
		model.addAttribute("out", jobj.toJSONString());
		
		return "admin/mgr_notice_write";
	}
	
	/**
	 * 공지사항 추가 또는 수정 처리
	 */
	@RequestMapping(value="notice_edit_proc.do", method=POST)
	public String noticeEditProc(NoticeVO nVO, String control, HttpSession session, Model model) throws SQLException {
		String jspPage="admin/mgr_notice_write";
		
		nVO.setAdmin_id((String)session.getAttribute("admin_id"));
		
		if( "add".equals(control) ) { //추가하는 경우
			ns.addNotice(nVO);
			model.addAttribute("msg", "등록됐습니다.");
			model.addAttribute("control", "add");
			model.addAttribute("url", "mgr_notice.do");
		} else { //수정하는 경우
			if( ns.editNotice(nVO) ) { //성공
				model.addAttribute("msg", "등록됐습니다.");
				model.addAttribute("control", "edit");
				model.addAttribute("url", "mgr_notice_detail.do?notice_id="+nVO.getNotice_id());
			} else {
				//jspPage="/error/error";
			} //end else
		} //end else
		model.addAttribute("notice", ns.noticeDetail(nVO.getNotice_id(), "admin"));
		
		return jspPage;
	} //noticeEditForm
	
	/**
	 * 공지사항 삭제
	 */
	@RequestMapping(value="mgr_notice_delete.do", method=GET)
	public String noticeDelete(int notice_id, String writer, HttpSession session, Model model) throws SQLException {
		String jspPage="";
		
		//작성자가 맞는지 확인
		if( writer.equals((String)session.getAttribute("admin_id")) ){ //일치
			if( ns.deleteNotice(notice_id, writer) ) { //성공
				model.addAttribute("msg","삭제했습니다.");
				jspPage="admin/mgr_notice";
			}
		} else { //불일치
			model.addAttribute("msg","작성자만 삭제 가능합니다.");
			model.addAttribute("url", "javascript:histroy.back()");
			jspPage="admin/mgr_notice_detail";
		} //end else
		
		return jspPage;
	} //noticeDelete
	
	
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
