package com.mlwarren.mc;

import java.io.Console;

import com.mlwarren.mc.utils.LoggingUtils;

public class Main {
	
	public static void main(String[] args) {
		LoggingUtils.log("DEBUG", "main >");
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
			Provision p = new Provision(sourceDirectory, zipName, destinationDirectory);
			p.createNewServer();
			Thread t = new Thread(p);
			t.start();
			System.out.println("New container provisioned...");
			System.out.println(t.toString());
			input = console.readLine("Provision new server (p), Quit (q)");
		}
		
		LoggingUtils.log("DEBUG", "main <");
	}

}
