package org.elsys.GameLibrary;
import java.sql.*;

public class Main {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connectionString = "jdbc:mysql://localhost:3306/GameLibrary?user=root&password=root";
			User test = User.getUser(DriverManager.getConnection(connectionString), "Zdr", "Pass");
			
			System.out.println("userid : " + test.getId());
			System.out.println("username : " + test.getUserName());
			//connectionString = "jdbc:mysql://localhost:3306/GameLibrary?user=root&password=root";
			//GameLibrary.samplePopulate(DriverManager.getConnection(connectionString));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}