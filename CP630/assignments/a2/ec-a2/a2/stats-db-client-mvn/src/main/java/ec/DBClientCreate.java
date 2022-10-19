package ec;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBClientCreate {

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/test";
	static final String USER = "root";
	static final String PASS = "";
	
    
    public static void main(String[] args) throws Exception {
        Connection connection = null;
        Statement statement = null;
        
        String sql;

        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            sql = "CREATE TABLE IF NOT EXISTS euser ("
            		+ "id INT AUTO_INCREMENT, "
            		+ "name VARCHAR(40) NOT NULL UNIQUE, "
            		+ "password VARCHAR(255) NOT NULL, "
            		+ "role TINYINT NOT NULL,"
            		+ "PRIMARY KEY (id)) ";
            statement.execute(sql);
            
            sql = "CREATE TABLE IF NOT EXISTS model ("
            		+ "id INT AUTO_INCREMENT PRIMARY KEY, "
            		+ "name VARCHAR(40) NOT NULL, "
            		+ "classname VARCHAR(255) NOT NULL, "
            		+ "object MEDIUMBLOB,"
            		+ "date TIMESTAMP)";            
            
            statement.execute(sql);

        } catch (SQLException e) { // Handle errors for JDBC
            e.printStackTrace();
        } catch (Exception e) {   // Handle errors for Class.forName
            e.printStackTrace();
        } finally { // finally block used to close resources
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    

}