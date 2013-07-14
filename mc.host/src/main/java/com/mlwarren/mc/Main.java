package com.mlwarren.mc;

import com.mlwarren.mc.utils.LoggingUtils;

public class Main {
	
	public static void main(String[] args) {
		LoggingUtils.log("DEBUG", "main >");
		if(args.length!=3){
			System.err.println("Usage: java -jar <jarname> <source_directory> <zip_name> <destination_directory>");
			System.exit(1);
		}
		String sourceDirectory = args[0];
		String zipName = args[1];
		String destinationDirectory = args[2];
		
		Provision provision = new Provision();
		provision.createNewServer(sourceDirectory, zipName, destinationDirectory);
		LoggingUtils.log("DEBUG", "main <");
	}

}
