package com.signin.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String uname  = request.getParameter("name") ;
		String uemail = request.getParameter("email");
		String repass = request.getParameter("re_pass");
		String upwd = request.getParameter("pass");
		String umobile = request.getParameter("contact");
		Connection con = null;
		RequestDispatcher dispatcher = null ;
		
		if (uname == null || uname == "") {
			request.setAttribute("status","invalid_name");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}
		
		if (uemail == null || uemail == "") {
			request.setAttribute("status","invalid_email");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}
		
		if (upwd == null || upwd == "") {
			request.setAttribute("status","invalid_password");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}else if(!upwd.equals(repass)){
			
			request.setAttribute("status","invalid_confirm_password");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
			
		}
		
		if (umobile == null || umobile == "") {
			request.setAttribute("status","invalid_contact");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}else if(umobile.length() != 10) {
			
			request.setAttribute("status","invalid_mobileno_length");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}
		if (repass == null || repass == "") {
			request.setAttribute("status","invalid_repass");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/signin?useSSL=false","root","3183");
			
			PreparedStatement pst = con.prepareStatement("insert into users(uname,upwd, uemail,umobile) values(?,?,?,?)");
			
			pst.setString(1, uname);
			pst.setString(2, upwd);
			pst.setString(3, uemail);
			pst.setString(4, umobile);
			
			int rowCount = pst.executeUpdate();
			dispatcher = request.getRequestDispatcher("registration.jsp");
			
			if (rowCount > 0 ) {
				request.setAttribute("status", "success");
//				response.sendRedirect("registration.jsp");
			}else {
				request.setAttribute("status", "failed");
			}
		
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
