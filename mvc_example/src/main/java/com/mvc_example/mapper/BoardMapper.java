package com.mvc_example.mapper;

import com.mvc_example.model.vo.BoardVo;
import java.util.HashMap;
import java.util.List;

public interface BoardMapper {
  List<HashMap<String, Object>> getBoardList();
  
  Integer insert(BoardVo var1);
}
