package team6.dao;

import java.sql.ResultSet;

public interface IDatabaseOperator {
	
	// JDBC driver name and database URL
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	public static final String DB_URL="jdbc:mysql://localhost/CS584Project";

    //  Database credentials
	public static final String USER = "root";
	public static final String PASS = "root";
	
	public boolean NonFetchQuery(String query);
	
	public ResultSet FetchQuery(String query);

}
