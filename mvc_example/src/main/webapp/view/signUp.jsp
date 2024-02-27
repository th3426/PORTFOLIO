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

  <h2>회원가입</h2>

  <form action="signUp" method="post" name="request_signUp">

    신규 아이디   : <input type="text" name="userId"><br><br>
    신규 비밀번호 : <input type="password" name="userPw"><br>
    비밀번호 확인 : <input type="password" name="confirmPw"><br><br>
    <input type="submit" value="회원가입" name="submit"><br><br>

  </form>
  
  <jsp:include page ="/view/tail.jsp" />
</body>
</html>