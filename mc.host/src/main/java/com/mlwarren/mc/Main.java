package com.mlwarren.mc;

import java.io.Console;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mlwarren.mc.db.DBConnector;
import com.mlwarren.mc.db.ServerDAO;
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
			Server server = sp.createNewServer(sourceDirectory, zipName, destinationDirectory);
			System.out.println("New container provisioned...");
			ServerController sc = new ServerController();
			sc.startServer(server);
			System.out.println("Started server...");
			server.setPid(ShellUtils.getPIDForServer(server));
			ServerDAO serverDAO = new ServerDAO();
			serverDAO.createServerTable();
			serverDAO.saveServerToDB(server);
			List<Server> serverList = serverDAO.getAllServers();
			logger.debug("Server list = " + serverList.toString());
			Server serverFromDB = serverDAO.getServerByPID(server.getPid());
			logger.debug("Server from db = " + serverFromDB.toString());
			ServerDecomissioner serverDecomissioner = new ServerDecomissioner();
			logger.debug("Decomission server with pid = " + serverFromDB.getPid());
			serverDecomissioner.removeServer(serverFromDB.getPid());
			input = console.readLine("Provision new server (p), Quit (q)");
		}
		
		
		logger.debug( "main <");
	}

}
