package kr.co.sist.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import kr.co.sist.vo.CategoryVO;
import kr.co.sist.vo.WantBuyVO;

@Component
public class WantBuyDAO {

	@Autowired(required = false)
	private JdbcTemplate jt;
	
	/**
	 * �� ���� ���
	 * @param category ī�װ� ��ȣ
	 * @return �� ����
	 * @throws SQLException
	 */
	public int selectBuyCnt(int category) throws DataAccessException {
		int cnt=0;
		
		String select="";
		if(category == 0) {
			select="select count(*) from want_buy";
			cnt=jt.queryForObject(select, Integer.class);
		} else {
			select="select count(*) from want_buy where category_id=?";
			cnt=jt.queryForObject(select, new Object[] { category }, Integer.class);
		} //end else
		
		return cnt;
	} //selectBuyCnt
	
	/**
	 * ���� Title ���
	 * @param category
	 * @param begin
	 * @param end
	 * @return �� ��ȣ, ����, ����, �ۼ���, �ۼ���, ��ȸ�� 
	 * @throws SQLException
	 */
	public List<WantBuyVO> selectBuyTitle(int category, int begin, int end) throws SQLException {
		List<WantBuyVO> list = null;

		StringBuilder select=new StringBuilder();
		
		if(category == 0) {
			select
			.append("	select buy_id, title, price, input_date, user_id, view_cnt	" )
			.append("	from (select rownum r_num, buy_id, title, price, input_date, user_id, view_cnt	")
			.append("	from (select buy_id, title, price, to_char(input_date,'yyyy-MM-dd') input_date, user_id, view_cnt	")
			.append("	from want_buy	")
			.append("	order by buy_id desc))	")
			.append("	where r_num between ? and ?	");
			
			list=jt.query(select.toString(), new Object[] { begin, end }, new SelectBuy());
			
		} else {
			select
			.append("	select buy_id, title, price, input_date, user_id, view_cnt	" )
			.append("	from (select rownum r_num, buy_id, title, price, input_date, user_id, view_cnt	")
			.append("	from (select buy_id, title, price, to_char(input_date,'yyyy-MM-dd') input_date, user_id, view_cnt	")
			.append("	from want_buy	")
			.append("	where category_id=?	")
			.append("	order by buy_id desc))	")
			.append("	where r_num between ? and ?	");
			
			list=jt.query(select.toString(), new Object[] { category, begin, end }, new SelectBuy());
		}

		return list;
	} //selectBuyTitle
	
	/////////////// inner class : ������ ������ ������ Ŭ���� ���� /////////////////
	public class SelectBuy implements RowMapper<WantBuyVO>{
		public WantBuyVO mapRow(ResultSet rs, int rowCnt) throws SQLException {
			WantBuyVO wVO = new WantBuyVO();
			wVO.setBuy_id(rs.getInt("buy_id"));
			wVO.setTitle(rs.getString("title"));
			wVO.setPrice(rs.getInt("price"));
			wVO.setInput_date(rs.getString("input_date"));
			wVO.setUser_id(rs.getString("user_id"));
			wVO.setView_cnt(rs.getInt("view_cnt"));
	
			return wVO;
		}//mapRow
	}
	/////////////// inner class : ������ ������ ������ Ŭ���� �� /////////////////
	
	/**
	 * ���;�� ��ȸ�� ����
	 * @param buy_id
	 * @return
	 * @throws SQLException
	 */
	public WantBuyVO selectBuy(int buy_id) throws SQLException {
		WantBuyVO nVO = new WantBuyVO();

		String updateCnt = "update want_buy set view_cnt = (view_cnt + 1) where buy_id = ?";
		jt.update(updateCnt, buy_id);

		StringBuilder select=new StringBuilder();
		select
		.append("	select buy_id,title,comments,price,to_char(input_date,'yyyy-MM-dd')input_date,view_cnt,user_id	")
		.append("	from want_buy	")
		.append("	where buy_id = ?	");

		nVO = jt.queryForObject(select.toString(), new Object[] { Long.valueOf(buy_id) }, new RowMapper<WantBuyVO>() {

			@Override
			public WantBuyVO mapRow(ResultSet rs, int rowCnt) throws SQLException {
				WantBuyVO nVO = new WantBuyVO();

				nVO.setBuy_id(rs.getInt("buy_id"));
				nVO.setTitle(rs.getString("title"));
				nVO.setComments(rs.getString("comments"));
				nVO.setPrice(rs.getInt("price"));
				nVO.setInput_date(rs.getString("input_date"));
				nVO.setView_cnt(rs.getInt("view_cnt"));
				nVO.setUser_id(rs.getString("user_id"));

				return nVO;
			}

		});
		
		return nVO;
	} //selectBuy
	
