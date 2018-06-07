package org.elsys.GameLibrary;
import java.sql.*;

public class Main {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connectionString = "jdbc:mysql://localhost:3306/?user=root&password=root";
			Connection conn = DriverManager.getConnection(connectionString);
			
			Statement createQuery = conn.createStatement();
			ResultSet rs = createQuery.executeQuery("SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'GameLibrary'");
			rs.next();
			if(rs.getInt("COUNT(*)") <= 0) {
				GameLibrary.createDatabase(conn);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}