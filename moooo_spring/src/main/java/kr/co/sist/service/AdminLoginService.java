package kr.co.sist.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import kr.co.sist.dao.AdminLoginDAO;
import kr.co.sist.vo.AdminLoginVO;
import kr.co.sist.vo.UpdateAdminPassVO;

@Component
public class AdminLoginService {

	@Autowired(required = false)
	private AdminLoginDAO alDAO;
	
	/**
	 * 로그인 처리 업무로직
	 * @param aVO
	 * @return true 성공 | false 실패
	 * @throws SQLException
	 */
	public boolean procLogin(AdminLoginVO aVO) {
		// 비밀번호 암호화
		aVO.setPass(encryptPass(aVO.getPass()));
		
		String id="";
		try {
			id = alDAO.selectLogin(aVO.getAdmin_id(), aVO.getPass());
			
		} catch (DataAccessException dae) {
			return false;
		}//end catch
		
		if( "".equals(id) ) { //로그인 실패
			return false;
		} else { //로그인 성공
			return true;
		}//end else
	} //procLogin
	
	/**
	 * 비밀번호 변경 업무로직
	 * @param uVO
	 * @return true 성공 | false 실패
	 * @throws DataAccessException, SQLException
	 */
	public boolean changePass(UpdateAdminPassVO uVO) throws DataAccessException, SQLException {
		uVO.setNew_pass(encryptPass(uVO.getNew_pass())); // 비밀번호 암호화
		
		/* 입력한 이전 비밀번호가 맞는지 확인 */
		String beforePass=uVO.getBefore_pass();
		String newPass=uVO.getNew_pass();
		int cnt=0;

		String dbPass=alDAO.selectChangePass( uVO.getAdmin_id() );

		if( beforePass.equals(dbPass) ){
			cnt=alDAO.updatePass(uVO.getAdmin_id(), newPass);
		} else {
			return false;
		} //end else
		
		// 변경 DB작업에서 성공여부
		if( cnt == 0) { //실패
			return false;
		} else { //성공
			return true;
		}//end else
	} //changePass
	
	/**
	 * 비밀번호 암호화 업무로직
	 * @param plainPass
	 * @return
	 */
	public String encryptPass(String plainPass) {
		String encryptPass=plainPass;
		
//		try {
//			encryptPass=DataEncrypt.messageDigest("MD5", plainPass);
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
		
		return encryptPass;
	} //encryptPass
	
} //class
