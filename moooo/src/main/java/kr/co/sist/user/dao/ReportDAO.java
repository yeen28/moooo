package kr.co.sist.user.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import kr.co.sist.dao.GetJdbcTemplate;
import kr.co.sist.user.vo.ReportVO;

public class ReportDAO {
	
	/**
	 * 신고 추가
	 * @param ReportVO
	 * @throws DataAccessException
	 */
	public void insertReport(ReportVO rv) throws DataAccessException {
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		String insert="insert into report(reported_user_id, reason_id, user_id) values(?,?,?)";
		
		jt.update(insert, rv.getReported_user_id(), rv.getReason_id(), rv.getUser_id());
		
		gjt.closeAc();
	} //selectBuyCnt
	
}
