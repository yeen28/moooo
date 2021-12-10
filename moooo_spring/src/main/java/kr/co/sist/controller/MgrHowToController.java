package kr.co.sist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import kr.co.sist.service.MgrHowToService;

@Controller
@RequestMapping("admin/")
public class MgrHowToController {

	@Autowired
	private MgrHowToService mhts;
	
	@RequestMapping(value="mgr_how_to.do", method=GET)
	public String howToForm(Model model) {
		String jspPage="admin/mgr_how_to";
		return jspPage;
	} //howToForm

	@RequestMapping("mgr/update_how_to.do")
	public String updateHowTo(String comments, Model model) {
		String jspPage="admin/mgr_how_to";
		return jspPage;
	} //updateHowTo
	
} //class
