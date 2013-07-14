package com.mlwarren.mc.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellUtils {
	public static void outputToConsole(Process p){
		LoggingUtils.log("DEBUG","outputToConsole >");
		try{
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String s = "";
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		LoggingUtils.log("DEBUG", "outputToConsole <");
	}
	
	public static String outputToString(Process p){
		String retVal="";
		try{
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String s = "";
			while ((s = stdInput.readLine()) != null) {
				retVal+=s;
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return retVal;
	}
}
