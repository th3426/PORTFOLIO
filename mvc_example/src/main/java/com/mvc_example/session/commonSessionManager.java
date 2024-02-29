package com.mvc_example.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class commonSessionManager {
  String sessionId;
  String sessionIndex;
  HttpSession session;
  public commonSessionManager() {
  }
  
  // 세션 userId 서버 측 로그
  public void loggingSessionUserId(HttpServletRequest req){
    session = req.getSession();
    sessionId = (String) session.getAttribute("userId");
    
    if (sessionId == null || sessionId == "") {
      System.out.println("[checkSession] 세션 데이터 없음");
    }
    else{
      System.out.println("[checkSession] 세션 데이터 있음 : " + sessionId);
    }
  }
  
  // 세션 userId 존재 여부
  public boolean existSession(HttpServletRequest req){
    session = req.getSession();
    sessionId = (String) session.getAttribute("userId");
    
    System.out.println("[checkSession] current sessionId : [" + sessionId + "]");
    
    if (sessionId == null || sessionId == "") {
      // 세션 데이터 없는 경우
      return false;
    }
    else{
      // 세션 데이터 있는 경우
      return true;
    }
  }
  
  // 세션 userId 반환
  public String getSessionId(HttpServletRequest req){
    session = req.getSession();
    sessionId = (String) session.getAttribute("userId");
    
    return  sessionId;
  }
  
  public String getSessionIndex(HttpServletRequest req){
    session = req.getSession();
    sessionIndex = session.getAttribute("currentIndex").toString();
    
    return  sessionIndex;
  }
  
  // 세션 userId 속성 제거 및 세션 종료
  public void removeSession(HttpServletRequest req){
    session = req.getSession();
    session.removeAttribute("userId");
    session.invalidate();
  }
}
