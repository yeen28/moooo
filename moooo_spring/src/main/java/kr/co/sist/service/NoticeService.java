package kr.co.sist.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.sist.dao.NoticeDAO;
import kr.co.sist.vo.NoticeVO;

@Component
public class NoticeService {

	@Autowired(required = false)
	private NoticeDAO nDAO;
	
	public List<NoticeVO> searchNoticeList(int page) {
		List<NoticeVO> list=null;
		
		try {
			int numPerPage=10;
			int totData=nDAO.selectNotiCnt();
			int LastPage=totData/numPerPage;
			if(totData % numPerPage > 0){
				++LastPage;
			}//end if
			int blockPage=10;
			int nowPage=page; //현재 페이지
			
			int start=((nowPage-1)/blockPage)*10+1;
			int end=start+blockPage-1;
			if( end > LastPage ){
				end=LastPage;
			}//end if
			
			int rowBegin=(nowPage-1)*numPerPage+1;
			int rowEnd=nowPage*numPerPage;
			list=nDAO.selectNotiTitle(rowBegin, rowEnd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	} //searchNoticeList
	
	public NoticeVO noticeDetail(int notice_id) {
		NoticeVO nVO=null;
		
		try {
			nVO=nDAO.selectNotice(notice_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return nVO;
	} //noticeDetail
	
}
