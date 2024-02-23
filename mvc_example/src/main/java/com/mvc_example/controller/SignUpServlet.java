package com.mvc_example.controller;

import com.mvc_example.model.dao.UserDao;
import com.mvc_example.model.vo.UserVo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpServlet extends HttpServlet {
  
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    System.out.println("==== signUp Servlet ====");
    String REGEXP_VALID_INPUT = "^[0-9a-zA-Z]*$";
    String new_userId = req.getParameter("UserId");
    String new_userPw_1st = req.getParameter("UserPw");
    String new_userPw_2nd = req.getParameter("confirmPw");
    
    String btn = req.getParameter("button");
    
    System.out.println("input id : " + new_userId);
    System.out.println("input pw : [" + new_userPw_1st + "]");
    System.out.println("input pw2 : [" + new_userPw_2nd + "]");
    
    if (btn.equals("create")) {
      if (new_userId.isEmpty() || new_userPw_1st.isEmpty() || new_userPw_2nd.isEmpty()) {
        make_alert("怨꾩젙 �깮�꽦 �떎�뙣 (怨듬갚 議댁옱)", resp);
      }
      
      if (!Pattern.matches(REGEXP_VALID_INPUT, new_userId) || !Pattern.matches(REGEXP_VALID_INPUT, new_userPw_1st) || !Pattern.matches(REGEXP_VALID_INPUT, new_userPw_2nd)) {
        make_alert("怨꾩젙 �깮�꽦 �떎�뙣 (�쑀�슚�븯吏� �븡�� 媛�)", resp);
      }
      
      boolean confirm_state;
      
      if (new_userPw_1st.equals(new_userPw_2nd)) {
        System.out.println("confirm ok");
        confirm_state = true;
      }
      else {
        confirm_state = false;
      }
      
      if (confirm_state) {
        UserDao userDao = new UserDao();
        UserVo userVo = new UserVo();
        
        userVo.setUserId(new_userId);
        userVo.setUserPw(new_userPw_1st);
        
        int ret = userDao.checkDuplicatedUser(new_userId);
        
        if (ret == 1) {
          this.make_alert("怨꾩젙 �깮�꽦 �떎�뙣 (�븘�씠�뵒 以묐났)", resp);
        }
        else {
          ret = userDao.enrollUser(userVo);
          if (ret == 1) {
            make_alert("怨꾩젙 �깮�꽦 �꽦怨�", resp);
          }
          else {
            make_alert("怨꾩젙 �깮�꽦 �떎�뙣 (荑쇰━ �닔�뻾 遺덇�)", resp);
          }
        }
      }
      else {
        this.make_alert("怨꾩젙 �깮�꽦 �떎�뙣 (鍮꾨�踰덊샇 遺덉씪移�)", resp);
      }
    }
    
  }
  
  protected void make_alert(String message, HttpServletResponse response) throws IOException {
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    
    PrintWriter out = response.getWriter();
    
    if (message.contains("�꽦怨�")) {
      out.println("<script>alert('" + message + "'); location.href='/mvc_example/home?action=login'; </script>");
    }
    else {
      out.println("<script>alert('" + message + "'); history.go(-1); </script>");
    }
    
    out.flush();
    out.close();
  }
}