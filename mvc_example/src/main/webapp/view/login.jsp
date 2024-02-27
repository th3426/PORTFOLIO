<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>
    연습
  </title>
</head>
<body>
  <jsp:include page ="/view/head.jsp" />

  <h2>로그인</h2>

  <form action="doLogin" method="post" name="request_login">

    아이디   : <input type="text" name="userId"><br>
    비밀번호 : <input type="password" name="userPw">

    <input type="submit" value="로그인" name="submit">

  </form>

  <jsp:include page ="/view/tail.jsp" />
</body>
</html>