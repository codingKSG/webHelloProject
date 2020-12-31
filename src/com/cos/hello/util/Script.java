package com.cos.hello.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class Script {
	
	public static void href(HttpServletResponse resp, String msg, String url) throws IOException {
		//setHeader 방법과 setConetentType 방법 2가지있음
//		resp.setHeader("Content-Type", "text/html; CharSet = UTF-8");
//		resp.setContentType("text/html; CharSet = UTF-8");
		PrintWriter out = resp.getWriter();
		out.println("<script>");
		out.println("alert('"+ msg +"');");
		out.println("location.href = '"+ url +"';");
		out.println("</script>");
		out.flush();
	}
	
	public static void back(HttpServletResponse resp, String msg) throws IOException {
//		resp.setContentType("text/html; CharSet = UTF-8");
		PrintWriter out = resp.getWriter();
		out.println("<script>");
		out.println("alert('"+ msg +"');");
		out.println("history.back();");
		out.println("</script>");
		out.flush();
	}
}
