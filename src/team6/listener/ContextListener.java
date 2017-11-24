package team6.listener;

import javax.servlet.*;

import team6.dao.MySQLDatabaseOperator;

public class ContextListener implements ServletContextListener {
	
	private MySQLDatabaseOperator mySql = MySQLDatabaseOperator.INSTANCE;
    @Override
    public void contextDestroyed(ServletContextEvent e) {
    	closeMySqlConnection();
    }

	@Override
    public void contextInitialized(ServletContextEvent e) {
    	initMySqlConnection(e.getServletContext());
    }

	private void initMySqlConnection(ServletContext sc) {
		mySql.initConnection();
		mySql.setupDb(sc);
	}
	
    private void closeMySqlConnection() {
    	mySql.closeConnection();
	}
}