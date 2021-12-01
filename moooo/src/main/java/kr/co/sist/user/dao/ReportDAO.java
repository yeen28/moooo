package kr.co.sist.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.co.sist.dao.GetJdbcTemplate;
import kr.co.sist.user.vo.ReportReasonVO;
import kr.co.sist.user.vo.ReportVO;

public class ReportDAO {
	
	/**
	 * 신고하는 사용자가 신고할 사용자를 이전에 신고한 적이 있는지 확인
	 * @param ReportVO
	 * @return 이전에 신고한 적이 있는지
	 * @throws SQLException
	 */
	public String beforeReport(ReportVO rv) throws SQLException {
		String result="";
		
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		String beforeReport="select reason_id from report where reported_user_id=? and user_id=?";
		try {
			result=jt.queryForObject(beforeReport, new Object[] { rv.getReported_user_id(), rv.getUser_id() }, String.class);
		} catch (EmptyResultDataAccessException erdae) {
			result="";
		} //end catch
		
		gjt.closeAc();
		
		return result;
	} //beforeReport
	
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
	} //insertReport
	
	/**
	 * 화면에 보여줄 신고이유
	 * @return 신고코드번호,이유 List
	 * @throws DataAccessException
	 */
	public List<ReportReasonVO> selectReport() throws SQLException {
		List<ReportReasonVO> result=null;
		
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		String select="select * from report_reason";
		
		result=jt.query(select, new RowMapper<ReportReasonVO>() {
			public ReportReasonVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ReportReasonVO rv=new ReportReasonVO();
				rv.setReason_id(rs.getInt("reason_id"));
				rv.setReason(rs.getString("reason"));
				return rv;
			} //mapRow
		});
		
		gjt.closeAc();
		
		return result;
	} //selectBuyCnt
	
}
