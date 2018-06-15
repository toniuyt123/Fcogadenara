package org.elsys.GameLibrary;
import java.sql.*;
import java.util.Scanner;

public class Publisher {
	private String name;
	private String establishedDate;
	private String passHash;
	private byte[] passSalt;
	private int id;
	
	public Publisher(int id, String name, String passHash, byte[] passSalt, String establishedDate) {
		this.id = id;
		this.name = name;
		this.passHash = passHash;
		this.establishedDate = establishedDate;
		this.passSalt = passSalt;
	}
	
	public Publisher(String name, String passHash, byte[] passSalt,String establishedDate) {
		this.name = name;
		this.passHash = passHash;
		this.establishedDate = establishedDate;
		this.passSalt = passSalt;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getEstablishedDate() {
		return this.establishedDate;
	}
	
	public String getPassHash() {
		return this.passHash;
	}
	
	public int getId() {
		return this.id;
	}
	
	public byte[] getPassSalt() {
		return this.passSalt;
	}
	
	
	
	public static byte[] getSalt(Connection conn, String publisherName) throws SQLException {
		PreparedStatement prp = null;
		conn.setAutoCommit(false);
		try {
			String selectString = "SELECT PasswordSalt FROM Publishers WHERE Name = ?";
			prp = conn.prepareStatement(selectString);
			prp.setString(1, publisherName);
			ResultSet rs = prp.executeQuery();
			rs.next();
			byte[] passsalt = rs.getBytes("PasswordSalt");
			System.out.println("GET +" + passsalt);
			return passsalt;
		}catch (Exception e){
			System.out.println(e.getMessage());
			return null;
		} finally {
			if (prp != null) {
				prp.close();
			}
		}
	}
	
	public static void addPublisher(Connection conn, Publisher publisher) throws SQLException {
		PreparedStatement prp = null;
		conn.setAutoCommit(false);
		try {
			String insertSql = "INSERT INTO Publishers "
					+ "(Name, EstablishedDate, PasswordHash, PasswordSalt)"
					+ "VALUES(?, ?, ?, ?)";
			prp = conn.prepareStatement(insertSql);
			prp.setString(1, publisher.getName());
			prp.setString(2, publisher.getEstablishedDate());
			prp.setString(3, publisher.getPassHash());
			prp.setBytes(4, publisher.getPassSalt());
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
	
	public static Publisher getPublisher(Connection conn, String name, String passHash) throws SQLException {
		PreparedStatement prp = null;
		conn.setAutoCommit(false);
		try {
			String selectString = "SELECT * FROM Publishers WHERE Name = ? AND PasswordHash = ?";
			prp = conn.prepareStatement(selectString);
			prp.setString(1, name);
			prp.setString(2, passHash);
			ResultSet rs = prp.executeQuery(); 
			rs.next();
			String pname = rs.getString("Name");
			
			String established = rs.getString("EstablishedDate");
			byte[] passsalt = rs.getBytes("PasswordSalt");
			int id = rs.getInt("Id");
			return new Publisher(id, pname, passHash, passsalt, established);
		}catch (Exception e){
			System.out.println(e.getMessage());
			return null;
		} finally {
			if (prp != null) {
				prp.close();
			}
		}
	}
	
	public static void deletePublisher(Connection conn, String name, String passHash) throws SQLException {
		PreparedStatement prp = null;
		conn.setAutoCommit(false);
		try {
			String selectString = "DELETE * FROM Publishers WHERE UserName = ? AND PasswordHash = ?";
			prp = conn.prepareStatement(selectString);
			prp.setString(1, name);
			prp.setString(2, passHash);
			prp.executeQuery();
		}catch (Exception e){
			System.out.println(e.getMessage());
		} finally {
			if (prp != null) {
				prp.close();
			}
		}
	}
	
	public void addGame(Connection conn, Scanner in)throws SQLException {
		PreparedStatement prp = null;
		conn.setAutoCommit(false);
		try {
			String insertString = "INSERT INTO Games "
								+ "(Title, PublisherId, ReleaseDate)"
								+ "VALUES(?, ?, ?)";
			prp = conn.prepareStatement(insertString);
			System.out.println("Enter game title");
			prp.setString(1, in.next());
			prp.setInt(2, getId());
			prp.setDate(3, Date.valueOf(java.time.LocalDate.now()));
			conn.commit();
		}catch (Exception e){
			System.out.println(e.getMessage());
		} finally {
			if (prp != null) {
				prp.close();
			}
		}
	}
	
	public void removeGame(Connection conn, Scanner in)throws SQLException {
		PreparedStatement prp = null;
		conn.setAutoCommit(false);
		try {
			String insertString = "DELETE FROM Games g"
								+ "WHERE g.PublisherId = ? AND g.Title = ?";
			prp = conn.prepareStatement(insertString);
			prp.setInt(1, getId());
			System.out.println("Enter game title");
			prp.setString(2, in.nextLine());
			if(Menu.confirm("Are you sure you want to delete this game?", in)) {
				conn.commit();
			}
		}catch (Exception e){
			System.out.println(e.getMessage());
		} finally {
			if (prp != null) {
				prp.close();
			}
		}
	}
	
	public void updateGame(Connection conn, Scanner in)throws SQLException {
		PreparedStatement prp = null;
		conn.setAutoCommit(false);
		try {
			String insertString = "UPDATE Games g"
								+ "SET g.Title = ?, g.ReleaseDate = ?"
								+ "WHERE g.PublisherId = ? AND g.Title = ?";
			prp = conn.prepareStatement(insertString);
			System.out.println("Enter game title");
			prp.setString(4, in.nextLine());
			prp.setInt(3, getId());
			System.out.println("Enter new title");
			prp.setString(1, in.nextLine());
			System.out.println("Enter new releaseDate");
			prp.setDate(2, Date.valueOf(in.nextLine()));
			if(Menu.confirm("Are you sure you want to delete this game?", in)) {
				conn.commit();
			}
		}catch (Exception e){
			System.out.println(e.getMessage());
		} finally {
			if (prp != null) {
				prp.close();
			}
		}
	}
}
