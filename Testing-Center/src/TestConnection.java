import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Use this class to interface with the database on the command line.
 * Most of the code can be repurposed for general use in the program.
 * 
 * @author danharel
 *
 */

public class TestConnection {

	public static void main(String[] args) {
		boolean connectionEstablished = false;
		
		System.out.println("-------- MySQL JDBC Connection Testing ------------");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}

		System.out.println("MySQL JDBC Driver Registered!");
		Connection conn = null;

		try {
			conn = DriverManager.getConnection("jdbc:mysql://mysql2.cs.stonybrook.edu:3306/ssattar","ssattar", "108862829");

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
		try {
			if (conn != null) {
				System.out.println("You made it, take control your database now!");
				connectionEstablished = true;
			} else {
				System.out.println("Failed to make connection!");
			}

		} catch(Exception e){
			e.printStackTrace();
		}
		
		while (connectionEstablished) {
			Scanner sc = new Scanner(System.in);
			String query;
			
			System.out.print("Enter SQL statement: ");
			
			while (!(query = sc.nextLine()).equals("")) {
				try {
					Statement statement = conn.createStatement();
					boolean hasResultSet = statement.execute(query);
					if (hasResultSet) {
						ResultSet rs = statement.getResultSet();
						ResultSetMetaData rsmd = rs.getMetaData();
						int numColumns = rsmd.getColumnCount();
						while (rs.next()) {
							for (int i = 0; i < numColumns; i++) {
								System.out.println(rs.getString(i+1));
							}
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("Please check your SQL statement");
				}
				
				System.out.print("Enter SQL statement: ");
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				connectionEstablished = false;
			}
		}
	}
}