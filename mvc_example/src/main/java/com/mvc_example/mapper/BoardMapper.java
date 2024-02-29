package com.mvc_example.mapper;

import com.mvc_example.model.vo.BoardVo;

import java.util.HashMap;
import java.util.List;

public interface BoardMapper {
  List<HashMap<String, Object>> getBoardList();
  
  Integer insert(BoardVo boardVo);
  
  Boolean searchByUserIdAndIndex(BoardVo boardVo);
  
  List<BoardVo> searchByIndex(String index);
  
  int delete(String index);
}
