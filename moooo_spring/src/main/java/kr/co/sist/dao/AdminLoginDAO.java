package kr.co.sist.dao;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdminLoginDAO {

	@Autowired(required = false)
	private JdbcTemplate jt;
	
	/**
	 * �Է��� ���̵�, ��й�ȣ�� ���̵� ���
	 * @param id, pass
	 * @return ���̵�
	 * @throws SQLException
	 */
	public String selectLogin( String id, String pass ) throws DataAccessException {
		String result="";
		
		StringBuilder selectMember=new StringBuilder();
		selectMember
		.append("	select admin_id ")
		.append("	from admin ")
		.append("	where admin_id=? and pass=? ");
		result=jt.queryForObject(selectMember.toString(), new Object[] { id, pass }, String.class);
		
		return result;
	}//selectLogin
	
	/**
	 * ������ ���̵�� ��й�ȣ ���
	 * @param id
	 * @return ���� ��й�ȣ
	 * @throws SQLException
	 */
	public String selectChangePass(String id) throws SQLException {
		String pass="";
		
		StringBuilder selectPw=new StringBuilder();
		selectPw
		.append("	select pass	")
		.append("	from admin	")
		.append("	where admin_id=?	");
		pass=jt.queryForObject(selectPw.toString(), new Object[] { id }, String.class);
		
		return pass;
	}//selectChangePass
	
	/**
	 * ��й�ȣ ����
	 * @param id
	 * @param pass
	 * @throws DataAccessException
	 */
	public int updatePass( String id, String pass ) throws DataAccessException {
		int cnt=0;
		
		StringBuilder updateMember=new StringBuilder();
		updateMember
		.append("	update admin	")
		.append("	set pass=?	")
		.append("	where admin_id=?");
		cnt=jt.update(updateMember.toString(), pass, id);
		
		return cnt;
	}//updatePass

}//class
