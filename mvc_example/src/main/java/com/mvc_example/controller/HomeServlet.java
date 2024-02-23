package com.mvc_example.controller;

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
    HttpSession session = req.getSession();
    session.invalidate();
    
    if (action.equals("login")) {
      page = "/view/login.jsp";
    }
    else if (action.equals("signUp")) {
      page = "/view/signUp.jsp";
    }
    else {
      page = "/view/error.jsp";
    }
    
    RequestDispatcher dispatcher = req.getRequestDispatcher(page);
    dispatcher.forward(req, resp);
  }
  
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    super.doPost(req, resp);
  }
}