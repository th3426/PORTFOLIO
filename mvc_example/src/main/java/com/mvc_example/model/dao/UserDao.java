package com.mvc_example.model.dao;

import com.mvc_example.mapper.UserMapper;
import com.mvc_example.model.mvcSqlSessionFactory;
import com.mvc_example.model.vo.UserVo;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

public class UserDao {
  public UserDao() {
  }
  
  public int checkDuplicatedUser(String userId) throws IOException {
    SqlSession session = mvcSqlSessionFactory.getInstance().openSession();
    UserMapper userMapper = session.getMapper(UserMapper.class);
    
    int ret;
    try {
      ret = userMapper.checkDuplicatedUser(userId);
    } catch (NullPointerException var6) {
      ret = 0;
    }
    
    return ret;
  }
  
  public int enrollUser(UserVo userVo) throws IOException {
    SqlSession session = mvcSqlSessionFactory.getInstance().openSession(true);
    UserMapper userMapper = session.getMapper(UserMapper.class);
    
    int ret;
    try {
      ret = userMapper.enrollUser(userVo);
    } catch (NullPointerException var6) {
      ret = 0;
    }
    
    return ret;
  }
  
  public int loginUser(UserVo userVo) throws IOException {
    SqlSession session = mvcSqlSessionFactory.getInstance().openSession();
    UserMapper userMapper = session.getMapper(UserMapper.class);
    
    int ret;
    try {
      List<UserVo> retVoList = userMapper.loginUser(userVo);
      System.out.println("retVo size : " + retVoList.size());
      
      ret = retVoList.size();
    } catch (NullPointerException var7) {
      System.out.println("NULL EXCEPTION");
      ret = 0;
    }
    
    return ret;
  }
}