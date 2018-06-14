package org.elsys.GameLibrary;

import java.util.List;

public class BackMenu extends Menu {
	public BackMenu(String id, String name, List<Menu> subMenus) {
		super(id, name, subMenus);
	}
	
	@Override
	public Menu show() {
		return this;
	}
	
	@Override
	public Menu action(Menu caller, String in) {
		return caller;
	}
}
