<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%  
  request.setCharacterEncoding("UTF-8");

  String sessionId = (String) session.getAttribute("userId");
%>

<div style="background-color:darkseagreen; color: black; height: 20px; padding: 5px;">
  MVC 패턴 게시판 연습 프로젝트
  <div style="position: absolute; height: 20px; right: 0px; top: 0px; padding: 5px;">
    <p>
      <%if (sessionId != null) {%>
        <strong>로그인 된 아이디 : <%=sessionId%></strong>
        <a href="<%=request.getContextPath()%>/home?action=logout">로그아웃</a>
      <% }%>
    </p>
  </div>
</div>