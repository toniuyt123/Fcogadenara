package org.elsys.GameLibrary;
import java.sql.*;

public class Main {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connectionString = "jdbc:mysql://localhost:3306/?user=root&password=root";
			GameLibrary.createDatabase(DriverManager.getConnection(connectionString));
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}