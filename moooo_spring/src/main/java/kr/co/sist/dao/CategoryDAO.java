package kr.co.sist.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import kr.co.sist.vo.CategoryVO;

@Component
public class CategoryDAO {
	
	@Autowired(required = false)
	private JdbcTemplate jt;
	
	/**
	 * 메인에 보여줄 전체 카테고리
	 * @return
	 * @throws SQLException
	 */
	public List<CategoryVO> selectAllCategory() throws SQLException {
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
	} //selectAllCategory
} //class
