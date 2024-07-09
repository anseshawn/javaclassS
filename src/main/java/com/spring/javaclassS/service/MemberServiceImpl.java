package com.spring.javaclassS.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
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
	
	@Override
	public String setQrCodeCreate(String realPath, String mid, String qrCodeToken) {
		String qrCodeName = javaclassProvide.newNameCreate(2);
		String qrCodeImage = "";
		try {
			String today = qrCodeName.substring(0, qrCodeName.length()-3);
			
			// QR코드안의 한글 인코딩
			qrCodeName += mid;
			//qrCodeImage = "http://49.142.157.251:9090/javaclassS/member/qrLoginConfirm/"+mid+"/"+qrCodeToken+"/"+today;
			qrCodeImage = "http://192.168.50.20:9090/javaclassS/member/qrLoginConfirm/"+mid+"/"+qrCodeToken+"/"+today;
			//HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
			//qrCodeImage = request.getContextPath()+"/member/qrLoginConfirm/"+mid+"/"+qrCodeToken+"/"+today;
			qrCodeImage = new String(qrCodeImage.getBytes("UTF-8"), "ISO-8859-1");
			
			// qr 코드 만들기
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeImage, BarcodeFormat.QR_CODE, 200, 200);
			
			//MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig();  // 기본컬러(글자색:검정,배경색:흰색)
			int qrCodeColor = 0xFF000000;
			int qrCodeBackColor = 0xFFFFFFFF;
			
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrCodeColor, qrCodeBackColor);
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
			
			// 랜더링된 QR코드 이미지를 실제 그림파일로 만들어낸다.
			ImageIO.write(bufferedImage, "png", new File(realPath + qrCodeName + ".png"));
			
			// QR코드 생성후, 생성된 정보를 DB에 저장시켜준다.(모바일에서 qr코드 확인시에 아이디가 본회원에 확인되면 DB에 저장한다.) 
			//memberDAO.setQrCodeLogin(mid, qrCodeToken, today);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return qrCodeName;
	}

	@Override
	public void setQrCodeLogin(String mid, String qrCodeToken, String today) {
		memberDAO.setQrCodeLogin(mid, qrCodeToken, today);
	}

	@Override
	public String getQrCodeLoginCheck(String mid, String qrCodeToken) {
		return memberDAO.getQrCodeLoginCheck(mid, qrCodeToken);
	}
}