	/**
	 * �� �߰�
	 * @param WantBuyVO
	 * @throws DataAccessException
	 */
	public void insertBuy(WantBuyVO wv) throws DataAccessException {
		StringBuilder insert=new StringBuilder();
		insert
		.append("	insert into want_buy(buy_id,title,comments,price,view_cnt, input_date,ip_addr,user_id,category_id)	")
		.append("	values(seq_want_buy.nextval,?,?,?,0,sysdate,?,?,?)");
		
		jt.update(insert.toString(), new Object[] { wv.getTitle(), wv.getComments(), wv.getPrice(), wv.getIp_addr(),wv.getUser_id(), wv.getCategory_id() });
	}//insertBuy
	
	/**
	 * �� ������ ���� select
	 * @param �� ��ȣ
	 * @return WantBuyVO
	 * @throws SQLException
	 */
	public WantBuyVO selectEditBuy(int buy_id, String user_id) throws SQLException {
		WantBuyVO unv=new WantBuyVO();
		
		String select="select * from want_buy where buy_id=? and user_id=?";
		
		unv=jt.queryForObject(select, new Object[] { buy_id,user_id }, new RowMapper<WantBuyVO>() {
			public WantBuyVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				WantBuyVO unv=new WantBuyVO();
				unv.setBuy_id(rs.getInt("buy_id"));
				unv.setTitle(rs.getString("title"));
				unv.setComments(rs.getString("comments"));
				unv.setPrice(rs.getInt("price"));
				unv.setView_cnt(rs.getInt("view_cnt"));
				unv.setInput_date(rs.getString("input_date"));
				unv.setIp_addr(rs.getString("ip_addr"));
				unv.setUser_id(rs.getString("user_id"));
				unv.setCategory_id(rs.getInt("category_id"));
				return unv;
			}//mapRow
		});
		
		return unv;
	}//selectEditBuy
	
	/**
	 * ���;�� ����
	 * @param WantBuyVO
	 * @throws DataAccessException
	 */
	public int updateBuy(WantBuyVO wv) throws DataAccessException {
		int cnt=0;
		
		String update="update want_buy set title=?,comments=?,price=?,ip_addr=?,category_id=? where buy_id=?";
		
		jt.update(update, wv.getTitle(), wv.getComments(), wv.getPrice(), wv.getIp_addr(),wv.getCategory_id(), wv.getBuy_id() );
		
		return cnt;
	}//updateBuy
	
	/**
	 * ���;�� ����
	 * @param buy_id
	 * @throws DataAccessException
	 */
	public int deleteBuy(int buy_id) throws DataAccessException {
		int cnt=0;
		
		String delete="delete from want_buy where buy_id=?";
		
		jt.update(delete, buy_id);
		
		return cnt;
	}//deleteBuy
	
	/**
	 * ī�װ� ��ü ���
	 * @return ī�װ� �ڵ��ȣ, ī�װ���
	 * @throws SQLException
	 */
	public List<CategoryVO> selectCategory() throws SQLException {
		List<CategoryVO> list=null;
		
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
		
		return list;
	}//selectCategory
	
	/**
	 * �������������� ������ ���� �� ��
	 * @param user_id
	 * @return WantBuyVO List
	 * @throws SQLException
	 */
	public List<WantBuyVO> selectMypageBuy(String user_id) throws SQLException {
		List<WantBuyVO> unv=null;
		
		String select="select buy_id,title,price,to_char(input_date,'yyyy-MM-dd')input_date,view_cnt from want_buy where user_id=?";
		
		unv=jt.query(select, new Object[] { user_id }, new RowMapper<WantBuyVO>() {
			public WantBuyVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				WantBuyVO unv=new WantBuyVO();
				unv.setBuy_id(rs.getInt("buy_id"));
				unv.setTitle(rs.getString("title"));
				unv.setPrice(rs.getInt("price"));
				unv.setView_cnt(rs.getInt("view_cnt"));
				unv.setInput_date(rs.getString("input_date"));
				unv.setUser_id(rs.getString("user_id"));
				return unv;
			}//mapRow
		});
		
		return unv;
	}//selectMypageBuy
}