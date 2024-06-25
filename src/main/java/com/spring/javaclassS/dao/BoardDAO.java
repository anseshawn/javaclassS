package com.spring.javaclassS.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.spring.javaclassS.vo.BoardVO;

public interface BoardDAO {

	public ArrayList<BoardVO> getBoardList(); // DAO에선 오버로딩이 가능하지만 어차피 mapper에선 아이디 하나밖에 못씀

	public int setBoardInput(@Param("vo") BoardVO vo);

	public BoardVO getBoardContent(@Param("idx") int idx);

	public int totRecCnt();

	public ArrayList<BoardVO> getBoardList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize);

	public void setReadNumPlus(@Param("idx") int idx);

	public BoardVO getPreNexSearch(@Param("idx") int idx, @Param("str") String str);

}
