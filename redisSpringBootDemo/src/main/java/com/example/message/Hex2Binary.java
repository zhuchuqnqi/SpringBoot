package com.example.message;

public class Hex2Binary {
	 public static void main(String[] args)  
	    {  
	        String hexString = "6000020000613100";  
	        System.out.println(hexString2binaryString(hexString));  
	    }  
	  
	    public static String hexString2binaryString(String hexString)  
	    {  
	        if (hexString == null || hexString.length() % 2 != 0)  
	            return null;  
	        String bString = "", tmp;  
	        for (int i = 0; i < hexString.length(); i++)  
	        {  
	            tmp = "0000"  
	                    + Integer.toBinaryString(Integer.parseInt(hexString  
	                            .substring(i, i + 1), 16));  
	            bString += tmp.substring(tmp.length() - 4);  
	        }  
	        return bString;  
	    }  
}
