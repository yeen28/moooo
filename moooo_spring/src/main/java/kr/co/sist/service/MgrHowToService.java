package kr.co.sist.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import kr.co.sist.dao.HowToDAO;

@Component
public class MgrHowToService {
	
	@Autowired(required = false)
	private HowToDAO hDAO;

	/**
	 * 이용방법 내용 얻기
	 * @return 이용방법 내용
	 */
	public String getHowToUse() {
		String result="";
		
		try {
			result=hDAO.selectHowTo();
		}catch(DataAccessException dae) {
			result="아직 관리자가 작성하지 않았습니다.";
		} //end catch
		
		return result;
	} //getHowToUse
	
	/**
	 * 이용방법 수정 처리 업무로직
	 * @param comments
	 * @return true 성공 | false 실패
	 * @throws SQLException
	 */
	public boolean editHowToUse(String comments) throws SQLException {
		boolean flag=false;
		
		if( hDAO.updateHowTo(comments) != 0 ) { //변경 성공
			flag=true;
		} //end if
		
		return flag;
	} //editHowToUse
	
} //class
