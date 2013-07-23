package com.mlwarren.mc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mlwarren.mc.utils.ShellUtils;

public class ServerProvisioner{
	
	private static Logger logger = LogManager.getLogger(ServerProvisioner.class);
	
	String serverSourceAbsolutePath;
	String zipFileName;
	String serverDestinationAbsolutePath;

	public void createNewServer(String serverSourceAbsolutePath, String zipFileName, String serverDestinationAbsolutePath){
		logger.debug( "createNewServer >");
		copyZipToDestination(serverSourceAbsolutePath, zipFileName, serverDestinationAbsolutePath);
		configureServerProperties(serverDestinationAbsolutePath+zipFileName);
		buildStartMinecraftShellScript(serverDestinationAbsolutePath+zipFileName);
		logger.debug( "createNewServer <");
	}
	
	public void copyZipToDestination(String serverSourceAbsolutePath, String outputDirectory, String serverDestinationAbsolutePath){
		logger.debug( "copyZipToDestination >");
		try {
			Process p = Runtime.getRuntime().exec("mkdir " + serverDestinationAbsolutePath);
			logger.debug(ShellUtils.outputToString(p));
			p = Runtime.getRuntime().exec("cp " + serverSourceAbsolutePath+outputDirectory+".zip" + " " + serverDestinationAbsolutePath);
			logger.debug(ShellUtils.outputToString(p));
			p = Runtime.getRuntime().exec("unzip " + serverDestinationAbsolutePath+outputDirectory+".zip" + " -d " + serverDestinationAbsolutePath);
			logger.debug(ShellUtils.outputToString(p));
			p = Runtime.getRuntime().exec("rm " + serverDestinationAbsolutePath+outputDirectory);
			logger.debug(ShellUtils.outputToString(p));

		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.debug( "copyZipToDestination <");
	}
	
	public void configureServerProperties(String serverProperties){
		logger.debug( "configureServerProperties >");
		serverProperties+="/server.properties";
		BufferedReader br = null;
		List<String> propertyList = new ArrayList<String>();
		try{
			br = new BufferedReader(new FileReader(serverProperties));
			String currentLine="";
			Console console = System.console();
			while((currentLine=br.readLine())!=null){
				System.out.println(currentLine);
				String input = console.readLine("Change config line or leave blank to retain default: ");
				if(!input.equals("")){
					currentLine=input;
				}
				propertyList.add(currentLine);
			}
			br.close();
			FileWriter fw = new FileWriter(new File(serverProperties));
			BufferedWriter bw = new BufferedWriter(fw);
			for(int i = 0; i<propertyList.size(); i++){
				bw.write(propertyList.get(i)+"\n");
			}
			bw.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		logger.debug( "configureServerProperties <");
	}
	
	public void buildStartMinecraftShellScript(String minecraftDirectory){
		logger.debug( "buildStartMinecraftShellScript >");
		try{
			FileWriter fw = new FileWriter(new File(minecraftDirectory+"/start_minecraft.sh"));
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("cd " + minecraftDirectory +"\n");
			bw.write("java -Xmx2048M -Xms32M -jar minecraft_server.1.6.2.jar nogui &");
			bw.write("echo $! > ./pid.txt");
			bw.close();
			
			//Make .sh runnable
			Process p = Runtime.getRuntime().exec("chmod +x " + minecraftDirectory + "/start_minecraft.sh");
			logger.debug(ShellUtils.outputToString(p));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		logger.debug( "buildStartMinecraftShellScript <");
	}
	
}
