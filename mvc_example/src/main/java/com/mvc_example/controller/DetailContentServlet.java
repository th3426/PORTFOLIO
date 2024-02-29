package com.mvc_example.controller;

import com.mvc_example.model.dao.BoardDao;
import com.mvc_example.model.vo.BoardVo;
import com.mvc_example.session.commonSessionManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/detail")
public class DetailContentServlet extends HttpServlet {
  commonSessionManager commonSessionManager = new commonSessionManager();
  
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html; charset=UTF-8");
    
    System.out.println("==== detailContentServlet : doGet ====");
    
    String action = req.getParameter("action");
    String index = req.getParameter("index");
    String page = null;
    
    System.out.println("action : " + action);
    System.out.println("index : " + index);
    
    BoardDao boardDao = new BoardDao();
    
    if (action.equals("view")) {
      // 게시물 상세 보기 요청
      
      // 해당 index 에 해당하는 게시물 vo return
      BoardVo boardVo = boardDao.searchByIndex(index);
      if (boardVo != null) {
        req.setAttribute("boardVoResult", boardVo);
      }
      else {
        System.out.println("boardVo is null");
      }
      
      RequestDispatcher requestDispatcher = req.getRequestDispatcher("/view/detailContent.jsp");
      requestDispatcher.forward(req, resp);
    }
    else {
      // ???
    }
  }
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html; charset=UTF-8");
    
    System.out.println("==== detailContentServlet : doPost ====");
    
    String btn = req.getParameter("submit");
    BoardDao boardDao = new BoardDao();
    
    System.out.println("btn : " + btn);
    
    if (btn == null) {
      System.out.println("btn is null");
    }
    else if (btn.equals("뒤로가기")) {
      System.out.println("go back");
      
      resp.setCharacterEncoding("UTF-8");
      resp.setContentType("text/html; charset=UTF-8");
      
      PrintWriter out = resp.getWriter();
      
      // 뒤로가기
      out.println("<script> history.go(-2); </script>");
      
      out.flush();
      out.close();
      
    }
    else if (btn.equals("글 삭제")) {
      System.out.println("do delete");
      
      System.out.println(commonSessionManager.getSessionIndex(req));
      
      String index = commonSessionManager.getSessionIndex(req);
      
      int ret = boardDao.delete(index);
      System.out.println("delete result : " + ret );
      
      resp.setCharacterEncoding("UTF-8");
      resp.setContentType("text/html; charset=UTF-8");
      
      PrintWriter out = resp.getWriter();
      
      out.println("<script>alert('" + "삭제 완료" + "'); location.href='/mvc_example/board'; </script>");
      
      out.flush();
      out.close();
      
    }
    else {
    
    }
  }
}
