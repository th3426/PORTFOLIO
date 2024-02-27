package com.mvc_example.controller;

import com.mvc_example.session.commonSessionManager;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet({"/home"})
public class HomeServlet extends HttpServlet {
  
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String action = req.getParameter("action");
    String page = null;
    
    commonSessionManager commonSessionManager = new commonSessionManager();
    
    commonSessionManager.getSessionUserId(req);
    
    if (action == null) {
      System.out.println("go to home jsp");
      page = "/view/home.jsp";
    }
    else {
      if (action.equals("login")) {
        System.out.println("go to login jsp");
        page = "/view/login.jsp";
      }
      else if (action.equals("signUp")) {
        System.out.println("go to signUp jsp");
        page = "/view/signUp.jsp";
      }
      else if (action.equals("board")){
        System.out.println("go to board jsp");
        page = "/view/board.jsp";
      }
      else if(action.equals("logout")){
        System.out.println("로그아웃 요청");
        
        // 세션 삭제
        commonSessionManager.removeSession(req);
        commonSessionManager.getSessionUserId(req);
        
        page = "";
      }
      else {
        System.out.println("go to error jsp");
        page = "/view/error.jsp";
      }
    }
    
    RequestDispatcher dispatcher = req.getRequestDispatcher(page);
    dispatcher.forward(req, resp);
  }
  
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    super.doPost(req, resp);
  }
  
  
  @Override
  public void destroy() {
    System.out.println("home 소멸");
    super.destroy();
  }
}