package com.cos.hello.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.cos.hello.controller.UserController;
import com.cos.hello.model.Users;

// 마지막 순서
public class AttackFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("공격 방어 필터 실행");

		// joinProc 일때
		// post 요청만 받아서 처리
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String method = req.getMethod();
		System.out.println("method : " + method);

		if (method.equals("POST")) {
			System.out.println("method POST로 확인");
			String username = request.getParameter("username");
			System.out.println("username : " + username);
			username = username.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
			System.out.println("username : " + username);
			// username에 <> 꺽쇠 들어오는것을 방어
			// 만약에 꺽쇠가 들어오면 전부 &lt; &gt; 로 치환
			// 다시 필터 타게 할 예정

		}
		chain.doFilter(request, response);
	}

	class MyRequest extends HttpServletRequestWrapper {

		HttpServletRequest request;

		public MyRequest(HttpServletRequest request) {
			super(request);

			this.request = request;
		}

		public String getParameter(String name) {

			String username = request.getParameter(name);

			return null;
		}
	}

}
