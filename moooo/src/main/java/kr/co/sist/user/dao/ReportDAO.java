package kr.co.sist.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.co.sist.dao.GetJdbcTemplate;
import kr.co.sist.user.vo.ReportVO;

public class ReportDAO {
	
	/**
	 * �Ű� �߰�
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
	 * ȭ�鿡 ������ �Ű�����
	 * @return �Ű����� List
	 * @throws DataAccessException
	 */
	public List<String> selectReport() throws DataAccessException {
		List<String> result=null;
		
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		String select="select reason from report_reason";
		
		result=jt.query(select, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("reason");
			}
		});
		
		gjt.closeAc();
		
		return result;
	} //selectBuyCnt
	
}
