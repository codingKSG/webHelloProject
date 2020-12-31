<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file = "../layout/header.jsp" %>

<h1>회원정보수정</h1>
<form method = "post" action = "user?gubun=updateProc">
<table border = "1">
<tr>
<td>번호</td>
<td>유저네임</td>
<td>패스워드</td>
<td>이메일</td>
</tr>
<tr>
<td>
<input type = "hidden" name = "id" value = "${user.id}"/>
</td>
<td>${user.username}</td>
<td>
<input type = "password" name = "password" value = "${user.password}"/>
</td>
<td><input type = "email" name = "email" value = "${user.email}"/></td>
</tr>
</table>
<button>회원수정</button>
</form>
</body>
</html>