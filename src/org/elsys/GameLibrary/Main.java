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
				createQuery.execute("CREATE DATABASE IF NOT EXISTS GameLibrary");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/GameLibrary?user=root&password=root");
				
				Statement query = conn.createStatement();
				query.execute("CREATE TABLE IF NOT EXISTS Games ("
						+ "Id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,"
						+ "Title VARCHAR(100) );");
				PreparedStatement prQuery = conn.prepareStatement("INSERT INTO Games(Title) VALUES (?)");
				prQuery.setString(1, "Fortnite");
				int affectedRows = prQuery.executeUpdate();
				System.out.println(affectedRows);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}