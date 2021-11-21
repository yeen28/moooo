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
	 * 회원가입 처리
	 * @param mVO 회원정보VO
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
	 * 로그인 처리
	 * @param id
	 * @param pw
	 * @return 회원아이디
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
	 * 아이디 찾기
	 * @param name
	 * @param email
	 * @return 아이디
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
	 * 비밀번호 찾기
	 * @param name
	 * @param id
	 * @param email
	 * @return 비밀번호
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
	 * 세션의 아이디를 사용하여 일치하는 비밀번호 얻기
	 * @param id
	 * @return 비밀번호
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
	 * 비밀번호 변경
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
	 * 아이디 중복검사 : 아이디가 DB Table에 존재하는지?
	 * @param id 조회할 id
	 * @return 조회된 id
	 * @throws SQLException
	 */
	public String selectId(String id) throws SQLException{
		String returnId="";
		
		GetJdbcTemplate gjt=GetJdbcTemplate.getInstance();
		
		JdbcTemplate jt=gjt.getJdbcTemplate();
		
		String selectId="select user_id from users where user_id=?";
		try {
			//한 건의 레코드가 조회되면 조회결과가 변수에 저장
			returnId=jt.queryForObject(selectId, new Object[] { id }, String.class);
		} catch(EmptyResultDataAccessException erdae) {
			//조회결과가 없을 때에는 예외발생.
			returnId="";
		}//end catch
		
		gjt.closeAc();
		
		return returnId;
	}//selectId
	
	/**
	 * 닉네임 중복검사 : 닉네임이 DB Table에 존재하는지?
	 * @param nickname 조회할 nickname
	 * @return 조회된 nickname
	 * @throws SQLException
	 */
	public String selectNickname(String nickname) throws SQLException{
		String result="";
		
		GetJdbcTemplate gjt=GetJdbcTemplate.getInstance();
		
		JdbcTemplate jt=gjt.getJdbcTemplate();
		
		String select="select nickname from users where nickname=?";
		try {
			//한 건의 레코드가 조회되면 조회결과가 변수에 저장
			result=jt.queryForObject(select, new Object[] { nickname }, String.class);
		} catch(EmptyResultDataAccessException erdae) {
			//조회결과가 없을 때에는 예외발생.
			result="";
		}//end catch
		
		gjt.closeAc();
		
		return result;
	}//selectNickname
	
	/**
	 * 회원탈퇴<br/>
	 * 회원의 아이디와 비밀번호가 일치하는지 확인
	 * @param mVO
	 * @return 비밀번호
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
	 * 회원탈퇴 ( 회원이 탈퇴하면 모든 기록 삭제 )
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
	 * 휴대폰번호 얻기
	 * @param id
	 * @return 휴대폰 번호
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
	 * 닉네임 얻기
	 * @param id
	 * @return 닉네임
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
	 * 공지사항 최근 몇개만 얻기
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
	 * 동네이야기 메인화면에 보여줄 몇개만 얻기
	 * @return 동네이야기 제목
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
