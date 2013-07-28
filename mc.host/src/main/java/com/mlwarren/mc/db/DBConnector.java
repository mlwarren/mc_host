package com.mlwarren.mc.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mlwarren.mc.Server;

public class DBConnector {
	private static Logger logger = LogManager.getLogger(DBConnector.class);
	
	Connection conn;
	
	public DBConnector(){
		try {
			String db_file_name_prefix = "mc";
			Class.forName("org.hsqldb.jdbcDriver");
	        conn = DriverManager.getConnection("jdbc:hsqldb:file:"
                    + db_file_name_prefix,    // filenames
                    "sa",                     // username
                    "");                      // password
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized List<Server> getAllServers(){
		logger.debug("getAllServers > ");
		ArrayList<Server> serverList = new ArrayList<Server>();
		try{
			Statement st = null;
			ResultSet rs = null;
			st = conn.createStatement();
			rs=st.executeQuery("SELECT * from mc_server");
			while(rs.next()){
				int pid = rs.getInt("pid");
				Date createDate = rs.getTimestamp("create_date");
				String serverContainerAbsolutePath = rs.getString("server_container_absolute_path");
				String serverStartScriptAbsolutePath = rs.getString("server_start_script_absolute_path");
				boolean started = rs.getBoolean("started");
				serverList.add(new Server(pid, createDate, serverContainerAbsolutePath, serverStartScriptAbsolutePath, started));
			};
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		logger.debug("getAllServers < ");
		return serverList;
	}
	
	public synchronized Server getServerByPID(int pid){
		logger.debug("getServerByPID > ");
		Server server = null;
		try{
			Statement st = null;
			ResultSet rs = null;
			st = conn.createStatement();
			rs=st.executeQuery("SELECT * from mc_server WHERE pid = " + pid);
			if(!rs.next()){
				logger.debug("Couldn't find pid = " + pid);
				logger.debug("getServerByPID returning null < ");
				return null;
			} //now pointing to first row
			pid = rs.getInt("pid");
			Date createDate = rs.getTimestamp("create_date");
			String serverContainerAbsolutePath = rs.getString("server_container_absolute_path");
			String serverStartScriptAbsolutePath = rs.getString("server_start_script_absolute_path");
			boolean started = rs.getBoolean("started");
			server = new Server(pid, createDate, serverContainerAbsolutePath, serverStartScriptAbsolutePath,started);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		logger.debug("getServerByPID < ");
		return server;
	}
	
	public synchronized void update(String expression){
		try{
			Statement st = null;
			st=conn.createStatement();
			int i = st.executeUpdate(expression);
			if(i==-1){
				logger.error("DB Error: " + expression);
			}
			st.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
}
