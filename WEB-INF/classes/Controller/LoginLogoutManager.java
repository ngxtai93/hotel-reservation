package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.MySQLDatabaseOperator;
import Helper.ValidateUser;

public class LoginLogoutManager extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		PrintWriter pw = response.getWriter();
		HttpSession session=request.getSession();
		
		switch (action) {
		
			case "login":
				Login( request,  pw);
				break;
				
			case "logout":
				 LogOut(session,pw);
				break;
				
			case "signup":
				Sighup(request,pw);
				break;

		}
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		PrintWriter pw = response.getWriter();
		HttpSession session=request.getSession();
		
		switch (action) {
		
			case "login":
				Login( request,  pw);
				break;
				
			case "logout":
				 LogOut(session,pw);
				break;
				
			case "signup":
				Sighup(request,pw);
				break;

		}
		
	}
	
	private void Sighup(HttpServletRequest request, PrintWriter pw) {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		if(ValidateUser.IsUsernameAlreadyTaken(username))
		{
			pw.print("duplicate");
			return;
		}
		
		int lastuid = getLastUID();
		
		if(lastuid==-1)
		{
			pw.print("database error");
			return;
		}
		
		lastuid++;
			
		String query = "insert into user values ("+  lastuid +",'" + username + "','" + password + "','" + email + "')";
		boolean ret = MySQLDatabaseOperator.GetInstance().NonFetchQuery(query);
		
		if(ret)
			pw.print("signupdone");
		else
			pw.print("signupfailed"); 
		
		
		
	}
	
	private void Login(HttpServletRequest request, PrintWriter pw)  {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		boolean rs = ValidateUser.IsUPCombineCorrect(username, password);
		
		if(rs) {
			pw.print("logindone");
			request.getSession().setAttribute("islogin", "true");
			request.getSession().setAttribute("username", username);
		}
		else
			pw.print("loginfail");
		
	}
	
	private void LogOut(HttpSession session,PrintWriter pw) {
		
		try {
			
			session.invalidate();
			
		} catch(Exception e) {
			
			System.out.println("LogOut Exception" + e.getMessage());
		
		}
		
		pw.print("logoutdone");

	}
	
	private int getLastUID() {
		
		String query = "SELECT MAX(UID) AS MUID FROM USER";
		ResultSet rs = MySQLDatabaseOperator.GetInstance().FetchQuery(query);
		
		
		
		try {
			
			if(rs.next())
				return rs.getInt("MUID");
		
		} 
		
		catch (SQLException e) {
			
			System.out.println("Error in getLastUID for Query " + query);
			
		}
		
		return -1;
		
		
	}
	
	

}
