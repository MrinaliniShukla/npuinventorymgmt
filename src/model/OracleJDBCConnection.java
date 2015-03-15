package model;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.Inventory;
 
public class OracleJDBCConnection {
	private static String dbDriver="oracle.jdbc.driver.OracleDriver";
	private static String dbSourceUrl="jdbc:oracle:thin:@localhost:1521:orcl";
	private static Connection connection = null;
	
	
	public OracleJDBCConnection() throws SQLException{
 
		/*System.out.println("-------- Oracle JDBC Connection Testing ------");
 
		try {
 
			Class.forName("oracle.jdbc.driver.OracleDriver");
 
		} catch (ClassNotFoundException e) {
 
			System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
			return;
 
		}
 
		System.out.println("Oracle JDBC Driver Registered!");
 		try {
 
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:orcl", "npu","npu");
 
		} catch (SQLException e) {
 
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		}
 		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}*/
		//connectToDB();
	}
	
	public static void connectToDB(String dbSourceUrl, String dbusername, String dbpassword) throws SQLException{
		try {
			Class.forName(dbDriver).newInstance();
			connection = DriverManager.getConnection(dbSourceUrl, dbusername,dbpassword);
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception e) {
			throw new SQLException("Unable to connect to database");
		}
	}
	
	public static Connection getDBConnection() throws SQLException{
		return connection;
	}
	
	public static void closeConnection()throws SQLException{
        if(connection!=null){
            try {
            	connection.close();
            } catch (SQLException ex) {
                 throw ex;
            }
        }

    }
	
 
}
