package com.mvc_example.model.vo;

public class UserVo {
  private String userId;
  private String userPw;
  
  public UserVo() {
  }
  
  public String getUserId() {
    return this.userId;
  }
  
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  public String getUserPw() {
    return this.userPw;
  }
  
  public void setUserPw(String userPw) {
    this.userPw = userPw;
  }
}
