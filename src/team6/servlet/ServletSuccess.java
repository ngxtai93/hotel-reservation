package team6.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletSuccess
 */
@WebServlet("/success")
public class ServletSuccess extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String action = (String) session.getAttribute("action");
		if(action == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		switch(action) {
			case "create-account":
			{
				session.removeAttribute("action");
				request.getRequestDispatcher("/WEB-INF/jsp/success/create-account.jsp").forward(request, response);
				break;
			}
			case "add-hotel":
			{
				session.removeAttribute("action");
				request.getRequestDispatcher("/WEB-INF/jsp/success/add-hotel.jsp").forward(request, response);
				break;
			}
		}
	}

}
