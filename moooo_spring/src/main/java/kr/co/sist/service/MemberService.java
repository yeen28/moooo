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
	 * �α��� ó��
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
	 * ���̵� �ߺ��˻�
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
	 * ȸ������ ó��
	 * @param MemberVO
	 * @return true|false (��������)
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
	 * ���̵� ã�� ó�� ��������
	 * @param mVO
	 * @return user_id ���̵�
	 * @throws DataAccessException
	 */
	public String findIdProc(MemberVO mVO) throws DataAccessException {
		String id="";
		
		id=mDAO.selectFindId(mVO.getNickname(), mVO.getPhone());
		
		return id;
	} //findIdProc
	
	public String findPassProc(MemberVO mVO) throws DataAccessException, SQLException {
		// ȸ�������� ��ġ���� ������ ���ܸ� ����
		mDAO.selectFindPass(mVO.getUser_id(), mVO.getPhone()); //��ȣȭ�Ǿ��ִ� ��й�ȣ�� ���ϵ�
		
		return tempPass(mVO);
	} //findPassProc
	
	/**
	 * �ӽ� ��й�ȣ ���� �� ��й�ȣ ������Ʈ
	 * @param tVO
	 * @return
	 * @throws SQLException 
	 */
	public String tempPass(MemberVO mVO) throws SQLException {
		String tempPass="";
		
		for(int i=0; tempPass.length()<8; i++) {
			int rand = (int) (Math.random() * 75) + 48;
			if (rand < 58 || (rand > 64 && rand < 91) || rand > 96) { // ����, �빮��, �ҹ���
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
	 * ȸ�� Ż�� ������ ������ �г��� ���
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
	 * ��й�ȣ ��ȣȭ ��������
	 * @param plainPass
	 * @return
	 */
	public String encryptPass(String plainPass) {
		String encryptPass="";
		
		try {
		//�Ϲ��� �ؽ� �˰��� ����
		MessageDigest md=MessageDigest.getInstance("SHA-512"); /* MD2, MD5, SHA-384, SHA-512 */
		//�Ϲݹ��ڿ��� ��ȯ
		md.update( plainPass.getBytes() );
		//��ȯ�� ���ڿ��� �޴´�.
		byte[] sha=md.digest();
//		md.update( plainText2.getBytes() );
//		byte[] sha2=md.digest();
//		//�˾ƺ� �� �ִ� ���ڿ��� ��ȯ
//		Base64 base=new Base64();
//		String cipherText=new String( base.encode( sha ) );
//		String cipherText2=new String( base.encode( sha2 ) );
//		%>
//
//		�Ϲ� ���ڿ� : <%= plainText %>, <%= plainText2 %><br/>
//		encode���ڿ� : <%= cipherText %><br/>
//		encode���ڿ� : <%= cipherText2 %><br/>
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
