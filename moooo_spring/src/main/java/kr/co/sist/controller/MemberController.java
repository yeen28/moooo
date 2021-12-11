package kr.co.sist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import kr.co.sist.service.MemberService;
import kr.co.sist.vo.MemberVO;
import kr.co.sist.vo.UpdateUserPassVO;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

@Controller
@RequestMapping("user/login/")
@SessionAttributes("user_id")
public class MemberController {

	@Autowired
	private MemberService ms;
	
	/**
	 * �α��� ��
	 */
	@RequestMapping(value="login.do",method=GET)
	public String loginForm() {
		String jspPage="user/login/login";
		return jspPage;
	} //loginForm
	
	/**
	 * �α��� ó��
	 */
	@RequestMapping(value="login_proc.do",method=POST)
	public String loginProc(MemberVO mVO, Model model) {
		String page="user/login/process/login_process";
		
		if( ms.loginProc(mVO) ) { //�α��� ����
			model.addAttribute("user_id",mVO.getUser_id());
			
		} else { //�α��� ����
			model.addAttribute("msg", "���̵�, ��й�ȣ�� Ȯ�����ּ���.");
		} //end else
		
		return page;
	} //loginProc
	
	/**
	 * �α׾ƿ� (���� ����)
	 */
	@RequestMapping(value="logout_proc.do",method=GET)
	public String logoutProc(SessionStatus ss, Model model) {
		if( !ss.isComplete() ) {
			ss.setComplete(); //���� ����
		}//end if
		
		return "/index";
	} //logoutProc
	
	/**
	 * ȸ������ ��
	 */
	@RequestMapping(value="sign_up.do",method=GET)
	public String signUpForm() {
		String page="user/login/sign_up";
		return page;
	} //signUpForm
	
	/**
	 * ���̵� �ߺ�Ȯ��
	 */
	@RequestMapping(value="id_dup.do",method=GET)
	public String idDuplication(String user_id, Model model) throws SQLException {
		String page="user/login/id_dup";
		
		model.addAttribute("input_id", user_id);
		String msg="�Է��Ͻ� "+user_id+"�� ��� ";
		if( ms.searchIdDup(user_id) ) { //��밡���� ���̵��� ���
			model.addAttribute("msg", msg+"�����մϴ�.");
			model.addAttribute("available",true);
		} else {
			model.addAttribute("msg", msg+"�Ұ����մϴ�.");
			model.addAttribute("available",false);
		} //end else
		
		return page;
	} //idDuplication
	
	/**
	 * ���̵� �Է����� �ʾ��� �� AJAX�� �޽��� ������
	 */
	@RequestMapping(value="id_dup_null.do",method=GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String idDuplicationNull(Model model) {
		JSONObject json=new JSONObject();
		json.put("alertMsg", "�ߺ� �˻��� ���̵� �Է����ּ���.");
		return json.toJSONString();
	} //idDuplicationNull
	
	/**
	 * ȸ������ ó��
	 */
	@RequestMapping(value="sign_up_proc.do",method=POST)
	public String signUpProc(MemberVO mVO, Model model) {
		String page="user/login/process/sign_up_process";
		
		if( ms.signUpProc(mVO) ) { //ȸ������ ����
			model.addAttribute("nickname",mVO.getNickname());
		} else { //ȸ������ ����
			page="redirect:/common/error/error.jsp";
		}//end else
		
		return page;
	} //signUpProc
	
	/**
	 * ���̵�/��й�ȣ ã�� ��
	 */
	@RequestMapping(value="find_form.do",method=GET)
	public String findForm() {
		String page="user/login/find";
		return page;
	} //findForm
	
	/**
	 * ���̵� ã�� ó��
	 */
	@RequestMapping(value="find_id_proc.do",method=POST)
	public String findId(MemberVO mVO, Model model) {
		String page="user/login/process/find_id_process";
		
		try {
			mVO.setUser_id( ms.findIdProc(mVO) );
			model.addAttribute("mVO", mVO);
		} catch(DataAccessException dae) {  }
		
		return page;
	} //findId
	
	/**
	 * ��й�ȣ ã�� ó��
	 */
	@RequestMapping(value="find_pass_proc.do",method=POST)
	public String findPass(MemberVO mVO, Model model) throws SQLException {
		String page="user/login/process/find_pass_process";
		
		try {
			mVO.setPass( ms.findPassProc(mVO) );
			model.addAttribute("mVO", mVO);
		} catch(DataAccessException dae) {  }
		
		return page;
	} //findPass
	
	/**
	 * ��й�ȣ ���� ��
	 */
	@RequestMapping(value="change_pass_form.do",method=GET)
	public String changePassForm() {
		String page="user/login/change_password";
		return page;
	} //changePassForm
	
	/**
	 * ��й�ȣ ���� ó��
	 */
	@RequestMapping(value="change_pass_proc.do",method=GET)
	public String changePassProc(UpdateUserPassVO uVO, Model model) throws SQLException {
		String page="user/login/process/change_pass_process";
		
		if( ms.changePass(uVO) ) { //��й�ȣ ���� ����
			model.addAttribute("msg", "��й�ȣ�� �����߽��ϴ�.");
		}
		
		return page;
	} //changePassProc
	
	/**
	 * ȸ��Ż�� ��
	 */
	@RequestMapping(value="leave_form.do",method=GET)
	public String leaveForm(HttpSession session, Model model) throws SQLException {
		String jspPage="";
		
		// ������ �ִ��� Ȯ��
		if(session.getAttribute("user_id") == null) { 
			jspPage="redirect:/index.do";
		} else {
			jspPage="user/login/leave";
			model.addAttribute("nickname", ms.getUserNickname((String)session.getAttribute("user_id")));
		} //end else
		
		return jspPage;
	} //leaveForm
	
	/**
	 * ȸ��Ż�� ó��
	 */
	@RequestMapping(value="leave_proc.do",method=GET)
	public String leaveProc() {
		String page="user/login/process/leave_process";
		return page;
	} //leaveProc
	
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
