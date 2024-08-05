package com.spring.javaclassS.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.openqa.selenium.remote.http.HttpHeader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.javaclassS.vo.BoardVO;

@Controller
@RequestMapping("/errorPage")
public class ErrorController {

	// 에러 연습폼....
	@RequestMapping(value = "/errorMain", method = RequestMethod.GET)
	public String errorMainGet() {
		return "errorPage/errorMain";
	}
	
	// JSP페이지에서 에러발생시 이동처리할 폼보여주기
	@RequestMapping(value = "/error1", method = RequestMethod.GET)
	public String error1Get() {
		return "errorPage/error1";
	}
	
	// JSP페이지에서 에러발생시 공사중화면으로 보내어 보여주는 폼
	@RequestMapping(value = "/errorMessage1", method = RequestMethod.GET)
	public String errorMessage1Get() {
		return "errorPage/errorMessage1";
	}
	
	// 400에러가 났을때 이동할 메세지 폼 보기(400번에러는 서버로 요청이 오지도 않은경우이기에 이곳처리할것이 없다. 따라서 서버요청변수 타입을 잘 살펴봐야한다.)
	@RequestMapping(value = "/error400", method = RequestMethod.GET)
	public String error400Get(BoardVO vo) {
		return "";
	}
	
	// 404에러가 났을때 이동할 메세지 폼 보기(web.xml에 기술한다.)
	@RequestMapping(value = "/error404", method = RequestMethod.GET)
	public String error404Get() {
		return "errorPage/error404";
	}
	
	// JSP페이지에서 에러발생시 공사중화면으로 보내어 보여주는 폼
	@RequestMapping(value = "/errorMessage1Get", method = RequestMethod.POST)
	public String errorMessage1GetPost() {
		return "errorPage/errorMessage1";
	}
	
	// 405에러가 났을때 이동할 메세지 폼 보기(web.xml에 기술한다.)
	@RequestMapping(value = "/error405", method = RequestMethod.GET)
	public String error405Get() {
		return "errorPage/error405";
	}
	
	// servlet에러(500)가 났을때 이동할 메세지 폼...
	@RequestMapping(value = "/error500Check", method = RequestMethod.GET)
	public String error500CheckGet(HttpSession session) {
		String mid = (String) session.getAttribute("ssMid");
		int su = 100 + Integer.parseInt(mid);
		System.out.println("su : " + su);
		return "errorPage/errorMain";
	}
	
	// 500에러가 났을때 이동할 메세지 폼 보기(web.xml에 기술한다.)
	@RequestMapping(value = "/error500", method = RequestMethod.GET)
	public String error500Get() {
		return "errorPage/error500";
	}
	
	// 500에러(NumberFormatException)가 났을때 이동할 메세지 폼 보기(web.xml에 기술한다.)
	@RequestMapping(value = "/errorNumberFormat", method = RequestMethod.GET)
	public String errorNumberFormatGet() {
		return "errorPage/errorNumberFormat";
	}
	
	// NullPointerException에러 발생 메소드
	@RequestMapping(value = "/errorNullPointerCheck", method = RequestMethod.GET)
	//public String errorNullPointerCheckGet(@RequestParam(name="name",defaultValue="",required=false) String name) {
	public String errorNullPointerCheckGet(String name) {
		System.out.println("name : " + name);
		if(name.equals("admin")) return "";
		return "errorPage/errorMain";
	}

	// NullPointerException에러가 났을때 이동할 메세지 폼 보기(web.xml에 기술한다.)
	@RequestMapping(value = "/errorNullPointer", method = RequestMethod.GET)
	public String errorNullPointerGet() {
		return "errorPage/errorNullPointer";
	}
	
	// 400번 에러 처리에 도움을 주기위한 예외처리...
	//@ExceptionHandler(value = Exception.class) // 예외오류는 무조건 여기를 거침 (활성화시 500번 에러도 여기를 거치게 된다)
	public ResponseEntity<Map<String, String>> ExceptionHandler(Exception e) {
		HttpHeaders responseHeaders = new HttpHeaders();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		
		System.out.println("e : " + e.getMessage());
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("error type", httpStatus.getReasonPhrase());
		map.put("code", "400");
		map.put("message", "에러 발생");		
		
		return new ResponseEntity<Map<String,String>>(map, responseHeaders, httpStatus);
	}
	
}
