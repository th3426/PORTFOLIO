<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.mvc_example.model.dao.BoardDao"%>
<!DOCTYPE html>

<%
    BoardDao boardDao = new BoardDao();
    List<HashMap<String, Object>> boardList = boardDao.getBoardList();
  %>

<html>
<head>
  <meta charset="UTF-8">
  <style>
    th, td {padding:10px;}
  </style>
  <title>
    연습
  </title>
</head>
<body>
  <jsp:include page ="/view/head.jsp" />

  <h2>게시판</h2>

  <table border="1">
    <tr align="center">
      <th>인덱스</th>
      <th>제목</th>
      <th>내용</th>
      <th>아이디</th>
      <th>작성일</th>
    </tr>
    <% for(int i = 0; i < boardList.size(); i++) {%>
    <tr align="center">
      <td><%=boardList.get(i).get("BINDEX")%></td>
      <td><a href="<%=request.getContextPath()%>/detail?action=view&index=<%=boardList.get(i).get("BINDEX")%>"</a> <%=boardList.get(i).get("BTITLE")%></td>
      <td><%=boardList.get(i).get("BCONTENT")%></td>
      <td><%=boardList.get(i).get("BWRITER")%></td>
      <td><%=boardList.get(i).get("BDATE")%></td>
    </tr>
    <% }%>
  </table><br>

  <form action="board" method="post" name="request_write">
    <input type="submit" value="글쓰기" name="submit">
  </form>
  
  <jsp:include page ="/view/tail.jsp" />
</body>
</html>