package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQLDatabaseOperator implements IDatabaseOperator {

	private static Connection conn;
	
	private static final MySQLDatabaseOperator mysql = new MySQLDatabaseOperator();
	
	public MySQLDatabaseOperator() {
		
	      try
	      {
	          Class.forName(JDBC_DRIVER);
	          conn = DriverManager.getConnection(DB_URL,USER,PASS);
	      } 
	      catch (Exception e) 
	      {
	           e.printStackTrace();
	      }
	      
	}
	
	public static MySQLDatabaseOperator GetInstance() {
		return MySQLDatabaseOperator.mysql;
	}

	@Override
	public boolean NonFetchQuery(String query) {
		
	      try
	      {
	          Statement stmt = conn.createStatement();
	          stmt.executeUpdate(query);
	      } 
	      catch (Exception e) 
	      {
				System.out.println("NonFetchQuery Failed For ");
				System.out.println(query);
				System.out.println(e.getMessage());
				return false;
	      }
	      
	      return true;
	      
	}

	@Override
	public ResultSet FetchQuery(String query) {
		
		ResultSet rs = null;
		
		try
		{
			
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			return rs;
			
		}
		catch (Exception e)
		{
			System.out.println("FetchQuery Failed For :");
			System.out.println(query +"\n");
			System.out.println(e.getMessage());
		}
		
		return rs;
	}

}
