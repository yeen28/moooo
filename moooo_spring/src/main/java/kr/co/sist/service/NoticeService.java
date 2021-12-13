package kr.co.sist.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import kr.co.sist.dao.NoticeDAO;
import kr.co.sist.vo.NoticeVO;
import kr.co.sist.vo.PaginationVO;

@Component
public class NoticeService {

	@Autowired(required = false)
	private NoticeDAO nDAO;
	
	public List<NoticeVO> searchNoticeList(String page) {
		List<NoticeVO> list=null;
		
		PaginationVO pVO=getPagination(page);
		
		try {
			list=nDAO.selectNotiTitle(pVO.getRowBegin(), pVO.getRowEnd());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	} //searchNoticeList
	
	/**
	 * ���������̼� ���
	 * @param page
	 * @return
	 */
	public PaginationVO getPagination(String page) {
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
	 */
	public int searchAllCnt() {
		int cnt=0;
		
		try {
			cnt = nDAO.selectNotiCnt();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
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
	 * ���� ��������
	 * @param notice_id
	 * @return NoticeVO
	 * @throws SQLException 
	 */
	public NoticeVO noticeDetail(int notice_id, String control) throws SQLException {
		NoticeVO nVO=null;
		
		try {
			nVO=nDAO.selectNotice(notice_id, control);
		} catch(DataAccessException dae) {  }
		
		return nVO;
	} //noticeDetail
	
	/**
	 * �������� �߰�
	 * @param NoticeVO
	 * @throws SQLException
	 */
	public void addNotice(NoticeVO nVO) throws SQLException {
		nDAO.insertNoti(nVO);
	} //addNotice
	
	/**
	 * �������� ����
	 * @param NoticeVO
	 * @return true ���� | false ����
	 * @throws DataAccessException
	 */
	public boolean editNotice(NoticeVO nVO) throws DataAccessException {
		int cnt=nDAO.updateNoti(nVO);
		if(cnt == 0) { //���� ����
			return false;
		} else { //���� ����
			return true;
		} //end else
	} //editNotice
	
	/**
	 * �������� ����
	 * @param notice_id
	 * @param admin_id
	 * @return true ���� | false ����
	 * @throws SQLException
	 */
	public boolean deleteNotice(int notice_id, String admin_id) throws SQLException {
		int cnt=nDAO.deleteNoti(notice_id, admin_id);
		if( cnt == 0 ) { //����
			return false;
		} else { //����
			return true;
		} //end else
	} //deleteNotice
	
} //class
