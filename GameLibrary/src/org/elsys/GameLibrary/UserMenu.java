package org.elsys.GameLibrary;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserMenu extends Menu {
	User currUser;
	Connection conn;
	public UserMenu(String id, String name, List<Menu> subMenus, Connection conn) {
		super(id, name, subMenus);
		this.conn = conn;
	}

	public void setCurrUser(User currUser) {
		this.currUser = currUser;
	}
	
	@Override
	public Object action(Menu caller, Scanner in) {
		try {
		Menu res = null; 
			while(true) {
				String input = in.next();
				res = getSelectedSub(input);
				System.out.println("wut");
				if(res != null) {
					System.out.println("Oke?");
					break;
				}
				if(input.equals("3")) {
					GameLibrary.showUserGames(currUser, conn);
				}else if(input.equals("4")){
					currUser.addGame(conn, in);
				}
			}
			res.prevMenu = this;
			return res;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void show() {
		showSubMenus();
		System.out.println("List of available commands: ");
		System.out.println("---------------------------------------------------------------");	
		System.out.println("3 - Show owned Games 4 - Add game 5 - Remove game 6 - Edit game");
		System.out.println("---------------------------------------------------------------");	
	}
	
	
}
