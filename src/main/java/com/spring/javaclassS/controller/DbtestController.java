package com.spring.javaclassS.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.javaclassS.service.DbtestService;
import com.spring.javaclassS.vo.UserVO;

@Controller
@RequestMapping("/dbtest")
public class DbtestController {
	
	@Autowired
	DbtestService dbtestService;
	
	/* -------------------------------DB Test------------------------------------- */
	
	@RequestMapping(value = "/dbtestList", method = RequestMethod.GET)
	public String dbtestListGet(Model model) {
		ArrayList<UserVO> vos = dbtestService.getDbtestList();
		model.addAttribute("vos",vos);
		return "user/dbtestList";
	}
	
	// 아이디 검색
	@RequestMapping(value = "/dbtestSearch/{mid}", method = RequestMethod.GET)
	public String dbtestSearchGet(Model model, @PathVariable String mid) {
		ArrayList<UserVO> vos = dbtestService.getDbtestList();
		model.addAttribute("vos",vos);
		
		ArrayList<UserVO> searchVos = dbtestService.getDbtestSearch(mid);
		model.addAttribute("searchVos",searchVos);
		return "user/dbtestList";
	}
	
	// 회원 삭제
	@RequestMapping(value = "/dbtestDelete", method = RequestMethod.GET)
	public String dbtestDeleteGet(int idx) {
		int res = dbtestService.setDbtestDelete(idx);
		
		if(res != 0) return "redirect:/message/dbtestDeleteOk";
		else return "redirect:/message/dbtestDeleteNo";
		
	}
	
	// 회원 추가
	@RequestMapping(value = "/dbtestInputOk", method = RequestMethod.POST)
	public String dbtestInputOkPost(UserVO vo) {
		int res = dbtestService.setDbtestInputOk(vo);
		
		if(res != 0) return "redirect:/message/dbtestInputOk";
		else return "redirect:/message/dbtestInputNo";
	}
	
	// 회원 정보 수정
	@RequestMapping(value = "/dbtestUpdateOk", method = RequestMethod.POST)
	public String dbtestUpdateOkPost(UserVO vo) {
		int res = dbtestService.setDbtestUpdateOk(vo);
		
		if(res != 0) return "redirect:/message/dbtestUpdateOk";
		else return "redirect:/message/dbtestUpdateNo";
		
	}
	
	// 아이디 중복 검사(새창)
	@RequestMapping(value = "/dbtestWindow", method = RequestMethod.GET)
	public String dbtestWindowGet(Model model, String mid) {
		int res = dbtestService.getDbtestWindow(mid);
		
		if(res != 0)	model.addAttribute("message", "사용할 수 없는 아이디입니다.");
		else	model.addAttribute("message", "사용 가능한 아이디입니다.");
		
		model.addAttribute("res", res);
		model.addAttribute("mid", mid);
		return "user/dbtestWindowGet";
	}
}
