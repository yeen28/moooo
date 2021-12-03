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

import kr.co.sist.vo.UpdatePassVO;
import kr.co.sist.vo.MemberVO;

@Component
public class MemberDAO {

	@Autowired(required=false)
	private JdbcTemplate jt;
	
	/**
	 * 회원가입 처리
	 * @param mVO 회원정보VO
	 * @throws DataAccessException
	 */
	public void insertMember( MemberVO uVO ) throws DataAccessException {
		
		String insertMember="insert into users(user_id, pass, nickname, phone, reported_cnt, input_date) values(?,?,?,?,0,sysdate)";
		jt.update(insertMember, uVO.getUser_id(), uVO.getPass(), uVO.getNickname(), uVO.getPhone());
		
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
		
		StringBuilder selectMember=new StringBuilder();
		selectMember
		.append("	select user_id	")
		.append("	from users	")
		.append("	where user_id=? and pass=? ");
		result=jt.queryForObject(selectMember.toString(), new Object[] { id, pw }, String.class);
		
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
		
		StringBuilder selectId=new StringBuilder();
		selectId
		.append("	select user_id	")
		.append("	from users	")
		.append("	where nickname=? and phone=?	");
		id=jt.queryForObject(selectId.toString(), new Object[] { nickname, phone }, String.class);
		
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
		
		StringBuilder selectId=new StringBuilder();
		selectId
		.append("	select pass ")
		.append("	from users	")
		.append("	where user_id=? and phone=?	");
		pw=jt.queryForObject(selectId.toString(), new Object[] { user_id, phone }, String.class);
		
		return pw;
	}//selectFindPw
	
	/**
	 * 세션의 아이디를 사용하여 일치하는 비밀번호 얻기
	 * @param id
	 * @return 비밀번호
	 */
	public String selChangePw(String id) throws SQLException {
		String pw="";
		
		StringBuilder selectPw=new StringBuilder();
		selectPw
		.append("	select pass	")
		.append("	from users	")
		.append("	where user_id=?	");
		pw=jt.queryForObject(selectPw.toString(), new Object[] { id }, String.class);
		
		return pw;
	}//selectPw
	
	/**
	 * 비밀번호 변경
	 * @param uVO
	 * @return
	 * @throws DataAccessException
	 */
	public int updatePw( UpdatePassVO uVO ) throws DataAccessException {
		int cnt=0;
		
		StringBuilder updateMember=new StringBuilder();
		updateMember
		.append("	update users	")
		.append("	set pass=?	")
		.append("	where user_id=?");
		jt.update(updateMember.toString(), uVO.getNew_pass(), uVO.getUser_id());
		
		return cnt;
	}//insertMember
	
	/**
	 * 아이디 중복검사 : 아이디가 DB Table에 존재하는지?
	 * @param id 조회할 id
	 * @return 조회된 id
	 * @throws SQLException
	 */
	public String selectId(String id) throws SQLException{
		String returnId="";
		
		String selectId="select user_id from users where user_id=?";
		try {
			//한 건의 레코드가 조회되면 조회결과가 변수에 저장
			returnId=jt.queryForObject(selectId, new Object[] { id }, String.class);
		} catch(EmptyResultDataAccessException erdae) {
			//조회결과가 없을 때에는 예외발생.
			returnId="";
		}//end catch
		
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
		
		String select="select nickname from users where nickname=?";
		try {
			//한 건의 레코드가 조회되면 조회결과가 변수에 저장
			result=jt.queryForObject(select, new Object[] { nickname }, String.class);
		} catch(EmptyResultDataAccessException erdae) {
			//조회결과가 없을 때에는 예외발생.
			result="";
		}//end catch
		
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
		
		StringBuilder selectPw=new StringBuilder();
		selectPw
		.append("	select pass	")
		.append("	from users	")
		.append("	where user_id=?	");
		pw=jt.queryForObject(selectPw.toString(), new Object[] { id }, String.class);
		
		return pw;
	}//selectPw
	
	/**
	 * 회원탈퇴 ( 회원이 탈퇴하면 모든 기록 삭제 )
	 * @throws SQLException
	 */
	public int deleteMember(String id) throws SQLException{
		int cnt=0;
		
		String deleteMember="delete from users where user_id=?";
		jt.update(deleteMember, id);
		
		return cnt;
	}//deleteMember
	
	/**
	 * 휴대폰번호 얻기
	 * @param id
	 * @return 휴대폰 번호
	 * @throws SQLException
	 */
	public String selectPhone(String id) throws SQLException{
		String result=null;
		
		StringBuilder select=new StringBuilder();
		select
		.append("	select phone	")
		.append("	from users ")
		.append("	where user_id=?	");
		result=jt.queryForObject(select.toString(), new Object[] { id }, String.class);
		
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
		
		StringBuilder select=new StringBuilder();
		select
		.append("	select nickname	")
		.append("	from users	")
		.append("	where user_id=?	");
		result=jt.queryForObject(select.toString(), new Object[] { id }, String.class);
		
		return result;
	}//selNickname
	
	/**
	 * 전체회원 목록
	 * @return UserVO 목록
	 * @throws SQLException
	 */
	public List<MemberVO> selectAllUser() throws SQLException {
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
	
	public int selectUserCnt() throws SQLException {
		int cnt=0;
		
		String select="select count(*) from users";
		cnt=jt.queryForObject(select, Integer.class);
		
		return cnt;
	} //selectUserCnt
	
}//class
