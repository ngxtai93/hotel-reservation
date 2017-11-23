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
import team6.entity.Room;
import team6.entity.RoomType;
import team6.entity.User;
import team6.model.HotelManager;

/**
 * Servlet implementation class ServletRoomManagement
 */
@WebServlet("/room/*")
public class ServletRoomManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HotelManager hotel = new HotelManager();
    private Gson gson = new Gson();
    
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
			case "update":
			{
				processGetUpdateRoom(request, response);
				break;
			}
			case "delete":
			{
				processGetDeleteRoom(request, response);
				break;
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
				processPostAddRoom(request, response);
				break;
			}
			case "update":
			{
				processPostUpdateRoom(request, response);
				break;
			}
			case "delete":
			{
				processPostDeleteRoom(request, response);
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
			String[] queryAction = queryStringSplit[0].split("=");
			if(!queryAction[0].equals("action")) {
				return;
			}
			
			String action = queryAction[1];
			switch(action) {
				case "getHotel":
				{
					String[] locationParam = queryStringSplit[1].split("=");
					if(locationParam[0].equals("location")) {
						processGetHotelByLocation
							(request, response, Integer.parseInt(locationParam[1]));
					}
					break;
				}
				case "getRoomType":
				{
					processGetRoomType(request, response);
					break;
				}
				case "isRoomExist":
				{
					String[] hotelParam = queryStringSplit[1].split("=");
					String[] roomParam = queryStringSplit[2].split("=");
					if(hotelParam[0].equals("hotel") && roomParam[0].equals("room")) {
						processCheckRoomExist
							(request, response
							, Integer.parseInt(hotelParam[1]), Integer.parseInt(roomParam[1]));
					}
					break;
				}
			}
		}
	}

	private void processGetUpdateRoom(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		String queryString = request.getQueryString();
		
		// go to page if no query string
		if(queryString == null) {
			List<Location> listLocation = hotel.getAvailableLocation();
			request.setAttribute("list-location", listLocation);
			request.getRequestDispatcher("/WEB-INF/jsp/room/room_update.jsp").forward(request, response);
		}
		else {
			String[] queryStringSplit = queryString.split("&");
			String[] queryAction = queryStringSplit[0].split("=");
			if(!queryAction[0].equals("action")) {
				return;
			}
			
			String action = queryAction[1];
			switch(action) {
				case "getHotel":
				{
					String[] locationParam = queryStringSplit[1].split("=");
					if(locationParam[0].equals("location")) {
						processGetHotelByLocation
							(request, response, Integer.parseInt(locationParam[1]));
					}
					break;
				}
				case "getRoom":
				{
					String[] hotelParam = queryStringSplit[1].split("=");
					if(hotelParam[0].equals("hotel")) {
						processGetListRoom(request, response, Integer.parseInt(hotelParam[1]));
					}
					break;
				}
				case "getRoomType":
				{
					processGetRoomType(request, response);
					break;
				}
			}
		}
		
	}

	private void processGetDeleteRoom(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		String queryString = request.getQueryString();
		
		// go to page if no query string
		if(queryString == null) {
			List<Location> listLocation = hotel.getAvailableLocation();
			request.setAttribute("list-location", listLocation);
			request.getRequestDispatcher("/WEB-INF/jsp/room/room_delete.jsp").forward(request, response);
		}
		else {
			String[] queryStringSplit = queryString.split("&");
			String[] queryAction = queryStringSplit[0].split("=");
			if(!queryAction[0].equals("action")) {
				return;
			}
			
			String action = queryAction[1];
			switch(action) {
				case "getHotel":
				{
					String[] locationParam = queryStringSplit[1].split("=");
					if(locationParam[0].equals("location")) {
						processGetHotelByLocation
							(request, response, Integer.parseInt(locationParam[1]));
					}
					break;
				}
				case "getRoom":
				{
					String[] hotelParam = queryStringSplit[1].split("=");
					if(hotelParam[0].equals("hotel")) {
						processGetListRoom(request, response, Integer.parseInt(hotelParam[1]));
					}
					break;
				}
				case "getRoomType":
				{
					processGetRoomType(request, response);
					break;
				}
			}
		}
		
	}

	private void processGetListRoom(HttpServletRequest request, HttpServletResponse response, int hotelId)
		throws IOException {
		List<Room> listRoom = hotel.getListRoom(hotelId);
		String json = listRoom == null ? "" : gson.toJson(listRoom);
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}

	private void processCheckRoomExist
		(HttpServletRequest request, HttpServletResponse response, int hotelId, int roomNum)
		throws IOException {
		boolean isRoomExist = hotel.isRoomExist(hotelId, roomNum);
		String json = isRoomExist ? gson.toJson("true"): gson.toJson("false");
		
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}

	/**
	 * Process AJAX request get list room type. Return an JSON object of list room type 
	 * @throws IOException 
	 */
	private void processGetRoomType(HttpServletRequest request, HttpServletResponse response)
		throws IOException {
		List<RoomType> listRoomType = hotel.getListRoomType();
		String json = listRoomType == null ? "" : gson.toJson(listRoomType);
		
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}

	/**
	 * Process AJAX request get list hotel. Return an JSON object of list hotel 
	 */
	private void processGetHotelByLocation(HttpServletRequest request, HttpServletResponse response, int locationId)
		throws IOException {
		List<Hotel> listHotel = hotel.getAvailableHotel(locationId);
		String json = listHotel == null ? "" : gson.toJson(listHotel);
		
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}

	private void processPostAddRoom(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		int hotelId = Integer.parseInt(request.getParameter("hotel-id"));
		int roomNum = Integer.parseInt(request.getParameter("room-num"));
		int roomTypeId = Integer.parseInt(request.getParameter("room-type"));
		
		hotel.addRoom(hotelId, roomNum, roomTypeId);
		
		request.getSession().setAttribute("action", "add-room");
		response.sendRedirect(request.getContextPath() + "/success");
	}

	private void processPostUpdateRoom(HttpServletRequest request, HttpServletResponse response)
		throws IOException {
		int roomId = Integer.parseInt(request.getParameter("room-id"));
		int roomNum = Integer.parseInt(request.getParameter("room-num"));
		int roomTypeId = Integer.parseInt(request.getParameter("room-type"));
		
		hotel.updateRoom(roomId, roomNum, roomTypeId);
		
		request.getSession().setAttribute("action", "update-room");
		response.sendRedirect(request.getContextPath() + "/success");
	}
	
	private void processPostDeleteRoom(HttpServletRequest request, HttpServletResponse response)
		throws IOException {
		int roomId = Integer.parseInt(request.getParameter("room-id"));
		
		hotel.deleteRoom(roomId);
		
		request.getSession().setAttribute("action", "delete-room");
		response.sendRedirect(request.getContextPath() + "/success");
	}

}
