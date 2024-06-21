package com.spring.javaclassS.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController //restapi 규칙을 따르려면 해당 어노테이션 필요(ResponseBody 가 없어도 값을 받아올 수 있다)
@RequestMapping("/restapi")
public class RestAPIController {
	
	@ResponseBody
	@RequestMapping(value = "/restapiTest2/{message}", method = RequestMethod.GET)
	public String restapiTest2Get(@PathVariable String message) {
		System.out.println("message : "+message);
		return "message : "+message;
	}
	
	@RequestMapping(value = "/restapiTest3/{message}", method = RequestMethod.GET)
	public String restapiTest3Get(@PathVariable String message) {
		System.out.println("message : "+message);
		return "message : "+message;
	}
	
}
