package kr.co.sist.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.co.sist.dao.GetJdbcTemplate;
import kr.co.sist.user.vo.UserVO;

public class MypageDAO {

	/**
	 * update 실행
	 * @param UserVO
	 * @throws DataAccessException
	 */
	public void updateMypage(UserVO uv) throws DataAccessException {
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
//		if(uv.getIdx() == 0) {
//			String insertProfile = "insert into profile(idx,gender,phone,description,url,img,input_date,tech_idx,id) values(profile_seq.nextval,?,?,?,?,?,sysdate,?,?)";
//			jt.update(insertProfile, uv.getGender(), uv.getPhone(), pVO.getDescription(), pVO.getUrl(), pVO.getImg(),
//					uv.getTech_idx(), uv.getId());
//		} else {
			String update= "update users set nickname=?,phone=?,img=? where user_id=?";
			jt.update(update, uv.getNickname(), uv.getPhone(), uv.getImg(), uv.getUser_id());
//		}
		
		gjt.closeAc();
		
	} //updateMypage
	
	/**
	 * 마이페이지 select
	 * @param user_id
	 * @return 닉네임, 휴대폰번호, 이미지
	 * @throws DataAccessException
	 */
	public UserVO selMypage(String user_id) throws DataAccessException {
		UserVO pv = new UserVO();

		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();

		String select = "select nickname, phone, img from users where user_id=?";
		pv = jt.queryForObject(select, new Object[] { user_id }, new RowMapper<UserVO>() {
			public UserVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserVO pv = new UserVO();
				pv.setNickname(rs.getString("nickname"));
				pv.setPhone(rs.getString("phone"));
				pv.setImg(rs.getString("img"));
				return pv;
			}
		});

		gjt.closeAc();

		return pv;
	}// selMypage

}// class