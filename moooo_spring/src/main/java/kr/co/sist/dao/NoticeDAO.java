package kr.co.sist.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import kr.co.sist.vo.NoticeVO;

@Component
public class NoticeDAO {

	@Autowired(required = false)
	private JdbcTemplate jt;
	
	/**
	 * 전체 공지 개수 얻기
	 * @return 공지 개수
	 * @throws SQLException
	 */
	public int selectNotiCnt() throws DataAccessException {
		int cnt=0;
		
		String select="select count(*) from notice";
		cnt=jt.queryForObject(select, Integer.class);
		
		return cnt;
	}
	
	/**
	 * 공지 Title 얻기
	 * @param begin
	 * @param end
	 * @return 공지 Title
	 * @throws SQLException
	 */
	public List<NoticeVO> selectNotiTitle(int begin, int end) throws SQLException {
		List<NoticeVO> list = null;

		StringBuilder selectNotice=new StringBuilder();
		selectNotice
		.append("	select notice_id, title, input_date,view_cnt,admin_id	" )
		.append("	from (select rownum r_num, notice_id, title,input_date,view_cnt,admin_id	")
		.append("	from (select notice_id, title,to_char(input_date,'yyyy-MM-dd') input_date,view_cnt,admin_id	")
		.append("	from notice	")
		.append("	order by notice_id desc))	")
		.append("	where r_num between ? and ?	");
		
		list=jt.query(selectNotice.toString(), new Object[] { begin, end }, new RowMapper<NoticeVO>() {
			@Override
			public NoticeVO mapRow(ResultSet rs, int rowCnt) throws SQLException {
				NoticeVO nVO = new NoticeVO();
				nVO.setNotice_id(rs.getInt("notice_id"));
				nVO.setTitle(rs.getString("title"));
				nVO.setInput_date(rs.getString("input_date"));
				nVO.setView_cnt(rs.getInt("view_cnt"));
				nVO.setAdmin_id(rs.getString("admin_id"));

				return nVO;
			}
		});

		return list;
	} //selectNotiTitle
	
	/**
	 * 공지 내용
	 * @param notice_id
	 * @return
	 * @throws SQLException
	 */
	public NoticeVO selectNotice(int notice_id) throws SQLException {
		NoticeVO nVO = new NoticeVO();
		
		String updateCnt = "update notice set view_cnt = (view_cnt + 1) where notice_id = ?";
		jt.update(updateCnt, notice_id);
		
		String selectQuery = "select notice_id, title, to_char(input_date,'yyyy-MM-dd')input_date,view_cnt,admin_id,comments from notice where notice_id = ?";

		nVO = jt.queryForObject(selectQuery, new Object[] { Long.valueOf(notice_id) }, new RowMapper<NoticeVO>() {
			@Override
			public NoticeVO mapRow(ResultSet rs, int rowCnt) throws SQLException {
				NoticeVO nVO = new NoticeVO();

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
		
		return nVO;
	}//selectNotice
	
	/**
	 * 글 수정을 위한 select
	 * @param 글 번호
	 * @return NoticeVO
	 * @throws SQLException
	 */
	public NoticeVO selectEditNotice(int notice_id, String admin_id) throws SQLException {
		NoticeVO unv=new NoticeVO();
		
		String select="select * from notice where notice_id=? and admin_id=?";
		
		unv=jt.queryForObject(select, new Object[] { notice_id, admin_id }, new RowMapper<NoticeVO>() {
			public NoticeVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				NoticeVO unv=new NoticeVO();
				unv.setNotice_id(rs.getInt("notice_id"));
				unv.setTitle(rs.getString("title"));
				unv.setComments(rs.getString("comments"));
				unv.setView_cnt(rs.getInt("view_cnt"));
				unv.setInput_date(rs.getString("input_date"));
				unv.setAdmin_id(rs.getString("admin_id"));
				return unv;
			}//mapRow
		});
		
		return unv;
	}//selectEditNotice

	/**
	 * 글 추가
	 * @param NotiInsertVO
	 * @throws DataAccessException
	 */
	public void insertNoti(NoticeVO wv) throws DataAccessException {
		StringBuilder insert=new StringBuilder();
		insert
		.append("	insert into notice(notice_id,title,comments,view_cnt,input_date,admin_id)	")
		.append("	values(seq_notice.nextval,?,?,0,sysdate,?)");
		
		jt.update(insert.toString(), new Object[] { wv.getTitle(), wv.getComments(), wv.getAdmin_id() });
	}//insertNoti
	
	/**
	 * 글 수정
	 * @param NoticeVO
	 * @throws DataAccessException
	 */
	public int updateNoti(NoticeVO wv) throws DataAccessException {
		int cnt=0;
		
		String update="update notice set title=?,comments=? where notice_id=?";
		
		jt.update(update, wv.getTitle(), wv.getComments(), wv.getNotice_id() );
		
		return cnt;
	}//updateSell
	
	/**
	 * 글 삭제
	 * @param notice_id, admin_id
	 * @throws DataAccessException
	 */
	public int deleteNoti(int notice_id, String admin_id) throws SQLException {
		int cnt=0;
		
		//delete는 조건에 맞는 것이 없다면 에러가 아니라 아무 일도 일어나지 않는다.
		//그래서 select을 했다.
		String select="select title from notice where notice_id=? and admin_id=?";
		jt.queryForObject(select, new Object[] {notice_id, admin_id}, String.class);
		
		String delete="delete from notice where notice_id=? and admin_id=?";
		jt.update(delete, notice_id, admin_id);
		
		return cnt;
	}//deleteNoti
	
	/**
	 * 공지사항 최근 몇개만 얻기
	 * @return 
	 * @throws SQLException
	 */
	public List<String> selectNoticeTitle() throws SQLException{
		List<String> title=null;
		
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
		
		return title;
	}//selectNoticeTitle
	
} //class