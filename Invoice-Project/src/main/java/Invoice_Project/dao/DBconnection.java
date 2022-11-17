package Invoice_Project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import Invoice_Project.errorHandler.DbException;

public class DBconnection {

  private static final String HOST ="localhost"; 
  private static final String USER = "invoice";
  private static final String PASSWORD = "invoice";
  private static final int PORT = 3306;
  private static final String SCHEMA = "invoice";
  
  
  public static Connection getConnection() {
    // @formatter:off
    String uri = String.format(
        "jdbc:mysql://%s:%d/%s?user=%s&password=%s", HOST, PORT, SCHEMA, USER,
        PASSWORD);
    
    try {
      Connection conn = DriverManager.getConnection(uri);
      System.out.println("Successfully Got connection with: " + SCHEMA);
      return conn;
  } catch (SQLException e) {
      System.out.println("Error getting Connection for: " + SCHEMA );
      throw new DbException("Unable to get connetion at \" + uri");
  }
}
  //@formatter:on
}
