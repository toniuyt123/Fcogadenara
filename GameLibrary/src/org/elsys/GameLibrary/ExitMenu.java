package org.elsys.GameLibrary;

import java.util.List;
import java.util.Scanner;

public class ExitMenu extends Menu {
	public ExitMenu(String id, String name, List<Menu> subMenus) {
		super(id, name, subMenus);
	}
	@Override
	public void show() {
		return;
	}
	@Override
	public Object action(Menu caller, Scanner in) {
		return this;
	}
}
