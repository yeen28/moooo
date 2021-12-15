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

import kr.co.sist.vo.MemberVO;
import kr.co.sist.vo.ReportReasonVO;
import kr.co.sist.vo.ReportVO;

@Component
public class ReportDAO {
	
	@Autowired(required = false)
	private JdbcTemplate jt;
	
	/**
	 * �Ű��ϴ� ����ڰ� �Ű��� ����ڸ� ������ �Ű��� ���� �ִ��� Ȯ��
	 * @param ReportVO
	 * @return ������ �Ű��� ���� �ִ���
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
	 * �Ű� �߰�
	 * @param ReportVO
	 * @throws DataAccessException
	 */
	public void insertReport(ReportVO rv) throws DataAccessException {
		String insert="insert into report(reported_user_id, reason_id, user_id, reported_date) values(?,?,?,sysdate)";
		jt.update(insert, rv.getReported_user_id(), rv.getReason_id(), rv.getUser_id());
	} //insertReport
	
	/**
	 * �Ű���� ������ �Ű���Ƚ�� ����
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
	 * ȭ�鿡 ������ �Ű�����
	 * @return �Ű��ڵ��ȣ,���� List
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
	
	/**
	 * �Ű�� ȸ������ ���
	 * @return MemberVO List
	 * @throws SQLException
	 */
	public List<MemberVO> selectReportedUser() throws SQLException {
		List<MemberVO> list=null;
		
		String select="select * from users where reported_cnt > 0";
		try {
			list=jt.query(select, new RowMapper<MemberVO>() {
				@Override
				public MemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					MemberVO mVO=new MemberVO();
					mVO.setUser_id(rs.getString("user_id"));
					mVO.setNickname(rs.getString("nickname"));
					mVO.setPhone(rs.getString("phone"));
					mVO.setReported_cnt(rs.getInt("reported_cnt"));
					mVO.setInput_date(rs.getString("input_date"));
					return mVO;
				}
			});
		} catch(DataAccessException dae) {
			list=null;
		} //end catch
		
		return list;
	} //selectReportedUser
	
} //class
