package com.mvc_example.controller;

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
    System.out.println("==== doLogin Servlet ====");
    String userId = req.getParameter("UserId");
    String userPw = req.getParameter("UserPw");
    
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
      RequestDispatcher dispatcher = req.getRequestDispatcher("/board");
      HttpSession session = req.getSession();
      session.setAttribute("userId", userId);
      dispatcher.forward(req, resp);
    } else {
      this.make_alert("濡쒓렇�씤 �떎�뙣", resp);
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
