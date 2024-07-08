package com.spring.javaclassS.service;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.spring.javaclassS.common.JavaclassProvide;
import com.spring.javaclassS.dao.StudyDAO;
import com.spring.javaclassS.vo.CrimeVO;
import com.spring.javaclassS.vo.KakaoAddressVO;
import com.spring.javaclassS.vo.PetCafeVO;
import com.spring.javaclassS.vo.QrCodeVO;
import com.spring.javaclassS.vo.UserVO;

@Service
public class StudyServiceImpl implements StudyService {
	
	@Autowired
	StudyDAO studyDAO;
	
	@Autowired
	JavaclassProvide javaclassProvide;

	@Override
	public String[] getCityStringArray(String dodo) {
		String[] strArray = new String[100];
		
		if(dodo.equals("서울")) {
			strArray[0] = "강남구";
			strArray[1] = "강북구";
			strArray[2] = "강서구";
			strArray[3] = "강동구";
			strArray[4] = "서초구";
			strArray[5] = "관악구";
			strArray[6] = "종로구";
			strArray[7] = "영등포구";
			strArray[8] = "마포구";
			strArray[9] = "동대문구";
		}
		else if(dodo.equals("경기")) {
			strArray[0] = "수원시";
			strArray[1] = "안양시";
			strArray[2] = "안성시";
			strArray[3] = "평택시";
			strArray[4] = "시흥시";
			strArray[5] = "용인시";
			strArray[6] = "성남시";
			strArray[7] = "광명시";
			strArray[8] = "김포시";
			strArray[9] = "안산시";
		}
		else if(dodo.equals("충북")) {
			strArray[0] = "청주시";
			strArray[1] = "충주시";
			strArray[2] = "제천시";
			strArray[3] = "단양군";
			strArray[4] = "음성군";
			strArray[5] = "진천군";
			strArray[6] = "괴산군";
			strArray[7] = "증평군";
			strArray[8] = "옥천군";
			strArray[9] = "영동군";
		}
		else if(dodo.equals("충남")) {
			strArray[0] = "천안시";
			strArray[1] = "아산시";
			strArray[2] = "논산시";
			strArray[3] = "공주시";
			strArray[4] = "당진시";
			strArray[5] = "서산시";
			strArray[6] = "홍성군";
			strArray[7] = "청양군";
			strArray[8] = "계룡시";
			strArray[9] = "예산군";
		}
		
//		for(String s : strArray) {
//			if(s==null) break;
//			System.out.println("s: "+s);
//		} 
		return strArray;
	}

	@Override
	public ArrayList<String> getCityArrayList(String dodo) {
		ArrayList<String> vos = new ArrayList<String>();
		if(dodo.equals("서울")) {
			vos.add("강남구");
			vos.add("강북구");
			vos.add("강서구");
			vos.add("강동구");
			vos.add("서초구");
			vos.add("관악구");
			vos.add("종로구");
			vos.add("영등포구");
			vos.add("마포구");
			vos.add("동대문구");
		}
		else if(dodo.equals("경기")) {
			vos.add("수원시");
			vos.add("안양시");
			vos.add("안성시");
			vos.add("평택시");
			vos.add("시흥시");
			vos.add("용인시");
			vos.add("성남시");
			vos.add("광명시");
			vos.add("김포시");
			vos.add("안산시");
		}
		else if(dodo.equals("충북")) {
			vos.add("청주시");
			vos.add("충주시");
			vos.add("제천시");
			vos.add("단양군");
			vos.add("음성군");
			vos.add("진천군");
			vos.add("괴산군");
			vos.add("증평군");
			vos.add("옥천군");
			vos.add("영동군");
		}
		else if(dodo.equals("충남")) {
			vos.add("천안시");
			vos.add("아산시");
			vos.add("논산시");
			vos.add("공주시");
			vos.add("당진시");
			vos.add("서산시");
			vos.add("홍성군");
			vos.add("청양군");
			vos.add("계룡시");
			vos.add("예산군");
		}
		return vos;
	}

