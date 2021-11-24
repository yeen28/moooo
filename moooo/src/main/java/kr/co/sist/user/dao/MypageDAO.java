package kr.co.sist.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.co.sist.dao.GetJdbcTemplate;
import kr.co.sist.user.vo.UserVO;
import kr.co.sist.user.vo.WantSellVO;

public class MypageDAO {

	/**
	 * 내 정보 update
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
	 * 마이페이지 보여주기
	 * @param user_id
	 * @return 닉네임, 휴대폰번호, 이미지
	 * @throws DataAccessException
	 */
	public UserVO selMypage(String user_id) throws DataAccessException { //SQLException로 변경하기
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
	
	/**
	 * 관심글 목록 조회
	 * @param user_id
	 * @return 관심글 번호 List
	 * @throws DataAccessException
	 */
	public List<Integer> selInterNumList(String user_id) throws SQLException {
		List<Integer> list = null;

		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();

		String select = "select sell_id from interest_prod where user_id=?";
		list = jt.query(select, new Object[] { user_id }, new RowMapper<Integer>() {
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt("sell_id");
			}
		});

		gjt.closeAc();

		return list;
	}// selInterNumList
	
	/**
	 * user_id에 맞는 제목, 작성일 얻기
	 * @param sell_id
	 * @return WantSellVO List
	 * @throws SQLException
	 */
	//public List<WantSellVO> selInterList(String sell_id) throws SQLException {
	public List<WantSellVO> selInterList(List<Integer> sellIdList) throws SQLException {
		List<WantSellVO> list = new ArrayList<WantSellVO>();
		
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		for(int val : sellIdList) {
		String select = "select sell_id,title,input_date from want_sell where sell_id=?";
		list.add( jt.queryForObject(select, new Object[] { val }, new RowMapper<WantSellVO>() {
			public WantSellVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				WantSellVO wv=new WantSellVO();
				wv.setSell_id(rs.getInt("sell_id"));
				wv.setTitle(rs.getString("title"));
				wv.setInput_date(rs.getString("input_date"));
				return wv;
			}
		}) );
		}
		
		gjt.closeAc();
		
		return list;
	}// selInterList
	
//	public static void main(String[] args) {
//		MypageDAO md=new MypageDAO();
//		List<Integer> list=new ArrayList<Integer>();
//		list.add(1);
//		try {
//			System.out.println(md.selInterList(list));
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

}// class