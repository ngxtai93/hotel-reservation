package team6.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import team6.business.BusinessLogic;
import team6.dao.OrderDAO;
import team6.entity.CustomerProfile;
import team6.entity.Order;
import team6.entity.OrderStatus;
import team6.entity.User;

public class OrderManager {

	private OrderDAO orderDao = new OrderDAO();
	private BusinessLogic logic = BusinessLogic.INSTANCE;
	
	/**
	 * Insert into mysql DB
	 */
	public void processOrderPlaced(Order order) {
		order.setStatus(OrderStatus.PLACED);
		CustomerProfile cp = order.getCustomer();
		Integer customerId = orderDao.selectCustomerProfile(
			cp.getFirstName(), cp.getLastName(), cp.getPhone()
			, cp.getEmail(), cp.getAddress(), cp.getCity()
			, cp.getState(), cp.getZip(), cp.getCreditCardNum()
			, cp.getExpirationDate()
		);
		if(customerId == null) {
			customerId = orderDao.insertCustomerProfile(order.getCustomer());
		}
		cp.setSeqNo(customerId);
		
		orderDao.insertOrder(order);
	}

	public List<Order> getListOrder(User user) {
		return orderDao.selectOrder(user);
	}

	public Order getOrder(int orderId) {
		return orderDao.selectOrder(orderId);
	}

	public void cancelOrder(Order order) {
		order.setStatus(OrderStatus.CANCELLED);
		orderDao.updateOrder(order);
	}

	public void updateOrder(Order order) {
		orderDao.updateOrder(order);
	}

	/**
	 * Get list of available order for check-in (by hotel ID) 
	 */
	public List<Order> getCheckInOrder(int hotelId) {
		LocalDate date = LocalDate.now();
		LocalTime checkIn = logic.getCheckInTime();
		LocalDateTime ldt = LocalDateTime.of(date, checkIn);
		
		return orderDao.selectOrderByCheckInDateTime(hotelId, ldt);
	}

}
