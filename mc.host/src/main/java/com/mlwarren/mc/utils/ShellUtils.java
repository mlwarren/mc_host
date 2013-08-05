package com.mlwarren.mc.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mlwarren.mc.Server;

public class ShellUtils {
	
	private static Logger logger = LogManager.getLogger(ShellUtils.class);
	
	public static void outputToConsole(Process p){
		logger.debug("outputToConsole >");
		try{
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String s = "";
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		logger.debug( "outputToConsole <");
	}
	
	public static String outputToString(Process p){
		String retVal="";
		try{
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String s = "";
			while ((s = stdInput.readLine()) != null) {
				retVal+=s;
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return retVal;
	}
	
	public static int getPIDForServer(Server server){
		logger.debug("getPIDForServer > ");
		String pid = "-1";
		try {
			//Check if file exists. If it doesn't wait then try again
			//File should eventually be created by ServerStarter thread which
			//invokes start_minecraft.sh; start_minecraft.sh creates pid.txt
			//Try for 30 seconds then if failed we return pid of -1
			File pidFile = new File(server.getServerContainerAbsolutePath()+"/pid.txt");
			int numCheckForPidFile=0;
			while(!pidFile.exists() && numCheckForPidFile<60){
				logger.debug("pid file does not yet exist, sleeping (try "+numCheckForPidFile+" out of 60)");
				try {
					Thread.sleep(500);
					numCheckForPidFile++;
					pidFile = new File(server.getServerContainerAbsolutePath()+"/pid.txt");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			logger.debug("pid file exists, opening reader");
			FileReader fw = new FileReader(server.getServerContainerAbsolutePath()+"/pid.txt");
			BufferedReader br = new BufferedReader(fw);
			pid = br.readLine();
			br.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.debug("getPIDForServer, pid = " + pid + " < ");
		return Integer.parseInt(pid);
	}
	
	public static String getSupportedMCVersions(){
		logger.debug("getSupportedMCVersions >");
		String output = "";
		try{
			FileReader fw = new FileReader("version_list.properties");
			BufferedReader br = new BufferedReader(fw);
			String line;
			while((line=br.readLine())!=null){
				output+=line+"\n";
			}
			br.close();
			fw.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		logger.debug("getSupportedMCVersions <");
		return output;
	}
	
	public static String getVersionsDataDirectory(){
		logger.debug("getVersionsDataDirectory >");
		String output = "";
		try{
			FileReader fw = new FileReader("versions.properties");
			BufferedReader br = new BufferedReader(fw);
			output=br.readLine();
			br.close();
			fw.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		logger.debug("getVersionsDataDirectory <");
		return output;
	}
}
