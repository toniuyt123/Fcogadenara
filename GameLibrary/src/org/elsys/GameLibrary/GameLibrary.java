package org.elsys.GameLibrary;

import java.sql.*;

public class GameLibrary {
	public static void createDatabase(Connection conn) {
		try {
			Statement createQuery = conn.createStatement();
			ResultSet rs = createQuery.executeQuery("SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'GameLibrary'");
			rs.next();
			if(rs.getInt("COUNT(*)") <= 0) {
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
					String insertSql = "INSERT INTO RatingAsWord VALUES(?, ?, ?)";
					PreparedStatement prp = conn.prepareStatement(insertSql);
					String ratings[] = {"Appaling", "Horrible", "Very Bad", "Bad", "Average", "Fine", "Good", "Very Good", "Great", "Masterpiece"};
					for(int i = 0;i < ratings.length;i++) {
						prp.setObject(1, i, java.sql.Types.NUMERIC);
						prp.setObject(2, i + 1, java.sql.Types.NUMERIC);
						prp.setString(3, ratings[i]);
						prp.executeUpdate();
					}
					
					insertSql = "INSERT INTO Statuses(Status) VALUES(?)";
					prp = conn.prepareStatement(insertSql);
					String statuses[] = {"Plan to Play", "Playing", "Completed"};
					for(int i = 0;i < statuses.length;i++) {
						prp.setString(1, statuses[i]);
						prp.executeUpdate();
					}
					conn.commit();
				} catch (Exception e){
					conn.rollback();
					throw e;
				} finally {
					conn.setAutoCommit(false);
				}
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	public static void samplePopulate(Connection conn) {
		return;
	}
}
