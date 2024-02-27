package com.mvc_example.controller;

import com.mvc_example.model.dao.BoardDao;
import com.mvc_example.model.vo.BoardVo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class WriteServlet extends HttpServlet {
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    
    String action = req.getParameter("action");
    String index = req.getParameter("index");
    String page = null;
    
    
    
    if (action.equals("write")) {
      page = "/view/write.jsp";
      page_forward(page, req, resp);
    }
    else {
      page = "/view/error.jsp";
      page_forward(page, req, resp);
    }
  }
  
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("text/html; charset=UTF-8");
    
    String title = req.getParameter("title");
    String content = req.getParameter("content");
    
    System.out.println("title : " + title);
    System.out.println("content : " + content);
    
    BoardDao boardDao = new BoardDao();
    BoardVo boardVo = new BoardVo();
    
    boardVo.setTitle(title);
    boardVo.setContent(content);
    
    boardVo.setWriter(req.getParameter("userId"));
    
    if(boardVo.getWriter() == null || boardVo.getWriter() == ""){
      System.out.println("세션 아이디 없음");
    }
    
    try {
      System.out.println("vo title : " + boardVo.getTitle());
      System.out.println("vo content : " + boardVo.getContent());
      System.out.println("vo writer : " + boardVo.getWriter());
      
      if (boardDao.insertBoard(boardVo) == 1) {
        System.out.println("BOARD INSERT SUCCESS");
        make_alert("작성이 완료되었습니다.", resp);
      }
      else {
        System.out.println("BOARD INSERT FAILED");
        make_alert("작성에 실패했습니다.", resp);
      }
      
    } catch (SQLException var9) {
      throw new RuntimeException(var9);
    }
  }
  
  protected void page_forward(String page, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    RequestDispatcher dispatcher = req.getRequestDispatcher(page);
    dispatcher.forward(req, resp);
  }
  
  protected void make_alert(String message, HttpServletResponse response) throws IOException {
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    
    PrintWriter out = response.getWriter();
    
    if (message.contains("성공")) {
      out.println("<script>alert('" + message + "'); history.go(-2); history.go(); </script>");
    }
    else {
      out.println("<script>alert('" + message + "'); history.go(-2); history.go(); </script>");
    }
    
    out.flush();
    out.close();
  }
}