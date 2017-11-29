package team6.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import team6.entity.Order;
import team6.entity.User;
import team6.model.OrderManager;

/**
 * Servlet implementation class ServletOrder
 */
@WebServlet("/order/*")
public class ServletOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderManager om = new OrderManager();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("current-user");
		if(user == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		String[] uriSplit = request.getRequestURI().split("/");
		int orderIndex = -1;
		for(int i = 0; i < uriSplit.length; i++) {
			if(uriSplit[i].equals("order")) {
				orderIndex = i;
				break;
			}
		}
		
		switch(uriSplit[orderIndex + 1]) {
			case "view":
			{
				String queryString = request.getQueryString();
				if(queryString == null) {
					List<Order> listOrder = om.getListOrder(user);
					
					request.setAttribute("list-order", listOrder);
					request.getRequestDispatcher("/WEB-INF/jsp/order/view_order.jsp").forward(request, response);
				}
				else {
					String[] queryStringSplit = queryString.split("&");
					String[] orderIdParam = queryStringSplit[0].split("=");
					if(orderIdParam[0].equals("orderId")) {
						Order order = om.getOrder(Integer.parseInt(orderIdParam[1]));
						request.setAttribute("queried-order", order);
						request.getRequestDispatcher("/WEB-INF/jsp/order/order_details.jsp").forward(request, response);
					}
				}
				break;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] uriSplit = request.getRequestURI().split("/");
		int orderIndex = -1;
		for(int i = 0; i < uriSplit.length; i++) {
			if(uriSplit[i].equals("order")) {
				orderIndex = i;
				break;
			}
		}
		
		switch(uriSplit[orderIndex + 1]) {
			case "cancel":
			{
				int orderId = Integer.parseInt(uriSplit[orderIndex + 2]);
				
				User currentUser = (User) request.getSession().getAttribute("current-user");
				Order order = om.getOrder(orderId);
				if(!currentUser.getuId().equals(order.getUser().getuId())) {
					response.sendRedirect(request.getContextPath());
					return;
				}
				
				om.cancelOrder(order);
				request.getSession().setAttribute("action", "cancel-order");
				response.sendRedirect(request.getContextPath() + "/success");
			}
		}
	}

}
