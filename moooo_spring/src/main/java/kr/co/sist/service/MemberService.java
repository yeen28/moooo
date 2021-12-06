package kr.co.sist.service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import kr.co.sist.dao.MemberDAO;
import kr.co.sist.util.cipher.DataEncrypt;
import kr.co.sist.vo.DeleteMemberVO;
import kr.co.sist.vo.MemberVO;
import kr.co.sist.vo.TempPassVO;
import kr.co.sist.vo.UpdatePassVO;

@Component
public class MemberService {

	@Autowired
	private MemberDAO mDAO;
	
	private HttpSession session;
	
	/**
	 * 로그인 처리
	 * @param mVO
	 * @return
	 */
	public boolean loginProc(MemberVO mVO) {
		boolean result=false;
		
		try {
			mDAO.selectLogin(mVO.getUser_id(), mVO.getPass());
			result=true;
		} catch(SQLException se) {}
		
		return result;
	} //loginProc
	
//	/**
//	 * 로그아웃 처리
//	 * @param SessionStatus
//	 */
//	public void logoutProc(SessionStatus ss) {
//		ss.setComplete();
//	} //logoutProc
	
	/**
	 * 아이디 중복검사
	 * @param id
	 * @return
	 */
	public boolean searchIdDup(String id) {
		boolean result=true;
		
		try {
			String dbId=mDAO.selectId(id);
			if(dbId == null) { // ""
				result=false;
			} //end if
		} catch(SQLException se) {
			result=false;
		} //end catch
		
		return result;
	} //searchIdDup
	
	/**
	 * 회원가입 처리
	 * @param MemberVO
	 * @return true|false (성공여부)
	 */
	public boolean signUpProc(MemberVO mVO) {
		boolean result=false;
		
		try {
			String encryptPass=encryptPass(mVO.getPass());
			mVO.setPass(encryptPass);
			
			mDAO.insertMember(mVO);
			result=true;
		} catch(DataAccessException dae) {
			result=false;
		} //end catch
		
		return result;
	} //signUpProc
	
	
	public String findIdProc(MemberVO mVO) {
		String id="";
		
		return id;
	} //findIdProc
	
	public String findPassProc(MemberVO mVO) {
		String pass="";
		
		return pass;
	} //findPassProc
	
	/**
	 * 임시 비밀번호
	 * @param tVO
	 * @return
	 */
	public String tempPass(TempPassVO tVO) {
		String pass="";
		
		return pass;
	} //tempPass

	public boolean changePass(UpdatePassVO uVO) {
		boolean result=false;
		
		return result;
	} //changePass

	public boolean deleteMember(DeleteMemberVO dVO) {
		boolean result=false;
		
		String user_id=(String)session.getAttribute("user_id");
		
		try {
			mDAO.deleteMember(user_id);
		} catch(SQLException se) {}
		
		return result;
	} //deleteMember

	/**
	 * 비밀번호 암호화 업무로직
	 * @param plainPass
	 * @return
	 */
	public String encryptPass(String plainPass) {
		String encryptPass="";
		
		try {
			encryptPass=DataEncrypt.messageDigest("MD5", plainPass);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return encryptPass;
	} //encryptPass
	
} //class
