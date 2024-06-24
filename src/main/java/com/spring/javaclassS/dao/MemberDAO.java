package com.spring.javaclassS.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.spring.javaclassS.vo.MemberVO;

public interface MemberDAO {

	MemberVO getMemberIdCheck(@Param("mid") String mid);

	MemberVO getMemberNickCheck(@Param("nickName") String nickName);

	int setMemberJoinOk(@Param("vo") MemberVO vo);

	void setMemberPasswordUpdate(@Param("mid") String mid,@Param("pwd")  String pwd);

	MemberVO getMemberNameCheck(@Param("name") String name);

	void setMemberInforUpdate(@Param("mid") String mid, @Param("point") int point);

	int setPwdChangeOk(@Param("mid") String mid, @Param("pwd") String pwd);

	ArrayList<MemberVO> getMemberList(@Param("level") int level);

	int setMemberUpdateOk(@Param("vo") MemberVO vo, @Param("mid") String mid);

}
