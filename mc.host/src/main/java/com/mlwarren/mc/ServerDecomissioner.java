package com.mlwarren.mc;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mlwarren.mc.db.ServerDAO;
import com.mlwarren.mc.utils.ShellUtils;

public class ServerDecomissioner {
	private static Logger logger = LogManager.getLogger(ServerDecomissioner.class);
	
	public Server removeServer(int pid){
		logger.debug("removeServer > ");
		ServerDAO serverDAO = new ServerDAO();

		//Make sure given PID is in database
		Server server = serverDAO.getServerByPID(pid);
		if(server==null){
			logger.debug("Can't kill that PID, it's not catalogued as a server...");
			logger.debug("removeServer < ");
			return null;
		}
		
		try{
			logger.debug("Killing process with pid = " + pid);
			Process p = Runtime.getRuntime().exec("kill " + pid);
			logger.debug(ShellUtils.outputToString(p));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		//Remove server information from database
		logger.debug("Removing server with PID = " + pid + " from database");
		serverDAO.deleteServerByPID(pid);
		
		logger.debug("removeServer < ");
		return server;
	}
	
	public void deleteServerFilesFromFileSystem(String serverAbsolutePath){
		logger.debug("deleteServerFilesFromFileSystem > ");
		try{
			logger.debug("Deleting server directory " + serverAbsolutePath);
			Process p = Runtime.getRuntime().exec("rm -rf " + serverAbsolutePath);
			logger.debug(ShellUtils.outputToString(p));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		logger.debug("deleteServerFilesFromFileSystem < ");
	}
}
