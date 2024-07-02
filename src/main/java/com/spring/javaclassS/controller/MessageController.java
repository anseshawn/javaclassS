package com.spring.javaclassS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {
	
	@RequestMapping(value = "/message/{msgFlag}", method = RequestMethod.GET)
	public String getMessage(Model model,
			@PathVariable String msgFlag,
			@RequestParam(name="mid", defaultValue = "", required = false) String mid,
			@RequestParam(name="idx", defaultValue = "", required = false) String idx,
			@RequestParam(name="pag", defaultValue = "1", required = false) String pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) String pageSize
			) {
		
		if(msgFlag.equals("userDeleteOk")) {
			model.addAttribute("msg", "회원 자료가 삭제되었습니다.");
			model.addAttribute("url", "/user/userList");
		}
		else if(msgFlag.equals("userDeleteNo")) {
			model.addAttribute("msg", "회원 자료 삭제 실패");
			model.addAttribute("url", "/user/userList");
		}
		else if(msgFlag.equals("userInputOk")) {
			model.addAttribute("msg", "회원 자료가 등록되었습니다.");
			model.addAttribute("url", "/user/userList");
		}
		else if(msgFlag.equals("userInputNo")) {
			model.addAttribute("msg", "회원 자료 등록 실패");
			model.addAttribute("url", "/user/userList");
		}
		else if(msgFlag.equals("userUpdateOk")) {
			model.addAttribute("msg", "회원 정보를 수정하였습니다.");
			model.addAttribute("url", "/user/userList");
		}
		else if(msgFlag.equals("userUpdateNo")) {
			model.addAttribute("msg", "회원 정보 수정 실패~~");
			model.addAttribute("url", "/user/userList");
		}
		else if(msgFlag.equals("dbtestDeleteOk")) {
			model.addAttribute("msg", "회원 삭제 완료");
			model.addAttribute("url", "/dbtest/dbtestList");
		}
		else if(msgFlag.equals("dbtestDeleteNo")) {
			model.addAttribute("msg", "회원 삭제 실패");
			model.addAttribute("url", "/dbtest/dbtestList");
		}
		else if(msgFlag.equals("dbtestInputOk")) {
			model.addAttribute("msg", "회원 자료를 등록하였습니다.");
			model.addAttribute("url", "/dbtest/dbtestList");
		}
		else if(msgFlag.equals("dbtestInputNo")) {
			model.addAttribute("msg", "회원 자료 등록 실패");
			model.addAttribute("url", "/dbtest/dbtestList");
		}
		else if(msgFlag.equals("dbtestUpdateOk")) {
			model.addAttribute("msg", "회원 정보 수정 완료");
			model.addAttribute("url", "/dbtest/dbtestList");
		}
		else if(msgFlag.equals("dbtestUpdateNo")) {
			model.addAttribute("msg", "회원 정보 수정 실패");
			model.addAttribute("url", "/dbtest/dbtestList");
		}
		else if(msgFlag.equals("mailSendOk")) {
			model.addAttribute("msg", "메일이 전송 되었습니다.");
			model.addAttribute("url", "/study/mail/mailForm");
		}
		else if(msgFlag.equals("guestInputOk")) {
			model.addAttribute("msg", "방명록이 작성되었습니다.");
			model.addAttribute("url", "/guest/guestList");
		}
		else if(msgFlag.equals("guestInputNo")) {
			model.addAttribute("msg", "방명록 작성 실패");
			model.addAttribute("url", "/guest/guestInput");
		}
		else if(msgFlag.equals("idCheckNo")) {
			model.addAttribute("msg", "이미 사용중인 아이디입니다.");
			model.addAttribute("url", "/member/memberJoin");
		}
		else if(msgFlag.equals("nickCheckNo")) {
			model.addAttribute("msg", "이미 사용중인 닉네임입니다.");
			model.addAttribute("url", "/member/memberJoin");
		}
		else if(msgFlag.equals("memberJoinOk")) {
			model.addAttribute("msg", "회원 가입이 완료되었습니다.");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("memberJoinNo")) {
			model.addAttribute("msg", "회원 가입 실패");
			model.addAttribute("url", "/member/memberJoin");
		}
		else if(msgFlag.equals("memberLoginOk")) {
			model.addAttribute("msg", mid+"님 로그인 되었습니다.");
			model.addAttribute("url", "/member/memberMain");
		}
		else if(msgFlag.equals("memberLoginNewOk")) {
			model.addAttribute("msg", mid+"님 로그인 되었습니다.\\n신규 비밀번호가 발급되었습니다. 이메일 확인 후 회원정보를 변경해주세요.");
			model.addAttribute("url", "/member/memberMain");
		}
		else if(msgFlag.equals("memberLoginNo")) {
			model.addAttribute("msg", "회원 정보가 맞지 않습니다. 다시 로그인 해주세요.");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("memberLogout")) {
			model.addAttribute("msg", mid+"님 로그아웃 되었습니다.");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("kakaoLogout")) {
			model.addAttribute("msg", mid+"님 로그아웃 되었습니다.(kakao)");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("fileUploadOk")) {
			model.addAttribute("msg", "파일 업로드가 완료되었습니다.");
			model.addAttribute("url", "/study/fileUpload/fileUpload");
		}
		else if(msgFlag.equals("fileUploadNo")) {
			model.addAttribute("msg", "파일 업로드 실패");
			model.addAttribute("url", "/study/fileUpload/fileUpload");
		}
		else if(msgFlag.equals("memberUpdateOk")) {
			model.addAttribute("msg", "회원 정보가 수정되었습니다.");
			model.addAttribute("url", "/member/memberMain");
		}
		else if(msgFlag.equals("memberUpdateNo")) {
			model.addAttribute("msg", "회원 정보 수정 실패");
			model.addAttribute("url", "/member/memberUpdate");
		}
		else if(msgFlag.equals("adminNo")) {
			model.addAttribute("msg", "관리자 전용 메뉴입니다. 일반 회원은 사용할 수 없습니다.");
			model.addAttribute("url", "/");
		}
		else if(msgFlag.equals("memberNo")) {
			model.addAttribute("msg", "회원 로그인 후 사용 가능합니다.");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("memberLevelNo")) {
			model.addAttribute("msg", "회원 등급을 확인하세요.");
			model.addAttribute("url", "/member/memberMain");
		}
		else if(msgFlag.equals("boardInputOk")) {
			model.addAttribute("msg", "게시판에 글이 등록되었습니다.");
			model.addAttribute("url", "/board/boardList");
		}
		else if(msgFlag.equals("boardInputNo")) {
			model.addAttribute("msg", "글쓰기 실패");
			model.addAttribute("url", "/board/boardInput");
		}
		else if(msgFlag.equals("boardUpdateOk")) {
			model.addAttribute("msg", "게시글이 수정되었습니다.");
			model.addAttribute("url", "/board/boardContent?idx="+idx+"&pag="+pag+"&pageSize="+pageSize);
		}
		else if(msgFlag.equals("boardUpdateNo")) {
			model.addAttribute("msg", "게시글 수정 실패");
			model.addAttribute("url", "/board/boardUpdate?idx="+idx+"&pag="+pag+"&pageSize="+pageSize);
		}
		else if(msgFlag.equals("boardDeleteOk")) {
			model.addAttribute("msg", "게시글이 삭제되었습니다.");
			model.addAttribute("url", "/board/boardList");
		}
		else if(msgFlag.equals("boardDeleteNo")) {
			model.addAttribute("msg", "게시글 삭제 실패");
			model.addAttribute("url", "/board/boardContent?idx="+idx+"&pag="+pag+"&pageSize="+pageSize);
		}
		else if(msgFlag.equals("multiFileUploadOk")) {
			model.addAttribute("msg", "파일이 업로드 되었습니다.");
			model.addAttribute("url", "/study/fileUpload/fileUpload");
		}
		else if(msgFlag.equals("multiFileUploadNo")) {
			model.addAttribute("msg", "파일 업로드 실패");
			model.addAttribute("url", "/study/fileUpload/multiFile");
		}
		else if(msgFlag.equals("pdsUploadOk")) {
			model.addAttribute("msg", "파일이 업로드 되었습니다.");
			model.addAttribute("url", "/pds/pdsList");
		}
		else if(msgFlag.equals("pdsUploadNo")) {
			model.addAttribute("msg", "파일 업로드 실패");
			model.addAttribute("url", "/pds/pdsInput");
		}
		else if(msgFlag.equals("midSameSearch")) {
			model.addAttribute("msg", "같은 아이디를 가진 회원이 존재합니다.\\n아이디 확인 후 다시 로그인해 주세요.");
			model.addAttribute("url", "/member/memberLogin");
		}
		
		return "include/message";
	}
	
}
