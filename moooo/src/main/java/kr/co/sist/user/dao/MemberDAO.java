package kr.co.sist.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.co.sist.dao.GetJdbcTemplate;
import kr.co.sist.user.vo.UserVO;
import kr.co.sist.user.vo.UpdatePwdVO;

public class MemberDAO {

	/**
	 * ȸ������ ó��
	 * @param mVO ȸ������VO
	 * @throws DataAccessException
	 */
	public void insertMember( UserVO uVO ) throws DataAccessException {
		GetJdbcTemplate gjt=GetJdbcTemplate.getInstance();
		
		JdbcTemplate jt=gjt.getJdbcTemplate();
		
		String insertMember="insert into users(user_id, pass, nickname, phone, reported_cnt, input_date) values(?,?,?,?,0,sysdate)";
		jt.update(insertMember, uVO.getUser_id(), uVO.getPass(), uVO.getNickname(), uVO.getPhone());
		
		gjt.closeAc();
		
	}//insertMember
	
	/**
	 * �α��� ó��
	 * @param id
	 * @param pw
	 * @return ȸ�����̵�
	 * @throws SQLException
	 */
	public String selectLogin( String id, String pw ) throws SQLException {
		String result="";
		
		GetJdbcTemplate gjt=GetJdbcTemplate.getInstance();
		
		JdbcTemplate jt=gjt.getJdbcTemplate();
		
		StringBuilder selectMember=new StringBuilder();
		selectMember
		.append("	select user_id	")
		.append("	from users	")
		.append("	where user_id=? and pass=? ");
		result=jt.queryForObject(selectMember.toString(), new Object[] { id, pw }, String.class);
		
		gjt.closeAc();
		
		return result;
	}//selectLogin
	
	/**
	 * ���̵� ã��
	 * @param name
	 * @param email
	 * @return ���̵�
	 * @throws SQLException
	 */
	public String selectFindId( String nickname, String phone ) throws SQLException {
		String id="";
		
		GetJdbcTemplate gjt=GetJdbcTemplate.getInstance();
		
		JdbcTemplate jt=gjt.getJdbcTemplate();
		
		StringBuilder selectId=new StringBuilder();
		selectId
		.append("	select user_id	")
		.append("	from users	")
		.append("	where nickname=? and phone=?	");
		id=jt.queryForObject(selectId.toString(), new Object[] { nickname, phone }, String.class);
		
		gjt.closeAc();
		
		return id;
	}//selectFindId
	
	/**
	 * ��й�ȣ ã��
	 * @param name
	 * @param id
	 * @param email
	 * @return ��й�ȣ
	 * @throws SQLException
	 */
	public String selectFindPw (String user_id, String phone) throws SQLException {
		String pw="";
		
		GetJdbcTemplate gjt=GetJdbcTemplate.getInstance();
		
		JdbcTemplate jt=gjt.getJdbcTemplate();
		
		StringBuilder selectId=new StringBuilder();
		selectId
		.append("	select pass ")
		.append("	from users	")
		.append("	where user_id=? and phone=?	");
		pw=jt.queryForObject(selectId.toString(), new Object[] { user_id, phone }, String.class);
		
		gjt.closeAc();
		
		return pw;
	}//selectFindPw
	
	/**
	 * ������ ���̵� ����Ͽ� ��ġ�ϴ� ��й�ȣ ���
	 * @param id
	 * @return ��й�ȣ
	 */
	public String selChangePw(String id) throws SQLException {
		String pw="";
		
		GetJdbcTemplate gjt=GetJdbcTemplate.getInstance();
		
		JdbcTemplate jt=gjt.getJdbcTemplate();
		
		StringBuilder selectPw=new StringBuilder();
		selectPw
		.append("	select pass	")
		.append("	from users	")
		.append("	where user_id=?	");
		pw=jt.queryForObject(selectPw.toString(), new Object[] { id }, String.class);
		
		gjt.closeAc();
		
		return pw;
	}//selectPw
	
	/**
	 * ��й�ȣ ����
	 * @param uVO
	 * @return
	 * @throws DataAccessException
	 */
	public void updatePw( UpdatePwdVO uVO ) throws DataAccessException {
		
		GetJdbcTemplate gjt=GetJdbcTemplate.getInstance();
		
		JdbcTemplate jt=gjt.getJdbcTemplate();
		
		StringBuilder updateMember=new StringBuilder();
		updateMember
		.append("	update users	")
		.append("	set pass=?	")
		.append("	where user_id=?");
		jt.update(updateMember.toString(), uVO.getNew_pass(), uVO.getUser_id());
		
		gjt.closeAc();
		
	}//insertMember
	
	/**
	 * ���̵� �ߺ��˻� : ���̵� DB Table�� �����ϴ���?
	 * @param id ��ȸ�� id
	 * @return ��ȸ�� id
	 * @throws SQLException
	 */
	public String selectId(String id) throws SQLException{
		String returnId="";
		
		GetJdbcTemplate gjt=GetJdbcTemplate.getInstance();
		
		JdbcTemplate jt=gjt.getJdbcTemplate();
		
		String selectId="select user_id from users where user_id=?";
		try {
			//�� ���� ���ڵ尡 ��ȸ�Ǹ� ��ȸ����� ������ ����
			returnId=jt.queryForObject(selectId, new Object[] { id }, String.class);
		} catch(EmptyResultDataAccessException erdae) {
			//��ȸ����� ���� ������ ���ܹ߻�.
			returnId="";
		}//end catch
		
		gjt.closeAc();
		
		return returnId;
	}//selectId
	
