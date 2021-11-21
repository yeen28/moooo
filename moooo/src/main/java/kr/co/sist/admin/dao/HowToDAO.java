package kr.co.sist.admin.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import kr.co.sist.dao.GetJdbcTemplate;

public class HowToDAO {
	
	/**
	 * ���� ȭ�鿡 ������ �̿���
	 * @return �̿��� ����
	 * @throws DataAccessException
	 */
	public String selHowTo() throws DataAccessException {
		String result="";
		
		GetJdbcTemplate gjt=GetJdbcTemplate.getInstance();
		JdbcTemplate jt=gjt.getJdbcTemplate();
		
		String select="select * from how_to_use_it";
		result=jt.queryForObject(select, String.class);
		
		return result;
	} //selHowTo
	
	/**
	 * �̿��� ������Ʈ
	 * @param comm ������Ʈ�� ����
	 * @throws DataAccessException
	 */
	public void updateHowTo(String comm) throws DataAccessException{
		GetJdbcTemplate gjt=GetJdbcTemplate.getInstance();
		JdbcTemplate jt=gjt.getJdbcTemplate();
		
		String update="update how_to_use_it set comments=?";
		jt.update(update, comm);
	} //updateHowTo
	
}
