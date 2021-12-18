package kr.co.sist.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.co.sist.dao.MypageDAO;
import kr.co.sist.dao.WantBuyDAO;
import kr.co.sist.dao.WantSellDAO;
import kr.co.sist.util.cipher.DataDecrypt;
import kr.co.sist.vo.MemberVO;
import kr.co.sist.vo.WantBuyVO;
import kr.co.sist.vo.WantSellVO;

@Component
public class MypageService {
	
	@Autowired(required = false)
	private MypageDAO mDAO;
	@Autowired(required = false)
	private WantBuyDAO bDAO;
	@Autowired(required = false)
	private WantSellDAO sDAO;
	
	/**
	 * �������� ���� �����ֱ�
	 * @param user_id
	 * @return MemberVO
	 * @throws SQLException 
	 */
	public MemberVO getMypageInfo(String user_id) throws SQLException {
		MemberVO mVO=mDAO.selectMypage(user_id);
		mVO.setPhone( decryptPhone(mVO.getPhone(), "AbcdeFgHijklmnOpq") );
		
		return mVO;
	} //getMypageInfo
	
	/**
	 * �������� ó��
	 * @param request
	 * @param mVO
	 * @return true ���� | false ����
	 * @throws IOException
	 * @throws SQLException
	 */
	public boolean updateMypage(HttpServletRequest request, MemberVO mVO) throws IOException, SQLException {
		boolean result=false;
		
		//1. ���Ͼ��ε� ������Ʈ ����. ( ������ ���ε� �ȴ�. )
		File uploadPath=new File("C:/Users/user/git/moooo/moooo_spring/src/main/webapp/upload");
		//File uploadPath=new File("E:/moooo/upload"); //�������� ���
		if( !uploadPath.exists() ){ //���ε� ������ �������� ������
			uploadPath.mkdirs();
		}//end if
		int maxSize=1024*1024*10;
		//�����Ǹ� ���Ͼ��ε尡 �ȴ�.
		MultipartRequest mr=new MultipartRequest( request, uploadPath.getAbsolutePath(), maxSize, "UTF-8", new DefaultFileRenamePolicy() );

		//���� ������Ʈ�� ����Ͽ� �Ķ���� �ޱ�
		String user_id=mr.getParameter("user_id");
		String nickname=mr.getParameter("nickname");
		String phone=mr.getParameter("phone");
		//���ϸ� ���
		String originName=mr.getOriginalFileName("img"); //���� ���ϸ�
		String fileSystemName=mr.getFilesystemName("img"); //����� ���ϸ�

		mVO.setUser_id(user_id);
		mVO.setNickname(nickname);
		mVO.setPhone(phone);
		mVO.setImg(fileSystemName);
		
		//���� ������ �����ϱ�
		String beforeFileName=mDAO.selectMypage(user_id).getImg();
		File deleteImg=new File("C:/Users/user/git/moooo/moooo_spring/src/main/webapp/upload/"+beforeFileName);
		//File deleteImg=new File("E:/moooo/upload/"+beforeFileName);
		if(deleteImg.exists() && deleteImg.isFile()){ //���� �̹����� �����Ѵٸ�
			if(deleteImg.delete()){ //���� ����
				//System.out.println("���� : "+beforeFileName);
			}//else{System.out.println("����");}
		}
		
		int cnt=mDAO.updateMypage(mVO);
		if(cnt != 0) {
			result=true;
		}//end if
		
		return result;
	} //updateMypage

	/**
	 * ���� �� �� (���;��)
	 * @param user_id
	 * @return WantBuyVO ����Ʈ
	 * @throws SQLException
	 */
	public List<WantBuyVO> getWriteListBuy(String user_id) throws SQLException {
		List<WantBuyVO> list=null;
		
		list=bDAO.selectMypageBuy(user_id);
		
		return list;
	} //getWriteListBuy
	
	/**
	 * ���� �� �� (�Ⱦƿ�)
	 * @param user_id
	 * @return WantSellVO ����Ʈ
	 * @throws SQLException
	 */
	public List<WantSellVO> getWriteListSell(String user_id) throws SQLException{
		List<WantSellVO> list=null;
		
		list=sDAO.selectMypageSell(user_id);
		
		return list;
	} //getWriteListSell
	
	/**
	 * ���� �� �� ����
	 * @param listCode
	 * @return true ���� | false ����
	 */
	public boolean deleteList(List<Integer> listCode) {
		boolean result=false;
		
		
		return result;
	} //deleteList

	/**
	 * ���ɱ� ��� ���
	 * @param user_id
	 * @return WantSellVO ����Ʈ
	 * @throws SQLException
	 */
	public List<WantSellVO> getInterestList(String user_id) throws SQLException{
		List<WantSellVO> list=null;
		
		List<Integer> listCode=mDAO.selectInterestList(user_id);
		list=mDAO.selectInterestMypage(listCode);
		
		return list;
	} //getInterestList
	
	/**
	 * ���ɱ� üũ�ڽ��� ����
	 * @param listCode
	 * @return true ���� | false ����
	 */
	public boolean cancelInterestList(List<Integer> listCode) {
		boolean result=false;
		
		return result;
	} //cancelInterestList
	
	/**
	 * key�� ��ȣȭ����
	 * @param encryption
	 * @return
	 */
	public String decryptPhone(String encryptPhone, String key) {
		String decryption="";
		
		DataDecrypt dd;
		try {
			//��ȣȭ
			dd=new DataDecrypt(key);
			decryption=dd.decryption(encryptPhone);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException gse) {
			gse.printStackTrace();
		}
		
		return decryption;
	} //decryptPhone

} //class
