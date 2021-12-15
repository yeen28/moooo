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

import kr.co.sist.vo.UpdateUserPassVO;
import kr.co.sist.vo.MemberVO;

@Component
public class MemberDAO {

	@Autowired(required=false)
	private JdbcTemplate jt;
	
	/**
	 * ȸ������ ó��
	 * @param mVO ȸ������VO
	 * @throws DataAccessException
	 */
	public void insertMember( MemberVO uVO ) throws DataAccessException {
		
		StringBuilder insertMember=new StringBuilder();
		insertMember
		.append("	insert into users(user_id, pass, nickname, phone, reported_cnt, input_date, leave_flag)	")
		.append("	values(?,?,?,?,0,sysdate,'n')	");
		jt.update(insertMember.toString(), uVO.getUser_id(), uVO.getPass(), uVO.getNickname(), uVO.getPhone());
		
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
		
		StringBuilder selectMember=new StringBuilder();
		selectMember
		.append("	select user_id	")
		.append("	from users	")
		.append("	where user_id=? and pass=? and leave_flag='n'  ");
		result=jt.queryForObject(selectMember.toString(), new Object[] { id, pw }, String.class);
		
		return result;
	}//selectLogin
	
	/**
	 * ���̵� ã��
	 * @param name
	 * @param email
	 * @return ���̵�
	 * @throws DataAccessException
	 */
	public String selectFindId( String nickname, String phone ) throws DataAccessException {
		String id="";
		
		StringBuilder selectId=new StringBuilder();
		selectId
		.append("	select user_id	")
		.append("	from users	")
		.append("	where nickname=? and phone=? and leave_flag='n'	");
		id=jt.queryForObject(selectId.toString(), new Object[] { nickname, phone }, String.class);
		
		return id;
	}//selectFindId
	
	/**
	 * ��й�ȣ ã��
	 * @param name
	 * @param id
	 * @param email
	 * @return ��й�ȣ
	 * @throws DataAccessException
	 */
	public String selectFindPass (String user_id, String phone) throws DataAccessException {
		String pw="";
		
		StringBuilder selectId=new StringBuilder();
		selectId
		.append("	select pass ")
		.append("	from users	")
		.append("	where user_id=? and phone=?	and leave_flag='n'	");
		pw=jt.queryForObject(selectId.toString(), new Object[] { user_id, phone }, String.class);
		
		return pw;
	}//selectFindPass
	
	/**
	 * ������ ���̵� ����Ͽ� ��ġ�ϴ� ��й�ȣ ���
	 * @param id
	 * @return ��й�ȣ
	 */
	public String selChangePw(String id) throws SQLException {
		String pw="";
		
		StringBuilder selectPw=new StringBuilder();
		selectPw
		.append("	select pass	")
		.append("	from users	")
		.append("	where user_id=? and leave_flag='n'	");
		pw=jt.queryForObject(selectPw.toString(), new Object[] { id }, String.class);
		
		return pw;
	}//selectPw
	
	/**
	 * ��й�ȣ ����
	 * @param uVO
	 * @return
	 * @throws SQLException
	 */
	public int updatePass( UpdateUserPassVO uVO ) throws SQLException {
		int cnt=0;
		
		StringBuilder updateMember=new StringBuilder();
		updateMember
		.append("	update users	")
		.append("	set pass=?	")
		.append("	where user_id=? and leave_flag='n'	");
		cnt=jt.update(updateMember.toString(), uVO.getNew_pass(), uVO.getUser_id());
		
		return cnt;
	}//insertMember
	
	/**
	 * ���̵� �ߺ��˻� : ���̵� DB Table�� �����ϴ���?
	 * @param id ��ȸ�� id
	 * @return ��ȸ�� id
	 * @throws SQLException
	 */
	public String selectId(String id) throws SQLException{
		String returnId="";
		
		String selectId="select user_id from users where user_id=?";
		try {
			//�� ���� ���ڵ尡 ��ȸ�Ǹ� ��ȸ����� ������ ����
			returnId=jt.queryForObject(selectId, new Object[] { id }, String.class);
		} catch(EmptyResultDataAccessException erdae) {
			//��ȸ����� ���� ������ ���ܹ߻�.
			returnId="";
		}//end catch
		
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
		
		String select="select nickname from users where nickname=?";
		try {
			//�� ���� ���ڵ尡 ��ȸ�Ǹ� ��ȸ����� ������ ����
			result=jt.queryForObject(select, new Object[] { nickname }, String.class);
		} catch(EmptyResultDataAccessException erdae) {
			//��ȸ����� ���� ������ ���ܹ߻�.
			result="";
		}//end catch
		
		return result;
	}//selectNickname
	
