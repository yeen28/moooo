package kr.co.sist.admin.dao;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import kr.co.sist.dao.GetJdbcTemplate;

public class LoginDAO {

	/**
	 * 입력한 아이디, 비밀번호로 아이디 얻기
	 * @param id, pass
	 * @return 아이디
	 * @throws SQLException
	 */
	public String selectLogin( String id, String pass ) throws SQLException {
		String result="";
		
		GetJdbcTemplate gjt=GetJdbcTemplate.getInstance();
		
		JdbcTemplate jt=gjt.getJdbcTemplate();
		
		StringBuilder selectMember=new StringBuilder();
		selectMember
		.append("	select admin_id ")
		.append("	from admin ")
		.append("	where admin_id=? and pass=? ");
		result=jt.queryForObject(selectMember.toString(), new Object[] { id, pass }, String.class);
		
		gjt.closeAc();
		
		return result;
	}//selectLogin
	
	/**
	 * 세션의 아이디로 비밀번호 얻기
	 * @param id
	 * @return 기존 비밀번호
	 * @throws SQLException
	 */
	public String selChangePwd(String id) throws SQLException {
		String pass="";
		
		GetJdbcTemplate gjt=GetJdbcTemplate.getInstance();
		
		JdbcTemplate jt=gjt.getJdbcTemplate();
		
		StringBuilder selectPw=new StringBuilder();
		selectPw
		.append("	select pass	")
		.append("	from admin	")
		.append("	where admin_id=?	");
		pass=jt.queryForObject(selectPw.toString(), new Object[] { id }, String.class);
		
		gjt.closeAc();
		
		return pass;
	}//selChangePwd
	
	/**
	 * 비밀번호 변경
	 * @param id
	 * @param pass
	 * @throws DataAccessException
	 */
	public void updatePwd( String id, String pass ) throws DataAccessException {
		
		GetJdbcTemplate gjt=GetJdbcTemplate.getInstance();
		
		JdbcTemplate jt=gjt.getJdbcTemplate();
		
		StringBuilder updateMember=new StringBuilder();
		updateMember
		.append("	update admin	")
		.append("	set pass=?	")
		.append("	where admin_id=?");
		jt.update(updateMember.toString(), pass, id);
		
		gjt.closeAc();
	}//updatePwd

}//class
