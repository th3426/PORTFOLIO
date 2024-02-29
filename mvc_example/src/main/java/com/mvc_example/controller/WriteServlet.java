package com.mvc_example.controller;

import com.mvc_example.model.dao.BoardDao;
import com.mvc_example.model.vo.BoardVo;
import com.mvc_example.session.commonSessionManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/write")
public class WriteServlet extends HttpServlet {
  com.mvc_example.session.commonSessionManager commonSessionManager = new commonSessionManager();
  
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    System.out.println("==== writeServlet : doGet ====");
    
    String page = null;
    
    page = "/view/write.jsp";
    page_forward(page, req, resp);
  }
  
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    System.out.println("==== writeServlet : doPost ====");
    
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html; charset=UTF-8");
    
    BoardDao boardDao = new BoardDao();
    BoardVo boardVo = new BoardVo();
    
    if (!commonSessionManager.existSession(req)) {
      make_alert("세션이 만료되었습니다.", resp);
    }
    
    String title = req.getParameter("title");
    String content = req.getParameter("content");
    
    boardVo.setTitle(title);
    boardVo.setContent(content);
    boardVo.setWriter(commonSessionManager.getSessionId(req));
    
    System.out.println("vo title : " + boardVo.getTitle());
    System.out.println("vo content : " + boardVo.getContent());
    System.out.println("vo writer : " + boardVo.getWriter());
    
    // 세션이 만료되어 세션의 userId를 가져오지 못하는 경우는 위에서 체크했기 때문에 체크하지 않는다
    if (boardVo.getTitle() == null || boardVo.getContent() == null ||
            boardVo.getTitle() == "" || boardVo.getContent() == "") {
      make_alert("공백은 작성할 수 없습니다.", resp);
    }
    
    try {
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
    
    // 작성이 완료된 경우 and 세션이 만료된 경우  >> /board 로 이동
    // 작성에 실패한 경우                         >> /write로 재이동
    if (message.contains("완료") || message.contains("만료")) {
      out.println("<script>alert('" + message + "'); location.href='/mvc_example/board'; </script>");
    }
    else {
      out.println("<script>alert('" + message + "'); history.go(-1); </script>");
    }
    
    out.flush();
    out.close();
  }
}