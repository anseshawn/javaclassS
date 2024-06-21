package com.spring.javaclassS.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.spring.javaclassS.vo.UserVO;

public interface DbtestDAO {

	public ArrayList<UserVO> getDbtestList();

	public ArrayList<UserVO> getDbtestSearch(@Param("mid") String mid);

	public int setDbtestDelete(int idx);
	// param을 사용하지 않아도 되는 경우: dao의 변수와 xml에서의 매개변수 명이 똑같을때 (vo는 생략불가)
	
	public int setDbtestInputOk(@Param("vo") UserVO vo);

	public int setDbtestUpdateOk(@Param("vo") UserVO vo);

	public int getDbtestWindow(String mid);
	
	public UserVO getUserIdCheck(@Param("mid") String mid);

	public ArrayList<String> getDbtestMidList();

	public ArrayList<String> getDbtestAddressList();

	public ArrayList<UserVO> getUserAddressCheck(@Param("address") String address);
}