	/**
	 * �г��� �ߺ��˻� : �г����� DB Table�� �����ϴ���?
	 * @param nickname ��ȸ�� nickname
	 * @return ��ȸ�� nickname
	 * @throws SQLException
	 */
	public String selectNickname(String nickname) throws SQLException{
		String result="";
		
		GetJdbcTemplate gjt=GetJdbcTemplate.getInstance();
		
		JdbcTemplate jt=gjt.getJdbcTemplate();
		
		String select="select nickname from users where nickname=?";
		try {
			//�� ���� ���ڵ尡 ��ȸ�Ǹ� ��ȸ����� ������ ����
			result=jt.queryForObject(select, new Object[] { nickname }, String.class);
		} catch(EmptyResultDataAccessException erdae) {
			//��ȸ����� ���� ������ ���ܹ߻�.
			result="";
		}//end catch
		
		gjt.closeAc();
		
		return result;
	}//selectNickname
	
	/**
	 * ȸ��Ż��<br/>
	 * ȸ���� ���̵�� ��й�ȣ�� ��ġ�ϴ��� Ȯ��
	 * @param mVO
	 * @return ��й�ȣ
	 * @throws SQLException
	 */
	public String selectPw(String id) throws SQLException {
		String pw="";
		
		GetJdbcTemplate gjt=GetJdbcTemplate.getInstance();
		
		JdbcTemplate jt=gjt.getJdbcTemplate();
		
		StringBuilder selectPw=new StringBuilder();
		selectPw
		.append("	select pass	")
		.append("	from users	")
		.append("	where user_id=?	");
		pw=jt.queryForObject(selectPw.toString(), new Object[] { id }, String.class);
		
		gjt.closeAc();
		
		return pw;
	}//selectPw
	
	/**
	 * ȸ��Ż�� ( ȸ���� Ż���ϸ� ��� ��� ���� )
	 * @throws SQLException
	 */
	public void deleteMember(String id) throws SQLException{
		
		GetJdbcTemplate gjt=GetJdbcTemplate.getInstance();
	
		JdbcTemplate jt=gjt.getJdbcTemplate();
	
		String deleteMember="delete from users where user_id=?";
		jt.update(deleteMember, id);
		
		gjt.closeAc();
	}//deleteMember
	
	/**
	 * �޴�����ȣ ���
	 * @param id
	 * @return �޴��� ��ȣ
	 * @throws SQLException
	 */
	public String selectPhone(String id) throws SQLException{
		String result=null;
		
		GetJdbcTemplate gjt=GetJdbcTemplate.getInstance();
		JdbcTemplate jt=gjt.getJdbcTemplate();
		
		StringBuilder select=new StringBuilder();
		select
		.append("	select phone	")
		.append("	from users ")
		.append("	where user_id=?	");
		result=jt.queryForObject(select.toString(), new Object[] { id }, String.class);
		
		gjt.closeAc();
		
		return result;
	}//selectPhone
	
	/**
	 * �г��� ���
	 * @param id
	 * @return �г���
	 * @throws SQLException
	 */
	public String selNickname(String id) throws SQLException{
		String result="";
		
		GetJdbcTemplate gjt=GetJdbcTemplate.getInstance();
		
		JdbcTemplate jt=gjt.getJdbcTemplate();
		
		StringBuilder select=new StringBuilder();
		select
		.append("	select nickname	")
		.append("	from users	")
		.append("	where user_id=?	");
		result=jt.queryForObject(select.toString(), new Object[] { id }, String.class);
		
		gjt.closeAc();
		
		return result;
	}//selNickname
	
	/**
	 * �������� �ֱ� ��� ���
	 * @return 
	 * @throws SQLException
	 */
	public List<String> selNoticeTitle() throws SQLException{
		List<String> title=null;
		
		GetJdbcTemplate gjt=GetJdbcTemplate.getInstance();
		
		JdbcTemplate jt=gjt.getJdbcTemplate();
		
		StringBuilder select=new StringBuilder();
		select
		.append("	select title	")
		.append("	from (select rownum r_num, title	")
		.append("	from (select title	")
		.append("	from notice	")
		.append("	order by notice_id desc))	")
		.append("	where r_num between 1 and 10	");
		title=jt.query(select.toString(), new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("title");
			}
		});
		
		gjt.closeAc();
		
		return title;
	}//selNoticeTitle
	
	/**
	 * �����̾߱� ����ȭ�鿡 ������ ��� ���
	 * @return �����̾߱� ����
	 * @throws SQLException
	 */
	public List<String> selStoryTitle() throws SQLException{
		List<String> result=null;
		
		GetJdbcTemplate gjt=GetJdbcTemplate.getInstance();
		
		JdbcTemplate jt=gjt.getJdbcTemplate();
		
		StringBuilder select=new StringBuilder();
		select
		.append("	select title	")
		.append("	from (select rownum r_num, title	")
		.append("	from (select title	")
		.append("	from story	")
		.append("	order by story_id desc))	")
		.append("	where r_num between 1 and 8	");
		result=jt.query(select.toString(), new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("title");
			}
		});
		
		gjt.closeAc();
		
		return result;
	}//selStoryTitle
	
}//class
