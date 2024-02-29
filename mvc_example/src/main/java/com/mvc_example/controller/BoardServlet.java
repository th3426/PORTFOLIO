package com.mvc_example.controller;

import com.mvc_example.session.commonSessionManager;
import com.mvc_example.model.dao.BoardDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/board"})
public class BoardServlet extends HttpServlet {
  commonSessionManager commonSessionManager = new commonSessionManager();
  
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    System.out.println("==== boardServlet : doGet ====");
    this.doPost(req, resp);
  }
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    System.out.println("==== boardServlet : doPost ====");
    req.setCharacterEncoding("UTF-8");
    resp.setCharacterEncoding("UTF-8");
    
    commonSessionManager.loggingSessionUserId(req);
    BoardDao boardDao = new BoardDao();
    
    String btn = req.getParameter("submit");
    System.out.println("btn : [" + btn + "]");
    
    if(btn == null || btn == ""){
      List<HashMap<String, Object>> boardList = boardDao.getBoardList();
      
      if (!boardList.isEmpty()) {
        System.out.println("board size : " + boardList.size());
        req.setAttribute("boardList", boardList);
      }
      else {
        System.out.println("board is empty");
      }
      
      req.getRequestDispatcher("/view/board.jsp").forward(req, resp);
    }
    else {
      if(commonSessionManager.existSession(req)){
        resp.sendRedirect(req.getContextPath()+"/write");
      }
      make_alert("로그인 세션이 없습니다.", resp);
    }
  }
  
  protected void make_alert(String message, HttpServletResponse response) throws IOException {
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    
    PrintWriter out = response.getWriter();
    
    // 새로고침
    out.println("<script>alert('" + message + "'); history.go(-1); </script>");
    
    out.flush();
    out.close();
  }
}