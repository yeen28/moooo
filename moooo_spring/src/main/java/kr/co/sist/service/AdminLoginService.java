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
	public boolean procLogin(AdminLoginVO aVO) {
		// ��й�ȣ ��ȣȭ
		aVO.setPass(encryptPass(aVO.getPass()));
		
		String id="";
		try {
			id = alDAO.selectLogin(aVO.getAdmin_id(), aVO.getPass());
			
		} catch (DataAccessException dae) {
			return false;
		}//end catch
		
		if( "".equals(id) ) { //�α��� ����
			return false;
		} else { //�α��� ����
			return true;
		}//end else
	} //procLogin
	
	/**
	 * ��й�ȣ ���� ��������
	 * @param uVO
	 * @return true ���� | false ����
	 * @throws DataAccessException, SQLException
	 */
	public boolean changePass(UpdateAdminPassVO uVO) throws DataAccessException, SQLException {
		uVO.setNew_pass(encryptPass(uVO.getNew_pass())); // ��й�ȣ ��ȣȭ
		
		/* �Է��� ���� ��й�ȣ�� �´��� Ȯ�� */
		String beforePass=uVO.getBefore_pass();
		String newPass=uVO.getNew_pass();
		int cnt=0;

		String dbPass=alDAO.selectChangePass( uVO.getAdmin_id() );

		if( beforePass.equals(dbPass) ){
			cnt=alDAO.updatePass(uVO.getAdmin_id(), newPass);
		} else {
			return false;
		} //end else
		
		// ���� DB�۾����� ��������
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
		String encryptPass=plainPass;
		
//		try {
//			encryptPass=DataEncrypt.messageDigest("MD5", plainPass);
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
		
		return encryptPass;
	} //encryptPass
	
} //class
