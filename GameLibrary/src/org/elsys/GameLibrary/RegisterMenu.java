package org.elsys.GameLibrary;

import java.util.List;

public class RegisterMenu extends Menu {
	public RegisterMenu(String id, String name, List<Menu> subMenus) {
		super(id, name, subMenus);
	}
	
	@Override
	public Menu show() {
		return showSubMenus();
	}
	
	@Override
	public Menu action(Menu caller, String in) {
		Menu res = null;
		//System.out.print("ae we1");
		res = getSelectedSub(in);
		//System.out.print("ae we1");
		if(res != null) {
			if(res instanceof BackMenu) {
				//System.out.print("ae we");
				return res.action(caller, in);
			}
			return res;
		}
		return this;
	}
}
