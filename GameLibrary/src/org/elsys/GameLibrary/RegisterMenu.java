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
        System.out.println("SECURE PASS" + generatedPassword);
        return generatedPassword;
    }
	
	@Override
	public void show() {
		if(this.prevMenu instanceof UserMenu) {
			System.out.print("Enter Username Name: ");
		}else {
			System.out.print("Enter Publisher Name: ");
		}
	}
	@Override
	public Object action(Menu caller, Scanner in) {
		if(this.prevMenu instanceof UserMenu) {
			//prevMenu = caller;
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
			//System.out.println("Action+ " + salt);
			pass = getSecurePassword(pass, salt);
			//System.out.println("LAStBIT" + salt);
			User user = new User(userName, realName, pass, salt, age);
			return user;
		}else { 
			//prevMenu = caller;
			String publisherName = in.next();
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
			System.out.println("Enter establishment date: ");
			String establishedDate = in.next();
			byte[] salt = getSalt();
			//System.out.println("Action+ " + salt);
			pass = getSecurePassword(pass, salt);
			//System.out.println("LAStBIT" + salt);
			Publisher publisher = new Publisher(publisherName, pass, salt, establishedDate);
			return publisher;
		}
	}
}
