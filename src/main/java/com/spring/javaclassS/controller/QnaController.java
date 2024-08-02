package com.spring.javaclassS.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.javaclassS.common.JavaclassProvide;
import com.spring.javaclassS.pagination.PageProcess;
import com.spring.javaclassS.service.QnaService;
import com.spring.javaclassS.vo.PageVO;
import com.spring.javaclassS.vo.QnaVO;

@Controller
@RequestMapping("/qna")
public class QnaController {
  String msgFlag = "";
  
  @Autowired
  QnaService qnaService;
  
  @Autowired
  PageProcess pageProcess;
  
  @Autowired
  JavaclassProvide javaclassProvide;
	
  @RequestMapping(value = "/qnaList", method = RequestMethod.GET)
  public String qnaListGet(
  		@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize,
			Model model) {
  	PageVO pageVO = pageProcess.totRecCnt(pag, pageSize, "qna", "", "");
		List<QnaVO> vos = qnaService.getQnaList(pageVO.getStartIndexNo(), pageSize);
		model.addAttribute("vos", vos);
		model.addAttribute("pageVO", pageVO);
		
  	return "qna/qnaList";
  }
  
  // 질문글로 호출될때는 qnaSw가 'q'로, 답변글로 호출될때는 'a'로 qnaSw값에 담겨 넘어온다.
  @RequestMapping(value = "/qnaInput", method = RequestMethod.GET)
  public String qnaListGet(String qnaFlag, HttpSession session, Model model) {
  	String mid = (String) session.getAttribute("sMid");
  	String email = qnaService.getEmail(mid);
  	
  	model.addAttribute("qnaFlag", qnaFlag);
  	model.addAttribute("email", email);
  	
  	return "qna/qnaInput";
  }
  
  // qna '글올리기'와 '답변글 올리기'에서 이곳을 모두 사용하고 있다. 
  @Transactional
  @RequestMapping(value = "/qnaInput", method = RequestMethod.POST)
  public String qnaListPost(QnaVO vo, HttpSession session) {
		// content에 이미지가 저장되어 있다면, 저장된 이미지만 골라서 /resources/data/qna/폴더에 저장시켜준다.
  	if(vo.getContent().indexOf("src=\"/") != -1) {
  		javaclassProvide.imgCheck(vo.getContent(), "ckeditor", "qna");	// 이미지를 ckeditor폴더에서 qna폴더로 복사하기
  		
  		// 이미지 복사작업이 끝나면, qna폴더에 실제로 저장된 파일명을 DB에 저장시켜준다.(/resources/data/ckeditor/  ==>> /resources/data/qna/)
  		vo.setContent(vo.getContent().replace("/data/ckeditor/", "/data/qna/"));
  	}
  	
		// 앞에서 ckeditor의 그림작업이 끝나고 일반작업들을 수행시킨다.
		
  	int level = (int) session.getAttribute("sLevel");
  	
  	// 먼저 idx 설정하기
  	int newIdx = qnaService.getMaxIdx() + 1;
  	vo.setIdx(newIdx);
  	
  	// 회원의 고유등급 저장하기
  	vo.setAnsLevel(level);
  	
  	// qnaIdx 설정하기
  	if(vo.getQnaSw().equals("q")) vo.setQnaIdx(newIdx);
  	else {  	
  		if(level == 0) vo.setTitle(vo.getTitle().replace("(Re)", "<font color='red'>(Re)</font>"));
  	}
  	
  	// 질문글이나 답변글을 DB에 저장한다.
  	qnaService.qnaInputOk(vo);
  	
  	// 답변여부 테이블에 대한 저장을 한다.
  	// 질문글이라면 '답변대기'라고 저장해야하고, 답변글이라면 그중에서도 관리자가 답변달았을때만 '답변완료'라고 업데이트한다.(일반 사용자가 답변을 달았을때는 답변테이블에는 저장하지 않는다. 즉, 관리자만이 답변을 달았을때만 답변테이블에 저장..)
  	if(vo.getQnaSw().equals("q"))	qnaService.qnaAdminInputOk(vo.getQnaIdx());
  	else if(vo.getQnaSw().equals("a") && level == 0) qnaService.qnaAdminAnswerUpdateOk(vo.getQnaIdx());
  	
  	return "redirect:/message/qnaInputOk";
  }
  
