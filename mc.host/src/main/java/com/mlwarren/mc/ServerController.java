package com.mlwarren.mc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerController{
	
	private static Logger logger = LogManager.getLogger(ServerController.class);
	
	public void startServer(Server server){
		logger.debug( "startServer >");
		ServerStarter starter = new ServerStarter(server);
		Thread t = new Thread(starter);
		t.start();
		logger.debug( "startServer <");
	}
	
}
