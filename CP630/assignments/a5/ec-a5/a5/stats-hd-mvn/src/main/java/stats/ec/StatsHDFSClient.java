package stats.ec;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import java.sql.*;

public class StatsHDFSClient {

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/test";
	static final String USER = "root";
	static final String PASS = "";
	
	public static void main(String[] args) throws IOException {
		String hdfsPath = "hdfs://localhost:19000";

		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", hdfsPath);

		FileSystem fs = FileSystem.get(conf);

		FileStatus[] fsStatus = fs.listStatus(new Path("/"));
		for (int i = 0; i < fsStatus.length; i++) {
			System.out.println(fsStatus[i].getPath().toString());
		}

		// read a file
		Path path = new Path("/output/part-r-00000");
		if (!fs.exists(path)) {
			System.out.println("File " + " does not exists");
		} else {

			double count = 0, mean=0, min=0, max=0, std=0;
			try {

				FSDataInputStream in = fs.open(path);
				Scanner sin = new Scanner(in);
				String line;
				while (sin.hasNext()) {
					line = sin.nextLine();
					String[] arr = line.split("\\s+");
					
					String[] valuearr = arr[1].split(",");

					count =  Double.parseDouble(valuearr[0]);
					mean =  Double.parseDouble(valuearr[1]);
					min =  Double.parseDouble(valuearr[2]);
					max =  Double.parseDouble(valuearr[3]);
					std =  Double.parseDouble(valuearr[4]);

					System.out.println("count:"+count);
					System.out.println("mean:"+mean);
					System.out.println("min:"+min);
					System.out.println("max:"+max);
					System.out.println("std:"+std);
							
				}
				sin.close();
				

				StatsSummary ss = new StatsSummary(count, min, max, mean, std);
				ObjectOutputStream os = new ObjectOutputStream(
						new FileOutputStream("C:/enterprise/tmp/model/hdstats.bin"));
				os.writeObject(ss);
				in.close();
				os.close();
				
				Connection connection = null;
				Statement statement = null;
				ResultSet rs;
				String sql;
				
				try {

					Class.forName(JDBC_DRIVER);
					connection = DriverManager.getConnection(DB_URL, USER, PASS);
					statement = connection.createStatement();

					sql = "INSERT INTO MODEL (name, classname, object) VALUES ('hdstats', ?, ?)";
					PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql);
					StatsSummary ssobj = new StatsSummary();
				
					ps.setString(1, ssobj.getClass().getName());
					ps.setObject(2, ss);
					
					ps.executeUpdate();

					ps.close();
					
					System.out.println("saved to db!");

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
				
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

	}
}