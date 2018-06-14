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
						+ "EstablishedDate DATE NOT NULL,"
						+ "PasswordHash VARCHAR(250) NOT NULL"
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
						+ "Title VARCHAR(100) NOT NULL,"
						+ "PublisherId INTEGER NOT NULL,"
						+ "ReleaseDate DATE NOT NULL,"
						+ "FOREIGN KEY(PublisherId) REFERENCES Publishers(Id)"
						+ ");");
				
				query.execute("CREATE TABLE GamesTags("
						+ "GameId INTEGER NOT NULL,"
						+ "TagId INTEGER NOT NULL,"
						+ "PRIMARY KEY (GameId, TagId),"
						+ "FOREIGN KEY(GameId) REFERENCES Games(Id),"
						+ "FOREIGN KEY(TagId) REFERENCES Tags(Id)"
						+ ");");
				
				query.execute("CREATE TABLE Users ("
						+ "Id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,"
						+ "RealName VARCHAR(100) NOT NULL,"
						+ "Username VARCHAR(100) NOT NULL,"
						+ "Age INTEGER NOT NULL,"
						+ "PasswordHash VARCHAR(250) NOT NULL,"
						+ "PasswordSalt BLOB,"
						+ "CONSTRAINT U_User UNIQUE (UserName)"
						+ ");");
				
				query.execute("CREATE TABLE GamesUsers ("
						+ "UserId INTEGER NOT NULL,"
						+ "GameId INTEGER NOT NULL,"
						+ "Rating NUMERIC(4, 2),"
						+ "Status INTEGER,"
						+ "PRIMARY KEY (GameId, UserId),"
						+ "FOREIGN KEY(GameId) REFERENCES Games(Id),"
						+ "FOREIGN KEY(UserId) REFERENCES Users(Id),"
						+ "FOREIGN KEY(Status) REFERENCES Statuses(Id)"
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

					insertSql = "INSERT INTO Statuses (Status) VALUES(?)";
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
			e.printStackTrace();
		}
	}
	
	public static void samplePopulate(Connection conn) throws SQLException {
		conn.setAutoCommit(false);
		try {
			Statement stm = conn.createStatement();
			String sql = "INSERT INTO Publishers (Name, EstablishedDate, PasswordHash) VALUES('Epic Games', '1991-01-01', 'hashhash')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Publishers (Name, EstablishedDate, PasswordHash) VALUES('Bethesda Softworks', '1986-06-28', 'badibimm')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Publishers (Name, EstablishedDate, PasswordHash) VALUES('Nintendo', '1889-09-23', 'lolololo')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Publishers (Name, EstablishedDate, PasswordHash) VALUES('Blizzard Entertainment', '1991-02-08', '12345678')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Publishers (Name, EstablishedDate, PasswordHash) VALUES('EA', '1982-05-28', '87654321')";
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
			sql = "INSERT INTO Games (Title, PublisherId, ReleaseDate) VALUES('The Elder Scrolls V: Skyrim', 2, '2011-11-11')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Games (Title, PublisherId, ReleaseDate) VALUES('Fallout 4', 2, '2015-11-10')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Games (Title, PublisherId, ReleaseDate) VALUES('The Legend of Zelda: Breath of the Wild', 3, '2017-03-03')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Games (Title, PublisherId, ReleaseDate) VALUES('Super Mario Bros. 2', 3, '1985-09-13')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Games (Title, PublisherId, ReleaseDate) VALUES('Bayonetta 2', 3, '2014-09-20')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Games (Title, PublisherId, ReleaseDate) VALUES('World of Warcraft', 4, '2004-11-23')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Games (Title, PublisherId, ReleaseDate) VALUES('Warcraft II: Tides of Darkness', 4, '1995-12-09')";
			stm.executeUpdate(sql);

			sql = "INSERT INTO GamesTags VALUES (1, 1), (1, 10), (1, 8),"
					+ "(2, 1), (2, 7), (2, 9), " + "(4, 1), (4, 9), "
					+ "(5, 2), (5, 5)," + "(6, 7)," + "(7, 3), (7, 1), (7, 9), (7, 7),"
					+ "(8, 2), (8, 8);";
			stm.executeUpdate(sql);
			
			sql = "INSERT INTO Users (RealName, Username, Age, PasswordHash) VALUES('Antonio Milev', 'Toniuyt', 17, 'kekeckekec')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Users (RealName, Username, Age, PasswordHash) VALUES('Tele Tubis', 'Gamercheto', 42, 'ednodvetri')";
			stm.executeUpdate(sql);
			sql = "INSERT INTO Users (RealName, Username, Age, PasswordHash) VALUES('Todor The Taliban', 'TTT', 18, 'ugabugauga')";
			stm.executeUpdate(sql);
			
			sql = "INSERT INTO GamesUsers VALUES (1, 1, 8.00, 2), (1, 5, 10.00, 3), (1, 7, 9.69, 3)";
			stm.executeUpdate(sql);
			sql = "INSERT INTO GamesUsers (UserId, GameId) VALUES (2, 2), (2, 6)";
			stm.executeUpdate(sql);
			sql = "INSERT INTO GamesUsers (UserId, GameId, Rating) VALUES (3, 5, 5.00), (3, 8, 3.45)";
			stm.executeUpdate(sql);
			
			conn.commit();
		} catch (Exception e){
			conn.rollback();
			throw e;
		} finally {
			conn.setAutoCommit(false);
		}
	}	
	
	public static void showUserGames(User user, Connection conn) {
		try {
			String selectSql = "SELECT g.Id, g.Title, p.Name, gu.Rating, s.Status FROM GamesUsers gu\n" + 
					"LEFT JOIN Games g ON g.Id = gu.GameId\n" + 
					"LEFT JOIN Users u ON u.Id = gu.UserId\n" + 
					"LEFT JOIN Publishers p ON g.PublisherId = p.Id\n" + 
					"LEFT JOIN Statuses s ON gu.Status = s.Id\n" + 
					"WHERE u.Id = ?;";
			PreparedStatement prp = conn.prepareStatement(selectSql);
			prp.setInt(1, user.getId());
			ResultSet result = prp.executeQuery();
			
			int len = 20;
			System.out.println(String.format("%1$-3s | %2$-"+len+"s | %3$-"+len+"s | %4$-6s | %5$-13s |", "Num", "Title", "Publisher", "Rating", "Status"));
			System.out.println("----------------------------------------------------------------------------");
			int num = 0;
			while(result.next()) {
				String title = result.getString("Title").substring(0, Math.min(len, result.getString("Title").length()));
				String name = result.getString("Name").substring(0, Math.min(len, result.getString("Name").length()));
				
				System.out.println(String.format("%1$-3d | %2$-"+len+"s | %3$-"+len+"s | %4$-6s | %5$-13s |"
						, ++num, title, name, result.getDouble("Rating"), result.getString("Status")));
				System.out.println("----------------------------------------------------------------------------");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void showAllGames(Connection conn) {
		try {
			String selectSql = "SELECT n.Id, n.Title, n.Name, n.ReleaseDate, n.Rating, GROUP_CONCAT(n.Tag SEPARATOR ', ') AS Tags FROM (\n" + 
					"	SELECT g.Id, g.Title, p.Name, g.ReleaseDate, AVG(gu.Rating) AS Rating, t.Tag FROM Games g\n" + 
					"	LEFT JOIN Publishers p ON g.PublisherId = p.Id\n" + 
					"	LEFT JOIN GamesTags gt ON g.Id = gt.GameId\n" + 
					"	LEFT JOIN Tags t ON t.Id = gt.TagId\n" + 
					"	LEFT JOIN GamesUsers gu ON g.Id = gu.GameId\n" + 
					"	GROUP BY g.Id, g.Title, p.Name, g.ReleaseDate, gt.GameId, t.Id ) AS n\n" + 
					"GROUP BY n.Id, n.Title, n.Name, n.ReleaseDate, n.Rating;";
			Statement st = conn.createStatement();
			ResultSet result = st.executeQuery(selectSql);
			
			int len = 20;
			System.out.println(String.format("%1$-3s | %2$-"+len+"s | %3$-"+len+"s | %4$-10s | %5$-6s | %6$-25s |", "Id", "Title", "Publisher", "Released", "Rating", "Tags"));
			System.out.println("-----------------------------------------------------------------------------------------------------");
			
			while(result.next()) {
				String tags = result.getString("Tags");
				if(tags == null) {
					tags = "";
				} else {
					tags = tags.substring(0, Math.min(len + 5, result.getString("Tags").length()));
				}
				String title = result.getString("Title").substring(0, Math.min(len, result.getString("Title").length()));
				String name = result.getString("Name").substring(0, Math.min(len, result.getString("Name").length()));
				
				System.out.println(String.format("%1$-3d | %2$-"+len+"s | %3$-"+len+"s | %4$-10s | %5$-6.2f | %6$-25s |"
						, result.getInt("Id"), title, name, result.getDate("ReleaseDate"), result.getDouble("Rating"), tags));
				System.out.println("-----------------------------------------------------------------------------------------------------");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
