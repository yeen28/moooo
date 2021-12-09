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
	public boolean procLogin(AdminLoginVO aVO) throws SQLException {
		// 비밀번호 암호화
		aVO.setPass(encryptPass(aVO.getPass()));
		
		String id=alDAO.selectLogin(aVO.getAdmin_id(), aVO.getPass());
		if( "".equals(id) ) { //로그인 실패
			return false;
		} else { //로그인 성공
			return true;
		}//end else
	} //procLogin
	
	public boolean changePass(UpdateAdminPassVO uVO) throws DataAccessException {
		uVO.setNew_pass(encryptPass(uVO.getNew_pass())); // 비밀번호 암호화
		int cnt=alDAO.updatePass(uVO.getAdmin_id(), uVO.getNew_pass());
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
		String encryptPass="";
		
//		try {
//			encryptPass=DataEncrypt.messageDigest("MD5", plainPass);
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
		
		return encryptPass;
	} //encryptPass
	
} //class
