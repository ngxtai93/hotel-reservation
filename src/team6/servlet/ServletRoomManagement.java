package team6.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import team6.entity.Hotel;
import team6.entity.Location;
import team6.entity.Role;
import team6.entity.User;
import team6.model.HotelManager;

/**
 * Servlet implementation class ServletRoomManagement
 */
@WebServlet("/room/*")
public class ServletRoomManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HotelManager hotel = new HotelManager();
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User currentUser = (User) request.getSession().getAttribute("current-user");
		if(currentUser == null || currentUser.getRole() != Role.MANAGER) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		String[] uriSplit = request.getRequestURI().split("/");
		int roomIndex = -1;
		for(int i = 0; i < uriSplit.length; i++) {
			if(uriSplit[i].equals("room")) {
				roomIndex = i;
				break;
			}
		}
		// /room/
		if(uriSplit.length == roomIndex + 1) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		switch(uriSplit[roomIndex + 1]) {
			case "add":
			{
				processGetAddRoom(request, response);
				break;
			}
		}
	}

	private void processGetAddRoom(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		String queryString = request.getQueryString();
		
		// go to page if no query string
		if(queryString == null) {
			List<Location> listLocation = hotel.getAvailableLocation();
			request.setAttribute("list-location", listLocation);
			request.getRequestDispatcher("/WEB-INF/jsp/room/room_add.jsp").forward(request, response);
		}
		else {
			String[] queryStringSplit = queryString.split("&");
			if(queryStringSplit.length == 1 && queryStringSplit[0].contains("location")) {
				processGetHotelByLocation(request, response, Integer.parseInt(queryStringSplit[0].split("=")[1]));
			}
		}
	}

	/**
	 * Process AJAX request get list hotel. Return an JSON object of list hotel 
	 */
	private void processGetHotelByLocation(HttpServletRequest request, HttpServletResponse response, int locationId)
		throws IOException {
		List<Hotel> listHotel = hotel.getAvailableHotel(locationId);
		String json = listHotel == null ? "" : new Gson().toJson(listHotel);
		
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
