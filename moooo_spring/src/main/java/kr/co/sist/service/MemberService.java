package kr.co.sist.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import kr.co.sist.dao.MemberDAO;
//import kr.co.sist.util.cipher.DataEncrypt;
import kr.co.sist.vo.DeleteMemberVO;
import kr.co.sist.vo.MemberVO;
import kr.co.sist.vo.TempPassVO;
import kr.co.sist.vo.UpdateUserPassVO;

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
		} catch(SQLException se) {
			result=false;
		} catch(DataAccessException dae) {
			result=false;
		}//end catch
		
		return result;
	} //loginProc
	
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
	
	/**
	 * 아이디 찾기 처리 업무로직
	 * @param mVO
	 * @return user_id 아이디
	 * @throws DataAccessException
	 */
	public String findIdProc(MemberVO mVO) throws DataAccessException {
		String id="";
		
		id=mDAO.selectFindId(mVO.getNickname(), mVO.getPhone());
		
		return id;
	} //findIdProc
	
	public String findPassProc(MemberVO mVO) throws DataAccessException, SQLException {
		// 회원정보가 일치하지 않으면 예외를 던짐
		mDAO.selectFindPass(mVO.getUser_id(), mVO.getPhone()); //암호화되어있는 비밀번호가 리턴됨
		
		return tempPass(mVO);
	} //findPassProc
	
	/**
	 * 임시 비밀번호 생성 후 비밀번호 업데이트
	 * @param tVO
	 * @return
	 * @throws SQLException 
	 */
	public String tempPass(MemberVO mVO) throws SQLException {
		String tempPass="";
		
		for(int i=0; tempPass.length()<8; i++) {
			int rand = (int) (Math.random() * 75) + 48;
			if (rand < 58 || (rand > 64 && rand < 91) || rand > 96) { // 숫자, 대문자, 소문자
				tempPass += (char) rand;
			} else continue;
		}//end while
		UpdateUserPassVO uVO=new UpdateUserPassVO();
		uVO.setUser_id(mVO.getUser_id());
		uVO.setNew_pass(tempPass);
		mDAO.updatePass(uVO);
		
		return tempPass;
	} //tempPass

	public boolean changePass(UpdateUserPassVO uVO) {
		boolean result=false;
		
		return result;
	} //changePass

	/**
	 * 회원 탈퇴 폼에서 보여줄 닉네임 얻기
	 * @param mVO
	 * @return
	 * @throws SQLException 
	 */
	public String getUserNickname(String user_id) throws SQLException {
		String result="";
		
		result=mDAO.selectGetNickname(user_id);
		
		return result;
	} //getUserNickname
	
	public boolean deleteMember(DeleteMemberVO dVO) {
		boolean result=false;
		
		try {
			mDAO.deleteMember(dVO.getUser_id());
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
		//일방향 해쉬 알고리즘 선택
		MessageDigest md=MessageDigest.getInstance("SHA-512"); /* MD2, MD5, SHA-384, SHA-512 */
		//일반문자열을 변환
		md.update( plainPass.getBytes() );
		//변환된 문자열을 받는다.
		byte[] sha=md.digest();
//		md.update( plainText2.getBytes() );
//		byte[] sha2=md.digest();
//		//알아볼 수 있는 문자열로 변환
//		Base64 base=new Base64();
//		String cipherText=new String( base.encode( sha ) );
//		String cipherText2=new String( base.encode( sha2 ) );
//		%>
//
//		일반 문자열 : <%= plainText %>, <%= plainText2 %><br/>
//		encode문자열 : <%= cipherText %><br/>
//		encode문자열 : <%= cipherText2 %><br/>
		} catch(NoSuchAlgorithmException e) {
			
		}
		
		
//		try {
//			encryptPass=DataEncrypt.messageDigest("MD5", plainPass);
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
		
		return encryptPass;
	} //encryptPass
	
} //class
