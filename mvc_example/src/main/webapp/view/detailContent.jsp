<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.mvc_example.model.vo.BoardVo"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>
    MVC 게시판 예제 [detail]
  </title>
</head>
<body>
  <jsp:include page ="/view/head.jsp" />

  <h2>게시글</h2>
  <%
    request.setCharacterEncoding("UTF-8");

    BoardVo boardVo = new BoardVo();
    boardVo = (BoardVo)request.getAttribute("boardVoResult");

    String sessionId = (String)session.getAttribute("userId");

    session.setAttribute("currentIndex", boardVo.getIndex());
  %>

  <form action="detail" method="post" name="request_detail">
    <div align="center">
      <table border="1" width="50%">
        <tr bgcolor="lightgray">
          <td colspan="2" align="center">게시글</a></td>
        </tr>			
        <tr>	
          <td width="15%" align="center">제목</td>
          <td><%=boardVo.getTitle()%></td>
        </tr>
        <tr>	
          <td width="15%" align="center">작성자</td>
          <td><%=boardVo.getWriter()%></td>
        </tr>
        <tr>	
          <td width="15%" align="center">날짜</td>
          <td><%=boardVo.getDate()%></td>
        </tr>
        <tr>	
          <td width="15%" align="center">내용</td>
          <td><%=boardVo.getContent()%></td>
        </tr>
        <tr>	
          <td colspan="2" align="center">
             <input type="submit" value="뒤로가기" name="submit">
             <%
              System.out.println("sessionid : " + sessionId);
              System.out.println("board writer : " + boardVo.getWriter());
              
              if(sessionId != null && sessionId.equals(boardVo.getWriter())) { %>
                <input type="submit" value="글 삭제" name="submit">
              <%
              }
              %>
          </td>
        </tr>
      </table>
    </div>
  </form>
  
  <jsp:include page ="/view/tail.jsp" />
</body>
</html>