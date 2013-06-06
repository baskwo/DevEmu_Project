package org.devemu.utils;

import java.util.Random;

public class Crypt {
	static char[] alpha = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
                           't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
                           'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','-','_'};
	
	public static String randomString(int arg0) {
		StringBuilder loc1 = new StringBuilder();
		Random loc2 = new Random(System.nanoTime());
		for(int i = 0; i < arg0; i++) {
			loc1.append(alpha[loc2.nextInt(alpha.length)]);
		}
		return loc1.toString();
	}
	
	public static String randomIntHexString(int arg0) {
		StringBuilder loc1 = new StringBuilder();
		Random loc2 = new Random(System.nanoTime());
		for(int i = 0; i < arg0; i++) {
			String loc3 = "" + loc2.nextInt(10) + loc2.nextInt(10);
			loc1.append(loc3);
		}
		return loc1.toString();
	}
	
	public static String cryptAnkama(String arg0, String arg1) {
		String loc1 = "#1";
		char loc2;
		char loc3;
		int loc4 = 0;
		int loc5 = 0;
		for(int i = 0; i < arg0.length(); i++) {
			loc2 = arg0.charAt(i);
			loc3 = arg1.charAt(i);
			loc4 = loc2 / 16;
			loc5 = loc2 % 16;
			loc1 += alpha[(loc4 + loc3) % alpha.length];
			loc1 += alpha[(loc5 + loc3) % alpha.length];
		}
		return loc1;
	}
}
