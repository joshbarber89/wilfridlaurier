package stats.ec;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatsModelTest {
	
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/test";
	static final String USER = "root";
	static final String PASS = "";

	public static void main(String[] args) throws IOException {
		
		System.out.println("Save hdstats to file");
		try {
			StatsSummary ss = new StatsSummary();
			ss.setCount(1);
			ss.setMin(1);
			ss.setMax(1);
			ss.setMean(1);
			ss.setStd(1);
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("C:/enterprise/tmp/model/hdstats.bin"));
			os.writeObject(ss);
			os.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	
		System.out.println("Read hdstats from file");
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream("C:/enterprise/tmp/model/hdstats.bin"));
			StatsSummary ss = new StatsSummary();
			ss = 	(StatsSummary) is.readObject();
			is.close();
			System.out.print(ss.toString());

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs;
		String sql;

		try {
		
			System.out.println("Read hdstats from file and save to DB Model table");
			
			ObjectInputStream is = new ObjectInputStream(new FileInputStream("C:/enterprise/tmp/model/hdstats.bin"));
			StatsSummary ss = new StatsSummary();
			ss = 	(StatsSummary) is.readObject();
			is.close();
			
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			statement = connection.createStatement();
			sql = "INSERT INTO MODEL (name, classname, object) VALUES ('hdstats', ?, ?)";
			PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql);
			ps.setString(1, ss.getClass().getName());
			ps.setObject(2, ss);
			ps.executeUpdate();
			ps.close();
			statement.close();
			
			
			
			System.out.println("Read hdstats from DB Model table");			
			statement = connection.createStatement();
			rs = statement.executeQuery("SELECT * FROM model where name='hdstats'");
			if (rs.next()) {
				
				System.out.println(rs.getString("name"));
				String name = rs.getString("name");
				if (name.equals("hdstats")) {
					byte[] buf = rs.getBytes("object");

					if (buf != null) {

						ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
						
						ss = 	(StatsSummary) objectIn.readObject();
						is.close();
						System.out.print(ss.toString());

					}
				}
			}
			rs.close();
			statement.close();
			
			
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