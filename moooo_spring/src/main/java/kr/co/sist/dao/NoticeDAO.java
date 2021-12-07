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
	 * ��ü ���� ���� ���
	 * @return ���� ����
	 * @throws SQLException
	 */
	public int selectNotiCnt() throws DataAccessException {
		int cnt=0;
		
		String select="select count(*) from notice";
		cnt=jt.queryForObject(select, Integer.class);
		
		return cnt;
	}
	
	/**
	 * ���� Title ���
	 * @param begin
	 * @param end
	 * @return ���� Title
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
	 * ���� ����
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

				//CLOB �������� �б�
				//���1
				//1. ResultSet���κ��� CLOB�� ��´�.
				//Clob clobComments=rs.getClob("comments");
				//2. clobComments���� CharacterStream�� ��´�.
				//Reader reader=clobComments.getCharacterStream();
				//3. BufferedReader�� ���� (�÷��� ������ �� ������ �о���̱� ���ؼ�)
				//BufferedReader br=new BufferedReader(reader);
				
				//���2
				//CLOB ��ȸ����� ����
				StringBuilder comments=new StringBuilder();
				String temp="";
				BufferedReader br=null;
				try {
					br=new BufferedReader(rs.getClob("comments").getCharacterStream());
					while( (temp=br.readLine()) != null ) {
						//readLine()�� \n������ �о���δ�. temp���� \n�� ���Ե��� �ʴ´�.
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
	 * �� ������ ���� select
	 * @param �� ��ȣ
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
	 * �� �߰�
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
	 * �� ����
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
	 * �� ����
	 * @param notice_id, admin_id
	 * @throws DataAccessException
	 */
	public int deleteNoti(int notice_id, String admin_id) throws SQLException {
		int cnt=0;
		
		//delete�� ���ǿ� �´� ���� ���ٸ� ������ �ƴ϶� �ƹ� �ϵ� �Ͼ�� �ʴ´�.
		//�׷��� select�� �ߴ�.
		String select="select title from notice where notice_id=? and admin_id=?";
		jt.queryForObject(select, new Object[] {notice_id, admin_id}, String.class);
		
		String delete="delete from notice where notice_id=? and admin_id=?";
		jt.update(delete, notice_id, admin_id);
		
		return cnt;
	}//deleteNoti
	
	/**
	 * �������� �ֱ� ��� ���
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