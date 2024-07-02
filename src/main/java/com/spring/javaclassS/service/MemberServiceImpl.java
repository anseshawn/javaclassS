package com.spring.javaclassS.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javaclassS.common.JavaclassProvide;
import com.spring.javaclassS.dao.MemberDAO;
import com.spring.javaclassS.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	MemberDAO memberDAO;
	
	@Autowired
	JavaclassProvide javaclassProvide;

	@Override
	public MemberVO getMemberIdCheck(String mid) {
		return memberDAO.getMemberIdCheck(mid);
	}

	@Override
	public MemberVO getMemberNickCheck(String nickName) {
		return memberDAO.getMemberNickCheck(nickName);
	}

	@Override
	public int setMemberJoinOk(MultipartFile fName, MemberVO vo) {
		int res = 0;
		// 사진 처리
		// 파일이름 중복처리를 위해 UUID 객체 활용 (파일 이름 중복처리 자동으로 되지 않음)
		UUID uid = UUID.randomUUID();
		String oFileName = fName.getOriginalFilename();
		String sFileName = "";
		if(fName != null && oFileName != "")	{
			sFileName = vo.getMid()+"_"+uid.toString().substring(0,8)+"_"+oFileName;
			// 서버에 파일 올리기 (DB에는 sFileName 저장)
			try {
				javaclassProvide.writeFile(fName, sFileName, "member");
				res = 1;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(res != 0) vo.setPhoto(sFileName);
		else vo.setPhoto("noimage.jpg");
		
		return memberDAO.setMemberJoinOk(vo);
	}

	@Override
	public void setMemberPasswordUpdate(String mid, String pwd) {
		memberDAO.setMemberPasswordUpdate(mid,pwd);
	}

	@Override
	public MemberVO getMemberNameCheck(String name) {
		return memberDAO.getMemberNameCheck(name);
	}

	@Override
	public void setMemberInforUpdate(String mid, int point) {
		memberDAO.setMemberInforUpdate(mid, point);
	}

	@Override
	public int setPwdChangeOk(String mid, String pwd) {
		return memberDAO.setPwdChangeOk(mid,pwd);
	}

	@Override
	public ArrayList<MemberVO> getMemberList(int level) {
		return memberDAO.getMemberList(level);
	}

	@Override
	public int setMemberUpdateOk(MultipartFile fName, MemberVO vo, String mid) {
		int res = 0;
		UUID uid = UUID.randomUUID();
		String oFileName = fName.getOriginalFilename();
		String sFileName = "";
		if(fName != null && oFileName != "")	{
			sFileName = vo.getMid()+"_"+uid.toString().substring(0,8)+"_"+oFileName;
			// 서버에 파일 올리기 (DB에는 sFileName 저장)
			try {
				javaclassProvide.writeFile(fName, sFileName, "member");
				res = 1;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(res != 0) vo.setPhoto(sFileName);
		else vo.setPhoto("noimage.jpg");
		
		return memberDAO.setMemberUpdateOk(vo,mid);
	}

	// 회원 탈퇴 신청
	@Override
	public int setUserDel(String mid) {
		return memberDAO.setUserDel(mid);
	}

	// 닉네임, 이메일 DB 체크
	@Override
	public MemberVO getMemberNickNameEmailCheck(String nickName, String email) {
		return memberDAO.getMemberNickNameEmailCheck(nickName,email);
	}

	@Override
	public void setKakaoMemberInput(String mid, String pwd, String nickName, String email) {
		memberDAO.setKakaoMemberInput(mid, pwd, nickName, email);
	}
}
