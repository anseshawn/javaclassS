package com.spring.javaclassS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javaclassS.vo.DbOptionVO;
import com.spring.javaclassS.vo.DbProductVO;

public interface DbShopDAO {

	public DbProductVO getCategoryMainOne(@Param("categoryMainCode") String categoryMainCode, @Param("categoryMainName") String categoryMainName);

	public int setCategoryMainInput(@Param("vo") DbProductVO vo);

	public List<DbProductVO> getCategoryMain();

	public DbProductVO getCategoryMiddleOne(@Param("vo") DbProductVO vo);

	public int setCategoryMainDelete(@Param("categoryMainCode") String categoryMainCode);

	public int setCategoryMiddleInput(@Param("vo") DbProductVO vo);

	public List<DbProductVO> getCategoryMiddle();

	public DbProductVO getCategorySubOne(@Param("vo") DbProductVO vo);

	public int setCategoryMiddleDelete(@Param("categoryMiddleCode") String categoryMiddleCode);

	public List<DbProductVO> getCategoryMiddleName(@Param("categoryMainCode") String categoryMainCode);

	public List<DbProductVO> getCategorySub();

	public int setCategorySubInput(@Param("vo") DbProductVO vo);

	public DbProductVO getCategoryProductName(@Param("vo") DbProductVO vo);

	public int setCategorySubDelete(@Param("categorySubCode") String categorySubCode);

	public DbProductVO getProductMaxIdx();

	public int setDbProductInput(@Param("vo") DbProductVO vo);

	public List<DbProductVO> getSubTitle();

	public List<DbProductVO> getDbShopList(@Param("part") String part, @Param("mainPrice") String mainPrice);

	public List<DbProductVO> getCategorySubName(@Param("categoryMainCode") String categoryMainCode, @Param("categoryMiddleCode") String categoryMiddleCode);

	public DbProductVO getDbShopProduct(@Param("idx") int idx);

	public List<DbOptionVO> getDbShopOption(@Param("idx") int idx);

	public DbProductVO getProductInfor(@Param("productName") String productName);

	public List<DbOptionVO> getOptionList(@Param("productIdx") int productIdx);

	public List<DbProductVO> getCategoryProductNameAjax(@Param("categoryMainCode") String categoryMainCode, @Param("categoryMiddleCode") String categoryMiddleCode,	@Param("categorySubCode") String categorySubCode);

	public int getOptionSame(@Param("productIdx") int productIdx, @Param("optionName") String optionName);

	public int setDbOptionInput(@Param("vo") DbOptionVO vo);

	public int setOptionDelete(@Param("idx") int idx);

	public DbProductVO getCategoryProductNameOne(@Param("productName") String productName);

	public DbProductVO getCategoryProductNameOneVO(@Param("vo") DbProductVO imsiVO);

}
