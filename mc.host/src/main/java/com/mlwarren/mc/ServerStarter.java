package com.mlwarren.mc;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerStarter implements Runnable{
	
	private static Logger logger = LogManager.getLogger(ServerStarter.class);
	
	private String serverPath;
	
	public ServerStarter(String serverPath){
		this.serverPath = serverPath;
	}
	
	public void run() {
		startServer();
	}
	
	public void startServer(){
		logger.debug( "startServer >");
		try {
			Runtime.getRuntime().exec(serverPath+"/start_minecraft.sh &");
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.debug( "startServer <");
	}		
}

