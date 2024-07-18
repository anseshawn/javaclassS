package com.spring.javaclassS.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javaclassS.vo.ChartVO;
import com.spring.javaclassS.vo.CrimeVO;
import com.spring.javaclassS.vo.KakaoAddressVO;
import com.spring.javaclassS.vo.PetCafeVO;
import com.spring.javaclassS.vo.QrCodeVO;
import com.spring.javaclassS.vo.TransactionVO;
import com.spring.javaclassS.vo.UserVO;

public interface StudyDAO {

	public UserVO getUserMidSearch(@Param("mid") String mid);

	public ArrayList<UserVO> getUserMidList(@Param("mid") String mid);

	public void setSaveCrimeData(@Param("vo") CrimeVO vo);

	public int setDeleteCrimeData(@Param("year") String year);

	public ArrayList<CrimeVO> getListCrimeDate(@Param("year") String year);

	public ArrayList<CrimeVO> getYearPoliceListAsc(String police);

	public ArrayList<CrimeVO> getYearPoliceListDesc(String police);

	public ArrayList<CrimeVO> getYearStatsCrime(@Param("year") String year, @Param("police") String police);

	public KakaoAddressVO getKakaoAddressSearch(@Param("address") String address);

	public void setKakaoAddressInput(@Param("vo") KakaoAddressVO vo);

	public List<KakaoAddressVO> getKakaoAddressList();

	public int setKakaoAddressDelete(@Param("address") String address);
	
	public void setPetCafe(@Param("vo") PetCafeVO vo);

	public int setCsvTableDelete(@Param("csvTable") String csvTable);

	public void setQrCodeCreate(@Param("vo") QrCodeVO vo);

	public QrCodeVO getQrCodeSearch(@Param("qrCodeName") String qrCode);

	public List<ChartVO> getRecentlyVisitCount(@Param("i") int i);

	public ArrayList<TransactionVO> getTransactionList();

	public int setTransactionUserInput(@Param("vo") TransactionVO vo);

	public ArrayList<TransactionVO> getTransactionList2();

	public void setTransactionUser1Input(@Param("vo") TransactionVO vo);

	public void setTransactionUser2Input(@Param("vo") TransactionVO vo);

	public int setTransactionUserTotalInput(@Param("vo") TransactionVO vo);
	
//	public ArrayList<CrimeVO> getListCrimeDate(@Param("year") int year);
//
//	public ArrayList<CrimeVO> getYearPoliceCheck(@Param("year") int year, @Param("police") String police, @Param("yearOrder") String yearOrder);
//
//	public CrimeVO getAnalyzeTotal(@Param("year") int year, @Param("police") String police);

}
