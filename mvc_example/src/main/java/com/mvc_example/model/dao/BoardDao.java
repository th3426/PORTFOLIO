package com.mvc_example.model.dao;

import com.mvc_example.model.mvcSqlSessionFactory;
import com.mvc_example.mapper.BoardMapper;
import com.mvc_example.model.vo.BoardVo;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.SqlSession;


public class BoardDao {
  public int insertBoard(BoardVo boardVo) throws SQLException, IOException {
    SqlSession session = mvcSqlSessionFactory.getInstance().openSession(true);
    BoardMapper boardMapper = session.getMapper(BoardMapper.class);
    
    int ret = boardMapper.insert(boardVo);
    
    return ret;
  }
  
  public List<HashMap<String, Object>> getBoardList() throws IOException {
    SqlSession session = mvcSqlSessionFactory.getInstance().openSession();
    BoardMapper boardMapper = session.getMapper(BoardMapper.class);
    
    return boardMapper.getBoardList();
  }
}
