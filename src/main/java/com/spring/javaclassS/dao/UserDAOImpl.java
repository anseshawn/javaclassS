package com.spring.javaclassS.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.javaclassS.vo.UserVO;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {
	
	@Autowired
	SqlSession sqlSession;

	@Override
	public List<UserVO> getUserList() {
		List<UserVO> vos = sqlSession.selectList("userNS.getUserList"); // 매퍼에서 select로 자료를 읽어서 가져온다(sqlSessionFactory) 
		return vos;
	}

	@Override
	public int setUserDelete(int idx) {
		return sqlSession.delete("userNS.setUserDelete", idx);
	}

	@Override
	public int setUserInputOk(UserVO vo) {
		return sqlSession.insert("userNS.setUserInputOk", vo);
	}

	@Override
	public List<UserVO> getUserSearchList(String midSearch) {
		return sqlSession.selectList("userNS.getUserSearchList", midSearch);
	}

	@Override
	public int setUserUpdateOk(UserVO vo) {
		return sqlSession.update("userNS.setUserUpdateOk", vo);
	}

}
