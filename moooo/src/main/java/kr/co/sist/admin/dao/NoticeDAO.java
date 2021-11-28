package kr.co.sist.admin.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.co.sist.admin.vo.NotiInsertVO;
import kr.co.sist.dao.GetJdbcTemplate;
import kr.co.sist.user.vo.WantSellVO;

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
	 * 공지 내용
	 * @param notice_id
	 * @return
	 */
	public NotiInsertVO selectNotice(int notice_id) {
		NotiInsertVO nVO = new NotiInsertVO();

		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		String selectQuery = "select notice_id, title, to_char(input_date,'yyyy-MM-dd')input_date,view_cnt,admin_id,comments from notice where notice_id = ?";

		nVO = jt.queryForObject(selectQuery, new Object[] { Long.valueOf(notice_id) }, new RowMapper<NotiInsertVO>() {

			@Override
			public NotiInsertVO mapRow(ResultSet rs, int rowCnt) throws SQLException {
				NotiInsertVO nVO = new NotiInsertVO();

				nVO.setNotice_id(rs.getInt("notice_id"));
				nVO.setTitle(rs.getString("title"));
				//nVO.setComments(rs.getString("comments"));
				nVO.setInput_date(rs.getString("input_date"));
				nVO.setView_cnt(rs.getInt("view_cnt"));
				nVO.setAdmin_id(rs.getString("admin_id"));

				//CLOB 데이터형 읽기
				//방법1
				//1. ResultSet으로부터 CLOB을 얻는다.
				//Clob clobComments=rs.getClob("comments");
				//2. clobComments에서 CharacterStream을 얻는다.
				//Reader reader=clobComments.getCharacterStream();
				//3. BufferedReader를 연결 (컬럼의 내용을 줄 단위로 읽어들이기 위해서)
				//BufferedReader br=new BufferedReader(reader);
				
				//방법2
				//CLOB 조회결과를 저장
				StringBuilder comments=new StringBuilder();
				String temp="";
				BufferedReader br=null;
				try {
					br=new BufferedReader(rs.getClob("comments").getCharacterStream());
					while( (temp=br.readLine()) != null ) {
						//readLine()은 \n전까지 읽어들인다. temp에는 \n이 포함되지 않는다.
						comments.append( temp ).append("\n");
					} //end while
					
				} catch(IOException ie) {
					//ie.printStackTrace();
				} finally {
					if( br != null ) { 
						try {
							br.close();
						} catch (IOException e) {}
					} //end if
				} //end finally
				
				nVO.setComments( comments.toString() );
				
				return nVO;
			}

		});
		
		gjt.closeAc();

		return nVO;
	}//selectNotice
	
	/**
	 * 글 수정을 위한 select
	 * @param 글 번호
	 * @return NotiInsertVO
	 * @throws SQLException
	 */
	public NotiInsertVO selEditNotice(int notice_id, String admin_id) throws SQLException {
		NotiInsertVO unv=new NotiInsertVO();
		
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		String select="select * from notice where notice_id=? and admin_id=?";
		
		unv=jt.queryForObject(select, new Object[] { notice_id, admin_id }, new RowMapper<NotiInsertVO>() {
			public NotiInsertVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				NotiInsertVO unv=new NotiInsertVO();
				unv.setNotice_id(rs.getInt("notice_id"));
				unv.setTitle(rs.getString("title"));
				unv.setComments(rs.getString("comments"));
				unv.setView_cnt(rs.getInt("view_cnt"));
				unv.setInput_date(rs.getString("input_date"));
				unv.setAdmin_id(rs.getString("admin_id"));
				return unv;
			}//mapRow
		});
		
		gjt.closeAc();
		
		return unv;
	}//selEditNotice

	/**
	 * 글 추가
	 * @param NotiInsertVO
	 * @throws DataAccessException
	 */
	public void insertNoti(NotiInsertVO wv) throws DataAccessException {
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		StringBuilder insert=new StringBuilder();
		insert
		.append("	insert into notice(notice_id,title,comments,view_cnt,input_date,admin_id)	")
		.append("	values(seq_notice.nextval,?,?,0,sysdate,?)");
		
		jt.update(insert.toString(), new Object[] { wv.getTitle(), wv.getComments(), wv.getAdmin_id() });
		
		gjt.closeAc();
	}//insertNoti
	
	/**
	 * 글 수정
	 * @param NotiInsertVO
	 * @throws DataAccessException
	 */
	public void updateNoti(NotiInsertVO wv) throws DataAccessException {
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		String update="update notice set title=?,comments=? where notice_id=?";
		
		jt.update(update, wv.getTitle(), wv.getComments(), wv.getNotice_id() );
		
		gjt.closeAc();
	}//updateSell
	
	/**
	 * 글 삭제
	 * @param notice_id, admin_id
	 * @throws DataAccessException
	 */
	public void delNoti(int notice_id, String admin_id) throws SQLException {
		GetJdbcTemplate gjt = GetJdbcTemplate.getInstance();
		JdbcTemplate jt = gjt.getJdbcTemplate();
		
		//delete는 조건에 맞는 것이 없다면 에러가 아니라 아무 일도 일어나지 않는다.
		//그래서 select을 했다.
		String select="select title from notice where notice_id=? and admin_id=?";
		jt.queryForObject(select, new Object[] {notice_id, admin_id}, String.class);
		
		String delete="delete from notice where notice_id=? and admin_id=?";
		jt.update(delete, notice_id, admin_id);
		
		gjt.closeAc();
	}//delNoti
	
}