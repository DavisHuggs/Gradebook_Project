/*
 * Assignment: Gradebook Project
 * Name: Davis Huggins
 */
package gradebook.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DBUtil {

  private static Connection connection;
  private static String username;
  private static String password;
  private static boolean didntWork = false;

  private DBUtil() {}

  public static synchronized Connection getConnection(Scanner in){
    if (connection != null && didntWork == false) {
      return connection;
    } else {
      try {
    		System.out.println("what is your username?");
    		username = in.next();
    		System.out.println("what is your password?");
    		password = in.next();
    	  
        // set the db url, username, and password
        System.out.println("\ntrying a connection!");
        String url = "jdbc:mysql://myawsdb.cno2zewghfii.us-east-1.rds.amazonaws.com";

        // get and return connection

        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, username, password);
        System.out.println("\ngot a connection!\n");
        return connection;
      } catch (SQLException | ClassNotFoundException e) {
    	  System.out.println("\ncould not connect to MySQL databse...\t\ttry again\n");
    	  didntWork = true;
      }
    }
	return connection;
  }

  public static synchronized void closeConnection(){
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        connection = null;
      }
    }
  }
}
