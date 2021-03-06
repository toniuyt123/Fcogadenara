package org.elsys.GameLibrary;

import java.util.List;
import java.util.Scanner;
import java.sql.*;

public class LoginMenu extends Menu{
	Connection conn;
	
	public LoginMenu(String id, String name, List<Menu> subMenus, Connection conn) {
		super(id, name, subMenus);
		this.conn = conn;
	}

	@Override
	public Object action(Menu caller, Scanner in) {
		if(this.prevMenu instanceof UserMenu) {
			try {
				String userName = in.next();
				System.out.print("Enter Password: ");
				String password = in.next();
				byte[] salt;
				salt = User.getSalt(conn, userName);
				String passHash = RegisterMenu.getSecurePassword(password, salt);
				System.out.println(passHash);
				User res;
				res = User.getUser(conn, userName, passHash);
				if(res == null) {
					System.out.println("User error");
				}else {
					System.out.println("Logged in");
				}
				return res;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}else {
			try {
				String publisherName = in.next();
				System.out.print("Enter Password: ");
				String password = in.next();
				byte[] salt;
				salt = Publisher.getSalt(conn, publisherName);
				String passHash = RegisterMenu.getSecurePassword(password, salt);
				System.out.println(passHash);
				Publisher res;
				res = Publisher.getPublisher(conn, publisherName, passHash);
				if(res == null) {
					System.out.println("Publesher error");
				}else {
					System.out.println("Logged in");
				}
				return res;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
	}

	@Override
	public void show() {
		if(this.prevMenu instanceof UserMenu) {
			System.out.print("Enter Username Name: ");
		}else {
			System.out.print("Enter Publisher Name: ");
		}
	}
}
