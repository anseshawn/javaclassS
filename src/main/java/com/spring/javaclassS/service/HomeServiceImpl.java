package com.spring.javaclassS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javaclassS.dao.HomeDAO;
import com.spring.javaclassS.vo.WebChattingVO;

@Service
public class HomeServiceImpl implements HomeService {
	
	@Autowired
	HomeDAO homeDAO;

	@Override
	public int setMsgInput(WebChattingVO vo) {
		return homeDAO.setMsgInput(vo);
	}

}
