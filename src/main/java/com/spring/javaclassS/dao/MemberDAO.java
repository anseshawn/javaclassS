package com.spring.javaclassS.dao;

import org.apache.ibatis.annotations.Param;

import com.spring.javaclassS.vo.MemberVO;

public interface MemberDAO {

	MemberVO getMemberIdCheck(@Param("mid") String mid);

	MemberVO getMemberNickCheck(@Param("nickName") String nickName);

	int setMemberJoinOk(@Param("vo") MemberVO vo);

	void setMemberPasswordUpdate(@Param("mid") String mid,@Param("pwd")  String pwd);

}
