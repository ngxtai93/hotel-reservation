package team6.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import team6.entity.User;
import team6.model.Authenticator;

@WebServlet("/login")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Authenticator auth;
	private boolean debug = true;	// debug purpose
	
	public ServletLogin() {
		auth = new Authenticator();
	}
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		if(debug) {
			User user = auth.doLogin("manager", "manager");
			request.getSession().setAttribute("current-user", user);
			response.sendRedirect(request.getContextPath());
			return;
		}
		User currentUser = (User) request.getSession().getAttribute("current-user");
		if(currentUser != null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User loggedUser = auth.doLogin(username, password);
		HttpSession session = request.getSession();
		
		if(loggedUser != null) {
			String requestUrl = (String) session.getAttribute("request-url");
			request.getSession().setAttribute("current-user", loggedUser);
			if(requestUrl == null) {
				response.sendRedirect(request.getContextPath());
			}
			else {
				response.sendRedirect(requestUrl);
			}
			
		}
		else {
			request.setAttribute("login-failed", Boolean.TRUE);
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		}
	}
}
