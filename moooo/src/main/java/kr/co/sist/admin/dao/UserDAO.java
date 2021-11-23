package kr.co.sist.admin.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.co.sist.dao.GetJdbcTemplate;
import kr.co.sist.user.vo.UserVO;

public class UserDAO {
	
	/**
	 * 전체회원 목록
	 * @return UserVO 목록
	 * @throws SQLException
	 */
	public List<UserVO> selectAllUser() throws SQLException {
		List<UserVO> list=null;
		
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		String select="select user_id,pass,nickname,phone,nvl(img,'---')img,reported_cnt,input_date from users";
		
		list=jt.query(select, new RowMapper<UserVO>() {
			@Override
			public UserVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserVO uv=new UserVO();
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
		
		gjt.closeAc();
		
		return list;
	}
	
	public int selectUserCnt() throws SQLException {
		int cnt=0;
		
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		String select="select count(*) from users";
		
		cnt=jt.queryForObject(select, Integer.class);
		
		gjt.closeAc();
		
		return cnt;
	}
}
