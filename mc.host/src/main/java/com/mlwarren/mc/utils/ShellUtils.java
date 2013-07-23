package com.mlwarren.mc.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShellUtils {
	
	private static Logger logger = LogManager.getLogger(ShellUtils.class);
	
	public static void outputToConsole(Process p){
		logger.debug("outputToConsole >");
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
		logger.debug( "outputToConsole <");
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
	
	public static String getPIDForServer(String serverPath){
		String pid = "";
		try {
			FileReader fw = new FileReader(serverPath+"/pid.txt");
			BufferedReader br = new BufferedReader(fw);
			pid = br.readLine();
			br.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pid;
	}
}
