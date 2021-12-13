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
	 * 페이지네이션 얻기
	 * @param page
	 * @return
	 */
	public PaginationVO getPagination(String page) {
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
	 * 공지 상세페이지
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
	 * 공지사항 추가
	 * @param NoticeVO
	 * @throws SQLException
	 */
	public void addNotice(NoticeVO nVO) throws SQLException {
		nDAO.insertNoti(nVO);
	} //addNotice
	
	/**
	 * 공지사항 수정
	 * @param NoticeVO
	 * @return true 성공 | false 실패
	 * @throws DataAccessException
	 */
	public boolean editNotice(NoticeVO nVO) throws DataAccessException {
		int cnt=nDAO.updateNoti(nVO);
		if(cnt == 0) { //변경 실패
			return false;
		} else { //변경 성공
			return true;
		} //end else
	} //editNotice
	
	/**
	 * 공지사항 삭제
	 * @param notice_id
	 * @param admin_id
	 * @return true 성공 | false 실패
	 * @throws SQLException
	 */
	public boolean deleteNotice(int notice_id, String admin_id) throws SQLException {
		int cnt=nDAO.deleteNoti(notice_id, admin_id);
		if( cnt == 0 ) { //실패
			return false;
		} else { //성공
			return true;
		} //end else
	} //deleteNotice
	
} //class
