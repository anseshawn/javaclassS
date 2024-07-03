package com.spring.javaclassS.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.tokenizer.WhiteSpaceWordTokenizer;
import com.kennycason.kumo.palette.ColorPalette;
import com.spring.javaclassS.common.ARIAUtil;
import com.spring.javaclassS.common.SecurityUtil;
import com.spring.javaclassS.service.DbtestService;
import com.spring.javaclassS.service.StudyService;
import com.spring.javaclassS.vo.CrawlingVO;
import com.spring.javaclassS.vo.CrimeVO;
import com.spring.javaclassS.vo.MailVO;
import com.spring.javaclassS.vo.UserVO;

@Controller
@RequestMapping("/study")
public class StudyController {
	
	@Autowired
	StudyService studyService;
	
	@Autowired
	DbtestService dbtestService;
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/ajax/ajaxForm", method = RequestMethod.GET)
	public String ajaxFormGet() {
		return "study/ajax/ajaxForm";
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest1", method = RequestMethod.POST)
	public String ajaxTest1Post(int idx) {
		System.out.println("idx : " + idx);
		//return "study/ajax/ajaxForm";
		return idx + "";
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest2", method = RequestMethod.POST, produces="application/text; charset=utf-8")
	public String ajaxTest2Post(String str) {
		System.out.println("str : " + str);
		//return "study/ajax/ajaxForm";
		return str;
	}
	
	@RequestMapping(value = "/ajax/ajaxTest3_1", method = RequestMethod.GET)
	public String ajaxTest3_1Get() {
		return "study/ajax/ajaxTest3_1";
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest3_1", method = RequestMethod.POST)
	public String[] ajaxTest3_1Post(String dodo) {
		// String[] strArray = new String[100];
		// strArray = studyService.getCityStringArray();
		// return strArray;
		
		return studyService.getCityStringArray(dodo);
	}
	
	@RequestMapping(value = "/ajax/ajaxTest3_2", method = RequestMethod.GET)
	public String ajaxTest3_2Get() {
		return "study/ajax/ajaxTest3_2";
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest3_2", method = RequestMethod.POST)
	public ArrayList<String> ajaxTest3_2Post(String dodo) {
		return studyService.getCityArrayList(dodo);
	}
	
	@RequestMapping(value = "/ajax/ajaxTest3_3", method = RequestMethod.GET)
	public String ajaxTest3_3Get() {
		return "study/ajax/ajaxTest3_3";
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest3_3", method = RequestMethod.POST)
	public HashMap<Object, Object> ajaxTest3_3Post(String dodo) {
		ArrayList<String> vos = studyService.getCityArrayList(dodo);
		
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("city", vos);
		
		return map;
	}
	
	@RequestMapping(value = "/ajax/ajaxTest3_4", method = RequestMethod.GET)
	public String ajaxTest3_4Get(Model model) {
		ArrayList<String> midVos = dbtestService.getDbtestMidList();
		model.addAttribute("midVos", midVos);
		
		ArrayList<String> addressVos = dbtestService.getDbtestAddressList();
		model.addAttribute("addressVos", addressVos);
		return "study/ajax/ajaxTest3_4";
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest3_4", method = RequestMethod.POST, produces="application/text; charset=utf-8")
	public String ajaxTest3_4Post(String mid) {
		UserVO vo = dbtestService.getUserIdCheck(mid);
		String str = "<h3>회원정보</h3>";
		str += "아이디 : " + vo.getMid() + "<br>";
		str += "성명 : " + vo.getName() + "<br>";
		str += "나이 : " + vo.getAge() + "<br>";
		str += "주소 : " + vo.getAddress();
		return str;
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest3_5", method = RequestMethod.POST)
	public ArrayList<UserVO> ajaxTest3_5Post(String address) {
		return dbtestService.getUserAddressCheck(address);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest4-1", method = RequestMethod.POST)
	public UserVO ajaxTest4_1Post(String mid) {
		return studyService.getUserMidSearch(mid);
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest4-2", method = RequestMethod.POST)
	public ArrayList<UserVO> ajaxTest4_2Post(String mid) {
		return studyService.getUserMidList(mid);
	}
	
	@RequestMapping(value = "/restapi/restapi", method = RequestMethod.GET)
	public String restapiGet() {
		return "study/restapi/restapi";
	}
	
	@RequestMapping(value = "/restapi/restapiTest1/{message}", method = RequestMethod.GET)
	public String restapiTest1Get(@PathVariable String message) {
		System.out.println("message : " + message);
		return "message : "+message;
	}
	
	@RequestMapping(value = "/restapi/restapiTest4", method = RequestMethod.GET)
	public String restapiTest4Get() {
		return "study/restapi/restapiTest4";
	}
	
	@ResponseBody
	@RequestMapping(value = "/restapi/saveCrimeData", method = RequestMethod.POST)
	public void saveCrimeDataPost(CrimeVO vo) {
		studyService.setSaveCrimeData(vo);
	}
	
	@ResponseBody
	@RequestMapping(value = "/restapi/deleteCrimeData", method = RequestMethod.POST)
	public int deleteCrimeDataPost(String year) {
		return studyService.setDeleteCrimeData(year);
	}
	
	@ResponseBody
	@RequestMapping(value = "/restapi/listCrimeDate", method = RequestMethod.POST)
	public ArrayList<CrimeVO> listCrimeDatePost(String year) {
		return studyService.getListCrimeDate(year);
	}
	
	@ResponseBody
	@RequestMapping(value = "/restapi/yearPoliceList", method = RequestMethod.POST)
	public ArrayList<CrimeVO> yearPoliceListPost(String yearOrder, String police) {
		if(yearOrder.equals("a")) return studyService.getYearPoliceListAsc(police);
		else return studyService.getYearPoliceListDesc(police);
	}
	

	@ResponseBody
	@RequestMapping(value = "/restapi/getYearStatsCrime", method = RequestMethod.POST)
	public CrimeVO getYearStatsCrimePost(String year, String police) {
		ArrayList<CrimeVO> vos = studyService.getYearStatsCrime(year,police);
		int totRobbery = 0;
		int totTheft = 0;
		int totMurder = 0;
		int totViolence = 0;
		for(int i=0; i<vos.size(); i++) {
			totRobbery += vos.get(i).getTotRobbery();
			totTheft += vos.get(i).getTotTheft();
			totMurder += vos.get(i).getTotMurder();
			totViolence += vos.get(i).getTotViolence();
		}
		CrimeVO vo = new CrimeVO();
		vo.setTotRobbery(totRobbery);
		vo.setTotTheft(totTheft);
		vo.setTotMurder(totMurder);
		vo.setTotViolence(totViolence);
		return vo;
	}

	/* 과제
	@ResponseBody
	@RequestMapping(value = "/restapi/listCrimeDate", method = RequestMethod.POST)
	public ArrayList<CrimeVO> listCrimeDatePost(int year) {
		return studyService.getListCrimeDate(year);
	}
	
	@RequestMapping(value = "/restapi/yearPoliceCheck", method = RequestMethod.POST)
	public String yearPoliceCheckPost(int year, String police, String yearOrder, Model model) {
		ArrayList<CrimeVO> vos = studyService.getYearPoliceCheck(year, police, yearOrder);
		model.addAttribute("vos", vos);
		
		CrimeVO analyzeVo = studyService.getAnalyzeTotal(year, police);
		model.addAttribute("analyzeVo", analyzeVo);
		
		model.addAttribute("year", year);
		model.addAttribute("police", police);
		model.addAttribute("totalCnt", analyzeVo.getTotMurder()+analyzeVo.getTotRobbery()+analyzeVo.getTotTheft()+analyzeVo.getTotViolence());
		
		return "study/restapi/restapiTest4";
	}
	*/
	
	@RequestMapping(value = "/mail/mailForm", method = RequestMethod.GET)
	public String mailFormGet() {
		return "study/mail/mailForm";
	}
	// 메일 전송하기
	@RequestMapping(value = "/mail/mailForm", method = RequestMethod.POST)
	public String mailFormPost(MailVO vo, HttpServletRequest request) throws MessagingException {
		String toMail = vo.getToMail();
		String title = vo.getTitle();
		String content = vo.getContent();
		
		// 메일 전송을 위한 객체 :MimeMessage(), MimeMessageHelper()
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
		
		// 메일 보관함에 작성한 메세지들의 정보를 모두 저장시킨 후 작업처리
		messageHelper.setTo(toMail); // 받는 사람 메일 주소
		messageHelper.setSubject(title); // 메일 제목
		messageHelper.setText(content);	// 메일 내용
		
		// 메세지 보관함의 내용(content)에, 발신자의 필요한 정보를 추가로 담아서 전송처리한다.
		content = content.replace("\n", "<br>"); // 엔터키를 <br>태그로 바꾼 후 내용을 쌓는다 /는 html4에서 에러가 생길 수 있어서 생략
		content += "<br><hr><h3>javaclass 에서 보냅니다.</h3><hr><br>";
		content += "<p><img src='cid:main.jpg' width='500px'></p>"; // cid: 예약어, 보내고 싶은 그림 이름을 적어준다
		content += "<p>방문하기 : <a href='http://49.142.157.251:9090/javaclassJ9/Main.do'>javaclass</a></p>";
		content += "<hr>";
		messageHelper.setText(content, true);	// 기존 내용을 무시하고 덮어쓴다
		
		// 본문에 기재 될 그림파일의 경로를 별도로 표시시켜준 후 다시 보관함에 저장한다.
		//FileSystemResource file = new FileSystemResource("D:\\javaclass\\springframework\\works\\javaclassS\\src\\main\\webapp\\resources\\images\\main.jpg");
		
		// 본문에 그림 표시하기: cid개수대로 나와야 함
		FileSystemResource file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/main.jpg"));
		messageHelper.addInline("main.jpg", file);
		
		
		// 첨부파일 보내기 1 -- 파일명이 attachment로 바뀌어 가기 때문에 가급적이면 2번방법으로
		// request.getSession().getServletContext().getRealPath("/resources/images/main.jpg");
		// FileSystemResource file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/main.jpg"));
		// messageHelper.addInline("main.jpg", file); // 파일명이 맞아야 함
		
		// 첨부파일 보내기 2
		file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/chicago.jpg"));
		messageHelper.addAttachment("chicago.jpg", file);
		file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/sanfran.zip"));
		messageHelper.addAttachment("sanfran.zip", file);
		
		
		// 메일 전송하기
		mailSender.send(message);
		
		return "redirect:/message/mailSendOk";
	}
	
	// 파일 업로드 연습폼 호출하기
	@RequestMapping(value = "/fileUpload/fileUpload", method = RequestMethod.GET)
	public String fileUploadGet(HttpServletRequest request, Model model) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/fileUpload"); // 파일이 아니라 정보를 꺼내는 것이므로 끝에 / 없애기
		String[] files = new File(realPath).list();
		model.addAttribute("files", files);
		model.addAttribute("fileCount", files.length);
		return "study/fileUpload/fileUpload";
	}
	
	@RequestMapping(value = "/fileUpload/fileUpload", method = RequestMethod.POST)
	public String fileUploadPost(MultipartFile fName, String mid) {
		
		int res = studyService.fileUpload(fName,mid);
		
		if(res != 0) return "redirect:/message/fileUploadOk";
		else return "redirect:/message/fileUploadOk";
	}
	
	// 개별 파일 삭제
	@ResponseBody
	@RequestMapping(value = "/fileUpload/fileDelete", method = RequestMethod.POST)
	public String fileDeletePost(HttpServletRequest request, String file) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/fileUpload/");
		
		String res = "0";
		File fName = new File(realPath+file);
		
		if(fName.exists()) {
			fName.delete();
			res = "1";
		}
		return res;
	}
	
	// 모든 파일 삭제
	@ResponseBody
	@RequestMapping(value = "/fileUpload/fileDeleteAll", method = RequestMethod.POST)
	public String fileDeleteAllPost(HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/fileUpload/");
		
		String res = "0";
		File targetFolder = new File(realPath);
		if(!targetFolder.exists()) return res;
		
		File[] files = targetFolder.listFiles();
		
		if(files.length != 0) {
			for(File f : files) {
				if(!f.isDirectory()) f.delete();
			}
			res = "1";
		}
		return res;
	}
	
	// 멀티파일 업로드
	@RequestMapping(value = "/fileUpload/multiFile", method = RequestMethod.GET)
	public String multiFileGet() {
		return "study/fileUpload/multiFile";
	}
	
	// 그림파일 업로드
	@RequestMapping(value = "/fileUpload/multiFile2", method = RequestMethod.GET)
	public String multiFile2Get() {
		return "study/fileUpload/multiFile2";
	}
	
	@RequestMapping(value = "/fileUpload/multiFile", method = RequestMethod.POST)
	public String multiFilePost(MultipartHttpServletRequest mFile) {
		
		int res = studyService.multiFileUpload(mFile);
		
		if(res != 0) return "redirect:/message/multiFileUploadOk";
		else return "redirect:/message/multiFileUploadNo";
	}
	
	@RequestMapping(value = "/fileUpload/multiFile2", method = RequestMethod.POST)
	public String multiFile2Post(MultipartHttpServletRequest mFile, HttpServletRequest request, String imgNames) {
		// String[] imgNames = request.getParameter("imgNames").split("/");
		
		int res = studyService.multiFileUpload(mFile);
		
		if(res != 0) return "redirect:/message/multiFileUploadOk";
		else return "redirect:/message/multiFileUploadNo";
	}
	
	// 크롤링연습(jsoup)
	@RequestMapping(value = "/crawling/jsoup", method = RequestMethod.GET)
	public String jsoupGet() {
		return "study/crawling/jsoup";
	}
	/*
	// 크롤링연습 처리(jsoup)
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup", method = RequestMethod.POST, produces="application/text; charset=utf-8")
	public String jsoupPost(String url, String selector) throws IOException {
		Connection conn = Jsoup.connect(url);
		Document document = conn.get();
		//System.out.println("document: "+document);
		
		Elements selects = document.select(selector);
		//System.out.println(selects);
		//System.out.println(selects.text());
		
		String str = "";
		int i=0;
		for(Element select : selects) {
			i++;
			System.out.println(i+" : "+select.text());
			str += i + " : " +select+"<br/>";
		}
		
		return str;
	}
	*/
	
	// 크롤링연습 결과 객체 처리(jsoup) (한글 인코딩 필요 없음)
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup", method = RequestMethod.POST)
	public ArrayList<String> jsoupPost(String url, String selector) throws IOException {
		Connection conn = Jsoup.connect(url);
		Document document = conn.get();
		
		Elements selects = document.select(selector);
		
		ArrayList<String> vos = new ArrayList<String>(); // 타이틀, 그림, 출처 등 같이 가져오려면 vo타입으로
		int i=0;
		for(Element select : selects) {
			i++;
			System.out.println(i+" : "+select);
			//System.out.println(i+" : "+select.text());
			vos.add( i + " : " +select.html().replace("data-onshow-", ""));
		}
		
		return vos;
	}
	
	
	// 크롤링연습 결과 객체 처리2(jsoup) (네이버 뉴스 헤드라인 가져오기)
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup2", method = RequestMethod.POST)
	public ArrayList<CrawlingVO> jsoup2Post() throws IOException {
		Connection conn = Jsoup.connect("https://news.naver.com/");
		Document document = conn.get();
		
		Elements selects = null;
		
		ArrayList<String> titleVos = new ArrayList<String>();
		selects = document.select("div.cjs_t");
		for(Element select : selects) {
			titleVos.add(select.html());
		}
		
		ArrayList<String> imageVos = new ArrayList<String>();
		selects = document.select("div.cjs_news_mw");
		for(Element select : selects) {
			imageVos.add(select.html().replace("data-onshow-", ""));
		}
		
		ArrayList<String> broadcastVos = new ArrayList<String>();
		selects = document.select("h4.channel");
		for(Element select : selects) {
			broadcastVos.add(select.html());
		}
		
		ArrayList<CrawlingVO> vos = new ArrayList<CrawlingVO>();
		CrawlingVO vo = null;
		for(int i=0; i<titleVos.size(); i++) {
			vo = new CrawlingVO();
			vo.setItem1(titleVos.get(i));
			vo.setItem2(imageVos.get(i));
			vo.setItem3(broadcastVos.get(i));
			vos.add(vo);
		}
		
		return vos;
	}
	
	// 크롤링연습 결과 객체 처리3(jsoup) (다음 연예 뉴스 가져오기)
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup3", method = RequestMethod.POST)
	public ArrayList<CrawlingVO> jsoup3Post() throws IOException {
		Connection conn = Jsoup.connect("https://entertain.daum.net/");
		Document document = conn.get();
		
		Elements selects = null;
		
		ArrayList<String> titleVos = new ArrayList<String>();
		selects = document.select("ul.list_news strong.tit_thumb");
		for(Element select : selects) {
			titleVos.add(select.html());
		}
		
		ArrayList<String> imageVos = new ArrayList<String>();
		selects = document.select("ul.list_news a.link_thumb");
		for(Element select : selects) {
			imageVos.add(select.html());
		}
		
		ArrayList<String> broadcastVos = new ArrayList<String>();
		selects = document.select("ul.list_news span.info_thumb");
		for(Element select : selects) {
			broadcastVos.add(select.html());
		}
		
		ArrayList<CrawlingVO> vos = new ArrayList<CrawlingVO>();
		CrawlingVO vo = null;
		for(int i=0; i<titleVos.size(); i++) {
			vo = new CrawlingVO();
			vo.setItem1(titleVos.get(i));
			vo.setItem2(imageVos.get(i));
			vo.setItem3(broadcastVos.get(i));
			vos.add(vo);
		}
		
		return vos;
	}
	
	// 크롤링연습 결과 객체 처리4(jsoup) (네이버 검색어를 이용한 검색처리)
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup4", method = RequestMethod.POST)
	public ArrayList<String> jsoup4Post(String search, String searchSelector) throws IOException {
		Connection conn = Jsoup.connect(search);
		Document document = conn.get();
		
		Elements selects = document.select(searchSelector);
		
		ArrayList<String> vos = new ArrayList<String>();
		
		int i=0;
		for(Element select : selects) {
			i++;
			// System.out.println(i+" : "+select);
			vos.add(i+" : "+select.html());
		}
		
		return vos;
	}
	
	// 크롤링연습 결과 객체 처리5(jsoup) (네이버 검색어로 그림 검색결과 가져오기)
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup5", method = RequestMethod.POST)
	public ArrayList<String> jsoup5Post(String search, String searchSelector) throws IOException {
		Connection conn = Jsoup.connect(search);
		Document document = conn.get();
		
		Elements selects = document.select(searchSelector);
		
		ArrayList<String> vos = new ArrayList<String>();
		
		int i=0;
		for(Element select : selects) {
			i++;
			// System.out.println(i+" : "+select);
			vos.add(i+" : "+select.html().replace("data-lazy", ""));
		}
		
		return vos;
	}
	
	// 크롤링연습(selenium)
	@RequestMapping(value = "/crawling/selenium", method = RequestMethod.GET)
	public String seleniumGet() {
		return "study/crawling/selenium";
	}
	
	// 크롤링연습 처리(selenium) - CGV 상영작 크롤링
	@ResponseBody
	@RequestMapping(value = "/crawling/selenium", method = RequestMethod.POST)
	public List<HashMap<String, Object>> seleniumPost(HttpServletRequest request) {
		List<HashMap<String, Object>> vos = new ArrayList<HashMap<String,Object>>();
		
		try {
			String realPath = request.getSession().getServletContext().getRealPath("/resources/crawling/");
			System.setProperty("webdriver.chrome.driver", realPath+"chromedriver.exe");
			
			WebDriver driver = new ChromeDriver();
			driver.get("http://www.cgv.co.kr/movies/");
			
			// 현재 상영작만 보기
			WebElement btnMore = driver.findElement(By.id("chk_nowshow"));
			btnMore.click();
			
			// 더보기 버튼 클릭
			btnMore = driver.findElement(By.className("link-more"));
			btnMore.click();
			
			// 화면이 더 열리는 동안 시간을 지연시켜준다. 열리기전에 더보기 버튼이 클릭되면 이후 내용이 로딩이 안 될 수 있음
			try {	Thread.sleep(2000);	} catch (Exception e) {}
			
			// 낱개의 vos 객체(elements)를 HashMap에 등록 후 List객체로 처리해서 프론트로 넘겨준다.
			List<WebElement> elements = driver.findElements(By.cssSelector("div.sect-movie-chart ol li"));
			for(WebElement element : elements) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				String image = "<img src='"+element.findElement(By.tagName("img")).getAttribute("src")+"' width='200px'";
				String link = element.findElement(By.tagName("a")).getAttribute("href");
				String title = "<a href='"+link+"' target='_blank'>"+element.findElement(By.className("title")).getText()+"</a>";
				String percent = element.findElement(By.className("percent")).getText();
				map.put("image", image);
				map.put("link", link);
				map.put("title", title);
				map.put("percent", percent);
				vos.add(map);
			}
			driver.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("vos: "+vos);
		return vos;
	}
	
	// 크롤링연습 처리(selenium) - SRT 열차 시간표 크롤링
	@ResponseBody
	@RequestMapping(value = "/crawling/train", method = RequestMethod.POST)
	public List<HashMap<String, Object>> trainPost(HttpServletRequest request, String stationStart, String stationStop) {
		List<HashMap<String, Object>> array = new ArrayList<HashMap<String,Object>>();
		try {
			String realPath = request.getSession().getServletContext().getRealPath("/resources/crawling/");
			System.setProperty("webdriver.chrome.driver", realPath+"chromedriver.exe");
			
			WebDriver driver = new ChromeDriver();
			driver.get("http://srtplay.com/train/schedule");
			
			// 출발지 클릭 (우클릭 -> copy -> copy xpath 로 실제 아이디 가져오기)
			WebElement btnMore = driver.findElement(By.xpath("//*[@id=\"station-start\"]/span"));
			btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}
      // 입력란에 출발지 입력
      btnMore = driver.findElement(By.xpath("//*[@id=\"station-pos-input\"]"));
      btnMore.sendKeys(stationStart); // sendKeys: 박스 안에 값 입력
      btnMore = driver.findElement(By.xpath("//*[@id=\"stationListArea\"]/li/label/div/div[2]"));
      btnMore.click();
      btnMore = driver.findElement(By.xpath("//*[@id=\"stationDiv\"]/div/div[3]/div/button"));
      btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}
      
      // 도착지 클릭
      btnMore = driver.findElement(By.xpath("//*[@id=\"station-arrive\"]/span"));
      btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}
      btnMore = driver.findElement(By.id("station-pos-input"));
      // 입력란에 도착지 입력
      btnMore.sendKeys(stationStop);
      btnMore = driver.findElement(By.xpath("//*[@id=\"stationListArea\"]/li/label/div/div[2]"));
      btnMore.click();
      btnMore = driver.findElement(By.xpath("//*[@id=\"stationDiv\"]/div/div[3]/div/button"));
      btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}
      
      // 열차 조회버튼 클릭
      btnMore = driver.findElement(By.xpath("//*[@id=\"sr-train-schedule-btn\"]/div/button"));
      btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}
      
      // 화면에 나온 값을 가져가기
      List<WebElement> timeElements = driver.findElements(By.cssSelector(".table-body ul.time-list li"));
      HashMap<String, Object> map = null;
      
    	for(WebElement element : timeElements){
				map = new HashMap<String, Object>();
				String train=element.findElement(By.className("train")).getText();
				String start=element.findElement(By.className("start")).getText();
				String arrive=element.findElement(By.className("arrive")).getText();
				String time=element.findElement(By.className("time")).getText();
				String price=element.findElement(By.className("price")).getText();
				map.put("train", train);
				map.put("start", start);
				map.put("arrive", arrive);
				map.put("time", time);
				map.put("price", price);
				array.add(map);
			}
      
    	// 요금조회하기 버튼을 클릭한다.(처리 안됨 - 스크린샷으로 대체)
      btnMore = driver.findElement(By.xpath("//*[@id=\"scheduleDiv\"]/div[2]/div/ul/li[1]/div/div[5]/button"));
      //System.out.println("요금 조회버튼클릭");
      btnMore.click();
      try { Thread.sleep(2000);} catch (InterruptedException e) {}
    	
      // 지정경로에 브라우저 화면 스크린샷 저장처리 (검색하여 나온 화면을 스크린샷하여 저장한다...)
  		realPath = request.getSession().getServletContext().getRealPath("/resources/data/ckeditor/");
      File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      FileUtils.copyFile(scrFile, new File(realPath + "screenshot.png"));
      
      driver.close();
      
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}
	
	//크롤링연습 처리(selenium) - 네이버 게임 목록 조회하기
	@ResponseBody
	@RequestMapping(value = "/crawling/naverGameSearch", method = RequestMethod.POST)
	public List<CrawlingVO> naverGameSearchPost(HttpServletRequest request, int page) {
		List<CrawlingVO> vos = new ArrayList<CrawlingVO>();
		try {
			String realPath = request.getSession().getServletContext().getRealPath("/resources/crawling/");
			System.setProperty("webdriver.chrome.driver", realPath + "chromedriver.exe");
			
			WebDriver driver = new ChromeDriver();
			driver.get("https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=게임");
			
			WebElement btnMore = null;
			
			Connection conn = Jsoup.connect("https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=게임");
			Document document = conn.get();
			
			Elements selects = null;
			ArrayList<String> titleVos = new ArrayList<String>();
			ArrayList<String> jangreVos = new ArrayList<String>();
			ArrayList<String> platformVos = new ArrayList<String>();
			ArrayList<String> chulsiilVos = new ArrayList<String>();
			ArrayList<String> thumbnailVos = new ArrayList<String>();
		
			for(int i=0; i<page; i++) {
				// 페이지마다 새로고침된 HTML을 가져와서 Jsoup으로 파싱
				document = Jsoup.parse(driver.getPageSource());
						
				//selects =	document.select("a.title");
				selects =	document.selectXpath("//*[@id=\"mflick\"]/div/div/div/div/strong/a");
				for(Element select : selects) {
					//titleVos.add(select.html());
					//titleVos.add("<a href='"+select.tagName("a").attribute("href")+"' target='_blank'>"+select.text()+"</a>");
					titleVos.add("<a href='https://search.naver.com/search.naver?"+select.tagName("a").attribute("href").toString().substring(select.tagName("a").attribute("href").toString().indexOf("?")+1)+"' target='_blank'>"+select.text()+"</a>");
				}
				//System.out.println();
				
				selects =	document.selectXpath("//*[@id=\"mflick\"]/div/div/div/div/dl/dd[1]");
				for(Element select : selects) {
					jangreVos.add(select.text());
				}
				selects =	document.selectXpath("//*[@id=\"mflick\"]/div/div/div/div/dl/dd[2]");
				for(Element select : selects) {
					platformVos.add(select.text());
				}
				
				selects =	document.selectXpath("//*[@id=\"mflick\"]/div/div/div/div/dl/dd[3]");
				for(Element select : selects) {
					chulsiilVos.add(select.text());
				}
				
				selects =	document.selectXpath("//*[@id=\"mflick\"]/div/div/div/div/div/a");
				for(Element select : selects) {
					thumbnailVos.add(select.html());
				}
				
				btnMore = driver.findElement(By.xpath("//*[@id=\"main_pack\"]/section[5]/div[2]/div/div/div[4]/div/a[2]"));
	      btnMore.click();
	      try { Thread.sleep(2000);} catch (InterruptedException e) {}
			}
			driver.close();
			for(int i=0; i<jangreVos.size(); i++) {
				CrawlingVO vo = new CrawlingVO();
				vo.setItem1(titleVos.get(i));
				vo.setItem2(jangreVos.get(i));
				vo.setItem3(platformVos.get(i));
				vo.setItem4(chulsiilVos.get(i));
				vo.setItem5(thumbnailVos.get(i));
				vos.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vos;
	}
	
	// 암호화 종류
	@RequestMapping(value = "/password/password", method = RequestMethod.GET)
	public String passwordGet() {
		return "study/password/password";
	}
	// 암호화(SHA-256)
	@ResponseBody
	@RequestMapping(value = "/password/sha256", method = RequestMethod.POST, produces="application/text; charset=utf8")
	public String sha256Post(String pwd) {
		UUID uid = UUID.randomUUID();
		String salt = uid.toString().substring(0,8);
		
		SecurityUtil security = new SecurityUtil();
		String encPwd = security.encryptSHA256(salt + pwd);
		
		pwd = "salt키 : " + salt + " / 암호화된 비밀번호 : " + encPwd;
		
		return pwd;
	}
	
	// aria
	@ResponseBody
	@RequestMapping(value = "/password/aria", method = RequestMethod.POST, produces="application/text; charset=utf8")
	public String ariaPost(String pwd) throws InvalidKeyException, UnsupportedEncodingException {
		UUID uid = UUID.randomUUID();
		String salt = uid.toString().substring(0,8);
		
		String encPwd = "";
		String decPwd = "";
		
		encPwd = ARIAUtil.ariaEncrypt(salt + pwd);
		decPwd = ARIAUtil.ariaDecrypt(encPwd);
		
		pwd = "salt키 : " + salt + " / 암호화된 비밀번호 : " + encPwd + " / 복호화된 비밀번호 : " + decPwd.substring(8);
		
		return pwd;
	}
	
	// BCrypt
	@ResponseBody
	@RequestMapping(value = "/password/bCryptPassword", method = RequestMethod.POST, produces="application/text; charset=utf8")
	public String bCryptPasswordPost(String pwd) {
		String encPwd = "";
		encPwd = passwordEncoder.encode(pwd);
		
		pwd = "암호화된 비밀번호 : " + encPwd;
		
		return pwd;
	}
	
	// worldcloud
	@RequestMapping(value = "/wordcloud/wordcloudForm", method = RequestMethod.GET)
	public String wordcloudFormGet() {
		return "study/wordcloud/wordcloudForm";
	}
	
	// wordcloud 연습처리1
	@ResponseBody
	@RequestMapping(value = "/wordcloud/analyzer1", method = RequestMethod.POST)
	public Map<String, Integer> analyzer1Post(String content) {
		return studyService.analyzer1(content);
	}
	
	// wordcloud 연습처리2
	@ResponseBody
	@RequestMapping(value = "/wordcloud/analyzer2", method = RequestMethod.POST)
	public Map<String, Integer> analyzer2Post(HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/study/sample.txt");
		String content = "";
		
		try (BufferedReader br = new BufferedReader(new FileReader(realPath))) { // try-catch 안에서 외부내용 가져와서 처리 가능
			String line;
			while((line = br.readLine()) != null) {
				System.out.println(line);
				content += line + " ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studyService.analyzer1(content);
	}
	
	// wordcloud 연습처리3
	@ResponseBody
	@RequestMapping(value = "/wordcloud/analyzer3", method = RequestMethod.POST)
	public Map<String, Integer> analyzer3Post(HttpServletRequest request,
			String url,
			String selector,
			String excludeWord
			) throws IOException {
		Connection conn = Jsoup.connect(url);
		Document document = conn.get();
		Elements selectors = document.select(selector);
		
		int i=0;
		String str = "";
		for(Element select : selectors) {
			i++;
			System.out.println(i+" : "+select.html());
			str += select.html()+"\n";
		}
		
		// 제외할 문자 처리하기
		if(!excludeWord.equals("") || excludeWord != null ) {
			String[] tempStrs = excludeWord.split("/");			// [특종]/[단독]
			for(int k=0; k<tempStrs.length; k++) {
				str = str.replace(tempStrs[k], "");
			}
		}
		
		// 파일로 저장하기
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/study/sample2.txt");
		
		try (FileWriter writer =new FileWriter(realPath)) { // try-catch 안에서 외부내용 가져와서 처리 가능
			writer.write(str);
			System.out.println("파일 생성 OK");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studyService.analyzer1(str);
	}
	
	// 워드클라우드 생성하여 이미지로 보관하기
	@RequestMapping(value = "/wordcloud/wordcloudShow", method = RequestMethod.GET)
	public String wordcloudShowGet(HttpServletRequest request, Model model) throws IOException, FontFormatException {
		FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
		frequencyAnalyzer.setWordFrequenciesToReturn(300);
		frequencyAnalyzer.setMinWordLength(2);
		frequencyAnalyzer.setWordTokenizer(new WhiteSpaceWordTokenizer());
		
		List<WordFrequency> wordFrequencys = frequencyAnalyzer.load(getInputStream(request.getSession().getServletContext().getRealPath("/resources/data/study/sample2.txt")));
		
		Dimension dimension = new Dimension(500,500); // 워드클라우드 크기(픽셀)
		WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT); // 단어사이의 충돌을 감지해서 최대한 조밀하게 처리할 것
		wordCloud.setPadding(2); // 단어 사이의 여백
		wordCloud.setBackground(new CircleBackground(250)); // 워드클라우드의 배경모양 결정(반지름250인 원형)
		wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1),new Color(0x408DF1),new Color(0x40AAF1),new Color(0x40C5F1),new Color(0x40D3F1),new Color(0xCCCCCC),new Color(0xD1D1D1),new Color(0xE1E2E3))); // 색상 영어 안됨(16진수 숫자로 표현) 색깔은 객체라 하나하나 만들어줘야함
		wordCloud.setFontScalar(new LinearFontScalar(10, 60)); // (최소크기, 최대크기)
		
		// 한글 폰트 설정
		Font font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getClassLoader().getResourceAsStream("fonts/NanumGothic-Bold.ttf"));
		wordCloud.setKumoFont(new KumoFont(font));
		
		wordCloud.build(wordFrequencys);
		wordCloud.writeToFile(request.getSession().getServletContext().getRealPath("/resources/data/study/wordcloud.png"));
		
		model.addAttribute("imagePath","wordcloud.png");
		
		return "study/wordcloud/wordcloudShow";
	}
	
	private InputStream getInputStream(String path) throws IOException {
		return new FileInputStream(new File(path));
	}
}
