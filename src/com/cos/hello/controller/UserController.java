package com.cos.hello.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.hello.service.UsersService;

// javax로 시작하는 패키지는 톰캣이 들고있는 라이브러리

// 디스패쳐의 역할 = 분기 = 필요한 View를 응답해주는 것
public class UserController extends HttpServlet {

	// reqp와 res는 톰캣이 만들어줍니다. (클라이언트의 요청이 있을때 마다)
	// reqp와 res는 http프로토콜이 규약되어있는 버퍼
	// reqp는 BufferedReader 할 수 있는 ByteStream
	// resp는 BufferedWriter 할 수 있는 ByteStream

	// http://localhost:8000/hello/front
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Usercontroller 실행됨");
		// req.getParameter 함수 실행시에 파싱하기 때문에
		// 파싱전에 인코딩 해줘야함.
//		req.setCharacterEncoding("UTF-8");
//		String gubun = req.getRequestURI(); // hello/front
		String gubun = req.getParameter("gubun");
		System.out.println(gubun);

		route(gubun, req, resp);
	}

	private void route(String gubun, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		UsersService usersService = new UsersService();
		
		// http://localhost:8000/hello/front?gubun=login
		if (gubun.equals("login")) {
			resp.sendRedirect("auth/login.jsp"); // 한번더 request
		} else if (gubun.equals("join")) {
			resp.sendRedirect("auth/join.jsp"); // 한번더 request
		} else if (gubun.equals("selectOne")) { // 유저정보보기
			usersService.유저정보보기(req, resp);
		} else if (gubun.equals("updateOne")) {
			usersService.유저정보수정페이지(req, resp);
		} else if (gubun.equals("joinProc")) { // 회원가입 수행해줘
			usersService.회원가입(req, resp);
		} else if (gubun.equals("loginProc")) {
			usersService.로그인(req, resp);
		} else if (gubun.equals("updateProc")) {
			usersService.유저정보수정(req, resp);
		} else if (gubun.equals("deleteProc")) {
			usersService.회원탈퇴(req, resp);
		}
	}
}
