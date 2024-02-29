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
  
  public boolean searchByUserIdAndIndex(BoardVo boardVo) throws IOException {
    SqlSession session = mvcSqlSessionFactory.getInstance().openSession();
    BoardMapper boardMapper = session.getMapper(BoardMapper.class);
    
    return boardMapper.searchByUserIdAndIndex(boardVo);
  }
  
  public BoardVo searchByIndex(String index) throws IOException {
    SqlSession session = mvcSqlSessionFactory.getInstance().openSession();
    BoardMapper boardMapper = session.getMapper(BoardMapper.class);
    
    List<BoardVo> ResultList = boardMapper.searchByIndex(index);

    if(ResultList.size() != 1){
      return null;
    }
    else {
      return ResultList.get(0);
    }
    
  }
  
  public int delete(String index) throws IOException {
    SqlSession session = mvcSqlSessionFactory.getInstance().openSession(true);
    BoardMapper boardMapper = session.getMapper(BoardMapper.class);
    
    int ret = boardMapper.delete(index);
    
    return ret;
  }
}
