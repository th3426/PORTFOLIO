package com.mvc_example.controller;

import com.mvc_example.model.dao.UserDao;
import com.mvc_example.model.vo.UserVo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    System.out.println("==== signUpServlet : doPost ====");
    
    req.setCharacterEncoding("UTF-8");
    
    String REGEXP_VALID_INPUT = "^[0-9a-zA-Z]*$";
  
    String new_userId = req.getParameter("userId");
    String new_userPw_1st = req.getParameter("userPw");
    String new_userPw_2nd = req.getParameter("confirmPw");
    
    String btn = req.getParameter("submit");
    
    System.out.println("input id : " + new_userId);
    System.out.println("input pw : [" + new_userPw_1st + "]");
    System.out.println("input pw2 : [" + new_userPw_2nd + "]");
    System.out.println("btn name : [" + btn + "]");
    
    if (btn.equals("회원가입")) {
      if (new_userId.isEmpty() || new_userPw_1st.isEmpty() || new_userPw_2nd.isEmpty()) {
        make_alert("회원가입에 실패했습니다 (빈칸 있음)", resp);
      }
      
      if (!Pattern.matches(REGEXP_VALID_INPUT, new_userId) || !Pattern.matches(REGEXP_VALID_INPUT, new_userPw_1st) || !Pattern.matches(REGEXP_VALID_INPUT, new_userPw_2nd)) {
        make_alert("회원가입에 실패했습니다 (유효하지 않은 문자)", resp);
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
        System.out.println("duplicated log  : " + ret);
        
        if (ret == 1) {
          this.make_alert("회원가입에 실패했습니다 (null 문자)", resp);
        }
        else {
          ret = userDao.enrollUser(userVo);
          if (ret == 1) {
            make_alert("회원가입에 성공했습니다.", resp);
          }
          else {
            make_alert("회원가입에 실패했습니다 (DB insert 실패)", resp);
          }
        }
      }
      else {
        this.make_alert("회원가입에 실패했습니다 (2차 비밀번호 불일치)", resp);
      }
    }
  }
  
  protected void make_alert(String message, HttpServletResponse response) throws IOException {
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    
    PrintWriter out = response.getWriter();
    
    if (message.contains("성공")) {
      out.println("<script>alert('" + message + "'); location.href='/mvc_example/home?action=login'; </script>");
    }
    else {
      out.println("<script>alert('" + message + "'); history.go(-1); </script>");
    }
    
    out.flush();
    out.close();
  }
}