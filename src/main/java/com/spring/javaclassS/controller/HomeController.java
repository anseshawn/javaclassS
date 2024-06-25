package com.spring.javaclassS.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = {"/","/h"}, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	// 에디터 이미지 업로드
	@RequestMapping(value = "/imageUpload")
	public void imageUploadGet(MultipartFile upload, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/ckeditor/");
		String oFileName = upload.getOriginalFilename();
		
		// 파일명 중복 방지를 위한 이름 설정하기(날짜로 분류처리)
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		oFileName = sdf.format(date)+"_"+oFileName;
		
		FileOutputStream fos = new FileOutputStream(new File(realPath + oFileName)); // 서버에 쓰는거라 output
		fos.write(upload.getBytes());
		
		PrintWriter out = response.getWriter();
		String fileUrl = request.getContextPath()+"/data/ckeditor/" + oFileName;
		out.println("{\"originalFilename\":\""+oFileName+"\","
				+ " \"uploaded\":1,"
				+ " \"url\":\""+fileUrl+"\"}"); // json은 중괄호 안에 큰따옴표 {"키":"값"} oFileName은 값이므로 있는 값을 넣어줘야 함
		// 숫자는 큰 따옴표 안 줘도 됨, 업로드 값이 참이면 숫자 1
		
		out.flush();
		fos.close();
	}
	
}
