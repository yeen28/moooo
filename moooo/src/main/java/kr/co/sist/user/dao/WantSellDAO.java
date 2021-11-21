package kr.co.sist.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.co.sist.dao.GetJdbcTemplate;
import kr.co.sist.user.vo.CategoryVO;
import kr.co.sist.user.vo.WantSellVO;

public class WantSellDAO {

	/**
	 * 전체 글 개수 얻기
	 * @return 글 개수
	 * @throws SQLException
	 */
	public int selectSellCnt() throws SQLException {
		int cnt=0;
		
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		String select="select count(*) from want_sell";
		
		cnt=jt.queryForObject(select, Integer.class);
		
		gjt.closeAc();
		
		return cnt;
	}
	
	/**
	 * 팔아요 Title 얻기
	 * @param begin
	 * @param end
	 * @return 팔아요 Title
	 * @throws SQLException
	 */
	public List<WantSellVO> selectSellTitle(int begin, int end) throws SQLException {
		List<WantSellVO> list = null;

		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();

		StringBuilder selectNotice=new StringBuilder();
		selectNotice
		.append("	select sell_id, title, price, input_date, user_id, view_cnt	" )
		.append("	from (select rownum r_num, sell_id, title, price, input_date, user_id, view_cnt	")
		.append("	from (select sell_id, title, price, to_char(input_date,'yyyy-MM-dd') input_date, user_id, view_cnt	")
		.append("	from want_sell	")
		.append("	order by sell_id desc))	")
		.append("	where r_num between ? and ?	");
		
		list=jt.query(selectNotice.toString(), new Object[] { begin, end }, new RowMapper<WantSellVO>() {
			@Override
			public WantSellVO mapRow(ResultSet rs, int rowCnt) throws SQLException {
				WantSellVO wVO = new WantSellVO();
				wVO.setSell_id(rs.getInt("sell_id"));
				wVO.setTitle(rs.getString("title"));
				wVO.setPrice(rs.getInt("price"));
				wVO.setInput_date(rs.getString("input_date"));
				wVO.setUser_id(rs.getString("user_id"));
				wVO.setView_cnt(rs.getInt("view_cnt"));

				return wVO;
			}
		});

		gjt.closeAc();

		return list;
	} //selectSellTitle
	
	/**
	 * 조회수 세기
	 * @param sell_id
	 * @return
	 */
	public WantSellVO selectSell(int sell_id) throws DataAccessException {
		WantSellVO nVO = new WantSellVO();

		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		String updateCnt = "update want_sell set view_cnt = (view_cnt + 1) where sell_id = ?";
		jt.update(updateCnt, sell_id);

		String selectQuery = "select * from want_sell where sell_id = ?";

		nVO = jt.queryForObject(selectQuery, new Object[] { Long.valueOf(sell_id) }, new RowMapper<WantSellVO>() {

			@Override
			public WantSellVO mapRow(ResultSet rs, int rowCnt) throws SQLException {
				WantSellVO nVO = new WantSellVO();

				nVO.setSell_id(rs.getInt("sell_id"));
				nVO.setTitle(rs.getString("title"));
				nVO.setComments(rs.getString("comments"));
				nVO.setInput_date(rs.getString("input_date"));
				nVO.setView_cnt(rs.getInt("view_cnt"));
				nVO.setUser_id(rs.getString("user_id"));

				return nVO;
			}

		});
		
		gjt.closeAc();

		return nVO;
	} //selectSell
	
	/**
	 * 글 추가
	 * @param WantSellVO
	 * @throws DataAccessException
	 */
	public void insertSell(WantSellVO wv) throws DataAccessException {
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		StringBuilder insert=new StringBuilder();
		insert
		.append("	insert into want_sell(sell_id,title,comments,price,view_cnt,interest_cnt,input_date,ip_addr,user_id,category_id)	")
		.append("	values(seq_want_buy.nextval,?,?,?,0,0,sysdate,?,?,?)");
		
		jt.update(insert.toString(), new Object[] { wv.getTitle(), wv.getComments(), wv.getPrice(), wv.getIp_addr(),wv.getUser_id(), wv.getCategory_id() });
		
		gjt.closeAc();
	}//insertSell
	
	/**
	 * 글 수정을 위한 select
	 * @param 글 번호
	 * @return 제목, 내용
	 * @throws SQLException
	 */
//	public WantBuyVO selEditBuy(int buy_id) throws SQLException {
//		WantBuyVO unv=new WantBuyVO();
//		
//		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
//		JdbcTemplate jt = gjt.getJdbcTemplate();
//		
//		String select="select title,comments from want_buy where buy_id=?";
//		
//		unv=jt.queryForObject(select, new Object[] { buy_id }, new RowMapper<WantBuyVO>() {
//			public WantBuyVO mapRow(ResultSet rs, int rowNum) throws SQLException {
//				WantBuyVO unv=new WantBuyVO();
//				unv.setTitle(rs.getString("title"));
//				unv.setComments(rs.getString("comments"));
//				return unv;
//			}//mapRow
//		});
//		
//		gjt.closeAc();
//		
//		return unv;
//	}//selEditBuy
	
	/**
	 * 글 수정
	 * @param WantSellVO
	 * @throws DataAccessException
	 */
	public void updateSell(WantSellVO wv) throws DataAccessException {
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		String update="update want_sell set title=?,comments=?,price=?,ip_addr=?,category_id=? where sell_id=?";
		
		jt.update(update, wv.getTitle(), wv.getComments(), wv.getPrice(), wv.getIp_addr(),wv.getCategory_id(), wv.getSell_id() );
		
		gjt.closeAc();
	}//updateSell
	
	/**
	 * 글 삭제
	 * @param sell_id
	 * @throws DataAccessException
	 */
	public void delSell(int sell_id) throws DataAccessException {
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		String delete="delete from want_sell where sell_id=?";
		
		jt.update(delete, sell_id);
		
		gjt.closeAc();
	}//delSell
	
	/**
	 * 카테고리 전체 얻기
	 * @return 카테고리 코드번호, 카테고리명
	 * @throws DataAccessException
	 */
	public List<CategoryVO> selCategory() throws DataAccessException {
		List<CategoryVO> list=null;
		
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		String select="select category_id,name from category";
		
		list=jt.query(select, new RowMapper<CategoryVO>() {
			@Override
			public CategoryVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				CategoryVO cv=new CategoryVO();
				cv.setCategory_id(rs.getInt("category_id"));
				cv.setName(rs.getString("name"));
				return cv;
			}
		});
		
		gjt.closeAc();
		
		return list;
	}//selCategory

}