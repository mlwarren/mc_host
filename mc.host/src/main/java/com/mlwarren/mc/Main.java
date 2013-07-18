package com.mlwarren.mc;

import java.io.Console;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
	
	private static Logger logger = LogManager.getLogger(Main.class);
	
	public static void main(String[] args) {
		logger.debug( "main >");
		if(args.length!=2){
			System.err.println("Usage: java -jar <jarname> <source_directory> <zip_name>");
			System.exit(1);
		}
		String sourceDirectory = args[0];
		String zipName = args[1];
		
		Console console = System.console();
		String input = console.readLine("Provision new server (p), Quit (q)");
		while(!input.equals("q")){
			input = console.readLine("Provide destination directory... Don't forget trailing / \n");
			String destinationDirectory = input;
			ServerProvisioner sp = new ServerProvisioner();
			sp.createNewServer(sourceDirectory, zipName, destinationDirectory);
			System.out.println("New container provisioned...");
			input = console.readLine("Provision new server (p), Quit (q)");
		}
		
		logger.debug( "main <");
	}

}
