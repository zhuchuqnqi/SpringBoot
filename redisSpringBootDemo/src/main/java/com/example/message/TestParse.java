package com.example.message;

public class TestParse {

	public static void main(String[] args) {
		byte[] b = String.format("%03d", 2).getBytes();

		String str = String.format("%03d", 2);
		for (byte bit : b) {
			System.out.println(bit);
		}

		System.out.println(str);
		System.out.println(stringToAscii("彭玉军"));
		System.out.println(asciiToString("24429"));
	}

	public static String asciiToString(String value) {
		StringBuffer sbu = new StringBuffer();
		String[] chars = value.split(",");
		for (int i = 0; i < chars.length; i++) {
			sbu.append((char) Integer.parseInt(chars[i]));
		}
		return sbu.toString();
	}

	public static String stringToAscii(String value) {
		StringBuffer sbu = new StringBuffer();
		char[] chars = value.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (i != chars.length - 1) {
				sbu.append((int) chars[i]).append(",");
			} else {
				sbu.append((int) chars[i]);
			}
		}
		return sbu.toString();
	}
}
