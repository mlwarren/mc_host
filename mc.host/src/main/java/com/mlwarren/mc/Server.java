package com.mlwarren.mc;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server {
	private static Logger logger = LogManager.getLogger(Server.class);
	
	private int pid;
	private Date createDate;
	private String serverContainerAbsolutePath;
	private String serverStartScriptAbsolutePath;
	
	public Server(int pid, Date createDate, String serverContainerAbsolutePath,
			String serverStartScriptAbsolutePath) {
		logger.debug("server >");
		this.pid = pid;
		this.createDate = createDate;
		this.serverContainerAbsolutePath = serverContainerAbsolutePath;
		this.serverStartScriptAbsolutePath = serverStartScriptAbsolutePath;
		logger.debug("server <");
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getServerContainerAbsolutePath() {
		return serverContainerAbsolutePath;
	}
	public void setServerContainerAbsolutePath(String serverContainerAbsolutePath) {
		this.serverContainerAbsolutePath = serverContainerAbsolutePath;
	}
	public String getServerStartScriptAbsolutePath() {
		return serverStartScriptAbsolutePath;
	}
	public void setServerStartScriptAbsolutePath(
			String serverStartScriptAbsolutePath) {
		this.serverStartScriptAbsolutePath = serverStartScriptAbsolutePath;
	}
	
	public String toString(){
		String out="";
		out+="\nMinecraft server ... " + pid + "\n";
		out+="\tcreateDate ... "+createDate+"\n";
		out+="\tserverContainerAbsolutePath ... " + serverContainerAbsolutePath + "\n";
		out+="\tserverStartScriptAbsolutePath ... " + serverStartScriptAbsolutePath + "\n";
		return out;
	}
	
}
