package com.spring.javaclassS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javaclassS.dao.QnaDAO;
import com.spring.javaclassS.vo.QnaVO;

@Service
public class QnaServiceImpl implements QnaService {

	@Autowired
	QnaDAO qnaDAO;

	@Override
	public String getEmail(String mid) {
		return qnaDAO.getEmail(mid);
	}

	@Override
	public int getMaxIdx() {
		return qnaDAO.getCountIdx() == 0 ? 0 : qnaDAO.getMaxIdx();
	}

	@Override
	public void qnaInputOk(QnaVO vo) {
		qnaDAO.qnaInputOk(vo);
	}

	@Override
	public List<QnaVO> getQnaList(int startIndexNo, int pageSize) {
		return qnaDAO.getQnaList(startIndexNo, pageSize);
	}

	@Override
	public QnaVO getQnaContent(int idx) {
		return qnaDAO.getQnaContent(idx);
	}

	@Override
	public void setQnaDelete(int idx) {
		qnaDAO.setQnaDelete(idx);
	}

	@Override
	public void setQnaContentUpdate(QnaVO vo) {
		qnaDAO.setQnaContentUpdate(vo);
	}

	@Override
	public List<QnaVO> getQnaIdxCheck(int qnaIdx) {
		return qnaDAO.getQnaIdxCheck(qnaIdx);
	}

	@Override
	public void setQnaCheckUpdate(int idx, String title) {
		qnaDAO.setQnaCheckUpdate(idx, title);
	}

	@Override
	public void qnaAdminInputOk(int qnaIdx) {
		qnaDAO.qnaAdminInputOk(qnaIdx);
	}

	@Override
	public void qnaAdminAnswerUpdateOk(int qnaIdx) {
		qnaDAO.qnaAdminAnswerUpdateOk(qnaIdx);
	}
	
}
