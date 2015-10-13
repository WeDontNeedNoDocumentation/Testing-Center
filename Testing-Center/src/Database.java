import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Database {

	private static Database instance = null;
	
	private final String HOST = "jdbc:mysql://mysql2.cs.stonybrook.edu:3306/ssattar";
	private final String USERNAME = "ssattar";
	private final String PASSWORD = "108862829";
	
	private Connection conn;

	private Database() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Could not find MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}

		System.out.println("MySQL JDBC Driver Found");
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(HOST, USERNAME, PASSWORD);

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
		try {
			if (conn != null) {
				System.out.println("Database connection established");
				this.conn = conn;
			} else {
				System.out.println("Failed to make connection");
			}

		} catch(Exception e){
			e.printStackTrace();
		}
		
		Statement statement = null;
		try {
			statement = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.out.println("Unable to create SQL statement.");
			e1.printStackTrace();
		}
		try {
			boolean hasResultSet = statement.execute("USE ssattar");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to execute statement: \"USE ssattar\"");
			e.printStackTrace();
		}
	}

	public static Database getDatabase() {
		if (instance == null) {
			instance = new Database();
		}
		return instance;
	}
	
	public static void createDatabase() {
		getDatabase();
	}
	
	public List query(String queryString) {
		List results = new ArrayList();
		try {
			Statement statement = conn.createStatement();
			boolean hasResultSet = statement.execute(queryString);
			if (hasResultSet) {
				ResultSet rs = statement.getResultSet();
				ResultSetMetaData rsmd = rs.getMetaData();
				int numColumns = rsmd.getColumnCount();
				while (rs.next()) {
					for (int i = 0; i < numColumns; i++) {
						results.add(rs);
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Please check your SQL statement");
			e.printStackTrace();
		}
		
		return results;
	}

}
