package kr.co.sist.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import kr.co.sist.dao.CategoryDAO;
import kr.co.sist.dao.HowToDAO;
import kr.co.sist.dao.MemberDAO;
import kr.co.sist.dao.WantBuyDAO;
import kr.co.sist.dao.WantSellDAO;
import kr.co.sist.vo.CategoryVO;
import kr.co.sist.vo.WantBuyVO;
import kr.co.sist.vo.WantSellVO;

@Component
public class MainService {
	
	@Autowired(required = false)
	private CategoryDAO cDAO;
	@Autowired(required = false)
	private HowToDAO hDAO;
	@Autowired(required = false)
	private MemberDAO mDAO;
	@Autowired(required = false)
	private WantBuyDAO bDAO;
	@Autowired(required = false)
	private WantSellDAO sDAO;
	
//	public boolean isThereSession(String user_id) {
//		boolean flag=false;
//		if(user_id != null) {
//			flag=true;
//		} //end if
//		
//		return flag;
//	} //isThereSession
	
	/**
	 * @return 전체 카테고리 List
	 * @throws SQLException
	 */
	public List<CategoryVO> getCategory() throws SQLException {
		List<CategoryVO> list=cDAO.selectAllCategory();
		return list;
	} //getCategory
	
	public List<WantBuyVO> getWantBuyTitle() throws SQLException {
		int category=0;
		int begin=1;
		int end=7;
		
		List<WantBuyVO> listTitle=bDAO.selectBuyTitle(category, begin, end);
		return listTitle;
	} //getWantBuyTitle
	
	public List<WantSellVO> getWantSellTitle() throws SQLException {
		int category=0;
		int begin=1;
		int end=7;
		
		List<WantSellVO> listTitle=sDAO.selectSellTitle(category, begin, end);
		return listTitle;
	} //getWantSellTitle

	public String getHowToUse() throws SQLException {
		String comments=hDAO.selectHowTo();
		return comments;
	} //getHowToUse
	
	public String getUserNickname(String user_id) {
		String nickname="";
		
		return nickname;
	} //getUserNickname
	
	/**
	 * @param user_id
	 * @return 사용자의 휴대폰번호
	 * @throws SQLException
	 */
	public String getUserPhone(String user_id) throws DataAccessException {
		String phone=mDAO.selectPhone(user_id);
		return phone;
	} //getUserPhone
	
} //class
