package org.elsys.GameLibrary;

import java.util.List;

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
			if(i == len) {
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
	
	public abstract Menu action(Menu caller, String in);
	
	public abstract Menu show();

}
