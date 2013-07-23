package com.mlwarren.mc.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	
	public synchronized void query(String expression){
		try{
			Statement st = null;
			ResultSet rs = null;
			st = conn.createStatement();
			rs=st.executeQuery(expression);
			//Result set at this point is actually a cursor pointing before the first row
			rs.next(); //Now pointing to first row
			logger.debug("RESULT SET: " + rs.toString());
			ResultSetMetaData metaData = rs.getMetaData();
			logger.debug("column count = " + metaData.getColumnCount());
			logger.debug("col1 = " + rs.getString(1+1)); //SQL is 1 indexed, not 0
			st.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
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
