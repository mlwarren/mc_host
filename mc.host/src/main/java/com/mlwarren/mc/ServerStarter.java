package com.mlwarren.mc;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mlwarren.mc.utils.ShellUtils;

public class ServerStarter implements Runnable{
	
	private static Logger logger = LogManager.getLogger(ServerStarter.class);
	
	public void run() {
		//startServer();
	}
	
	public void startServer(String serverPath){
		logger.debug( "startServer >");
		try {
			Process p = Runtime.getRuntime().exec(serverPath+"/start_minecraft.sh");
			logger.debug(ShellUtils.outputToString(p));

		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.debug( "startServer <");
	}		
}

