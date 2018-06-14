package org.elsys.GameLibrary;

import java.util.List;

public class MainMenu extends Menu {
	
	public MainMenu(String id, String name, List<Menu> subMenus) {
		super(id, name, subMenus);
	}
	
	@Override
	public Menu show() {
		return showSubMenus();
	}
	
	@Override
	public Menu action(Menu caller, String in) {
		caller = this;
		return getSelectedSub(in);
	}
}
