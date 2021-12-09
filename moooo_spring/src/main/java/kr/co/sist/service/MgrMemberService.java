package kr.co.sist.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.sist.dao.MemberDAO;
import kr.co.sist.vo.MemberVO;

@Component
public class MgrMemberService {

	@Autowired(required = false)
	private MemberDAO mDAO;
	
	/**
	 * ��ü ȸ�� ��ȸ
	 * @return ȸ�� List
	 * @throws SQLException
	 */
	public List<MemberVO> searchMember() throws SQLException{
		List<MemberVO> listMember=null;
		
		listMember=mDAO.selectAllUser();
		
		return listMember;
	} //searchMember
	
} //class
