package com.mlwarren.mc.utils;

public class LoggingUtils {
	
	public static void log(String logLevel, String logString){
		logToConsole(logLevel, logString);
		//logToFile(logString);
	}
	
	//Eventually implement logging level support
	public static void logToConsole(String logLevel, String logString){
		System.out.println(logString);
	}
}
