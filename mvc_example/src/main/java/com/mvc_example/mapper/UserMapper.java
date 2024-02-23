package com.mvc_example.mapper;

import com.mvc_example.model.vo.UserVo;
import java.util.List;

public interface UserMapper {
  Integer checkDuplicatedUser(String var1);
  
  Integer enrollUser(UserVo var1);
  
  List<UserVo> loginUser(UserVo var1);
}
