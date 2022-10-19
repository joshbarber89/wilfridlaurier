package ec;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import weka.classifiers.functions.LinearRegression;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class ModelSelect {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/test";
	static final String USER = "root";
	static final String PASS = "";
	
	public static void main(String[] args) throws Exception {

		Connection connection = null;
		ResultSet resultSet = null;
		Statement statement = null;
		String sql;

		try {

			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			statement = connection.createStatement();

            sql = "SELECT * FROM model WHERE name = 'weka-lr' ORDER BY date DESC LIMIT 1";
            resultSet = statement.executeQuery(sql);  
            
            if (resultSet != null) {            	
	            while (resultSet.next()) {
		        	byte[] buf = resultSet.getBytes("object");
		        	
		            if (buf != null) {                      
		            	ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
		            	LinearRegression cls = (LinearRegression)  objectIn.readObject(); 
		            	
		        		Instances predicationDataSet = DataSource.read("data/house_unknown.arff");		    
		        		predicationDataSet.setClassIndex(predicationDataSet.numAttributes()-1);
		        		
		        	    // label instances
		        	    for (int i = 0; i < predicationDataSet.numInstances(); i++) {
		        	      double clsLabel = cls.classifyInstance(predicationDataSet.instance(i));
		        	      System.out.println(predicationDataSet.instance(i));
		        	      predicationDataSet.instance(i).setClassValue(clsLabel);
		        	    }
		        	    System.out.println(predicationDataSet.toString());
		            	
		            }                    	                    	
	            }                
            }

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
