package org.elsys.GameLibrary;
import java.sql.*;

public class Main {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connectionString = "jdbc:mysql://localhost:3306/?user=root&password=root";
			//GameLibrary.createDatabase(DriverManager.getConnection(connectionString));
			connectionString = "jdbc:mysql://localhost:3306/GameLibrary?user=root&password=root";
			Connection conn = DriverManager.getConnection(connectionString);
			//GameLibrary.samplePopulate(conn);
			//GameLibrary.showUserGames(new User(1, "Toniuyt", "Antonio Milev", "kekeckekec", 17), conn);
			GameLibrary.showAllGames(conn);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}