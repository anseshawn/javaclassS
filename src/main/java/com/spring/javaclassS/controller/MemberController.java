package com.spring.javaclassS.controller;

import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spring.javaclassS.service.MemberService;
import com.spring.javaclassS.vo.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService memberService;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	JavaMailSender mailSender;
	
	// 로그인 창 연결
	@RequestMapping(value = "/memberLogin", method = RequestMethod.GET)
	public String memberLoginGet(HttpServletRequest request) {
		// 로그인창에 아이디 체크 유무에 대한 처리
		// 쿠키를 검색해서 cMid가 있을때 가져와서 아이디입력창에 뿌릴수 있게 한다.
		Cookie[] cookies = request.getCookies();

		if(cookies != null) { // 저장된 쿠키가 있으면 작동하도록 if문 먼저
			for(int i=0; i<cookies.length; i++) {
				if(cookies[i].getName().equals("cMid")) {
					// pageContext.setAttribute("mid", cookies[i].getValue()); // pageContext는 뷰페이지에서만 유효
					request.setAttribute("mid", cookies[i].getValue());
					break;
				}
			}
		}
		return "member/memberLogin";
	}
	// 로그인 성공/실패 처리
	@RequestMapping(value = "/memberLogin", method = RequestMethod.POST)
	public String memberLoginPost(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam(name="mid",defaultValue = "hkd1234",required = false)String mid, // (폼에서 넘긴변수) 사용할 변수
			@RequestParam(name="pwd",defaultValue = "1234",required = false)String pwd,
			@RequestParam(name="idSave",defaultValue = "1234",required = false)String idSave
		) {
		// 로그인 인증처리(스프링 시큐리티의 BCryptPasswordEncoder객체를 이용하여 암호화되어 있는 비밀번호 비교하기)
		MemberVO vo = memberService.getMemberIdCheck(mid);
		if(vo != null && vo.getUserDel().equals("NO") && passwordEncoder.matches(pwd, vo.getPwd())) { // 논리 연산자는 앞에서부터 비교
			// 로그인 인증 완료시 처리할 부분(세션,쿠키,기타 설정값)
			// 1. 세션 처리
			String strLevel = "";
			if(vo.getLevel()==0) strLevel = "관리자";
			else if(vo.getLevel()==1) strLevel = "우수회원";
			else if(vo.getLevel()==2) strLevel = "정회원";
			else if(vo.getLevel()==3) strLevel = "준회원";
			session.setAttribute("sMid", mid);
			session.setAttribute("sNickName", vo.getNickName());
			session.setAttribute("sLevel", vo.getLevel());
			session.setAttribute("strLevel", strLevel);
			
			// 2. 쿠키 저장/삭제
			if(idSave.equals("on")) { // checkbox 체크시 소문자 on으로 들어옴
				Cookie cookieMid = new Cookie("cMid", mid);
				cookieMid.setPath("/"); // 쿠키 저장 위치를 루트부터
				cookieMid.setMaxAge(60*60*24*7); // 쿠키의 만료 시간을 7일로 지정
				response.addCookie(cookieMid);
			}
			else {
				Cookie[] cookies = request.getCookies();

				if(cookies != null) {
					for(int i=0; i<cookies.length; i++) {
						if(cookies[i].getName().equals("cMid")) {
							cookies[i].setMaxAge(0);
							response.addCookie(cookies[i]);
							break;
						}
					}
				}
			}
			
			// 3. 기타처리 (DB에 처리해야할 것들(방문카운트, 포인트, ... 등))
			// 방문포인트 : 1회 방문씨 point 10점 할당, 1일 최대 50점까지 할당 가능
			int point = 10;
			
			// 방문카운트
			memberService.setMemberInforUpdate(mid,point);
			
			return "redirect:/message/memberLoginOk?mid="+mid;
		}
		else {
			return "redirect:/message/memberLoginNo";
		}
	}
	
	// 로그아웃 처리
	@RequestMapping(value = "/memberLogout", method = RequestMethod.GET)
	public String memberLogoutGet(HttpSession session) {
		String mid = (String) session.getAttribute("sMid");
		session.invalidate();
		return "redirect:/message/memberLogout?mid="+mid;
	}
	
	// 멤버 메인
	@RequestMapping(value = "/memberMain", method = RequestMethod.GET)
	public String memberMainGet(HttpSession session, Model model) {
		String mid = (String) session.getAttribute("sMid");
		MemberVO mVo = memberService.getMemberIdCheck(mid);
		model.addAttribute("mVo", mVo);
		return "member/memberMain";
	}
	
	// 회원가입 창
	@RequestMapping(value = "/memberJoin", method = RequestMethod.GET)
	public String memberJoinGet() {
		return "member/memberJoin";
	}
	
	@RequestMapping(value = "/memberJoin", method = RequestMethod.POST)
	public String memberJoinPost(MemberVO vo) {
		// 아이디/닉네임 중복체크
		if(memberService.getMemberIdCheck(vo.getMid()) != null) return "redirect:/message/idCheckNo";
		if(memberService.getMemberNickCheck(vo.getNickName()) != null) return "redirect:/message/nickCheckNo";
		
		// 비밀번호 암호화
		vo.setPwd(passwordEncoder.encode(vo.getPwd()));
		
		// 회원 사진 처리(service객체에서 처리 후 DB에 저장한다)
		int res = memberService.setMemberJoinOk(vo);
		if(res != 0) return "redirect:/message/memberJoinOk";
		else return "redirect:/message/memberJoinNo";
	}
	
	@ResponseBody
	@RequestMapping(value = "/memberIdCheck", method = RequestMethod.GET)
	public String memberIdCheckGet(String mid) {
		MemberVO vo = memberService.getMemberIdCheck(mid);
		if(vo != null) return "1";
		else return "0";
	}
	@ResponseBody
	@RequestMapping(value = "/memberNickCheck", method = RequestMethod.GET)
	public String memberNickCheckGet(String nickName) {
		MemberVO vo = memberService.getMemberNickCheck(nickName);
		if(vo != null) return "1";
		else return "0";
	}
	
	// 임시 비밀번호 발급
	@ResponseBody
	@RequestMapping(value = "/memberNewPassword", method = RequestMethod.POST)
	public String memberNewPasswordPost(String mid, String email, HttpSession session) throws MessagingException {
		MemberVO vo = memberService.getMemberIdCheck(mid);
		if(vo != null && vo.getEmail().equals(email)) {
			// 정보 확인 후 정보가 맞으면 임시 비밀번호를 발급받아서 메일로 전송처리한다.
			UUID uid = UUID.randomUUID();
			String pwd = uid.toString().substring(0,8);
			
			// 새로 발급받은 비밀번호를 암호화 한 후, DB에 저장한다.
			memberService.setMemberPasswordUpdate(mid, passwordEncoder.encode(pwd));
			
			// 발급받은 비밀번호를 메일로 전송한다.
			String title = mid +"님의 임시 비밀번호를 발급하였습니다.";
			String imsiContent = "임시 비밀번호 : " + pwd;
			String mailFlag = "pwdSearch";
			String res = mailSend(email, title, imsiContent, mailFlag);
			
			// 새 비밀번호를 발급하였을 시에 sLogin이란 세션을 발생시키고, 2분 안에 새 비밀번호로 로그인 후 비밀번호를 변경처리할 수 있도록 처리
			// (sLogin값이 없을 경우는 )
			session.setAttribute("sLogin", "OK");
			
			if(res == "1")	return "1";
		}
		return "0";
	}
	
	// 아이디 찾기
	@ResponseBody
	@RequestMapping(value = "/memberMidSearch", method = RequestMethod.POST)
	public String memberMidSearchPost(String name, String email) throws MessagingException {
		MemberVO vo = memberService.getMemberNameCheck(name);
		if(vo != null && vo.getEmail().equals(email)) {
			// 정보 확인 후 정보가 맞으면 임시 비밀번호를 발급받아서 메일로 전송처리한다.
			
			// 발급받은 비밀번호를 메일로 전송한다.
			String title = "아이디 찾기";
			String imsiContent = "아이디 : "+vo.getMid();
			String mailFlag = "midSearch";
			String res = mailSend(email, title, imsiContent, mailFlag);
			
			if(res == "1")	return "1";
		}
		return "0";
	}

	// 메일 전송 메소드(아이디 찾기, 비밀번호 찾기)
	private String mailSend(String toMail, String title, String imsiContent, String mailFlag) throws MessagingException {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String content = "";
		
		// 메일 전송을 위한 객체 :MimeMessage(), MimeMessageHelper()
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
		
		// 메일 보관함에 작성한 메세지들의 정보를 모두 저장시킨 후 작업처리
		messageHelper.setTo(toMail); // 받는 사람 메일 주소
		messageHelper.setSubject(title); // 메일 제목
		messageHelper.setText(content);	// 메일 내용
		
		// 메세지 보관함의 내용(content)에, 발신자의 필요한 정보를 추가로 담아서 전송처리한다.
		if(mailFlag.equals("pwdSearch")) {
			content += "<br><hr><h3>임시 비밀번호 발급</h3><hr><br>";
			content += imsiContent+"<br>";
		}
		else if(mailFlag.equals("midSearch")) {
			content += "<br><hr><h3>아이디 찾기</h3><hr><br>";
			content += imsiContent+"<br>";
		}
		content += "<p><img src='cid:main.jpg' width='500px'></p>"; // cid: 예약어, 보내고 싶은 그림 이름을 적어준다
		content += "<p>방문하기 : <a href='http://49.142.157.251:9090/javaclassJ9/Main.do'>javaclass</a></p>";
		content += "<hr>";
		content = content.replace("\n", "<br>"); // 엔터키를 <br>태그로 바꾼 후 내용을 쌓는다 /는 html4에서 에러가 생길 수 있어서 생략
		messageHelper.setText(content, true);	// 기존 내용을 무시하고 덮어쓴다
		
		// 본문에 그림 표시하기: cid개수대로 나와야 함
		FileSystemResource file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/main.jpg"));
		messageHelper.addInline("main.jpg", file);
		
		// 메일 전송하기
		mailSender.send(message);
		
		return "1";
	}
	
	// 비밀번호 체크 (비밀번호 변경 / 회원정보 수정)
	@RequestMapping(value = "/memberPwdCheck/{pwdFlag}", method=RequestMethod.GET)
	public String memberPwdCheckGet(@PathVariable String pwdFlag, Model model) {
		model.addAttribute("pwdFlag",pwdFlag);
		return "member/memberPwdCheck";
	}
	@ResponseBody
	@RequestMapping(value = "/memberPwdCheck", method=RequestMethod.POST)
	public String memberPwdCheckPost(String pwd, String mid) {
		MemberVO vo = memberService.getMemberIdCheck(mid);
		if(passwordEncoder.matches(pwd, vo.getPwd())) return "1";
		else return "0";
	}
}
