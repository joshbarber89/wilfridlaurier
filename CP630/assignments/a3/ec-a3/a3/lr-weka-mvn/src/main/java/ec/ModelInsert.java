package ec;

import java.sql.*;

import weka.classifiers.functions.LinearRegression;

import java.io.*;

public class ModelInsert {
	
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

			sql = "INSERT INTO MODEL (name, classname, object) VALUES ('weka-lr', 'weka_regresssion', ?)";
			PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql);			 	

			LinearRegression cls = (LinearRegression) weka.core.SerializationHelper.read("model/weka_regression.bin");
	
			// set input parameters			
			ps.setObject(1, cls);
			
			ps.executeUpdate();

			ps.close();
			
			System.out.print("Successfully saved!");

		} catch (SQLException e) { // Handle errors for JDBC
			e.printStackTrace();
		} catch (Exception e) { // Handle errors for Class.forName
			e.printStackTrace();
		} finally { // finally block used to close resources
			try {
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
}
