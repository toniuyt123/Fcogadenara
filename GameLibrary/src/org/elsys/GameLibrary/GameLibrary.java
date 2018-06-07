package org.elsys.GameLibrary;

import java.sql.*;

public class GameLibrary {
	public static void createDatabase(Connection conn) {
		try {
			Statement createQuery = conn.createStatement();
			createQuery.execute("CREATE DATABASE GameLibrary");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/GameLibrary?user=root&password=root");
			
			Statement query = conn.createStatement();			
			query.execute("CREATE TABLE Publishers ("
					+ "Id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,"
					+ "Name VARCHAR(100) NOT NULL,"
					+ "EstablishedDate DATE NOT NULL"
					+ ");");
			
			query.execute("CREATE TABLE RatingAsWord ("
					+ "RangeStart NUMERIC(4,2) NOT NULL,"
					+ "RangeEnd NUMERIC(4,2) NOT NULL,"
					+ "RatingAsWord VARCHAR(15) NOT NULL"
					+ ");");
			
			query.execute("CREATE TABLE Tags("
					+ "Id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,"
					+ "Tag VARCHAR(30) NOT NULL"
					+ ");");
			
			query.execute("CREATE TABLE Statuses("
					+ "Id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,"
					+ "Status VARCHAR(30) NOT NULL"
					+ ");");
			
			query.execute("CREATE TABLE Games ("
					+ "Id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,"
					+ "Title VARCHAR(100),"
					+ "PublisherId INTEGER NOT NULL,"
					+ "ReleaseDate DATE NOT NULL,"
					+ "Rating NUMERIC(4, 2),"
					+ "Status INTEGER,"
					+ "FOREIGN KEY(PublisherId) REFERENCES Publishers(Id),"
					+ "FOREIGN KEY(Status) REFERENCES Statuses(Id)"
					+ ");");
			query.execute("CREATE TABLE GamesTags("
					+ "GameId INTEGER NOT NULL,"
					+ "TagId INTEGER NOT NULL,"
					+ "PRIMARY KEY (GameId, TagId),"
					+ "FOREIGN KEY(GameId) REFERENCES Games(Id),"
					+ "FOREIGN KEY(TagId) REFERENCES Tags(Id)"
					+ ");");
			
			conn.setAutoCommit(false);
			try {
				String ratingSql = "INSERT INTO RatingAsWord VALUES(?, ?, ?)";
				PreparedStatement ratingSt = conn.prepareStatement(ratingSql);
				String ratings[] = {"Appaling", "Horrible", "Very Bad", "Bad", "Average", "Fine", "Good", "Very Good", "Great", "Masterpiece"};
				System.out.println(ratings.length);
				for(int i = 0;i < ratings.length;i++) {
					System.out.println(i);
					ratingSt.setObject(1, i, java.sql.Types.NUMERIC);
					ratingSt.setObject(2, i + 1, java.sql.Types.NUMERIC);
					ratingSt.setString(3, ratings[i]);
					ratingSt.executeUpdate();
				}
				
				conn.commit();
			} catch (Exception e){
				conn.rollback();
				throw e;
			} finally {
				conn.setAutoCommit(false);
			}
			/*PreparedStatement prQuery = conn.prepareStatement("INSERT INTO Games(Title) VALUES (?)");
			prQuery.setString(1, "Fortnite");
			int affectedRows = prQuery.executeUpdate();
			System.out.println(affectedRows);*/
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
}
