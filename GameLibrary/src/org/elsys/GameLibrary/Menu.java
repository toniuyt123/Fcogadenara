package org.elsys.GameLibrary;

import java.util.List;
import java.util.Scanner;

public abstract class Menu {
	protected String menuId;
	protected String name;
	protected List<Menu> subMenus;
	
	public Menu(String menuId, String name, List<Menu> subMenus) {
		this.menuId = menuId;
		this.name = name;
		this.subMenus = subMenus;
	}

	public String getId() {
		return menuId;
	}
	
	public String getName() {
		return name;
	}
	
	
	public Menu findSubMenu(String id) {
		//System.out.println(id);
		for(Menu m : subMenus) {
			//System.out.println(m.getId());
			if(m.getId().equals(id)) {
				return m;
			}
		}
		System.out.println("Wrong selection.");
		return null;
	}
	
	public Menu showSubMenus() {
		StringBuilder sb = new StringBuilder();
		System.out.println(name);
		System.out.println("List of available commands: ");
		for(Menu m : subMenus) {
			sb.append(m.getId());
			sb.append(" - ");
			sb.append(m.getName() + " ");
		}
		int len = sb.toString().length();
		for(int i = 0; i < len * 2; i ++) {
			System.out.print("-");	
			if(i + 1 == len) {
				System.out.print("\n");
				System.out.println(sb.toString());
			}
		}
		System.out.print("\n");
		return this;
	}
	
	public Menu getSelectedSub(String in) {
		Menu res = null;
		//System.out.println("tuk2");
		while(res == null) {
			//System.out.println("tuk");
			res = findSubMenu(in);
		}
		return res;
	}
	
	public static boolean confirm(String confirmationString) {
		System.out.println("confirmationString" + "(y/n):");
		Scanner in = new Scanner(System.in);
		while(true) {
			String choice = in.nextLine();
			if(choice.equals("y")) {
				return true;
			} else if(choice.equals("n")) {
				return false;
			} else {
				System.out.println("Enter a valid option (y/n): ");
			}
		}
	}
	
	public abstract Menu action(Menu caller, String in);
	
	public abstract Menu show();

}
