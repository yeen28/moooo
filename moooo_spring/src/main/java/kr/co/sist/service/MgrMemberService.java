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
import kr.co.sist.vo.PaginationVO;

@Component
public class MgrMemberService {

	@Autowired(required = false)
	private MemberDAO mDAO;
	@Autowired(required = false)
	private ReportDAO rDAO;
	
	/**
	 * 페이지네이션 얻기
	 * @param page
	 * @return
	 * @throws SQLException 
	 */
	public PaginationVO getPagination(String page) throws SQLException {
		PaginationVO pVO=new PaginationVO();
		
		int numPerPage=numPerPage(); //한 페이지 당 보여줄 게시물의 수
		int totData=searchAllCnt(); //전체 게시물 수
		int lastPage=totalPage(totData,numPerPage); //마지막 페이지번호
		int blockPage=blockPage();
		int nowPage=1; //현재 페이지
		try{
			nowPage=Integer.parseInt(page);
		} catch (NumberFormatException nfe){
			nowPage=1;
		}
		int start=((nowPage-1)/blockPage)*10+1;
		int end=start+blockPage-1;
		if( end > lastPage ){
			end=lastPage;
		} //end if
		
		int rowBegin=(nowPage-1)*numPerPage+1;
		int rowEnd=nowPage*numPerPage;
		
		pVO.setNowPage(nowPage);
		pVO.setStart(start);
		pVO.setEnd(end);
		pVO.setRowBegin(rowBegin);
		pVO.setRowEnd(rowEnd);
		pVO.setLastPage(lastPage);
		
		return pVO;
	} //getPagination
	
	/**
	 * 전체 게시물의 수
	 * @return
	 * @throws SQLException 
	 */
	public int searchAllCnt() throws SQLException {
		int cnt=0;
		
		try {
			cnt = mDAO.selectUserCnt();
		} catch (DataAccessException e) {  }
		
		return cnt;
	}//searchAllCnt
	
	/**
	 * 한 화면에 보여줄 게시물의 수
	 * @return
	 */
	public int numPerPage() {
		return 10;
	} //pageScale
	
	/**
	 * 하단에 보여줄 페이지네이션 블럭의 수
	 * @return
	 */
	public int blockPage() {
		return 10;
	} //blockPage
	
	/**
	 * 모든 게시물을 보여주기 위한 페이지 수
	 * @param totData 전체 게시물 수
	 * @param numPerPage 한 페이지 당 보여줄 게시물 수
	 * @return
	 */
	public int totalPage(int totData, int numPerPage) {
		int totalPage=0;
		
		totalPage=(int)Math.ceil( (double)totData/numPerPage );
		
		return totalPage;
	} //totalPage
	
	/**
	 * 시작 숫자(rowBegin)
	 * @param nowPage 현재 페이지 번호
	 * @param blockPage 하단에 보여줄 페이지네이션 블럭의 수
	 * @return
	 */
	public int startNum(int nowPage, int blockPage) {
		int startNum=0;
		
		//startNum=(nowPage-1)*numPerPage+1;
		startNum=nowPage*blockPage-blockPage+1;
		
		return startNum;
	} //startNum
	
	/**
	 * 끝 숫자(end)
	 * @param start 시작번호
	 * @param blockPage 하단에 보여줄 페이지 개수
	 * @return
	 */
	public int endNum(int start, int blockPage) {
		int end=0;
		end=start+blockPage-1;
		
		return end;
	} //endNum
	
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
