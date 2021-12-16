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
	 * �α��� ó��
	 * @param mVO
	 * @return
	 */
	public boolean loginProc(MemberVO mVO) {
		boolean result=false;
		
		// ��й�ȣ ��ȣȭ
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
	 * ���̵� �ߺ��˻�
	 * @param �Է��� ���̵�
	 * @return true ��밡�� | false ���Ұ���
	 * @throws SQLException
	 */
	public boolean searchIdDup(String id) throws SQLException {
		boolean result=false;
		
		String dbId=mDAO.selectId(id);
		if( "".equals( dbId ) ) { // ��밡���� ���̵��� ���
			result=true;
		} else { // �̹� �����ϴ� ���̵��� ���
			result=false;
		} //end else
		
		return result;
	} //searchIdDup
	
	/**
	 * ȸ������ ó��
	 * @param MemberVO
	 * @return true|false (��������)
	 */
	public boolean signUpProc(MemberVO mVO) {
		boolean result=false;
		
		String encryptPass=encryptPass(mVO.getPass()); //��й�ȣ ��ȣȭ
		String encryptPhone=encryptPhone(mVO.getPhone()); //��ȭ��ȣ ��ȣȭ(AES)
		
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
	 * ���̵� ã�� ó�� ��������
	 * @param mVO
	 * @return user_id ���̵�
	 * @throws DataAccessException
	 */
	public String findIdProc(MemberVO mVO) throws DataAccessException {
		String encryptPhone=encryptPhone(mVO.getPhone());
		String id=mDAO.selectFindId(mVO.getNickname(), encryptPhone);
		
		return id;
	} //findIdProc
	
	/**
	 * ��й�ȣ ã�� ��������
	 * @param mVO
	 * @return �ӽú�й�ȣ
	 * @throws DataAccessException
	 * @throws SQLException
	 */
	public String findPassProc(MemberVO mVO) throws DataAccessException, SQLException {
		String encryptPhone=encryptPhone(mVO.getPhone());
		// ȸ�������� ��ġ���� ������ ���ܸ� ����
		mDAO.selectFindPass(mVO.getUser_id(), encryptPhone); //��ȣȭ�Ǿ��ִ� ��й�ȣ�� ���ϵ�
		
		return tempPass(mVO.getUser_id()); //�ӽú�й�ȣ ���� �� ����
	} //findPassProc
	
	/**
	 * �ӽ� ��й�ȣ ���� �� ��й�ȣ ������Ʈ
	 * @param ���̵�
	 * @return
	 * @throws SQLException 
	 */
	public String tempPass(String user_id) throws SQLException {
		String tempPass="";
		
		//8�ڸ� �ӽú�й�ȣ ����
		while( tempPass.length() < 8 ) {
			int rand = (int) (Math.random() * 75) + 48;
			if (rand < 58 || (rand > 64 && rand < 91) || rand > 96) { // ����, �빮��, �ҹ���
				tempPass += (char) rand;
			} else continue;
		}//end while
		
		UpdateUserPassVO uVO=new UpdateUserPassVO();
		uVO.setUser_id(user_id);
		uVO.setNew_pass(encryptPass(tempPass)); //��ȣȭ
		mDAO.updatePass(uVO);
		
		return tempPass;
	} //tempPass

	/**
	 * ��й�ȣ ���� ��������
	 * @param uVO
	 * @return true ���� | false ����
	 * @throws SQLException
	 */
	public boolean changePass(UpdateUserPassVO uVO, String new_pass2) throws SQLException {
		uVO.setNew_pass( encryptPass(uVO.getNew_pass()) );
		if( mDAO.updatePass(uVO) != 0) { //��й�ȣ ���� ����
			return true;
		} else { //����
			return false;
		} //end else
	} //changePass
	
	/**
	 * ���̵� �´� ��й�ȣ ���
	 * @param user_id
	 * @return
	 * @throws SQLException
	 */
	public String getPassword(String user_id) throws SQLException {
		return mDAO.selectPw(user_id);
	} //getPassword

	/**
	 * ȸ�� Ż�� ������ ������ �г��� ���
	 * @param mVO
	 * @return
	 * @throws SQLException
	 */
	public String getUserNickname(String user_id) throws SQLException {
		String result=mDAO.selectGetNickname(user_id);
		return result;
	} //getUserNickname
	
	/**
	 * ȸ��Ż�� ó�� ��������
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
	 * ��й�ȣ ��ȣȭ �������� (MD5)
	 * @param plainPass
	 * @return
	 */
	public String encryptPass(String plainPass) {
		String encryptPass="";
		
		try {
			encryptPass=DataEncrypt.messageDigest("MD5", plainPass);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace(); //�ּ�ó���ϱ�!!
		}
		
		return encryptPass;
	} //encryptPass
	
	/**
	 * ��ȣȭ�� �� �ִ� AES ����� ��ȣȭ
	 * @param phone
	 * @return
	 */
	public String encryptPhone(String phone) {
		String encryption="";
		
		DataEncrypt de;
		try {
			//��ȣȭ
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
	 * key�� ��ȣȭ����
	 * @param encryption
	 * @return
	 */
	public String decryptPhone(String encryption) {
		String decryption="";
		
		DataDecrypt dd;
		try {
			//��ȣȭ
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
