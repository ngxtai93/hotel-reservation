package team6.listener;

import javax.servlet.*;

import team6.dao.MySQLDatabaseOperator;

public class ContextListener implements ServletContextListener {
    @Override
    public void contextDestroyed(ServletContextEvent e) {
    	closeMySqlConnection();
    }

	@Override
    public void contextInitialized(ServletContextEvent e) {
    	initMySqlConnection();
    }

	private void initMySqlConnection() {
		MySQLDatabaseOperator.INSTANCE.initConnection();
	}
	
    private void closeMySqlConnection() {
		MySQLDatabaseOperator.INSTANCE.closeConnection();
	}
}