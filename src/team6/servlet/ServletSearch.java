package team6.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import team6.entity.Hotel;
import team6.model.HotelManager;

/**
 * Servlet implementation class ServletSearch
 */
@WebServlet("/search")
public class ServletSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HotelManager hotel = new HotelManager();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String queryString = request.getQueryString();
		if(queryString == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate checkIn = LocalDate.parse(request.getParameter("check-in-date"), dtf);
		LocalDate checkOut = LocalDate.parse(request.getParameter("check-out-date"), dtf);
		String location = request.getParameter("location");
		String[] locationSplit = location.split(", ");
		
		Map<Hotel, Boolean> listAvailableHotel = hotel.doSearch(locationSplit[0], locationSplit[1], checkIn, checkOut);
		if(listAvailableHotel != null) {
			request.setAttribute("search-result", listAvailableHotel);
		}
		request.setAttribute("query-location", location);
		request.setAttribute("query-check-in", checkIn);
		request.setAttribute("query-check-out", checkOut);
		request.getRequestDispatcher("/WEB-INF/jsp/search_result_hotel.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
