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
	 * ���������̼� ���
	 * @param page
	 * @return
	 * @throws SQLException 
	 */
	public PaginationVO getPagination(String page) throws SQLException {
		PaginationVO pVO=new PaginationVO();
		
		int numPerPage=numPerPage(); //�� ������ �� ������ �Խù��� ��
		int totData=searchAllCnt(); //��ü �Խù� ��
		int lastPage=totalPage(totData,numPerPage); //������ ��������ȣ
		int blockPage=blockPage();
		int nowPage=1; //���� ������
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
	 * ��ü �Խù��� ��
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
	 * �� ȭ�鿡 ������ �Խù��� ��
	 * @return
	 */
	public int numPerPage() {
		return 10;
	} //pageScale
	
	/**
	 * �ϴܿ� ������ ���������̼� ���� ��
	 * @return
	 */
	public int blockPage() {
		return 10;
	} //blockPage
	
	/**
	 * ��� �Խù��� �����ֱ� ���� ������ ��
	 * @param totData ��ü �Խù� ��
	 * @param numPerPage �� ������ �� ������ �Խù� ��
	 * @return
	 */
	public int totalPage(int totData, int numPerPage) {
		int totalPage=0;
		
		totalPage=(int)Math.ceil( (double)totData/numPerPage );
		
		return totalPage;
	} //totalPage
	
	/**
	 * ���� ����(rowBegin)
	 * @param nowPage ���� ������ ��ȣ
	 * @param blockPage �ϴܿ� ������ ���������̼� ���� ��
	 * @return
	 */
	public int startNum(int nowPage, int blockPage) {
		int startNum=0;
		
		//startNum=(nowPage-1)*numPerPage+1;
		startNum=nowPage*blockPage-blockPage+1;
		
		return startNum;
	} //startNum
	
	/**
	 * �� ����(end)
	 * @param start ���۹�ȣ
	 * @param blockPage �ϴܿ� ������ ������ ����
	 * @return
	 */
	public int endNum(int start, int blockPage) {
		int end=0;
		end=start+blockPage-1;
		
		return end;
	} //endNum
	
	/**
	 * ��ü ȸ�� ��ȸ
	 * @return ȸ�� List
	 * @throws SQLException
	 */
	public List<MemberVO> searchMember() throws DataAccessException {
		List<MemberVO> listMember=null;
		
		listMember=mDAO.selectAllUser();
		
		return listMember;
	} //searchMember
	
	/**
	 * �Ű�� ȸ�� ��ȸ
	 * @return �Ű�� ȸ�� List
	 * @throws SQLException
	 */
	public List<MemberVO> searchReportedMember() throws SQLException {
		List<MemberVO> listMember=null;
		
		listMember=rDAO.selectReportedUser();
		
		return listMember;
	} //searchMember
	
	/**
	 * ȸ������ ��������
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
	 * �Ű����� ��� ��������
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
			for(int i=0; i<reasonList.size(); i++) { //��ȸ�� ����� �ݺ�
				temp=new JSONObject(); //��ȸ�� ����� �����ϱ� ���� JSONObject ����
				temp.put("reason", reasonList.get(i)); //������ JSONObject�� ��ȸ����� ����
				jsonArr.add(temp); //���� ���� JSONObject�� JSONArray�� �Ҵ�
			} //end for
			
			//���� ���� ���� ���� JSONArray�� JSONObject�� ����
			jsonObj.put("data", jsonArr);
		
		} catch(DataAccessException dae) {
			dae.printStackTrace();
		} //end catch
		
		return jsonObj.toJSONString();
	} //searchReportReason
	
	/**
	 * ȸ������ ó�� ��������
	 */
	public boolean deleteMember(String user_id) {
		boolean result=false;
		
		try {
			if( mDAO.deleteMember(user_id) != 0 ) { //���� ����
				result=true;
			} //end if
		} catch(DataAccessException dae) { 
			result=false;
		} //end catch
		
		return result;
	} //deleteMember
	
} //class
