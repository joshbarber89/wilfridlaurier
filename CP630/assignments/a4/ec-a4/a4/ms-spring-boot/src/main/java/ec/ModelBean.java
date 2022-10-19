package ec;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import weka.classifiers.Classifier;
import weka.classifiers.functions.LinearRegression;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

@Service
public class ModelBean {
	
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/test";
	static final String USER = "root";
	static final String PASS = "";
	
	public LinearRegression getModel() {
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
		            	
		            	return cls;
		            	
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
		return null;
	}
	
    public String predict(String[] attrs) throws Exception {    	
	   	
        ArrayList<Attribute> atts = new ArrayList<Attribute>();
        
        Instances data;       
        
        double[] vals;       
    	
    	for (int i = 0; i < attrs.length; i++) {    		
    		atts.add(new Attribute("att"+(i+1)));    		
    	}    	
    	
        data=new Instances("MyRelation",atts,0);
        
        vals=new double[data.numAttributes()];

    	for (int i = 0; i < attrs.length; i++) {
    		String attr = attrs[i];
    		vals[i] = Double.parseDouble(attr);    		
    	}    
    	
        
        data.add(new DenseInstance(1,vals));
        System.out.println(data);
        
    	Classifier cls = this.getModel();
    	
		Instance predicationDataInstance = data.lastInstance();
		double value = cls.classifyInstance(predicationDataInstance);
		System.out.println(value);

    	
		return Double.toString(value);
    }
}
