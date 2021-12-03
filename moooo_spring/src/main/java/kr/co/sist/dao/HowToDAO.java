package kr.co.sist.dao;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class HowToDAO {
	
	@Autowired(required = false)
	private JdbcTemplate jt;
	
	/**
	 * ���� ȭ�鿡 ������ �̿���
	 * @return �̿��� ����
	 * @throws SQLException
	 */
	public String selectHowTo() throws SQLException {
		String result="";
		
		String select="select * from how_to_use_it";
		result=jt.queryForObject(select, String.class);
		
		return result;
	} //selectHowTo
	
	/**
	 * �̿��� ������Ʈ
	 * @param comm ������Ʈ�� ����
	 * @throws DataAccessException
	 */
	public int updateHowTo(String comm) throws DataAccessException{
		int cnt=0;
		
		String update="update how_to_use_it set comments=?";
		jt.update(update, comm);
		
		return cnt;
	} //updateHowTo
	
}
