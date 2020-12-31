<%@page import="com.cos.hello.model.Users"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<h1>회원정보보기</h1>
<table border="1">
	<tr>
		<td>번호</td>
		<td>유저네임</td>
		<td>패스워드</td>
		<td>이메일</td>
	</tr>
	<tr>
		<td>${user.id}</td>
		<td>${user.username}</td>
		<td>${user.password}</td>
		<td>${user.email}</td>
	</tr>
</table>
<form action="user?gubun=deleteProc" method="post">
	<input type="hidden" name="id" value="${user.id}" />
	<button>삭제</button>
</form>
</body>
</html>