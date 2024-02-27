package com.mvc_example.controller;

import com.mvc_example.session.commonSessionManager;
import com.mvc_example.model.dao.UserDao;
import com.mvc_example.model.vo.UserVo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet({"/doLogin"})
public class DoLoginServlet extends HttpServlet {
  
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    make_alert("get 방식 지원 안함", resp);
  }
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    System.out.println("==== doLogin Servlet (post) ====");
    
    req.setCharacterEncoding("UTF-8");
    
    commonSessionManager commonSessionManager = new commonSessionManager();
    commonSessionManager.getSessionUserId(req);
    
    String userId = req.getParameter("userId");
    String userPw = req.getParameter("userPw");
    
    if(userId == null || userId == "" || userPw == null || userPw == ""){
      System.out.println("계정 혹은 비밀번호 : null");
      make_alert("비정상 접근 (계정 또는 비밀번호 : null)", resp);
    }
    
    System.out.println("user id : " + userId);
    System.out.println("user pw : " + userPw);
    
    UserDao userDao = new UserDao();
    UserVo userVo = new UserVo();
    
    userVo.setUserId(userId);
    userVo.setUserPw(userPw);
    
    int ret = userDao.loginUser(userVo);
    
    System.out.println("ret : " + ret);
    
    if (ret != 0) {
      System.out.println("login SUCCESS");
      
      // 로그인 성공 시 세션 생성 (req.getSession)
      HttpSession session = req.getSession();
      session.setAttribute("userId", userId);
      
      resp.sendRedirect(req.getContextPath()+"/board");
    } else {
      make_alert("로그인에 실패했습니다.", resp);
    }
  }
  
  protected void make_alert(String message, HttpServletResponse response) throws IOException {
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    
    PrintWriter out = response.getWriter();
    
    out.println("<script>alert('" + message + "'); history.go(-1); </script>");
    
    out.flush();
    out.close();
  }
}
