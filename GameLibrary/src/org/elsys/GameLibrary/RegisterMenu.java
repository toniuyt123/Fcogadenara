package org.elsys.GameLibrary;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Scanner;

public class RegisterMenu extends Menu {
	public RegisterMenu(String id, String name, List<Menu> subMenus) {
		super(id, name, subMenus);
	}
	
	private static byte[] getSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[20];
        sr.nextBytes(salt);
        return salt;
    } 
	
	private static String getSecurePassword(String passwordToHash, byte[] salt)
    {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
	@Override
	public void show() {
		System.out.print("Enter Username Name: ");
	}
	@Override
	public Object action(Menu caller, Scanner in) {
		String userName = in.next();
		System.out.print("Enter Real Name: ");
		String realName = in.next();
		System.out.print("Enter Age: ");
		int age = in.nextInt();
		String pass;
		while(true) {
			System.out.print("Enter Password: ");
			pass = in.next();
			System.out.print("Repeat Pasword: ");
			String passRep = in.next();
			if(pass.equals(passRep)) {
				break;
			}else {
				System.out.println("Passwords dont match try again");
			}
		}
		byte[] salt = getSalt();
		String passHash = ""; 
		passHash = getSecurePassword(passHash, salt);
		
		User user = new User(userName, realName, passHash, age);
		/*Menu res = null;
		//System.out.print("ae we1");
		res = getSelectedSub(in);
		//System.out.print("ae we1");
		if(res != null) {
			if(res instanceof BackMenu) {
				//System.out.print("ae we");
				return res.action(caller, in);
			}
			return res;
		}*/
		return user;
	}
}
