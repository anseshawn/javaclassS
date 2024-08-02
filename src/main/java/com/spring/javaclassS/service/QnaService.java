package com.spring.javaclassS.service;

import java.util.List;

import com.spring.javaclassS.vo.QnaVO;

public interface QnaService {

	public String getEmail(String mid);

	public int getMaxIdx();

	public void qnaInputOk(QnaVO vo);

	public List<QnaVO> getQnaList(int startIndexNo, int pageSize);

	public QnaVO getQnaContent(int idx);

	public void setQnaDelete(int idx);

	public void setQnaContentUpdate(QnaVO vo);

	public List<QnaVO> getQnaIdxCheck(int qnaIdx);

	public void setQnaCheckUpdate(int idx, String title);

	public void qnaAdminInputOk(int qnaIdx);

	public void qnaAdminAnswerUpdateOk(int qnaIdx);

}
