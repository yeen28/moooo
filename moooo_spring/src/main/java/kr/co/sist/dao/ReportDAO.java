package kr.co.sist.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import kr.co.sist.vo.ReportReasonVO;
import kr.co.sist.vo.ReportVO;

@Component
public class ReportDAO {
	
	@Autowired(required = false)
	private JdbcTemplate jt;
	
	/**
	 * 신고하는 사용자가 신고할 사용자를 이전에 신고한 적이 있는지 확인
	 * @param ReportVO
	 * @return 이전에 신고한 적이 있는지
	 * @throws SQLException
	 */
	public String selectBeforeReport(ReportVO rv) throws SQLException {
		String result="";
		
		String beforeReport="select reason_id from report where reported_user_id=? and user_id=?";
		try {
			result=jt.queryForObject(beforeReport, new Object[] { rv.getReported_user_id(), rv.getUser_id() }, String.class);
		} catch (EmptyResultDataAccessException erdae) {
			result="";
		} //end catch
		
		return result;
	} //selectBeforeReport
	
	/**
	 * 신고 추가
	 * @param ReportVO
	 * @throws DataAccessException
	 */
	public void insertReport(ReportVO rv) throws DataAccessException {
		String insert="insert into report(reported_user_id, reason_id, user_id) values(?,?,?)";
		jt.update(insert, rv.getReported_user_id(), rv.getReason_id(), rv.getUser_id());
	} //insertReport
	
	/**
	 * 신고당한 유저의 신고누적횟수 증가
	 * @param reported_user_id
	 * @return
	 * @throws SQLException
	 */
	public int updateReport(String reported_user_id) throws SQLException {
		int cnt=0;
		
		String update="update users set reported_cnt=(reported_cnt+1) where user_id=?";
		cnt=jt.update(update, reported_user_id);
		
		return cnt;
	} //updateReport
	
	/**
	 * 화면에 보여줄 신고이유
	 * @return 신고코드번호,이유 List
	 * @throws SQLException
	 */
	public List<ReportReasonVO> selectReport() throws SQLException {
		List<ReportReasonVO> result=null;
		
		String select="select * from report_reason";
		
		result=jt.query(select, new RowMapper<ReportReasonVO>() {
			public ReportReasonVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ReportReasonVO rv=new ReportReasonVO();
				rv.setReason_id(rs.getInt("reason_id"));
				rv.setReason(rs.getString("reason"));
				return rv;
			} //mapRow
		});
		
		return result;
	} //selectReport
	
} //class
