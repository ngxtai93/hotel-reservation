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
				List<Order> listOrder = om.getListOrder(user);
				
				request.setAttribute("list-order", listOrder);
				request.getRequestDispatcher("/WEB-INF/jsp/order/view_order.jsp").forward(request, response);
				break;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
