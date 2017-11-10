package team6.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import team6.entity.User;

public class UserDAO {

	private IDatabaseOperator dbOperator;
	
	public UserDAO() {
		dbOperator = new MySQLDatabaseOperator();
	}
	
	/**
	 * Select user in MySQL given username and password 
	 */
	public User selectUser(String username, String password) {
		String sql = "SELECT * from user WHERE username = '" + username + "' AND password = '" + password + "'";
		ResultSet rs = dbOperator.FetchQuery(sql);
		
		User user = buildUser(rs);
		
		return user; 
	}

	/**
	 * Build User object from result set 
	 */
	private User buildUser(ResultSet rs) {
		User user = null;
		
		try {
			if(rs.first()) {	// found result
				user = new User();
				user.setuId(rs.getInt("uid"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}

}
