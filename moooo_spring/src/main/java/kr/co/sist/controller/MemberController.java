package kr.co.sist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import kr.co.sist.dao.MemberDAO;
import kr.co.sist.service.MemberService;
import kr.co.sist.util.cipher.DataEncrypt;
import kr.co.sist.vo.DeleteMemberVO;
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
public class MemberController {

	@Autowired
	private MemberService ms;
	
	/**
	 * 로그인 폼
	 */
	@RequestMapping(value="login.do",method=GET)
	public String loginForm() {
		String jspPage="user/login/login";
		return jspPage;
	} //loginForm
	
	/**
	 * 로그인 처리
	 */
	@RequestMapping(value="login_proc.do",method=POST)
	// 로그아웃할 때 url뒤에 아이디가 붙지 않도록 HttpSession으로 값을 넣음.
	// Model로 세션을 넣게 되면, 세션삭제 후 redirect로 메인화면으로 넘어갔을 때 뒤에 아이디가 붙게 됨.
	public String loginProc(MemberVO mVO, HttpSession session, Model model) {
		String page="user/login/process/login_process";
		
		if( ms.loginProc(mVO) ) { //로그인 성공
			session.setAttribute("user_id", mVO.getUser_id());
			
		} else { //로그인 실패
			model.addAttribute("msg", "아이디, 비밀번호를 확인해주세요.");
		} //end else
		
		return page;
	} //loginProc
	
	/**
	 * 로그아웃 (세션 삭제)
	 */
	@RequestMapping(value="logout_proc.do",method=GET)
//	public String logoutProc(SessionStatus ss, Model model) {
	public String logoutProc(HttpSession session, Model model) {
//		if( !ss.isComplete() ) {
//			ss.setComplete(); //세션 삭제
//		}//end if
		
//		session.removeAttribute("user_id");
		session.invalidate();
		
		return "redirect:/index.do";
	} //logoutProc
	
	/**
	 * 회원가입 폼
	 */
	@RequestMapping(value="sign_up.do",method=GET)
	public String signUpForm() {
		String page="user/login/sign_up";
		return page;
	} //signUpForm
	
	/**
	 * 아이디 중복확인
	 */
	@RequestMapping(value="id_dup.do",method=GET)
	public String idDuplication(String user_id, Model model) throws SQLException {
		String page="user/login/id_dup";
		
		model.addAttribute("input_id", user_id);
		String msg="입력하신 "+user_id+"는 사용 ";
		if( ms.searchIdDup(user_id) ) { //사용가능한 아이디인 경우
			model.addAttribute("msg", msg+"가능합니다.");
			model.addAttribute("available",true);
		} else {
			model.addAttribute("msg", msg+"불가능합니다.");
			model.addAttribute("available",false);
		} //end else
		
		return page;
	} //idDuplication
	
	/**
	 * 아이디를 입력하지 않았을 때 AJAX로 메시지 보여줌
	 */
	@RequestMapping(value="id_dup_null.do",method=GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String idDuplicationNull(Model model) {
		JSONObject json=new JSONObject();
		json.put("alertMsg", "중복 검사할 아이디를 입력해주세요.");
		return json.toJSONString();
	} //idDuplicationNull
	
	/**
	 * 회원가입 처리
	 */
	@RequestMapping(value="sign_up_proc.do",method=POST)
	public String signUpProc(MemberVO mVO, Model model) {
		String page="user/login/process/sign_up_process";
		
		if( ms.signUpProc(mVO) ) { //회원가입 성공
			model.addAttribute("nickname",mVO.getNickname());
		} else { //회원가입 실패
			return "error/error";
		}//end else
		
		return page;
	} //signUpProc
	
	/**
	 * 아이디/비밀번호 찾기 폼
	 */
	@RequestMapping(value="find_form.do",method=GET)
	public String findForm() {
		String page="user/login/find";
		return page;
	} //findForm
	
	/**
	 * 아이디 찾기 처리
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
	 * 비밀번호 찾기 처리
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
	 * 비밀번호 변경 폼
	 */
	@RequestMapping(value="change_pass_form.do",method= {GET,POST})
	public String changePassForm() {
		String page="user/login/change_password";
		return page;
	} //changePassForm
	
	/**
	 * 비밀번호 변경 처리
	 */
	@RequestMapping(value="change_pass_proc.do",method=POST)
	public String changePassProc(UpdateUserPassVO uVO, String new_pass2, Model model) throws SQLException {
		String page="user/login/change_password";
		
		uVO.setBefore_pass(ms.encryptPass(uVO.getBefore_pass()));
		String dbPw=ms.getPassword( uVO.getUser_id() );
		if( dbPw.equals(uVO.getBefore_pass()) && uVO.getNew_pass().equals(new_pass2) ){
		
			if( ms.changePass(uVO, new_pass2) ) { //비밀번호 변경 성공
				model.addAttribute("msg", "비밀번호를 변경했습니다.");
				model.addAttribute("url", "/user/mypage/mypage_form.do");
			} else {
				page="error/error";
			} //end else
		} else {
			model.addAttribute("msg", "비밀번호를 다시 확인해주세요.");
			model.addAttribute("url", "/user/login/change_pass_form.do");
		} //end else
		
		return page;
	} //changePassProc
	
	/**
	 * 회원탈퇴 폼
	 */
	@RequestMapping(value="leave_form.do",method=GET)
	public String leaveForm(HttpSession session, Model model) throws SQLException {
		String jspPage="";
		
		// 세션이 있는지 확인
		if(session.getAttribute("user_id") == null) { 
			jspPage="redirect:/index.do";
		} else {
			jspPage="user/login/leave";
			model.addAttribute("nickname", ms.getUserNickname((String)session.getAttribute("user_id")));
		} //end else
		
		return jspPage;
	} //leaveForm
	
	/**
	 * 회원탈퇴 처리
	 */
	@RequestMapping(value="leave_proc.do",method=POST)
	public String leaveProc(DeleteMemberVO dVO, HttpSession session, Model model) {
		String page="user/login/process/leave_process";
		
		dVO.setUser_id((String)session.getAttribute("user_id"));
		if( ms.deleteMember(dVO) ) { //탈퇴 성공
			session.invalidate(); //세션삭제
			model.addAttribute("flag", true);
		} else { //실패
			model.addAttribute("flag", false);
		} //end else
		
		return page;
	} //leaveProc
	
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
