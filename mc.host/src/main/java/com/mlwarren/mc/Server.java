package com.mlwarren.mc;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server {
	private static Logger logger = LogManager.getLogger(Server.class);
	
	private int id;
	private int pid;
	private Date createDate;
	private String serverContainerAbsolutePath;
	private String serverStartScriptAbsolutePath;
	private boolean started;
	
	public Server(int id, int pid, Date createDate, String serverContainerAbsolutePath,
			String serverStartScriptAbsolutePath, boolean started) {
		logger.debug("server >");
		this.id = id;
		this.pid = pid;
		this.createDate = createDate;
		this.serverContainerAbsolutePath = serverContainerAbsolutePath;
		this.serverStartScriptAbsolutePath = serverStartScriptAbsolutePath;
		this.started = started;
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
	public boolean isStarted() {
		return started;
	}
	public void setStarted(boolean started) {
		this.started = started;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String toString(){
		String out="";
		out+="ID: " + id;
		out+="\tPID: " + pid;
		out+="\tCreate Date: " + createDate;
		out+="\tServer Container: " + serverContainerAbsolutePath;
		out+="\tStart Script: " + serverStartScriptAbsolutePath;
		out+="\tStarted: " + started;
		return out;
	}
	
}
