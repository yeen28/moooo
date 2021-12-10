package kr.co.sist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.sist.dao.HowToDAO;

@Component
public class MgrHowToService {
	
	@Autowired(required = false)
	private HowToDAO hDAO;

} //class
