package org.elsys.GameLibrary;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Scanner;

public class RegisterMenu extends Menu {
	public RegisterMenu(String id, String name, List<Menu> subMenus) {
		super(id, name, subMenus);
	}
	
	private static byte[] getSalt(){
		SecureRandom sr;
		byte[] salt = new byte[20];
		try {
			sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
		    sr.nextBytes(salt);
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return salt;
    } 
	
	public static String getSecurePassword(String passwordToHash, byte[] salt) {
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
        System.out.println("SECURE PASS" + salt);
        return generatedPassword;
    }
	
	@Override
	public void show() {
		System.out.print("Enter Username Name: ");
	}
	@Override
	public Object action(Menu caller, Scanner in) {
		prevMenu = caller;
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
		byte[] salt = RegisterMenu.getSalt();
		String passHash = ""; 
		System.out.println("Action+ " + salt);
		passHash = getSecurePassword(passHash, salt);
		System.out.println("LAStBIT" + salt);
		User user = new User(userName, realName, passHash, salt, age);
		return user;
	}
}
