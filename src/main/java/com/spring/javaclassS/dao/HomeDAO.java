package com.spring.javaclassS.dao;

import org.apache.ibatis.annotations.Param;

import com.spring.javaclassS.vo.WebChattingVO;

public interface HomeDAO {

	public int setMsgInput(@Param("vo") WebChattingVO vo);

}
