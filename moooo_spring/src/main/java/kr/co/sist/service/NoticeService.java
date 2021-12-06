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
	
	public List<NoticeVO> searchNoticeList(int begin,int end){
		List<NoticeVO> list=null;
		
		try {
			list = nDAO.selectNotiTitle(begin, end);
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
