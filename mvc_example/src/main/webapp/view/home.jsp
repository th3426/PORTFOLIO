<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>
    MVC 게시판 예제 [HOME]
  </title>
</head>
<body>
  <jsp:include page ="/view/head.jsp" />

  <h2>메인 화면</h2>

  <h3><a href="<%=request.getContextPath()%>/home?action=login">로그인</a></h3>

  <h3><a href="<%=request.getContextPath()%>/home?action=signUp">회원가입</a></h3>
  
  <h3><a href="<%=request.getContextPath()%>/board">게시판</a></h3>

  <jsp:include page ="/view/tail.jsp" />
</body>
</html>