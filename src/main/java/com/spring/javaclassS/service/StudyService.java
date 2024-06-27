package com.spring.javaclassS.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.javaclassS.vo.CrimeVO;
import com.spring.javaclassS.vo.UserVO;

public interface StudyService {

	public String[] getCityStringArray(String dodo);

	public ArrayList<String> getCityArrayList(String dodo);

	public HashMap<Object, Object> getUserInfo(String name);

	public UserVO getUserMidSearch(String mid);

	public ArrayList<UserVO> getUserMidList(String mid);

	public void setSaveCrimeData(CrimeVO vo);

	public int setDeleteCrimeData(String year);

	public ArrayList<CrimeVO> getListCrimeDate(String year);
	//public ArrayList<CrimeVO> getListCrimeDate(int year);

	public ArrayList<CrimeVO> getYearPoliceListAsc(String police);

	public ArrayList<CrimeVO> getYearPoliceListDesc(String police);

	public ArrayList<CrimeVO> getYearStatsCrime(String year, String police);

	public int fileUpload(MultipartFile fName, String mid);

	public int multiFileUpload(MultipartHttpServletRequest mFile);

//	public ArrayList<CrimeVO> getYearPoliceCheck(int year, String police, String yearOrder);
//
//	public CrimeVO getAnalyzeTotal(int year, String police);


}
