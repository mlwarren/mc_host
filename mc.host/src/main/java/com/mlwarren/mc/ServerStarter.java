package com.mlwarren.mc;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerStarter implements Runnable{
	
	private static Logger logger = LogManager.getLogger(ServerStarter.class);
	
	private Server server;
	
	public ServerStarter(Server server){
		this.server = server;
	}
	
	public void run() {
		startServer();
	}
	
	public void startServer(){
		logger.debug( "startServer >");
		try {
			Runtime.getRuntime().exec(server.getServerStartScriptAbsolutePath()+" &");
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.debug( "startServer <");
	}		
}

