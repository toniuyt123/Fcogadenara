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
		for(Menu m : subMenus) {
			if(m.getId().equals(id)) {
				return m;
			}
		}
		System.out.print("\n\n\n\n\n");
		this.show();
		System.out.println("Wrong selection.");
		return null;
	}
	
	public void showSubMenus() {
		if(subMenus == null)
			return;
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
	}
	
	public Menu getSelectedSub(String in) {
		Menu res = null;
		res = findSubMenu(in);
		return res;
	}
	
	public abstract Object action(Menu caller, Scanner in);
	
	public abstract void show();

}
