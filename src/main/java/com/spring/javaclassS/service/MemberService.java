package com.spring.javaclassS.service;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.spring.javaclassS.vo.MemberVO;

public interface MemberService {

	public MemberVO getMemberIdCheck(String mid);

	public MemberVO getMemberNickCheck(String nickName);

	public int setMemberJoinOk(MultipartFile fName, MemberVO vo);

	public void setMemberPasswordUpdate(String mid, String pwd);

	public MemberVO getMemberNameCheck(String name);

	public void setMemberInforUpdate(String mid, int point);

	public int setPwdChangeOk(String mid, String pwd);

	public ArrayList<MemberVO> getMemberList(int level);

	public int setMemberUpdateOk(MultipartFile fName, MemberVO vo, String mid);

	public int setUserDel(String mid);

	public MemberVO getMemberNickNameEmailCheck(String nickName, String email);

	public void setKakaoMemberInput(String mid, String pwd, String nickName, String email);
	
	public String setQrCodeCreate(String realPath, String mid, String qrCodeToken);

	public void setQrCodeLogin(String mid, String qrCodeToken, String today);

	public String getQrCodeLoginCheck(String mid, String qrCodeToken);

}
