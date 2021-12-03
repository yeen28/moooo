package kr.co.sist.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.sist.dao.MemberDAO;
import kr.co.sist.vo.MemberVO;

@Component
public class MemberService {

	@Autowired
	private MemberDAO mDAO;
	
}
