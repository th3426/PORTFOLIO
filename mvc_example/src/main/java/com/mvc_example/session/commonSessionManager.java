package com.mvc_example.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class commonSessionManager {
  String sessionId;
  public commonSessionManager() {
  }
  
  public void getSessionUserId(HttpServletRequest req){
    HttpSession session = req.getSession();
    sessionId = (String) session.getAttribute("userId");
    
    if (sessionId == null || sessionId == "") {
      System.out.println("[checkSession] 세션 데이터 없음");
    }
    else{
      System.out.println("[checkSession] 세션 데이터 있음 : " + sessionId);
    }
  }
  
  public void removeSession(HttpServletRequest req){
    HttpSession session = req.getSession();
    session.removeAttribute("userId");
    session.invalidate();
  }
}
