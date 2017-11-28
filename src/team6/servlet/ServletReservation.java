package team6.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import team6.entity.Hotel;
import team6.entity.RoomType;
import team6.entity.User;
import team6.model.HotelManager;

/**
 * Servlet implementation class ServletReservation
 */
@WebServlet("/reserve/*")
public class ServletReservation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HotelManager hm = new HotelManager();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] queryStringSplit = request.getQueryString().split("&");
		String[] actionSplit = queryStringSplit[0].split("=");
		if(actionSplit[0].equals("action")) {
			switch(actionSplit[1]) {
				case "chooseRoom":
				{
					String[] hotelParam = queryStringSplit[1].split("=");
					String[] checkInParam = queryStringSplit[2].split("=");
					String[] checkOutParam = queryStringSplit[3].split("=");
					if(hotelParam[0].equals("hotel") && checkInParam[0].equals("checkIn") && checkOutParam[0].equals("checkOut")) {
						List<RoomType> listRoomType = hm.getListRoomType(Integer.parseInt(hotelParam[1]));
						Map<RoomType, Boolean> mapRoomTypeAvailable = new HashMap<>();
						for(RoomType rt: listRoomType) {
							mapRoomTypeAvailable.put(rt, Boolean.TRUE);
						}
						request.setAttribute("map-room-type", mapRoomTypeAvailable);
						request.setAttribute("hotel-id", hotelParam[1]);
						request.setAttribute("check-in", checkInParam[1]);
						request.setAttribute("check-out", checkOutParam[1]);
						request.getRequestDispatcher("/WEB-INF/jsp/choose-room.jsp").forward(request, response);
					}
					break;
				}
				case "reservation":
				{
					String[] hotelParam = queryStringSplit[1].split("=");
					String[] roomParam = queryStringSplit[2].split("=");
					String[] checkInParam = queryStringSplit[3].split("=");
					String[] checkOutParam = queryStringSplit[4].split("=");
					if(	hotelParam[0].equals("hotel")
						&& roomParam[0].equals("room")	
						&& checkInParam[0].equals("checkIn")
						&& checkOutParam[0].equals("checkOut")
					) {
						HttpSession session = request.getSession();
						session.setAttribute("reservation-hotel", hotelParam[1]);
						session.setAttribute("reservation-room", roomParam[1]);
						session.setAttribute("reservation-check-in", checkInParam[1]);
						session.setAttribute("reservation-check-out", checkOutParam[1]);
						
						User currentUser = (User) request.getSession().getAttribute("current-user");
						if(currentUser == null) {
							response.sendRedirect(request.getContextPath() +"/login");
						}
						else {
							DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mmDDyyyy");
							LocalDate checkInDate = LocalDate.parse(checkInParam[1], dtf);
							LocalDate checkOutDate = LocalDate.parse(checkOutParam[1], dtf);
							RoomType rt = hm.getRoomType(Integer.parseInt(roomParam[1]));
							Hotel hotel = rt.getHotelBelong();
							
							Integer numDay = Integer.valueOf(hm.calcDayBetween(checkInDate, checkOutDate));
							
							request.setAttribute("hotel", hotel);
							request.setAttribute("room-type", rt);
							request.setAttribute("check-in-date", checkInDate);
							request.setAttribute("check-out-date", checkOutDate);
							request.setAttribute("num-day", numDay);
							request.getRequestDispatcher("/WEB-INF/jsp/reservation.jsp").forward(request, response);
						}
						break;
					}
				}
			}
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
