package com.mlwarren.mc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerController{
	
	private static Logger logger = LogManager.getLogger(ServerController.class);
	
	public void startServer(String serverPath){
		logger.debug( "startServer >");
		ServerStarter starter = new ServerStarter(serverPath);
		Thread t = new Thread(starter);
		t.start();
		logger.debug( "startServer <");
	}
	
}
