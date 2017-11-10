package team6.helper;

import java.sql.ResultSet;
import java.sql.SQLException;

import team6.dao.MySQLDatabaseOperator;

public class ValidateUser {

	
	
	public static boolean IsUPCombineCorrect(String username, String password) {
		
		ResultSet rs = MySQLDatabaseOperator.GetInstance().FetchQuery("SELECT PASSWORD FROM USER WHERE USERNAME = '" + username +
																	  "' and PASSWORD = '" + password + "'");
		try {
			
			if(rs.next())
				return true;
			
			
		} catch (Exception e) {
			
			System.out.println("Error in GetPasswordFromDB");
			
		}
		
		return false;
		
		
	}
	
	
	public static boolean IsUsernameAlreadyTaken(String username) {
		
		
			
		ResultSet rs = MySQLDatabaseOperator.GetInstance().FetchQuery("SELECT * FROM USER WHERE USERNAME = '" + username + "'");
		
		try {
			
			if(rs.next())
				return true;
			
			
		} catch (Exception e) {
			
			System.out.println("Error in IsUsernameAlreadyTaken");
			
		}
		
		return false;
			
	}

}
