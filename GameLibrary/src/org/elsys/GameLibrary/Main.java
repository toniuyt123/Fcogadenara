package org.elsys.GameLibrary;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		/*try {
			Class.forName("com.mysql.jdbc.Driver");
			String connectionString = "jdbc:mysql://localhost:3306/?user=root&password=root";
			GameLibrary.createDatabase(DriverManager.getConnection(connectionString));
			connectionString = "jdbc:mysql://localhost:3306/GameLibrary?user=root&password=root";
			GameLibrary.samplePopulate(DriverManager.getConnection(connectionString));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}*/
		BackMenu back = new BackMenu("0", "Back", null);
		RegisterMenu register = new RegisterMenu("1", "Register", new ArrayList<Menu>(Arrays.asList(back)));
		MainMenu main = new MainMenu("-", "Main Menu", new ArrayList<Menu>(Arrays.asList(back, register)));
//		/main.show();
		Menu oldMenu = main, currMenu =  main;
		Scanner in = new Scanner(System.in);
		boolean shown = false;
		while(true) {
			if(!shown) {
				System.out.print("\n\n\n\n\n");
				currMenu.show();
				shown = true;
			}
			if(in.hasNext()) {
				currMenu = currMenu.action(oldMenu, in.next());
				shown = false;
			}
		}
	}
	

}