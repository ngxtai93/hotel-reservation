package team6.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum MySQLDatabaseOperator implements IDatabaseOperator {
	INSTANCE;

	// JDBC driver name and database URL
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	public static final String DB_URL="localhost";
	public static final int DB_PORT= 3306;
	public static final String DB_SCHEMA = "csp584_project";
	public static final String DB_OPTION = "?useSSL=false";

    //  Database credentials
	public static final String USER = "root";
	public static final String PASS = "root";
	
	private Connection conn;

	private MySQLDatabaseOperator() {
		
	      try
	      {
	          Class.forName(JDBC_DRIVER).newInstance();
	      } 
	      catch (Exception e) 
	      {
	           e.printStackTrace();
	      }
	      
	}
	
	public Connection getConnection() {
		return conn;
	}

	@Override
	public void initConnection() {
		try {
	        String sqlUrl = "jdbc:mysql://"
	            +   DB_URL
	            +   ":"
	            +   DB_PORT
	            +   "/" + DB_SCHEMA
	            +   DB_OPTION
	        ;
	        conn = DriverManager.getConnection(sqlUrl, USER, PASS);
	
	        System.out.println("Connected to MySQL server " + sqlUrl);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
    }
	
	@Override
    public void closeConnection() {
        if(conn != null) {
            try {
                conn.close();
                System.out.println("MySQL connection closed.");
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
