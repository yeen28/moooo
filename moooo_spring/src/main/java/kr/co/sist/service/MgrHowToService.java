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
	 * �̿��� ���� ���
	 * @return �̿��� ����
	 */
	public String getHowToUse() {
		String result="";
		
		try {
			result=hDAO.selectHowTo();
		}catch(DataAccessException dae) {
			result="���� �����ڰ� �ۼ����� �ʾҽ��ϴ�.";
		} //end catch
		
		return result;
	} //getHowToUse
	
	/**
	 * �̿��� ���� ó�� ��������
	 * @param comments
	 * @return true ���� | false ����
	 * @throws SQLException
	 */
	public boolean editHowToUse(String comments) throws SQLException {
		boolean flag=false;
		
		if( hDAO.updateHowTo(comments) != 0 ) { //���� ����
			flag=true;
		} //end if
		
		return flag;
	} //editHowToUse
	
} //class
