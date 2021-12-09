package kr.co.sist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import kr.co.sist.service.AdminLoginService;
import kr.co.sist.vo.AdminLoginVO;
import kr.co.sist.vo.UpdateAdminPassVO;

@Controller
@RequestMapping("mgr/")
@SessionAttributes("admin_id")
public class AdminLoginController {
	
	@Autowired(required = false)
	private AdminLoginService als;
	
	/**
	 * �α��� ȭ�� ( ������ ���ӽ� ù ȭ�� )
	 * @return
	 */
	@RequestMapping(value="login_form.do",method=GET)
	public String loginForm() {
		String jspPage="admin/login";
		return jspPage;
	} //loginForm
	
	/**
	 * �α��� ó��
	 * @param aVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="login_proc.do",method=POST)
	public String loginProc(AdminLoginVO aVO,Model model) throws SQLException {
		String jspPage="";
		
		if( als.procLogin(aVO) ) {  //�α��� ����
			model.addAttribute("admin_id", aVO.getAdmin_id());
			jspPage="redirect:admin/main.do";
			
		} else { //�α��� ����
			model.addAttribute("msg", "���̵�, ��й�ȣ�� Ȯ�����ּ���.");
			jspPage="admin/login";
		}//end else
		
		return jspPage;
	} //loginProc

	@RequestMapping(value="main_form.do",method=GET)
	public String mainForm(HttpSession session, Model model) {
		// session�� ������ jsp�� �����ְ�,
		// session�� ������ redirect�� login�������� �̵�
		String jspPage="admin/main";
		return jspPage;
	} //mainForm

	@RequestMapping(value="change_pass_form.do",method=GET)
	public String changePassForm() {
		String jspPage="admin/change_pass";
		return jspPage;
	} //changePassForm

	@RequestMapping(value="change_pass_proc.do",method=GET)
	public String changePassProc(UpdateAdminPassVO uVO, Model model) throws DataAccessException {
		String jspPage="admin/change_pass";
		
		if( als.changePass(uVO) ) { //��й�ȣ ���� ����
			
		} else { // ����
			
		} //end else
		
		return jspPage;
	} //changePassProc
	
	@ExceptionHandler(SQLException.class)
	public ModelAndView seErr(SQLException se) {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("error/error"); //������ �߻��ϸ� ������ ������������ ������.
		mav.addObject("se", se); //jsp�� �����޽��� ����
		return mav;
	} //seErr
	
	@ExceptionHandler(DataAccessException.class)
	public ModelAndView daeErr(DataAccessException dae) {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("error/error"); //������ �߻��ϸ� ������ ������������ ������.
		mav.addObject("dae", dae); //jsp�� �����޽��� ����
		return mav;
	} //daeErr
	
} //class
