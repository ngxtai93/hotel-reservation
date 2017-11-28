package team6.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import team6.entity.RoomType;
import team6.model.HotelManager;

/**
 * Servlet implementation class ServletReservation
 */
@WebServlet("/reserve/*")
public class ServletReservation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HotelManager hotel = new HotelManager();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] queryStringSplit = request.getQueryString().split("&");
		String[] actionSplit = queryStringSplit[0].split("=");
		if(actionSplit[0].equals("action")) {
			switch(actionSplit[1]) {
				case "chooseRoom":
				{
					String[] hotelParam = queryStringSplit[1].split("=");
					if(hotelParam[0].equals("hotel")) {
						List<RoomType> listRoomType = hotel.getListRoomType(Integer.parseInt(hotelParam[1]));
						Map<RoomType, Boolean> mapRoomTypeAvailable = new HashMap<>();
						for(RoomType rt: listRoomType) {
							mapRoomTypeAvailable.put(rt, Boolean.TRUE);
						}
						request.setAttribute("map-room-type", mapRoomTypeAvailable);
						request.setAttribute("hotel-id", hotelParam[1]);
						request.getRequestDispatcher("/WEB-INF/jsp/choose-room.jsp").forward(request, response);
					}
					break;
				}
			}
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
