package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.EmaillistDao;
import com.javaex.vo.EmaillistVo;

@WebServlet("/el") //EmaillistServlet -> el로 이름 바꿔줌
public class EmaillistServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("servlet 진입");
		
		request.setCharacterEncoding("UTF-8");
		
		String actionName = request.getParameter("a");
		if("form".equals(actionName)) {
			System.out.println("form 진입");
			RequestDispatcher rd = request.getRequestDispatcher("form.jsp");
			rd.forward(request, response);			
		} else if("insert".equals(actionName)) {
			System.out.println("insert 진입");
			
			String lastName = request.getParameter("ln");
			String firstName = request.getParameter("fn");
			String email = request.getParameter("email");
			
			EmaillistVo vo = new EmaillistVo();
			vo.setLastName(lastName);
			vo.setFirstName(firstName);
			vo.setEmail(email);
			
			System.out.println(vo.toString());
			
			EmaillistDao dao = new EmaillistDao();
			dao.insert(vo);
			
			response.sendRedirect("el?a=list");
		} else if("list".equals(actionName)) {
			System.out.println("list 진입");
			
			EmaillistDao dao = new EmaillistDao();
			List<EmaillistVo> list = dao.getList();
			
			request.setAttribute("list", list); //부를이름, 실제데이터
			RequestDispatcher rd = request.getRequestDispatcher("list.jsp");
			rd.forward(request, response);
		} else {
			System.out.println("잘못된 a값처리 로직");
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
}
