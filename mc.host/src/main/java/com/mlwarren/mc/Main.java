package com.mlwarren.mc;

import java.io.Console;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mlwarren.mc.db.DBConnector;
import com.mlwarren.mc.utils.ShellUtils;


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
		
//		logger.debug("creating db connector");
//		DBConnector db = new DBConnector();
//		db.update("CREATE TABLE sample_table (id INTEGER IDENTITY, str_col VARCHAR(256), num_col INTEGER)");
//		db.update("INSERT INTO sample_table(str_col,num_col) VALUES('mike',1)");
//		db.query("SELECT * FROM sample_table");
//		logger.debug("done creating db connector");
		
		Console console = System.console();
		String input = console.readLine("Provision new server (p), Quit (q)");
		while(!input.equals("q")){
			input = console.readLine("Provide destination directory... Don't forget trailing / \n");
			String destinationDirectory = input;
			ServerProvisioner sp = new ServerProvisioner();
			sp.createNewServer(sourceDirectory, zipName, destinationDirectory);
			System.out.println("New container provisioned...");
			ServerController sc = new ServerController();
			sc.startServer(destinationDirectory+zipName);
			System.out.println("Started server...");
			try { //TODO: Fix this depressingly ugly hack
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String pid = ShellUtils.getPIDForServer(destinationDirectory+zipName);
			System.out.println("pid = " + pid);
			input = console.readLine("Provision new server (p), Quit (q)");
		}
		
		
		logger.debug( "main <");
	}

}