	/**
	 * �г��� ���
	 * @param id
	 * @return �г���
	 * @throws SQLException
	 */
	public String selectGetNickname(String user_id) throws SQLException{
		String result="";
		
		StringBuilder select=new StringBuilder();
		select
		.append("	select nickname	")
		.append("	from users	")
		.append("	where user_id=? and leave_flag='n'	");
		result=jt.queryForObject(select.toString(), new Object[] { user_id }, String.class);
		
		return result;
	}//selectGetNickname
	
	/**
	 * ���̵�� ��й�ȣ ���
	 * (ȸ��Ż��, ��й�ȣ ����)
	 * @param ���̵�
	 * @return ��й�ȣ
	 * @throws SQLException
	 */
	public String selectPw(String id) throws SQLException {
		String pass="";
		
		StringBuilder selectPw=new StringBuilder();
		selectPw
		.append("	select pass	")
		.append("	from users	")
		.append("	where user_id=? and leave_flag='n'	");
		pass=jt.queryForObject(selectPw.toString(), new Object[] { id }, String.class);
		
		return pass;
	}//selectPw
	
	/**
	 * ȸ��Ż�� ( ȸ���� Ż���ϸ� ��� ��� ���� )
	 * @throws DataAccessException
	 */
	public int updateMember(String user_id) throws DataAccessException{
		int cnt=0;
		
		String update="update users set pass=' ',nickname=' ',phone=' ',reported_cnt=0,leave_flag='y' where user_id=?";
		cnt=jt.update(update, user_id);
		
		return cnt;
	}//updateMember
//	public int deleteMember(String id) throws DataAccessException{
//		int cnt=0;
//		
//		String deleteMember="delete from users where user_id=?";
//		cnt=jt.update(deleteMember, id);
//		
//		return cnt;
//	}//deleteMember
	
	/**
	 * �޴�����ȣ ���
	 * @param id
	 * @return �޴��� ��ȣ
	 * @throws SQLException
	 */
	public String selectPhone(String id) throws DataAccessException {
		String result=null;
		
		StringBuilder select=new StringBuilder();
		select
		.append("	select phone	")
		.append("	from users ")
		.append("	where user_id=? and leave_flag='n'	");
		result=jt.queryForObject(select.toString(), new Object[] { id }, String.class);
		
		return result;
	}//selectPhone
	
	/**
	 * ��üȸ�� ���
	 * @return UserVO List
	 * @throws DataAccessException
	 */
	public List<MemberVO> selectAllUser() throws DataAccessException {
		List<MemberVO> list=null;
		
		String select="select user_id,pass,nickname,phone,nvl(img,'---')img,reported_cnt,input_date from users";
		list=jt.query(select, new RowMapper<MemberVO>() {
			@Override
			public MemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				MemberVO uv=new MemberVO();
				uv.setUser_id(rs.getString("user_id"));
				uv.setPass(rs.getString("pass"));
				uv.setNickname(rs.getString("nickname"));
				uv.setPhone(rs.getString("phone"));
				uv.setImg(rs.getString("img"));
				uv.setReported_cnt(rs.getInt("reported_cnt"));
				uv.setInput_date(rs.getString("input_date"));
				
				return uv;
			}
		});
		
		return list;
	} //selectAllUser
	
	/**
	 * ��ü ȸ�� ��
	 * @return ��ü ȸ�� ��
	 * @throws SQLException
	 */
	public int selectUserCnt() throws SQLException {
		int cnt=0;
		
		String select="select count(*) from users where leave_flag='n'";
		cnt=jt.queryForObject(select, Integer.class);
		
		return cnt;
	} //selectUserCnt
	
}//class
