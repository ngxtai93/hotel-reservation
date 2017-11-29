package team6.listener;

import javax.servlet.*;

import team6.dao.MongoDBDatabaseOperator;
import team6.dao.MySQLDatabaseOperator;

public class ContextListener implements ServletContextListener {
	
	private MySQLDatabaseOperator mySql = MySQLDatabaseOperator.INSTANCE;
	private MongoDBDatabaseOperator mongoDb = MongoDBDatabaseOperator.INSTANCE;
	
	@Override
    public void contextDestroyed(ServletContextEvent e) {
    	closeMySqlConnection();
    	closeMongoDbConnection();
    }

	@Override
	public void contextInitialized(ServletContextEvent e) {
		ServletContext sc = e.getServletContext();
		initMySqlConnection(sc);
		initMongoDbConnection(sc);
	}

	private void initMySqlConnection(ServletContext sc) {
		mySql.initConnection();
		mySql.setupDb(sc);
	}
	
    private void closeMySqlConnection() {
    	mySql.closeConnection();
	}

	private void initMongoDbConnection(ServletContext sc) {
		mongoDb.initConnection();
	}

	private void closeMongoDbConnection() {
		mongoDb.closeConnection();
	}
}