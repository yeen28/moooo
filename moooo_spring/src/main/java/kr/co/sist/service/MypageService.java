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
	 * 정보변경 정보 보여주기
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
	 * 정보변경 처리
	 * @param request
	 * @param mVO
	 * @return true 성공 | false 실패
	 * @throws IOException
	 * @throws SQLException
	 */
	public boolean updateMypage(HttpServletRequest request, MemberVO mVO) throws IOException, SQLException {
		boolean result=false;
		
		//1. 파일업로드 컴포넌트 생성. ( 파일이 업로드 된다. )
		File uploadPath=new File("C:/Users/user/git/moooo/moooo_spring/src/main/webapp/upload");
		//File uploadPath=new File("E:/moooo/upload"); //서버에서 경로
		if( !uploadPath.exists() ){ //업로드 폴더가 존재하지 않으면
			uploadPath.mkdirs();
		}//end if
		int maxSize=1024*1024*10;
		//생성되면 파일업로드가 된다.
		MultipartRequest mr=new MultipartRequest( request, uploadPath.getAbsolutePath(), maxSize, "UTF-8", new DefaultFileRenamePolicy() );

		//파일 컴포넌트를 사용하여 파라메터 받기
		String user_id=mr.getParameter("user_id");
		String nickname=mr.getParameter("nickname");
		String phone=mr.getParameter("phone");
		//파일명 얻기
		String originName=mr.getOriginalFileName("img"); //원본 파일명
		String fileSystemName=mr.getFilesystemName("img"); //변경된 파일명

		mVO.setUser_id(user_id);
		mVO.setNickname(nickname);
		mVO.setPhone(phone);
		mVO.setImg(fileSystemName);
		
		//이전 파일은 삭제하기
		String beforeFileName=mDAO.selectMypage(user_id).getImg();
		File deleteImg=new File("C:/Users/user/git/moooo/moooo_spring/src/main/webapp/upload/"+beforeFileName);
		//File deleteImg=new File("E:/moooo/upload/"+beforeFileName);
		if(deleteImg.exists() && deleteImg.isFile()){ //이전 이미지가 존재한다면
			if(deleteImg.delete()){ //사진 삭제
				//System.out.println("삭제 : "+beforeFileName);
			}//else{System.out.println("실패");}
		}
		
		int cnt=mDAO.updateMypage(mVO);
		if(cnt != 0) {
			result=true;
		}//end if
		
		return result;
	} //updateMypage

	/**
	 * 내가 쓴 글 (사고싶어요)
	 * @param user_id
	 * @return WantBuyVO 리스트
	 * @throws SQLException
	 */
	public List<WantBuyVO> getWriteListBuy(String user_id) throws SQLException {
		List<WantBuyVO> list=null;
		
		list=bDAO.selectMypageBuy(user_id);
		
		return list;
	} //getWriteListBuy
	
	/**
	 * 내가 쓴 글 (팔아요)
	 * @param user_id
	 * @return WantSellVO 리스트
	 * @throws SQLException
	 */
	public List<WantSellVO> getWriteListSell(String user_id) throws SQLException{
		List<WantSellVO> list=null;
		
		list=sDAO.selectMypageSell(user_id);
		
		return list;
	} //getWriteListSell
	
	/**
	 * 내가 쓴 글 삭제
	 * @param listCode
	 * @return true 성공 | false 실패
	 */
	public boolean deleteList(List<Integer> listCode) {
		boolean result=false;
		
		
		return result;
	} //deleteList

	/**
	 * 관심글 목록 얻기
	 * @param user_id
	 * @return WantSellVO 리스트
	 * @throws SQLException
	 */
	public List<WantSellVO> getInterestList(String user_id) throws SQLException{
		List<WantSellVO> list=null;
		
		List<Integer> listCode=mDAO.selectInterestList(user_id);
		list=mDAO.selectInterestMypage(listCode);
		
		return list;
	} //getInterestList
	
	/**
	 * 관심글 체크박스로 삭제
	 * @param listCode
	 * @return true 성공 | false 실패
	 */
	public boolean cancelInterestList(List<Integer> listCode) {
		boolean result=false;
		
		return result;
	} //cancelInterestList
	
	/**
	 * key로 복호화진행
	 * @param encryption
	 * @return
	 */
	public String decryptPhone(String encryptPhone, String key) {
		String decryption="";
		
		DataDecrypt dd;
		try {
			//복호화
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
