package kr.co.sist.service;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import kr.co.sist.dao.MemberDAO;
import kr.co.sist.util.cipher.DataDecrypt;
import kr.co.sist.util.cipher.DataEncrypt;
import kr.co.sist.vo.DeleteMemberVO;
import kr.co.sist.vo.MemberVO;
import kr.co.sist.vo.UpdateUserPassVO;

@Component
public class MemberService {

	@Autowired
	private MemberDAO mDAO;
	
	/**
	 * 로그인 처리
	 * @param mVO
	 * @return
	 */
	public boolean loginProc(MemberVO mVO) {
		boolean result=false;
		
		// 비밀번호 암호화
		mVO.setPass(encryptPass(mVO.getPass()));
		
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
	 * @param 입력한 아이디
	 * @return true 사용가능 | false 사용불가능
	 * @throws SQLException
	 */
	public boolean searchIdDup(String id) throws SQLException {
		boolean result=false;
		
		String dbId=mDAO.selectId(id);
		if( "".equals( dbId ) ) { // 사용가능한 아이디인 경우
			result=true;
		} else { // 이미 존재하는 아이디인 경우
			result=false;
		} //end else
		
		return result;
	} //searchIdDup
	
	/**
	 * 회원가입 처리
	 * @param MemberVO
	 * @return true|false (성공여부)
	 */
	public boolean signUpProc(MemberVO mVO) {
		boolean result=false;
		
		String encryptPass=encryptPass(mVO.getPass()); //비밀번호 암호화
		String encryptPhone=encryptPhone(mVO.getPhone()); //전화번호 암호화(AES)
		
		try {
			mVO.setPass(encryptPass);
			mVO.setPhone(encryptPhone);
			
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
		String encryptPhone=encryptPhone(mVO.getPhone());
		String id=mDAO.selectFindId(mVO.getNickname(), encryptPhone);
		
		return id;
	} //findIdProc
	
	/**
	 * 비밀번호 찾기 업무로직
	 * @param mVO
	 * @return 임시비밀번호
	 * @throws DataAccessException
	 * @throws SQLException
	 */
	public String findPassProc(MemberVO mVO) throws DataAccessException, SQLException {
		String encryptPhone=encryptPhone(mVO.getPhone());
		// 회원정보가 일치하지 않으면 예외를 던짐
		mDAO.selectFindPass(mVO.getUser_id(), encryptPhone); //암호화되어있는 비밀번호가 리턴됨
		
		return tempPass(mVO.getUser_id()); //임시비밀번호 생성 후 리턴
	} //findPassProc
	
	/**
	 * 임시 비밀번호 생성 후 비밀번호 업데이트
	 * @param 아이디
	 * @return
	 * @throws SQLException 
	 */
	public String tempPass(String user_id) throws SQLException {
		String tempPass="";
		
		//8자리 임시비밀번호 생성
		while( tempPass.length() < 8 ) {
			int rand = (int) (Math.random() * 75) + 48;
			if (rand < 58 || (rand > 64 && rand < 91) || rand > 96) { // 숫자, 대문자, 소문자
				tempPass += (char) rand;
			} else continue;
		}//end while
		
		UpdateUserPassVO uVO=new UpdateUserPassVO();
		uVO.setUser_id(user_id);
		uVO.setNew_pass(encryptPass(tempPass)); //암호화
		mDAO.updatePass(uVO);
		
		return tempPass;
	} //tempPass

	/**
	 * 비밀번호 변경 업무로직
	 * @param uVO
	 * @return true 성공 | false 실패
	 * @throws SQLException
	 */
	public boolean changePass(UpdateUserPassVO uVO, String new_pass2) throws SQLException {
		uVO.setNew_pass( encryptPass(uVO.getNew_pass()) );
		if( mDAO.updatePass(uVO) != 0) { //비밀번호 변경 성공
			return true;
		} else { //실패
			return false;
		} //end else
	} //changePass
	
	/**
	 * 아이디에 맞는 비밀번호 얻기
	 * @param user_id
	 * @return
	 * @throws SQLException
	 */
	public String getPassword(String user_id) throws SQLException {
		return mDAO.selectPw(user_id);
	} //getPassword

	/**
	 * 회원 탈퇴 폼에서 보여줄 닉네임 얻기
	 * @param mVO
	 * @return
	 * @throws SQLException
	 */
	public String getUserNickname(String user_id) throws SQLException {
		String result=mDAO.selectGetNickname(user_id);
		return result;
	} //getUserNickname
	
	/**
	 * 회원탈퇴 처리 업무로직
	 * @param dVO
	 * @return
	 */
	public boolean deleteMember(DeleteMemberVO dVO) {
		boolean result=false;
		
		try {
			mDAO.updateMember(dVO.getUser_id());
			result=true;
		} catch(DataAccessException dae) { 
			result=false;
		} //end catch
		
		return result;
	} //deleteMember

	/**
	 * 비밀번호 암호화 업무로직 (MD5)
	 * @param plainPass
	 * @return
	 */
	public String encryptPass(String plainPass) {
		String encryptPass="";
		
		try {
			encryptPass=DataEncrypt.messageDigest("MD5", plainPass);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace(); //주석처리하기!!
		}
		
		return encryptPass;
	} //encryptPass
	
	/**
	 * 복호화할 수 있는 AES 방식의 암호화
	 * @param phone
	 * @return
	 */
	public String encryptPhone(String phone) {
		String encryption="";
		
		DataEncrypt de;
		try {
			//암호화
			de = new DataEncrypt("AbcdeFgHijklmnOpq");
			encryption=de.encryption(phone);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException gse) {
			gse.printStackTrace();
		}
		
		return encryption;
	} //encryptPhone
	
	/**
	 * key로 복호화진행
	 * @param encryption
	 * @return
	 */
	public String decryptPhone(String encryption) {
		String decryption="";
		
		DataDecrypt dd;
		try {
			//복호화
			dd=new DataDecrypt("AbcdeFgHijklmnOpq");
			decryption=dd.decryption(encryption);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException gse) {
			gse.printStackTrace();
		}
		
		return decryption;
	} //decryptPhone
	
} //class
