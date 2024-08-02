package com.spring.javaclassS.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class JavaclassProvide {
	
	@Autowired
	JavaMailSender mailSender;
	
	// urlPath에 파일 저장하는 메소드 : (업로드 파일명, 저장할 파일명, 저장 경로)
	public void writeFile(MultipartFile fName, String sFileName, String urlPath) throws IOException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/"+urlPath+"/"); // 저장이라 절대경로
		
		FileOutputStream fos = new FileOutputStream(realPath + sFileName);
		
		if(fName.getBytes().length != -1) { // 길이를 살펴서 파일이 존재하면 그때 업로드 명령
			fos.write(fName.getBytes());
		}
		fos.flush();
		fos.close();
	}
	
	public void deleteFile(String photo, String urlPath) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/"+urlPath+"/");
		
		File file = new File(realPath + photo);
		if(file.exists()) file.delete();
	}

	// 파일 이름 변경하기(중복방지)
	public String saveFileName(String oFileName) {
		String fileName = "";
		
		Calendar cal = Calendar.getInstance(); // 싱글톤 객체랑 같은 형식(객체 생성 x)
		fileName += cal.get(Calendar.YEAR);
		fileName += cal.get(Calendar.MONTH)+1;
		fileName += cal.get(Calendar.DATE);
		fileName += cal.get(Calendar.HOUR_OF_DAY);
		fileName += cal.get(Calendar.MINUTE);
		fileName += cal.get(Calendar.SECOND);
		fileName += cal.get(Calendar.MILLISECOND);
		fileName += "_" + oFileName;
		
		return fileName;
	}
	
	// 임시 비밀번호 메일 전송하기(스케줄러를 통한 메일 전송)
	public String mailSend(String email, String title, String pwd) throws MessagingException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String content = "";
		
		// 메일 전송을 위한 객체 : MimeMessage(), MimeMessageHelper()
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
		
		// 메일보관함에 작성한 메세지들의 정보를 모두 저장시킨후 작업처리...
		messageHelper.setTo(email);			// 받는 사람 메일 주소
		messageHelper.setSubject(title);	// 메일 제목
		messageHelper.setText(content);		// 메일 내용
		
		// 메세지 보관함의 내용(content)에 , 발신자의 필요한 정보를 추가로 담아서 전송처리한다.
		content = content.replace("\n", "<br>");
		//content += "<br><hr><h3> 임시비밀번호 : "+pwd+"</h3><hr><br>";
		content += "<br><hr><h3>"+pwd+"</h3><hr><br>";
		content += "<p><img src=\"cid:main.jpg\" width='500px'></p>";
		content += "<p>방문하기 : <a href='http://49.142.157.251:9090/cjgreen'>javaclass</a></p>";
		content += "<hr>";
		messageHelper.setText(content, true);
		
		// 본문에 기재될 그림파일의 경로를 별도로 표시시켜준다. 그런후 다시 보관함에 저장한다.
		FileSystemResource file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/main.jpg"));
		messageHelper.addInline("main.jpg", file);
		
		// 메일 전송하기
		mailSender.send(message);
		
		return "1";
	}
	
	public String newNameCreate(int len) {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		String newName = sdf.format(today);
		newName += RandomStringUtils.randomAlphanumeric(len) + "_";
		return newName;
	}
	
	// oriFilePath경로에 있는 파일을 copyFilePath경로로 복사시켜주기.
  @SuppressWarnings("unused")
	public void fileCopyCheck(String oriFilePath, String copyFilePath) {
    File oriFile = new File(oriFilePath);
    File copyFile = new File(copyFilePath);

    try {
      FileInputStream  fis = new FileInputStream(oriFile);
      FileOutputStream fos = new FileOutputStream(copyFile);

      byte[] buffer = new byte[2048];
      int count = 0;
      while((count = fis.read(buffer)) != -1) {
        fos.write(buffer, 0, count);
      }
      fos.flush();
      fos.close();
      fis.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
	// 1.공통으로 사용하는 ckeditor폴더(aFlag)에 임시그림파일 저장후 실제 저장할폴더(qna)로 복사하기(사용될 실제 파일이 저장될 경로를 bFlag에 받아온다.)
  // 2.실제로 저장된 폴더(qna(aFlag))에서, 공통으로 사용하는 ckeditor폴더(bFlag)에 그림파일을 복사하기
	public void imgCheck(String content, String aFlag, String bFlag) {
		//      0         1         2     2   33        4         5         6
		//      01234567890123456789012345678901234567890123456789012345678901234567890
		// <img src="/javaclassS/data/ckeditor/240111121324_green2209J_06.jpg" style="height:967px; width:1337px" /></p>
		// <img src="/javaclassS/data/qna/240111121324_green2209J_06.jpg" style="height:967px; width:1337px" /></p>
    // content안에 그림파일이 존재할때만 작업을 수행 할수 있도록 한다.(src="/_____~~)
		if(content.indexOf("src=\"/") == -1) return;
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		//String realPath = request.getSession().getServletContext().getRealPath("/resources/data/ckeditor/");
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		
		int position = 0;
		if(aFlag.equals("ckeditor")) position = 31;
		else if(aFlag.equals("qna")) position = 22;
		String nextImg = content.substring(content.indexOf("src=\"/") + position);
		boolean sw = true;
		
		while(sw) {
			String imgFile = nextImg.substring(0, nextImg.indexOf("\""));
			
			String origFilePath = realPath + aFlag + "/" + imgFile;
			String copyFilePath = realPath + bFlag + "/" + imgFile;
			
			fileCopyCheck(origFilePath, copyFilePath);  // __폴더에 파일을 복사하고자 한다.
			
			if(nextImg.indexOf("src=\"/") == -1) {
				sw = false;
			}
			else {
				nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
			}
		}
	}
	
	// 지정된 경로 아래의 파일들을 반복해서 삭제처리한다.
	public void imagesDelete(String content, String flag) {
		//      0         1         2         3         4         5         6
		//      01234567890123456789012345678901234567890123456789012345678901234567890
		// <img src="/javaclassS/data/qna/230111121324_green2209J_06.jpg" style="height:967px; width:1337px" /></p>
		// content안에 그림파일이 존재할때만 작업을 수행 할수 있도록 한다.(src="/_____~~)
		if(content.indexOf("src=\"/") == -1) return;
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/"+flag+"/");
		
		int position = 0;
		if(flag.equals("qna")) position = 26;
		
		String nextImg = content.substring(content.indexOf("src=\"/") + position);
		boolean sw = true;
		
		while(sw) {
			String imgFile = nextImg.substring(0, nextImg.indexOf("\""));	// 그림파일명 꺼내오기
			
			String origFilePath = realPath + imgFile;
			
			// ____폴더에 파일을 삭제하고자 한다.
			File delFile = new File(origFilePath);
			if(delFile.exists()) delFile.delete();
			
			if(nextImg.indexOf("src=\"/") == -1) {
				sw = false;
			}
			else {
				nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
			}
		}
	}
}
