package team6.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import team6.entity.Role;
import team6.entity.User;
import team6.model.HotelManager;

/**
 * Servlet implementation class ServletHotelManagement
 */
@WebServlet("/hotel/*")
public class ServletHotelManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HotelManager hotel = new HotelManager();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		User currentUser = (User) request.getSession().getAttribute("current-user");
		if(currentUser == null || currentUser.getRole() != Role.MANAGER) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		String[] uriSplit = request.getRequestURI().split("/");
		int hotelIndex = -1;
		for(int i = 0; i < uriSplit.length; i++) {
			if(uriSplit[i].equals("hotel")) {
				hotelIndex = i;
				break;
			}
		}
		// /hotel/
		if(uriSplit.length == hotelIndex + 1) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		switch(uriSplit[hotelIndex + 1]) {
			case "add":
			{
				processGetAddHotel(request, response);
				break;
			}
		}
	}

	private void processGetAddHotel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/hotel/hotel_add.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		User currentUser = (User) request.getSession().getAttribute("current-user");
		if(currentUser == null || currentUser.getRole() != Role.MANAGER) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		String[] uriSplit = request.getRequestURI().split("/");
		int hotelIndex = -1;
		for(int i = 0; i < uriSplit.length; i++) {
			if(uriSplit[i].equals("hotel")) {
				hotelIndex = i;
				break;
			}
		}
		// /hotel/
		if(uriSplit.length == hotelIndex + 1) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		switch(uriSplit[hotelIndex + 1]) {
			case "add":
			{
				processPostAddHotel(request, response);
				break;
			}
		}
		
	}

	private void processPostAddHotel(HttpServletRequest request, HttpServletResponse response)
		throws IOException {
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String zip = request.getParameter("zip");
		
		List<String> listError = hotel.validateInput(name, address, city, state, zip);
		if(listError == null) {
			hotel.addHotel(name, address, city, state, zip);
			request.getSession().setAttribute("action", "add-hotel");
			response.sendRedirect(request.getContextPath() + "/success");
		}
		
	}

}
