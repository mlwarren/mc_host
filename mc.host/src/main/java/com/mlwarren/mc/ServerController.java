package com.mlwarren.mc;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mlwarren.mc.db.ServerDAO;
import com.mlwarren.mc.utils.ShellUtils;

public class ServerController{
	
	private static Logger logger = LogManager.getLogger(ServerController.class);
	
	public Server startServer(Server server){
		logger.debug( "startServer >");
		ServerStarter starter = new ServerStarter(server);
		Thread t = new Thread(starter);
		t.start();
		server.setPid(ShellUtils.getPIDForServer(server));
		server.setStarted(true);
		ServerDAO serverDAO = new ServerDAO();
		serverDAO.saveServerToDB(server);
		logger.debug( "startServer <");
		return server;
	}
	
	public Server stopServer(Server server){
		logger.debug("stopServer >");
		try {
			Process p = Runtime.getRuntime().exec("kill " + server.getPid());
			logger.debug(ShellUtils.outputToString(p));
			p = Runtime.getRuntime().exec("rm -rf " + server.getServerContainerAbsolutePath()+"/pid.txt");
			logger.debug(ShellUtils.outputToString(p));
			ServerDAO serverDAO = new ServerDAO();
			serverDAO.updateServerStartedByPID(server.getPid(), false);
			server.setPid(-1);
			server.setStarted(false);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		logger.debug("stopServer <");
		return server;
	}
	
}
