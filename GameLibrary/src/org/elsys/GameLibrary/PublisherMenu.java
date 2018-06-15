package org.elsys.GameLibrary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class PublisherMenu extends Menu {
	Publisher currPublisher;
	Connection conn;
	public PublisherMenu(String id, String name, List<Menu> subMenus, Connection conn) {
		super(id, name, subMenus);
		this.conn = conn;
	}

	public void setcurrPublisher(Publisher currPublisher) {
		this.currPublisher = currPublisher;
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
					res.prevMenu = this;
					break;
				}
				if(input.equals("3")) {
					GameLibrary.showAllGames(conn);
				}else if(input.equals("4")){
					currPublisher.addGame(conn, in);
					break;
				}else if(input.equals("5")) {
					currPublisher.removeGame(conn, in);
					break;
				}else if(input.equals("6")) {
					currPublisher.updateGame(conn, in);
					break;
				}
			}
			if(res == null) {
				res = this;
				System.out.println("Yo");
			}
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
		System.out.println("3 - Show Games 4 - Add game 5 - Remove game 6 - Edit game");
		System.out.println("---------------------------------------------------------------");	
	}
}
