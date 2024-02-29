<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>
    MVC 게시판 예제 [WRITE]
  </title>
</head>
<body>
  <jsp:include page ="/view/head.jsp" />

  <h2>글쓰기</h2>

  <form action="write" method="post" name="request_write">
    <div align="center">
      <table border="1" width="90%">
        <tr bgcolor="lightgray">
          <td colspan="2" align="center">글쓰기</a></td>
        </tr>			
        <tr>	
          <td width="15%" align="center">제목</td>
          <td><input type="text" name="title" tabindex="2" style="width: 100%;"></td>
        </tr>
        <tr>	
          <td width="15%" align="center">내용</td>
          <td><textarea name="content" tabindex="4" style="width: 100%;" rows="15"></textarea></td>
        </tr>
        <tr>	
          <td colspan="2" align="center">
             <input type="submit" value="제출">
          </td>
        </tr>
      </table>
    </div>
  </form>
  
  
  <jsp:include page ="/view/tail.jsp" />
</body>
</html>