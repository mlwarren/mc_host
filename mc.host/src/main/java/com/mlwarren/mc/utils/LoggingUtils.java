package com.mlwarren.mc.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LoggingUtils {
	
	public static void log(String logLevel, String logString){
		//logToConsole(logLevel, logString);
		logToFile(logLevel, logString, "log.txt");
	}
	
	//Eventually implement logging level support
	public static void logToConsole(String logLevel, String logString){
		System.out.println(logString);
	}
	
	//Eventually implement logging level support
	public static void logToFile(String logLevel, String logString, String logFileName){
		try{
			FileWriter fw = new FileWriter(new File(logFileName),true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(logString+"\n");
			bw.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
