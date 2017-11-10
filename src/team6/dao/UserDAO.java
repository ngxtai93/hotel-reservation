package team6.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import team6.entity.User;

public class UserDAO {

	private IDatabaseOperator dbOperator;
	
	public UserDAO() {
		dbOperator = new MySQLDatabaseOperator();
	}
	
	public User selectUser(String username) {
		String sql = "SELECT * from user WHERE username = '" + username + "'";
		ResultSet rs = dbOperator.FetchQuery(sql);
		
		User user = buildUser(rs);
		
		return user; 
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
	 * Insert user into MySQL given parameter 
	 */
	public void insertUser(String username, String password, String email) {
		String sql = "INSERT INTO `csp584_project`.`user` (`username`, `password`, `email`)"
					+ "VALUES ("
					+ "'" + username + "',"
					+ "'" + password + "',"
					+ "'" + email + "'"
					+ ");"
		;
		dbOperator.NonFetchQuery(sql);
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
