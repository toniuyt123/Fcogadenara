package org.elsys.GameLibrary;

import java.util.List;
import java.util.Scanner;

public class MainMenu extends Menu {
	
	public MainMenu(String id, String name, List<Menu> subMenus) {
		super(id, name, subMenus);
	}
	@Override
	public void show() {
		showSubMenus();
	}
	@Override
	public Object action(Menu caller, Scanner in) {
		caller = this;
		Menu res = null; 
		while(res == null) {
			res = getSelectedSub(in.next());
		}
		return res;
			
	}
}