  // qna 작성글 보기(작성글보기 안에서 답글을 올릴경우 원본글의 title와 답변자의 email주소가 필요하기에 model로 보낸다.)
  @RequestMapping(value = "/qnaContent", method = RequestMethod.GET)
  public String qnaListGet(int idx, String title, int pag, HttpSession session, Model model) {
  	String mid = (String) session.getAttribute("sMid");
  	String email = qnaService.getEmail(mid);
  	
  	QnaVO vo = qnaService.getQnaContent(idx);
  	model.addAttribute("email", email);
  	model.addAttribute("title", title);
  	model.addAttribute("pag", pag);
  	model.addAttribute("vo", vo);
  	
  	return "qna/qnaContent";
  }
  
  // qna 업데이트 폼 보기
  @RequestMapping(value = "/qnaUpdate", method = RequestMethod.GET)
  public String qnaUpdateGet(Model model, int idx) {
  	QnaVO vo = qnaService.getQnaContent(idx);
  	
  	// content내용안에 그림파일이 있다면, 그림파일이 저장되어 있는 qna폴더에서 ckeditor폴더로 복사시켜준다.
  	if(vo.getContent().indexOf("src=\"/") != -1) {
   		javaclassProvide.imgCheck(vo.getContent(), "qna", "ckeditor");	// 이미지를 qna폴더에서 ckeditor폴더로 복사하기
   	}
  	
  	model.addAttribute("vo", vo);
  	return "qna/qnaUpdate";
  }
  
  // qna 업데이트 처리하기(임시그림폴더(ckeditor) 정리는 하지 않았음...)
  @RequestMapping(value = "/qnaUpdate", method = RequestMethod.POST)
  public String qnaUpdatePost(Model model, HttpSession session, QnaVO vo) {
    // content에 이미지가 저장되어 있다면, 저장된 이미지만 골라서 /resources/data/qna/폴더에 저장시켜준다.
   	if(vo.getContent().indexOf("src=\"/") != -1) {
   		javaclassProvide.imgCheck(vo.getContent(), "ckeditor", "qna");	// 이미지를 ckeditor폴더에서 qna폴더로 복사하기
   		
   		// 이미지 복사작업이 끝나면, qna폴더에 실제로 저장된 파일명을 DB에 저장시켜준다.(/resources/data/ckeditor/  ==>> /resources/data/qna/)
   		// 이때 기존에 존재하는 파일의 경로는 qna로 되어 있기에, 먼저 qna폴더를 ckeditor폴더로 변경후, 다시 모든 파일(새롭게 업데이트된파일들 포함)의 경로를 다시 ckeditor에서 qna로 변경처리한다.
   		vo.setContent(vo.getContent().replace("/data/qna/", "/data/ckeditor/"));	// 기존에 있던 파일들의 경로를 qna에서 ckeditor로 변경
   		vo.setContent(vo.getContent().replace("/data/ckeditor/", "/data/qna/"));	// 이번에는 모든파일(새로 업데이트된것 포함)들의 경로를 ckeditor에서 qna로 변경
   	}
  	// 이미지에대한 모든 작업(복사, 이름변경)이 끝나면, 변경된 사항을 DB에 저장한다.
  	qnaService.setQnaContentUpdate(vo);
  	return "redirect:/message/qnaUpdateOk";
  }
  
  // qna글 삭제하기
  @RequestMapping(value = "/qnaDelete", method = RequestMethod.GET)
  public String qnaDeleteGet(int idx) {
  	QnaVO vo = qnaService.getQnaContent(idx);
  	
    // content에 이미지가 저장되어 있다면, 저장된 이미지만 골라서 /resources/data/qna/폴더에서 삭제시켜준다.
   	if(vo.getContent().indexOf("src=\"/") != -1) {
   		javaclassProvide.imagesDelete(vo.getContent(), "qna");	// qna폴더의 이미지들을 모두 삭제하기   		
   	}
   	// 이미지 삭제작업이 끝나면, DB에서 자료를 삭제또는 Update시켜준다.(만약에 답변글의 삭제는 바로 삭제처리하고, 문의글의 삭제는 답변 댓글이 없다면 삭제하고, 답변글이 있다면 '삭제되었습니다.'라는 문구로 업데이트한다.
   	List<QnaVO> qnaCheckVO = qnaService.getQnaIdxCheck(vo.getQnaIdx());
   	
   	if(vo.getQnaSw().equals("a") || (vo.getQnaSw().equals("q") && qnaCheckVO.size() == 1)) qnaService.setQnaDelete(idx);
   	else qnaService.setQnaCheckUpdate(idx, "<font size='2' color='#ccc'>현재 삭제된 글입니다.</font>");
  	
  	return "redirect:/message/qnaDeleteOk";
  }
  
}
