package team6.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import team6.entity.CustomerProfile;
import team6.entity.Order;

public class OrderDAO {

	private Connection conn;
	
	public OrderDAO() {
		conn = MySQLDatabaseOperator.INSTANCE.getConnection();
	}

	public Integer selectCustomerProfile(String firstName, String lastName, String phone, String email, String address,
			String city, String state, String zip, String creditCardNum, String expirationDate) {
		String sql = "SELECT seq_no from csp584_project.customer_profile"
					+ " WHERE first_name = ? AND last_name = ? AND email = ?"
					+ " AND phone = ? AND address = ? AND city = ?"
					+ " AND state = ? AND zip = ? AND cc_num = ?"
					+ " AND cc_exp = ?"
		;
		Integer result = null;
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, email);
			ps.setString(4, phone);
			ps.setString(5, address);
			ps.setString(6, city);
			ps.setString(7, state);
			ps.setString(8, zip);
			ps.setString(9, creditCardNum);
			ps.setString(10, expirationDate);
			ResultSet rs = ps.executeQuery();
			
			if(rs.isBeforeFirst()) {
				rs.next();
				result = Integer.valueOf(rs.getInt("seq_no"));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return result;
	}

	/**
	 * Insert into customer_profile
	 * return seq_no of the new entry 
	 */
	public Integer insertCustomerProfile(CustomerProfile customer) {
		String sql = "INSERT INTO csp584_project.customer_profile"
					+ "(first_name, last_name, email"
					+ ", phone, address, city"
					+ ", state, zip, cc_num, cc_exp)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
		;
		Integer result = null;
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, customer.getFirstName());
			ps.setString(2, customer.getLastName());
			ps.setString(3, customer.getEmail());
			ps.setString(4, customer.getPhone());
			ps.setString(5, customer.getAddress());
			ps.setString(6, customer.getCity());
			ps.setString(7, customer.getState());
			ps.setString(8, customer.getZip());
			ps.setString(9, customer.getCreditCardNum());
			ps.setString(10, customer.getExpirationDate());
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		sql = "SELECT LAST_INSERT_ID()";
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			rs.next();
			result = Integer.valueOf(rs.getInt(1));
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}		
		return result;
	}

	public void insertOrder(Order order) {
		String sql = "INSERT INTO csp584_project.order"
				+ "(user, customer, hotel"
				+ ", room, order_date, check_in"
				+ ", check_out, status)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?);"
		;
		try(PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, order.getUser().getuId());
			ps.setInt(2, order.getCustomer().getSeqNo());
			ps.setInt(3, order.getHotel().getSeqNo());
			ps.setInt(4, order.getRoomType().getSeqNo());
			ps.setDate(5, Date.valueOf(order.getOrderDate()));
			ps.setTimestamp(6, Timestamp.valueOf(order.getCheckInDateTime()));
			ps.setTimestamp(7, Timestamp.valueOf(order.getCheckOutDateTime()));
			ps.setString(8, order.getStatus().toString());
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
}
