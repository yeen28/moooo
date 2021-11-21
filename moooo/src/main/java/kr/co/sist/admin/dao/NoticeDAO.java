package kr.co.sist.admin.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.co.sist.admin.vo.NotiInsertVO;
import kr.co.sist.dao.GetJdbcTemplate;

public class NoticeDAO {

	/**
	 * 전체 공지 개수 얻기
	 * @return 공지 개수
	 * @throws SQLException
	 */
	public int selectNotiCnt() throws SQLException {
		int cnt=0;
		
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		String select="select count(*) from notice";
		
		cnt=jt.queryForObject(select, Integer.class);
		
		gjt.closeAc();
		
		return cnt;
	}
	
	/**
	 * 공지 Title 얻기
	 * @param begin
	 * @param end
	 * @return 공지 Title
	 * @throws SQLException
	 */
	public List<NotiInsertVO> selectNotiTitle(int begin, int end) throws SQLException {
		List<NotiInsertVO> list = null;

		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();

		StringBuilder selectNotice=new StringBuilder();
		selectNotice
		.append("	select notice_id, title, input_date,view_cnt,admin_id	" )
		.append("	from (select rownum r_num, notice_id, title,input_date,view_cnt,admin_id	")
		.append("	from (select notice_id, title,to_char(input_date,'yyyy-MM-dd') input_date,view_cnt,admin_id	")
		.append("	from notice	")
		.append("	order by notice_id desc))	")
		.append("	where r_num between ? and ?	");
		
		list=jt.query(selectNotice.toString(), new Object[] { begin, end }, new RowMapper<NotiInsertVO>() {
			@Override
			public NotiInsertVO mapRow(ResultSet rs, int rowCnt) throws SQLException {
				NotiInsertVO nVO = new NotiInsertVO();
				nVO.setNotice_id(rs.getInt("notice_id"));
				nVO.setTitle(rs.getString("title"));
				nVO.setInput_date(rs.getString("input_date"));
				nVO.setView_cnt(rs.getInt("view_cnt"));
				nVO.setAdmin_id(rs.getString("admin_id"));

				return nVO;
			}
		});

		gjt.closeAc();

		return list;
	} //selectNotiTitle
	
	/**
	 * 공지 조회수
	 * @param idx
	 * @return
	 */
	public NotiInsertVO selectNotice(int notice_id) {
		NotiInsertVO nVO = new NotiInsertVO();

		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		String updateCnt = "update notice set view_cnt = (view_cnt + 1) where notice_id = ?";
		jt.update(updateCnt, notice_id);

		String selectQuery = "select * from notice where notice_id = ?";

		nVO = jt.queryForObject(selectQuery, new Object[] { Long.valueOf(notice_id) }, new RowMapper<NotiInsertVO>() {

			@Override
			public NotiInsertVO mapRow(ResultSet rs, int rowCnt) throws SQLException {
				NotiInsertVO nVO = new NotiInsertVO();

				nVO.setNotice_id(rs.getInt("notice_id"));
				nVO.setTitle(rs.getString("title"));
				nVO.setComments(rs.getString("comments"));
				nVO.setInput_date(rs.getString("input_date"));
				nVO.setView_cnt(rs.getInt("view_cnt"));
				nVO.setAdmin_id(rs.getString("admin_id"));

				return nVO;
			}

		});
		
		gjt.closeAc();

		return nVO;
	}

}