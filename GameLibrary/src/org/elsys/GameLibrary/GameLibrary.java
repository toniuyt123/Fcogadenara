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
	
	public static void samplePopulate(Connection conn) throws SQLException {
		conn.setAutoCommit(false);
		try {
			Statement stm = conn.createStatement();
			String sql = "INSERT INTO Publishers (Name, EstablishedDate) VALUES('Epic Games', '1991-01-01')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Publishers (Name, EstablishedDate) VALUES('Bethesda Softworks', '1986-06-28')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Publishers (Name, EstablishedDate) VALUES('Nintendo', '1889-09-23')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Publishers (Name, EstablishedDate) VALUES('Blizzard Entertainment', '1991-02-08')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Publishers (Name, EstablishedDate) VALUES('EA', '1982-05-28')";
			stm.executeUpdate(sql);
			
			sql = "INSERT INTO Tags (Tag) VALUES('3D')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Tags (Tag) VALUES('2D')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Tags (Tag) VALUES('MMORPG')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Tags (Tag) VALUES('MOBA')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Tags (Tag) VALUES('Platform')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Tags (Tag) VALUES('Horror')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Tags (Tag) VALUES('Adventure')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Tags (Tag) VALUES('Survival')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Tags (Tag) VALUES('Open-world')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Tags (Tag) VALUES('Battle Royale')";
			stm.executeUpdate(sql);
			
			sql = "INSERT INTO Games (Title, PublisherId, ReleaseDate) VALUES('Fortnite', 1, '2017-07-25')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Games (Title, PublisherId, ReleaseDate, Rating) VALUES('The Elder Scrolls V: Skyrim', 2, '2011-11-11', 9.00)";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Games (Title, PublisherId, ReleaseDate) VALUES('Fallout 4', 2, '2015-11-10')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Games (Title, PublisherId, ReleaseDate) VALUES('The Legend of Zelda: Breath of the Wild', 2, '2017-03-03')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Games (Title, PublisherId, ReleaseDate, Status) VALUES('Super Mario Bros.', 2, '1985-09-13', 2)";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Games (Title, PublisherId, ReleaseDate) VALUES('Bayonetta 2', 2, '2014-09-20')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Games (Title, PublisherId, ReleaseDate, Rating, Status) VALUES('World of Warcraft', 4, '2004-11-23', 10.00, 3)";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Games (Title, PublisherId, ReleaseDate) VALUES('Warcraft II: Tides of Darkness', 4, '1995-12-09')";
			stm.executeUpdate(sql);
			System.out.println("aaaa");
			sql = "INSERT INTO GamesTags VALUES (1, 1), (1, 10), (1, 8),"
					+ "(2, 1), (2, 7), (2, 9), " + "(4, 1), (4, 9), "
					+ "(5, 2), (5, 5)," + "(6, 7)," + "(7, 3), (7, 2), (7, 9), (7, 7),"
					+ "(8, 2), (8, 8);";
			stm.executeUpdate(sql);
			
			conn.commit();
		} catch (Exception e){
			conn.rollback();
			throw e;
		} finally {
			conn.setAutoCommit(false);
		}
	}
}
