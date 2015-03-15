package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import model.OracleJDBCConnection;


public class ContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context=sce.getServletContext();
        String dburl=context.getInitParameter("dbUrl");
        String dbusername=context.getInitParameter("dbUserName");
        String dbpassword=context.getInitParameter("dbPassword");
        try{
        OracleJDBCConnection.connectToDB(dburl, dbusername, dbpassword);
        }catch(Exception ex){
        	ex.printStackTrace();
        }
        System.out.println("Connection Establised.........");
    }

    public void contextDestroyed(ServletContextEvent sce) {
    	try{
    		
    		OracleJDBCConnection.closeConnection();
    		System.out.println("Connection Closed.........");
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }

}