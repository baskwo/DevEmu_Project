package org.devemu.utils;

import java.util.concurrent.ThreadLocalRandom;

public class Crypt {
	static char[] alpha = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
                           't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
                           'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','-','_'};
	
	public static String randomString(int size) {
		StringBuilder builder = new StringBuilder();
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		for(int i = 0; i < size; i++) {
			builder.append(alpha[rand.nextInt(alpha.length)]);
		}
		return builder.toString();
	}
	
	public static String randomIntHexString(int size) {
		StringBuilder builder = new StringBuilder();
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		for(int i = 0; i < size; i++) {
			String hex = "" + rand.nextInt(10) + rand.nextInt(10);
			builder.append(hex);
		}
		return builder.toString();
	}
	
	public static String cryptAnkama(String pass, String salt) {
		String out = "#1";
		char passC;
		char saltC;
		int n = 0;
		int m = 0;
		for(int i = 0; i < pass.length(); i++) {
			passC = pass.charAt(i);
			saltC = salt.charAt(i);
			n = passC / 16;
			m = passC % 16;
			out += alpha[(n + saltC) % alpha.length];
			out += alpha[(m + saltC) % alpha.length];
		}
		return out;
	}
}
