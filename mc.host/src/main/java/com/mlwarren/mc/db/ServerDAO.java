package com.mlwarren.mc.db;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mlwarren.mc.Server;

public class ServerDAO {
	
	private static Logger logger = LogManager.getLogger(ServerDAO.class);
	private DBConnector db;
	
	//db.update("CREATE TABLE sample_table (id INTEGER IDENTITY, str_col VARCHAR(256), num_col INTEGER)");
	
	public ServerDAO(){
		db = new DBConnector();
	}
	
	public void createServerTable(){
		logger.debug("createServerTable > ");
		db.update("CREATE TABLE mc_server (id INTEGER IDENTITY, pid INTEGER, create_date TIMESTAMP, server_container_absolute_path VARCHAR(256), server_start_script_absolute_path VARCHAR(256))");
		logger.debug("createServerTable < ");
	}
	
	public void saveServerToDB(Server server){
		logger.debug("saveServerToDB > ");
		Date currentDate = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = df.format(currentDate);
		String valueString = "'"+server.getPid()+"'," + "'"+dateString+"'," + "'"+server.getServerContainerAbsolutePath()+"'," + "'"+server.getServerStartScriptAbsolutePath()+"'";
		db.update("INSERT INTO mc_server(pid,create_date,server_container_absolute_path,server_start_script_absolute_path) VALUES("+valueString+")");
		logger.debug("saveServerToDB < ");
	}
	
	public List<Server> getAllServers(){
		logger.debug("getAllServers >< ");
		return db.getAllServers();
	}
	
	public Server getServerByPID(int pid){
		logger.debug("getServerByPID >< ");
		return db.getServerByPID(pid);
	}
	
}
