package org.elsys.GameLibrary;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connectionString = "jdbc:mysql://localhost:3306/?user=root&password=root";
			//GameLibrary.createDatabase(DriverManager.getConnection(connectionString));
			connectionString = "jdbc:mysql://localhost:3306/GameLibrary1?user=root&password=root";
			conn = DriverManager.getConnection(connectionString);
			//GameLibrary.samplePopulate(conn);
			//GameLibrary.showUserGames(new User(1, "Toniuyt", "Antonio Milev", "kekeckekec", 17), conn);
			//GameLibrary.showAllGames(conn);
		
			BackMenu back = new BackMenu("0", "Back", null);
			ExitMenu exit = new ExitMenu("0", "Exit", null);
			RegisterMenu register = new RegisterMenu("1", "Register", null);
			LoginMenu login = new LoginMenu("2", "Login", null, conn);
			UserMenu userMenu = new UserMenu("1", "User", new ArrayList<Menu>(Arrays.asList(back, register, login)), conn);
			PublisherMenu publisherMenu = new PublisherMenu("2", "Publisher", new ArrayList<Menu>(Arrays.asList(back, register, login)), conn);
			MainMenu main = new MainMenu("-", "Main Menu", new ArrayList<Menu>(Arrays.asList(exit, userMenu, publisherMenu)));
	//		/main.show();
			Menu oldMenu = main, currMenu =  main;
			Scanner in = new Scanner(System.in);
			boolean shown = false;
			User currUser = null;
			Publisher currPubl = null;
			while(true) {
				if(!shown) {
					System.out.print("\n\n\n\n\n");
					currMenu.show();
					shown = true;
				}
				if(in.hasNext()) {
					Object res = currMenu.action(currMenu, in);
					if(res instanceof User)
					{
						if(currMenu instanceof RegisterMenu) {
							User.addUser(conn, (User) res);
						}else if(currMenu instanceof LoginMenu) {
							currUser = (User) res;
							userMenu.setCurrUser(currUser);
						}
						currMenu = currMenu.prevMenu;
						
					}else if(res instanceof Publisher)
					{
						if(currMenu instanceof RegisterMenu) {
							Publisher.addPublisher(conn, (Publisher) res);
						}else if(currMenu instanceof LoginMenu) {
							currPubl = (Publisher) res;
							publisherMenu.setcurrPublisher(currPubl);
						}
						currMenu = currMenu.prevMenu;
					}else if(res instanceof Menu) {
						currMenu = (Menu) res;
					}else if(res instanceof ExitMenu) {
						break;
					}
					shown = false;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	

}