	@Override
	public HashMap<Object, Object> getUserInfo(String name) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		if(name.equals("아톰")) {
			map.put("name", "아톰");
			map.put("mid", "atom1234");
			map.put("age", "20");
			map.put("address", "서울");
		}
		else if(name.equals("관리자")) {
			map.put("name", "관리자");
			map.put("mid", "admin");
			map.put("age", "33");
			map.put("address", "청주");
		}
		else if(name.equals("홍길동")) {
			map.put("name", "홍길동");
			map.put("mid", "hkd1234");
			map.put("age", "20");
			map.put("address", "서울");
		}
		else if(name.equals("김말숙")) {
			map.put("name", "김말숙");
			map.put("mid", "kms1234");
			map.put("age", "20");
			map.put("address", "인천");
		}
		else if(name.equals("이기자")) {
			map.put("name", "이기자");
			map.put("mid", "lkj1234");
			map.put("age", "25");
			map.put("address", "서울");
		}
		return map;
	}

	@Override
	public UserVO getUserMidSearch(String mid) {
		return studyDAO.getUserMidSearch(mid);
	}

	@Override
	public ArrayList<UserVO> getUserMidList(String mid) {
		return studyDAO.getUserMidList(mid);
	}

	@Override
	public void setSaveCrimeData(CrimeVO vo) {
		studyDAO.setSaveCrimeData(vo);
	}

	@Override
	public int setDeleteCrimeData(String year) {
		return studyDAO.setDeleteCrimeData(year);
	}

	@Override
	public ArrayList<CrimeVO> getListCrimeDate(String year) {
		return studyDAO.getListCrimeDate(year);
	}

	@Override
	public ArrayList<CrimeVO> getYearPoliceListAsc(String police) {
		return studyDAO.getYearPoliceListAsc(police);
	}

	@Override
	public ArrayList<CrimeVO> getYearPoliceListDesc(String police) {
		return studyDAO.getYearPoliceListDesc(police);
	}

	@Override
	public ArrayList<CrimeVO> getYearStatsCrime(String year, String police) {
		return studyDAO.getYearStatsCrime(year, police);
	}

	@Override
	public int fileUpload(MultipartFile fName, String mid) {
		int res = 0;
		// 파일이름 중복처리를 위해 UUID 객체 활용 (파일 이름 중복처리 자동으로 되지 않음)
		UUID uid = UUID.randomUUID();
		String oFileName = fName.getOriginalFilename();
		String sFileName = mid+"_"+uid.toString().substring(0,8)+"_"+oFileName;
		
		// 서버에 파일 올리기 (DB에는 sFileName 저장)
		try {
			writeFile(fName,sFileName);
			res = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}

	// 파일 업로드 메소드
	private void writeFile(MultipartFile fName, String sFileName) throws IOException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/fileUpload/"); // 저장이라 절대경로
		
		FileOutputStream fos = new FileOutputStream(realPath + sFileName);
		
		//fos.write(fName.getBytes());
		if(fName.getBytes().length != -1) { // 길이를 살펴서 파일이 존재하면 그때 업로드 명령
			fos.write(fName.getBytes());
		}
		fos.flush();
		fos.close();
	}

	@Override
	public int multiFileUpload(MultipartHttpServletRequest mFile) {
		int res = 0;
		
		try {
			// 여러개가 들어오면 한개 파일 타입으로 받는다
			List<MultipartFile> fileList = mFile.getFiles("fName"); // form 에서 fName으로 넘어오는 파일들
			String oFileNames = "";
			String sFileNames = "";
			int fileSizes = 0;
			
			for(MultipartFile file : fileList) {
				// System.out.println("원본파일 : "+ file.getOriginalFilename());
				String oFileName = file.getOriginalFilename();
				String sFileName = javaclassProvide.saveFileName(oFileName);
				
				javaclassProvide.writeFile(file, sFileName, "fileUpload");
				
				oFileNames += oFileName + "/";				
				sFileNames += sFileName + "/";
				fileSizes += file.getSize();
			}
			oFileNames = oFileNames.substring(0, oFileNames.length()-1);
			sFileNames = sFileNames.substring(0, sFileNames.length()-1);
			
			System.out.println("원본파일 : "+oFileNames);
			System.out.println("저장파일 : "+sFileNames);
			System.out.println("파일크기 : "+fileSizes);
			
			res = 1;
		} catch (IOException e) {e.printStackTrace();}
		return res;
	}

	// wordcloud
	@Override
	public Map<String, Integer> analyzer1(String content) {
		int wordFrequenciesToReturn = 10;
		int minWordLength = 2;
		
		Map<String, Integer> frequencyMap = new HashMap<String, Integer>(); // 구현객체로
		
		String[] words = content.split("\\s+"); // 정규식으로 띄어쓰기 표현 (+: 1개이상)
		// String[] pps = {"은","는","이","가","을","를","와","과"};
		for(String word : words) {
			if(word.length() >= minWordLength) {
				word = word.toLowerCase();
				/*
				System.out.println("변경전 : "+word);
				for(int i=0; i<pps.length; i++) {
					word.replace(pps[i], "");
					System.out.println("변경후: " +word);
				}
				*/
				frequencyMap.put(word,(frequencyMap.getOrDefault(word, 0) + 1));
			}
		}
		
		return frequencyMap.entrySet().stream()
        .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
        .limit(wordFrequenciesToReturn)
        .collect(HashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), HashMap::putAll);
	}

	@Override
	public KakaoAddressVO getKakaoAddressSearch(String address) {
		return studyDAO.getKakaoAddressSearch(address);
	}

	@Override
	public void setKakaoAddressInput(KakaoAddressVO vo) {
		studyDAO.setKakaoAddressInput(vo);
	}

	@Override
	public List<KakaoAddressVO> getKakaoAddressList() {
		return studyDAO.getKakaoAddressList();
	}

	@Override
	public int setKakaoAddressDelete(String address) {
		return studyDAO.setKakaoAddressDelete(address);
	}
	
	@Override
	public String fileCsvToMysql(MultipartFile fName) {
		String str = "";
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/study/");
		
		// 작업할 원본 csv파일 업로드(원본파일이 utf-8파일이 아니라면 메모장에서 utf-8로 다시 저장해서 업로드시켜준다.
		try {
			FileOutputStream fos;
			fos = new FileOutputStream(realPath + fName.getOriginalFilename());
			if(fName.getBytes().length != -1) {
				fos.write(fName.getBytes());
			}
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 업로드된 파일을 Line단위로 읽어와서 ','로 분리후 DB에 저장하기
		realPath = request.getSession().getServletContext().getRealPath("/resources/data/study/"+fName.getOriginalFilename());
		try {
			BufferedReader br = new BufferedReader(new FileReader(realPath));
			String line;
			int cnt = 0;
			while ((line = br.readLine()) != null) {
				//System.out.println(line);
				cnt++;
				str += cnt + " : " + line + "<br>";
				String[] pet_cafe = line.split(",");
				PetCafeVO vo = new PetCafeVO();
				int k = 0;
				vo.setPlaceName(pet_cafe[k]); k++;
				vo.setCategory(pet_cafe[k]); k++;
				vo.setSido(pet_cafe[k]); k++;
				vo.setSigungu(pet_cafe[k]); k++;
				vo.setDong(pet_cafe[k]); k++;
				vo.setLatitude(Double.parseDouble(pet_cafe[k])); k++;
				vo.setLongitude(Double.parseDouble(pet_cafe[k])); k++;
				vo.setZipNum(Integer.parseInt(pet_cafe[k])); k++;
				vo.setRdnmAddress(pet_cafe[k]); k++;
				vo.setLnmAddress(pet_cafe[k]); k++;
				vo.setHomePage(pet_cafe[k]); k++;
				vo.setClosedDay(pet_cafe[k]); k++;
				vo.setOpenTime(pet_cafe[k]); k++;
				vo.setParking(pet_cafe[k]); k++;
				vo.setPlayPrice(pet_cafe[k]); k++;
				vo.setPetOK(pet_cafe[k]); k++;
				vo.setPetSize(pet_cafe[k]); k++;
				vo.setPetLimit(pet_cafe[k]); k++;
				vo.setInPlaceOK(pet_cafe[k]); k++;
				vo.setOutPlaceOK(pet_cafe[k]); k++;
				vo.setPlaceInfo(pet_cafe[k]); k++;
				vo.setPetExtraFee(pet_cafe[k]); k=0;
				
				// DB에 저장하기
				studyDAO.setPetCafe(vo);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	@Override
	public int setCsvTableDelete(String csvTable) {
		return studyDAO.setCsvTableDelete(csvTable);
	}

	// qr code 생성하기
	@Override
	public String setQrCodeCreate(String realPath) {
		String qrCodeName = javaclassProvide.newNameCreate(2);
		String qrCodeImage = "";
		try {
			// QR코드 안의 한글 인코딩
			qrCodeImage = "생성된 QR코드명 : "+ qrCodeName;
			qrCodeImage = new String(qrCodeImage.getBytes("UTF-8"), "ISO-8859-1");
			
			// QR 코드 만들기
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeImage, BarcodeFormat.QR_CODE, 200, 200);
			
			//MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(); // 기본컬러(글자:검정 배경:흰색)
			int qrCodeColor = 0xFF0000FF; // 16진수 표현하려면 0xFF 다음 글자색 표현
			int qrCodeBackColor = 0xFFFFFFFF;
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrCodeColor,qrCodeBackColor); // 괄호 안에 (글자색, 배경색) 숫자 적으면 컬러 지정
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
			
			// 랜더링 된 QR코드 이미지를 실제 그림파일로 만들어낸다.
			ImageIO.write(bufferedImage, "png", new File(realPath+qrCodeName+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return qrCodeName;
	}

	@Override
	public String setQrCodeCreate1(String realPath, QrCodeVO vo) {
		String qrCodeName = javaclassProvide.newNameCreate(2);
		String qrCodeImage = "";
		try {
			// QR코드 안의 한글 인코딩
			qrCodeName += vo.getMid()+"_"+vo.getName()+"_"+vo.getEmail();
			qrCodeImage = "생성 날짜 : "+ qrCodeName.substring(0,4)+"년 "+qrCodeName.substring(4,6)+"월 "+qrCodeName.substring(6,8)+"일\n";
			qrCodeImage += "아이디 : "+vo.getMid()+"\n";
			qrCodeImage += "성명 : "+vo.getName()+"\n";
			qrCodeImage += "이메일 : "+vo.getEmail()+"\n";
			qrCodeImage = new String(qrCodeImage.getBytes("UTF-8"), "ISO-8859-1");
			
			// QR 코드 만들기
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeImage, BarcodeFormat.QR_CODE, 200, 200);
			
			//MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(); // 기본컬러(글자:검정 배경:흰색)
			int qrCodeColor = 0xFF0000FF; // 16진수 표현하려면 0xFF 다음 글자색 표현
			int qrCodeBackColor = 0xFFFFFFFF;
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrCodeColor,qrCodeBackColor); // 괄호 안에 (글자색, 배경색) 숫자 적으면 컬러 지정
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
			
			// 랜더링 된 QR코드 이미지를 실제 그림파일로 만들어낸다.
			ImageIO.write(bufferedImage, "png", new File(realPath+qrCodeName+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return qrCodeName;
	}

	@Override
	public String setQrCodeCreate2(String realPath, QrCodeVO vo) {
		String qrCodeName = javaclassProvide.newNameCreate(2);
		String qrCodeImage = "";
		try {
			// QR코드 안의 한글 인코딩
			qrCodeName += vo.getMoveUrl();
			qrCodeImage = vo.getMoveUrl();
			qrCodeImage = new String(qrCodeImage.getBytes("UTF-8"), "ISO-8859-1");
			
			// QR 코드 만들기
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeImage, BarcodeFormat.QR_CODE, 200, 200);
			
			//MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(); // 기본컬러(글자:검정 배경:흰색)
			int qrCodeColor = 0xFF0000FF; // 16진수 표현하려면 0xFF 다음 글자색 표현
			int qrCodeBackColor = 0xFFFFFFFF;
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrCodeColor,qrCodeBackColor); // 괄호 안에 (글자색, 배경색) 숫자 적으면 컬러 지정
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
			
			// 랜더링 된 QR코드 이미지를 실제 그림파일로 만들어낸다.
			ImageIO.write(bufferedImage, "png", new File(realPath+qrCodeName+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return qrCodeName;
	}

	@Override
	public String setQrCodeCreate3(String realPath, QrCodeVO vo) {
		String qrCodeName = javaclassProvide.newNameCreate(2);
		String qrCodeImage = "";
		try {
			// QR코드 안의 한글 인코딩
			qrCodeName += vo.getMid()+"_"+vo.getMovieName()+"_"+vo.getMovieDate()+"_"+vo.getMovieTime().replace(":", "")+"_"+vo.getMovieAdult()+"_"+vo.getMovieChild();
			qrCodeImage = "구매자 아이디 : "+vo.getMid()+"\n";
			qrCodeImage += "영화제목 : "+vo.getMovieName()+"\n";
			qrCodeImage += "상영날짜 : "+vo.getMovieDate()+"\n";
			qrCodeImage += "상영시간 : "+vo.getMovieTime()+"\n";
			qrCodeImage += "관람인원(성인) : "+vo.getMovieAdult()+"\n";
			qrCodeImage += "관람인원(소인) : "+vo.getMovieChild();
			qrCodeImage = new String(qrCodeImage.getBytes("UTF-8"), "ISO-8859-1");
			
			// QR 코드 만들기
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeImage, BarcodeFormat.QR_CODE, 200, 200);
			
			//MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(); // 기본컬러(글자:검정 배경:흰색)
			int qrCodeColor = 0xFF0000FF; // 16진수 표현하려면 0xFF 다음 글자색 표현
			int qrCodeBackColor = 0xFFFFFFFF;
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrCodeColor,qrCodeBackColor); // 괄호 안에 (글자색, 배경색) 숫자 적으면 컬러 지정
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
			
			// 랜더링 된 QR코드 이미지를 실제 그림파일로 만들어낸다.
			ImageIO.write(bufferedImage, "png", new File(realPath+qrCodeName+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return qrCodeName;
	}
	
	@Override
	public String setQrCodeCreate4(String realPath, QrCodeVO vo) {
		String qrCodeName = javaclassProvide.newNameCreate(2);
		String qrCodeImage = "";
		try {
			String strToday = qrCodeName.substring(0,qrCodeName.length()-3);
			// QR코드 안의 한글 인코딩
			qrCodeName += vo.getMid()+"_"+vo.getMovieName()+"_"+vo.getMovieDate()+"_"+vo.getMovieTime().replace(":", "")+"_"+vo.getMovieAdult()+"_"+vo.getMovieChild();
			qrCodeImage = "구매자 아이디 : "+vo.getMid()+"\n";
			qrCodeImage += "영화제목 : "+vo.getMovieName()+"\n";
			qrCodeImage += "상영날짜 : "+vo.getMovieDate()+"\n";
			qrCodeImage += "상영시간 : "+vo.getMovieTime()+"\n";
			qrCodeImage += "관람인원(성인) : "+vo.getMovieAdult()+"\n";
			qrCodeImage += "관람인원(소인) : "+vo.getMovieChild();
			qrCodeImage = new String(qrCodeImage.getBytes("UTF-8"), "ISO-8859-1");
			
			// QR 코드 만들기
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeImage, BarcodeFormat.QR_CODE, 200, 200);
			
			//MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(); // 기본컬러(글자:검정 배경:흰색)
			int qrCodeColor = 0xFF0000FF; // 16진수 표현하려면 0xFF 다음 글자색 표현
			int qrCodeBackColor = 0xFFFFFFFF;
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrCodeColor,qrCodeBackColor); // 괄호 안에 (글자색, 배경색) 숫자 적으면 컬러 지정
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
			
			// 랜더링 된 QR코드 이미지를 실제 그림파일로 만들어낸다.
			ImageIO.write(bufferedImage, "png", new File(realPath+qrCodeName+".png"));
			
			// QR코드 생성 후 DB에 저장시켜준다.
			vo.setPublishDate(strToday);
			vo.setQrCodeName(qrCodeName);
			studyDAO.setQrCodeCreate(vo);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return qrCodeName;
	}

	@Override
	public QrCodeVO getQrCodeSearch(String qrCode) {
		return studyDAO.getQrCodeSearch(qrCode);
	}
	
	/*
	@Override
	public ArrayList<CrimeVO> getListCrimeDate(int year) {
		return studyDAO.getListCrimeDate(year);
	}

	@Override
	public ArrayList<CrimeVO> getYearPoliceCheck(int year, String police, String yearOrder) {
		return studyDAO.getYearPoliceCheck(year, police, yearOrder);
	}

	@Override
	public CrimeVO getAnalyzeTotal(int year, String police) {
		return studyDAO.getAnalyzeTotal(year, police);
	}
	*/
}
