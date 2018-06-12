package org.elsys.GameLibrary;
import java.sql.*;

public class User {
	private String userName;
	private String realName;
	private String passHash;
	private int age;
	private int id;
	
	public User(int id, String userName, String realName, String passHash, int age) {
		this.id = id;
		this.userName = userName;
		this.realName = realName;
		this.passHash = passHash;
		this.age = age;
	}
	
	public User(String userName, String realName, String passHash, int age) {
		this.userName = userName;
		this.realName = realName;
		this.passHash = passHash;
		this.age = age;
	}
	
	public String getRealName() {
		return this.realName;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public String getPassHash() {
		return this.passHash;
	}
	
	public int getAge() {
		return this.age;
	}
	
	public int getId() {
		return this.id;
	}
	
	public static void addUser(Connection conn, User user) throws SQLException {
		PreparedStatement prp = null;
		conn.setAutoCommit(false);
		try {
			String insertSql = "INSERT INTO Users "
					+ "(RealName, UserName, Age, PasswordHash)"
					+ "VALUES(?, ?, ?, ?)";
			prp = conn.prepareStatement(insertSql);
			prp.setString(1, user.getRealName());
			prp.setString(2, user.getUserName());
			prp.setInt(3, user.getAge());
			prp.setString(4, user.getPassHash());
			prp.executeUpdate();
			conn.commit();
		}catch (Exception e){
			System.out.println(e.getMessage());
			conn.rollback();
		} finally {
			conn.setAutoCommit(false);
			if (prp != null) {
				prp.close();
			}
		}
	}
	
	public static User getUser(Connection conn, String userName, String passHash) throws SQLException {
		PreparedStatement prp = null;
		conn.setAutoCommit(false);
		try {
			String selectString = "SELECT * FROM Users WHERE UserName = ? AND PasswordHash = ?";
			prp = conn.prepareStatement(selectString);
			prp.setString(1, userName);
			prp.setString(2, passHash);
			ResultSet rs = prp.executeQuery();
			rs.next();
			String realname = rs.getString("RealName");
			int age = rs.getInt("Age");
			int id = rs.getInt("Id");
			return new User(id, userName, realname, passHash, age);
		}catch (Exception e){
			System.out.println(e.getMessage());
			return null;
		} finally {
			if (prp != null) {
				prp.close();
			}
		}
	}
}
