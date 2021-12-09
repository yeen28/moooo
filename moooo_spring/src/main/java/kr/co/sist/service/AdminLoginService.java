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
	 * �α��� ó�� ��������
	 * @param aVO
	 * @return true ���� | false ����
	 * @throws SQLException
	 */
	public boolean procLogin(AdminLoginVO aVO) throws SQLException {
		// ��й�ȣ ��ȣȭ
		aVO.setPass(encryptPass(aVO.getPass()));
		
		String id=alDAO.selectLogin(aVO.getAdmin_id(), aVO.getPass());
		if( "".equals(id) ) { //�α��� ����
			return false;
		} else { //�α��� ����
			return true;
		}//end else
	} //procLogin
	
	public boolean changePass(UpdateAdminPassVO uVO) throws DataAccessException {
		uVO.setNew_pass(encryptPass(uVO.getNew_pass())); // ��й�ȣ ��ȣȭ
		int cnt=alDAO.updatePass(uVO.getAdmin_id(), uVO.getNew_pass());
		if( cnt == 0) { //����
			return false;
		} else { //����
			return true;
		}//end else
	} //changePass
	
	/**
	 * ��й�ȣ ��ȣȭ ��������
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
