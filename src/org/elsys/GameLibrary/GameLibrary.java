package org.elsys.GameLibrary;

import java.sql.*;

public class GameLibrary {
	public static void createDatabase(Connection conn) {
		try {
			Statement createQuery = conn.createStatement();
			createQuery.execute("CREATE DATABASE GameLibrary");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/GameLibrary?user=root&password=root");
			
			Statement query = conn.createStatement();
			query.execute("CREATE TABLE Games ("
					+ "Id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,"
					+ "Title VARCHAR(100),"
					+ "PublisherId INTEGER NOT NULL,"
					+ "ReleaseDate DATE NOT NULL,"
					+ "Rating NUMERIC(3, 2),"
					+ "Status STRING CHECK (Status IN ('Plan to play', 'Playing', 'Completed')),"
					+ "FOREIGN KEY(PublisherId) REFERENCES Publishers(Id)"
					+ ");");
			
			query.execute("CREATE TABLE Publishers ("
					+ "Id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,"
					+ "Name VARCHAR(100) NOT NULL,"
					+ "EstablishedDate DATE NOT NULL"
					+ ");");
			
			query.execute("CREATE TABLE RatingAsWord ("
					+ "RangeStart NUMERIC(3,2),"
					+ "RangeEnd NUMERIC(3,2),"
					+ "RatingAsWord VARCHAR(15)"
					+ ");");
			
			query.execute("CREATE TABLE Tags("
					+ "Id AUTO_INCREMENT NOT NULL PRIMARY KEY,"
					+ "Tag VARCHAR(30)"
					+ ");");
			
			query.execute("CREATE TABLE GamesTags("
					+ "GameId INTEGER NOT NULL,"
					+ "TagId INTEGER NOT NULL,"
					+ "PRIMARY KEY (GameId, TagId)"
					+ ");");
			/*PreparedStatement prQuery = conn.prepareStatement("INSERT INTO Games(Title) VALUES (?)");
			prQuery.setString(1, "Fortnite");
			int affectedRows = prQuery.executeUpdate();
			System.out.println(affectedRows);*/
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
}
