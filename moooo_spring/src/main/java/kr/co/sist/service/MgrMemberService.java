package kr.co.sist.service;

import java.sql.SQLException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import kr.co.sist.dao.MemberDAO;
import kr.co.sist.dao.ReportDAO;
import kr.co.sist.vo.MemberVO;

@Component
public class MgrMemberService {

	@Autowired(required = false)
	private MemberDAO mDAO;
	@Autowired(required = false)
	private ReportDAO rDAO;
	
	/**
	 * 전체 회원 조회
	 * @return 회원 List
	 * @throws SQLException
	 */
	public List<MemberVO> searchMember() throws DataAccessException {
		List<MemberVO> listMember=null;
		
		listMember=mDAO.selectAllUser();
		
		return listMember;
	} //searchMember
	
	/**
	 * 신고된 회원 조회
	 * @return 신고된 회원 List
	 * @throws SQLException
	 */
	public List<MemberVO> searchReportedMember() throws SQLException {
		List<MemberVO> listMember=null;
		
		listMember=rDAO.selectReportedUser();
		
		return listMember;
	} //searchMember
	
	/**
	 * 회원관리 상세페이지
	 * @param user_id
	 * @return MemberVO
	 * @throws SQLException
	 */
	public MemberVO searchMemberDetail(String user_id) throws SQLException {
		MemberVO mVO=null;
		
		try {
			mVO=mDAO.selectUserDetail(user_id);
		} catch(DataAccessException dae) {}
		
		return mVO;
	} //searchMemberDetail
	
	/**
	 * 신고이유 얻는 업무로직
	 * @param user_id
	 * @return
	 * @throws SQLException 
	 */
	public String searchReportReason(String reported_user_id) throws SQLException {
		List<String> reasonList=null;
		JSONObject jsonObj=new JSONObject();
		
		try {
			JSONArray jsonArr=new JSONArray();
			reasonList=mDAO.selectReportReason(reported_user_id);
			
			JSONObject temp=null;
			for(int i=0; i<reasonList.size(); i++) { //조회된 결과로 반복
				temp=new JSONObject(); //조회된 결과를 저장하기 위한 JSONObject 생성
				temp.put("reason", reasonList.get(i)); //생성된 JSONObject에 조회결과를 저장
				jsonArr.add(temp); //값을 가진 JSONObject을 JSONArray에 할당
			} //end for
			
			//여러 개의 값을 가진 JSONArray를 JSONObject에 저장
			jsonObj.put("data", jsonArr);
		
		} catch(DataAccessException dae) {
			dae.printStackTrace();
		} //end catch
		
		return jsonObj.toJSONString();
	} //searchReportReason
	
	/**
	 * 회원삭제 처리 업무로직
	 */
	public boolean deleteMember(String user_id) {
		boolean result=false;
		
		try {
			if( mDAO.deleteMember(user_id) != 0 ) { //삭제 성공
				result=true;
			} //end if
		} catch(DataAccessException dae) { 
			result=false;
		} //end catch
		
		return result;
	} //deleteMember
	
} //class
