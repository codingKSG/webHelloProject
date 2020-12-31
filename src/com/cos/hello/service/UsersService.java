package com.cos.hello.service;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.hello.dao.UsersDao;
import com.cos.hello.dto.JoinDto;
import com.cos.hello.dto.LoginDto;
import com.cos.hello.model.Users;
import com.cos.hello.util.Script;

public class UsersService {

	public void 회원가입(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 데이터 원형 -
		// (x-www-form-urlencoded)username=namja&password=1234&email=namja@nate.com

		// 1. form의 input태그에 있는 3가지 값 username, password, email 받기

		// getParameter함수는 get방식의 데이터와 post방식의 데이터를 다 받을 수 있음.
		// 단 post 방식에서는 데이터 타입이 x-www-form-urlencoded 방식만 받을 수 있음.
		JoinDto	joinDto = (JoinDto)req.getAttribute("user");

		UsersDao usersDao = new UsersDao(); // 싱글톤 형식으로 변경하기.
		int result = usersDao.insert(joinDto);

		if (result == 1) {
			// 3번 INSERT가 정상적으로 되었다면 index.jsp를 응답
			Script.href(resp, "회원가입을 성공했습니다.", "user?gubun=login");
		} else {
			Script.back(resp, "회원가입을 실패하였습니다.");

		}

	}

	public void 회원탈퇴(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int id = Integer.parseInt(req.getParameter("id"));

		Users userEntity = Users.builder().id(id).build();

		UsersDao usersDao = new UsersDao();
		int result = usersDao.delete(userEntity);

		if (result == 1) {
			HttpSession session = req.getSession();
			session.invalidate(); // 세션 무효화
			Script.href(resp, "회원탈퇴를 하였습니다.", "user?gubun=login");
		} else {
			resp.sendRedirect("user?gubun=selectOne");
		}
	}

	public void 유저정보수정(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		int id = Integer.parseInt(req.getParameter("id"));
		String password = req.getParameter("password");
		String email = req.getParameter("email");

//		HttpSession httpSession = req.getSession();
//		Users user = (Users)httpSession.getAttribute("sessionUser");

		Users userEntity = Users.builder().id(id).password(password).email(email).build();

		UsersDao usersDao = new UsersDao();
		int result = usersDao.update(userEntity);

		if (result == 1) {
			Script.href(resp, "회원정보가 수정되었습니다.", "index.jsp");
		} else {
			// 이전페이지로 이동
			Script.back(resp, "회원정보 수정 실패");
		}

	}

	public void 로그인(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// SELECT id, username, email FROM users WHERE username = ? AND password = ?;
		// DAO의 함수명 : login() : Users 오브젝트를 return하면 된다.
		// 정상 : 세션에 Users 오브젝트 담고 index.jsp
		// 비정상 : login.jsp

		// 1번 전달된 값 받기
		// 2번 DB에 값이 있는지 SELECT해서 확인
		LoginDto loginDto = (LoginDto)req.getAttribute("dto");
		UsersDao usersDao = new UsersDao();
		Users userEntity = new Users();
		userEntity = usersDao.login(loginDto);
		// 3번 DB에 값이 있으면 세션에 값을 추가하고 이동
		if (userEntity != null) {
			HttpSession httpSession = req.getSession(); // 세션 영역(Heap)에 접근
			httpSession.setAttribute("sessionUser", userEntity); // 세션 영역에 키값 저장
			// 4번 SELECT가 정상적으로 되었다면 index.jsp로 이동
			// 한글처리를 위해 resp 객체를 건드린다.
			// MIME 타입
			// Http Header에 Content-type
			Script.href(resp, "로그인 성공", "index.jsp");
		} else {
			Script.back(resp, "로그인 실패");
		}
	}

	public void 유저정보보기(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// 인증이 필요한 페이지

		// 1. 세션 체크
		HttpSession session = req.getSession();

		Users user = (Users) session.getAttribute("sessionUser");
		UsersDao usersDao = new UsersDao();

		if (user != null) {
			Users userEntity = usersDao.selectById(user.getId());
			req.setAttribute("user", userEntity);
			RequestDispatcher dis = req.getRequestDispatcher("user/selectOne.jsp");
			dis.forward(req, resp);
		} else {
			Script.href(resp, "회원정보를 확인할 수 없습니다.", "user?gubun=login");
		}
	}

	public void 유저정보수정페이지(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// 인증이 필요한 페이지

		// 1. 세션 체크
		HttpSession session = req.getSession();
		Users user = (Users) session.getAttribute("sessionUser");
		UsersDao usersDao = new UsersDao();

		if (user != null) {
			Users userEntity = usersDao.selectById(user.getId());
			req.setAttribute("user", userEntity);
			RequestDispatcher dis = req.getRequestDispatcher("user/updateOne.jsp");
			dis.forward(req, resp);
		} else {
			Script.href(resp, "회원정보를 확인할 수 없습니다.", "user?gubun=login");
		}
	}

}
