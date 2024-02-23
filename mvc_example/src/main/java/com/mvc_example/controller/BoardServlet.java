package com.mvc_example.controller;

import com.mvc_example.model.dao.BoardDao;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/board"})
public class BoardServlet extends HttpServlet {
  public BoardServlet() {
  }
  
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    System.out.println("board Servlet doget");
    
    BoardDao boardDao = new BoardDao();
    
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
}