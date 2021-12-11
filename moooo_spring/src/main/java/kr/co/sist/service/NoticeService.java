package kr.co.sist.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import kr.co.sist.dao.NoticeDAO;
import kr.co.sist.vo.NoticeVO;

@Component
public class NoticeService {

	@Autowired(required = false)
	private NoticeDAO nDAO;
	
	public List<NoticeVO> searchNoticeList(String page) {
		List<NoticeVO> list=null;
		
		try {
			int nowPage=nowPage(page); //현재 페이지
			int numPerPage=numPerPage(); // 한 페이지 당 보여줄 게시물 수
			int totData=searchAllCnt(); //전체 게시물 수
			int lastPage=totalPage(totData,numPerPage); //마지막 페이지번호
			int blockPage=blockPage();
			
			int start=((nowPage-1)/blockPage)*10+1;
			int end=endNum(start, blockPage);
			if( end > lastPage ){
				end=lastPage;
			}//end if
			
			int rowBegin=startNum(nowPage,blockPage);
			int rowEnd=nowPage*numPerPage;
			list=nDAO.selectNotiTitle(rowBegin, rowEnd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	} //searchNoticeList
	
	/**
	 * 현재 페이지를 숫자로 변환
	 * @param page 현재 페이지
	 * @return 숫자로 변환된 현재 페이지
	 */
	public int nowPage(String page) {
		int nowPage=0;
		
		try {
			nowPage=Integer.parseInt(page);
		} catch(NumberFormatException nfe) {
			nowPage=1;
		} //end catch
		
		return nowPage;
	} //nowPage
	
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
		
		nVO=nDAO.selectNotice(notice_id, control);
		
		return nVO;
	} //noticeDetail
	
}
