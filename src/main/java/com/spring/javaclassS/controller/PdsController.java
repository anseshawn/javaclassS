package com.spring.javaclassS.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.javaclassS.common.JavaclassProvide;
import com.spring.javaclassS.pagination.PageProcess;
import com.spring.javaclassS.service.PdsService;
import com.spring.javaclassS.vo.PageVO;
import com.spring.javaclassS.vo.PdsVO;

@Controller
@RequestMapping("/pds")
public class PdsController {
	
	@Autowired
	PdsService pdsService;
	
	@Autowired
	PageProcess pageProcess;
		
	@Autowired
	JavaclassProvide javaclassProvide;
	
	@RequestMapping(value = "/pdsList", method = RequestMethod.GET)
	public String pdsListGet(Model model,
			@RequestParam(name="part", defaultValue = "전체", required = false) String part,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize
			) {
		PageVO pageVO = pageProcess.totRecCnt(pag, pageSize, "pds", part, "");
		List<PdsVO> vos = pdsService.getPdsList(pageVO.getStartIndexNo(),pageSize,part);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVO", pageVO);
		return "pds/pdsList";
	}
	
	// 자료실 작성
	@RequestMapping(value = "/pdsInput", method = RequestMethod.GET)
	public String pdsInputGet(Model model,
			@RequestParam(name="part", defaultValue = "전체", required = false) String part) {
		model.addAttribute("part", part);
		return "pds/pdsInput";
	}
	// 자료실 작성
	@RequestMapping(value = "/pdsInput", method = RequestMethod.POST)
	public String pdsInputPost(MultipartHttpServletRequest mFile, PdsVO vo) {
		
		int res = pdsService.setPdsUpload(mFile, vo);
		
		if(res != 0) return "redirect:/message/pdsUploadOk";
		else return "redirect:/message/pdsUploadNo";
	}
	
	// 다운로드 수 증가
	@ResponseBody
	@RequestMapping(value = "/pdsDownNumCheck", method = RequestMethod.POST)
	public String pdsDownNumCheckPost(int idx) {
		return pdsService.setPdsDownNumPlus(idx)+"";
	}
	
	// 자료 삭제
	@ResponseBody
	@RequestMapping(value = "/pdsDeleteCheck", method = RequestMethod.POST)
	public String pdsDeleteCheckPost(int idx, String fSName, HttpServletRequest request) {
		// String realPath = request.getSession().getServletContext().getRealPath("/resources/data/pds/");
		return pdsService.setPdsDelete(idx, fSName, request)+"";
	}
	
	// 자료 본문
	@RequestMapping(value = "/pdsContent", method = RequestMethod.GET)
	public String pdsContentGet(int idx, Model model,
			@RequestParam(name="part", defaultValue = "전체", required = false) String part,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize
			) {
		PdsVO vo = pdsService.getPdsContent(idx);
		model.addAttribute("vo", vo);
		
		return "pds/pdsContent";
	}
	
	// 자료 전체 다운로드
	@RequestMapping(value = "/pdsTotalDown", method = RequestMethod.GET)
	public String pdsTotalDownGet(HttpServletRequest request, int idx) throws IOException {
		// 다운로드 수 증가하기
		pdsService.setPdsDownNumPlus(idx);
		
		// 여러개의 파일을 하나의 파일(zip)로 압축(통합)하여 다운로드 시켜준다. 압축파일의 이름은 '제목.zip'으로 처리한다.
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/pds/");
		
		PdsVO vo = pdsService.getPdsContent(idx);
		
		// if(vo.getFName().contains("/")) 이거 안줘도 되나??
		String[] fNames = vo.getFName().split("/");
		String[] fSNames = vo.getFSName().split("/");
		
		String zipPath = realPath+"temp/";
		String zipName = vo.getTitle()+".zip";
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath+zipName)); // 생성할 땐 실제 객체를 넣어줘야 한다
		
		byte[] bytes = new byte[2048];
		
		for(int i=0; i<fNames.length; i++) {
			fis = new FileInputStream(realPath + fSNames[i]);
			fos = new FileOutputStream(zipPath + fNames[i]);
			File copyFile = new File(zipPath+fNames[i]);
			
			// pds폴더의 파일을 temp폴더로 복사
			int data = 0;
			while((data = fis.read(bytes,0,bytes.length)) != -1) { // bytes가 존재하는 동안
				fos.write(bytes,0,data); // 0에서 읽어온 것 만큼(data)
			}
			fos.flush();
			fos.close();
			fis.close();
			
			// temp폴더로 복사된 파일을 zip파일에 담는다
			fis = new FileInputStream(copyFile);
			zout.putNextEntry(new ZipEntry(fNames[i]));
			while((data = fis.read(bytes, 0, bytes.length)) != -1) {
				zout.write(bytes,0,data);
			}
			zout.flush();
			zout.closeEntry();
			fis.close();
		}
		zout.close();
		
		// 클라이언트로 압축된 파일 전송하기
		return "redirect:/fileDownAction?path=pds&file="+java.net.URLEncoder.encode(zipName); // 한글 인코딩해서 넘기기
	}
	
	
}
