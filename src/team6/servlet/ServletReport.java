package team6.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import team6.entity.Hotel;
import team6.entity.Location;
import team6.entity.Role;
import team6.entity.RoomType;
import team6.entity.User;
import team6.model.HotelManager;
import team6.model.ReportManager;

@WebServlet("/report/*")
public class ServletReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public HotelManager hm = new HotelManager();
	private ReportManager rm = new ReportManager();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("current-user");
		
		if(user == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		else if(user.getRole() != Role.MANAGER) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		String[] uriSplit = request.getRequestURI().split("/");
		int reportIndex = -1;
		for(int i = 0; i < uriSplit.length; i++) {
			if(uriSplit[i].equals("report")) {
				reportIndex = i;
				break;
			}
		}
		// /report/
		if(uriSplit.length == reportIndex + 1) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		switch(uriSplit[reportIndex + 1]) {
		case "hotel":
		{
			if(uriSplit.length == reportIndex + 2) {
				processGetHotelReportMenu(request, response);
			}
			else if(uriSplit.length == reportIndex + 3) {
				switch(uriSplit[reportIndex + 2]) {
					case "list":
	                {
	                    processGetHotelReportList(request, response);
	                    break;
	                }
					case "barchart":
	                {
	                    processGetHotelReportBarchart(request, response);
	                    break;
	                }
					case "ondiscount":
					{
	                    processGetHotelReportDiscount(request, response);
	                    break;
	                }	
				}
			}
			break;
		}
		case "sales":
		{
			if(uriSplit.length == reportIndex + 2) {
				processGetSalesReport(request, response);
			}
			else if(uriSplit.length == reportIndex + 3) {
				
			}
			break;
		}
	}
	}

	private void processGetHotelReportList(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		Map<Hotel, List<RoomType>> mapHotelRoomType = rm.prepareHotelReportList();
		
		request.setAttribute("map-hotel-room-type", mapHotelRoomType);
		request.getRequestDispatcher("/WEB-INF/jsp/report/hotel_list.jsp").forward(request, response);
	}


	/**
	 *	Build a bar chart of <Location, Hotel> 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void processGetHotelReportBarchart(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		Map<String, Integer> mapLocationHotelCount = rm.prepareHotelReportBarchart();
		
		request.setAttribute("map-location-hotel-count", mapLocationHotelCount);
		request.getRequestDispatcher("/WEB-INF/jsp/report/hotel_barchart.jsp").forward(request, response);
	}

	private void processGetHotelReportDiscount(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		Map<Hotel, List<RoomType>> mapHotelRoomType = rm.prepareHotelReportDiscount();
		
		request.setAttribute("map-hotel-room-type", mapHotelRoomType);
		request.getRequestDispatcher("/WEB-INF/jsp/report/hotel_list.jsp").forward(request, response);		
	}

	private void processGetSalesReport(HttpServletRequest request, HttpServletResponse response) {
		
	}

	private void processGetHotelReportMenu(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/report/hotel.jsp").forward(request, response);
	}

}