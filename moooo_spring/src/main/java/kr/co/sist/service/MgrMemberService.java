package kr.co.sist.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import kr.co.sist.dao.MemberDAO;
import kr.co.sist.dao.ReportDAO;
import kr.co.sist.vo.MemberVO;

@Component
public class MgrMemberService {

	@Autowired(required = false)
	private MemberDAO mDAO;
	@Autowired(required = false)
	private ReportDAO rDAO;
	
	/**
	 * ��ü ȸ�� ��ȸ
	 * @return ȸ�� List
	 * @throws SQLException
	 */
	public List<MemberVO> searchMember() throws DataAccessException {
		List<MemberVO> listMember=null;
		
		listMember=mDAO.selectAllUser();
		
		return listMember;
	} //searchMember
	
	/**
	 * �Ű�� ȸ�� ��ȸ
	 * @return �Ű�� ȸ�� List
	 * @throws SQLException
	 */
	public List<MemberVO> searchReportedMember() throws SQLException {
		List<MemberVO> listMember=null;
		
		listMember=rDAO.selectReportedUser();
		
		return listMember;
	} //searchMember
	
} //class
