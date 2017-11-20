package team6.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletBrowseRoom
 */
@WebServlet("/room/*")
public class ServletBrowseRoom extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String[] uriSplit = uri.split("/");
		//0: blank, 1: CSP584-Project, 2: room
		if(uriSplit.length <= 3) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		switch(uriSplit[3]) {
			case "all": {
				request.getRequestDispatcher("/WEB-INF/jsp/all-rooms.jsp").forward(request, response);
				break;
			}
			case "single": {
				request.getRequestDispatcher("/WEB-INF/jsp/single-room.jsp").forward(request, response);
				break;
			}
			case "single-gallery": {
				request.getRequestDispatcher("/WEB-INF/jsp/single-room-gallery.jsp").forward(request, response);
				break;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
