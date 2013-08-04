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
		
	public ServerDAO(){
		db = new DBConnector();
	}
	
	public void createServerTable(){
		logger.debug("createServerTable > ");
		db.update("CREATE TABLE mc_server (id INTEGER IDENTITY, pid INTEGER, create_date TIMESTAMP, server_container_absolute_path VARCHAR(256), server_start_script_absolute_path VARCHAR(256), started BOOLEAN)");
		logger.debug("createServerTable < ");
	}
	
	public void saveServerToDB(Server server){
		logger.debug("saveServerToDB > ");
		Date currentDate = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = df.format(currentDate);
		if(!isServerInDB(server)){
			logger.debug("Server is not yet in DB, insert it for first time..");
			String valueString = "'"+server.getPid()+"'," + "'"+dateString+"'," + "'"+server.getServerContainerAbsolutePath()+"'," + "'"+server.getServerStartScriptAbsolutePath()+"'," + "'"+server.isStarted()+"'";
			db.update("INSERT INTO mc_server(pid,create_date,server_container_absolute_path,server_start_script_absolute_path,started) VALUES("+valueString+")");
		}
		else{
			logger.debug("Server is in DB, update server information..");
			String valueString="pid='"+server.getPid()+"'," + "create_date='"+dateString+"'," + "server_container_absolute_path='"+server.getServerContainerAbsolutePath()
					+"'," + "server_start_script_absolute_path='"+server.getServerStartScriptAbsolutePath()+"'," + "started='"+server.isStarted()+"'";
			db.update("UPDATE mc_server set " + valueString + " where id=" + server.getId());
		}
		logger.debug("saveServerToDB < ");
	}
	
	public boolean isServerInDB(Server server){
		if(getServerByID(server.getId())==null){
			return false;
		}
		return true;
	}
	
	public void updateServerStartedByPID(int pid, boolean started){
		logger.debug("updateServerStartedByPID > ");
		db.update("UPDATE mc_server set started="+started+" where pid="+pid);
		if(!started){ //Set pid -1 if server stopped
			db.update("UPDATE mc_server set pid=-1 where pid = " + pid);
		}
		logger.debug("updateServerStartedByPID < ");
	}
	
	public List<Server> getAllServers(){
		logger.debug("getAllServers >< ");
		return db.getAllServers();
	}
	
	public Server getServerByPID(int pid){
		logger.debug("getServerByPID >< ");
		return db.getServerByPID(pid);
	}
	
	public Server getServerByID(int id){
		logger.debug("getServerByID >< ");
		return db.getServerByID(id);
	}
	
	public void deleteServerByPID(int pid){
		logger.debug("deleteServerByPID > ");
		db.update("DELETE FROM mc_server where pid="+pid);
		logger.debug("deleteServerByPID < ");
	}
	
	public void deleteServerByID(int id){
		logger.debug("deleteServerByID > ");
		db.update("DELETE FROM mc_server where id="+id);
		logger.debug("deleteServerByID < ");
	}
	
}
