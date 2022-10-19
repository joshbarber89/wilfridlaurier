/*
 *  DB client test
 *  Lab task for CP630
 *  Author: HBF
 *  version: 2020-09-21 
 */
package ec;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBClientDrop {
	
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
                  
            sql = "DROP TABLE euser";
            statement.execute(sql);
            sql = "DROP TABLE model";
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