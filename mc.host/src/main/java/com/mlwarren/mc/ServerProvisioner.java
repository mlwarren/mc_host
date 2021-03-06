package com.mlwarren.mc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mlwarren.mc.db.ServerDAO;
import com.mlwarren.mc.utils.ShellUtils;

public class ServerProvisioner{
	
	private static Logger logger = LogManager.getLogger(ServerProvisioner.class);

	public Server createNewServer(String serverSourceAbsolutePath, String mcServerVersion, String serverDirectory, String serverParentDirectoryAbsolutePath){
		logger.debug( "createNewServer >");
		copyZipToDestination(serverSourceAbsolutePath, mcServerVersion, serverDirectory, serverParentDirectoryAbsolutePath);
		configureServerProperties(serverParentDirectoryAbsolutePath+serverDirectory);
		buildStartMinecraftShellScript(serverParentDirectoryAbsolutePath+serverDirectory);
		Server returnServer = new Server(-1, -1, new Date(), serverParentDirectoryAbsolutePath+serverDirectory, serverParentDirectoryAbsolutePath+serverDirectory+"/start_minecraft.sh", true);
		logger.debug( "createNewServer <");
		return returnServer;
	}
	
	public void editServer(int id){
		logger.debug("editServer > ");
		ServerDAO serverDAO = new ServerDAO();
		Server server = serverDAO.getServerByID(id);
		configureServerProperties(server.getServerContainerAbsolutePath());
		logger.debug("editServer < ");
	}
	
	public void copyZipToDestination(String serverSourceAbsolutePath, String zipFileNameDirectory, String serverDirectory, String serverDestinationAbsolutePath){
		logger.debug( "copyZipToDestination >");
		try {
			//Make directory for server destination absolute path
			Process p = Runtime.getRuntime().exec("mkdir " + serverDestinationAbsolutePath);
			logger.debug(ShellUtils.outputToString(p));
			//Copy zip to this directory
			p = Runtime.getRuntime().exec("cp " + serverSourceAbsolutePath+zipFileNameDirectory+".zip" + " " + serverDestinationAbsolutePath);
			logger.debug(ShellUtils.outputToString(p));
			//Unzip zip file
			p = Runtime.getRuntime().exec("unzip " + serverDestinationAbsolutePath+zipFileNameDirectory+".zip" + " -d " + serverDestinationAbsolutePath);
			logger.debug(ShellUtils.outputToString(p));
			//Output zip directory by default will be given zip name, change the name this way
			p = Runtime.getRuntime().exec("mv " + serverDestinationAbsolutePath+zipFileNameDirectory + " " + serverDestinationAbsolutePath+serverDirectory);
			logger.debug(ShellUtils.outputToString(p));
			//Remove zip file
			p = Runtime.getRuntime().exec("rm " + serverDestinationAbsolutePath+zipFileNameDirectory+".zip");
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
			//TODO: make dynamic based on input .txt file -- different versions of vanilla minecraft and bukkit
			FileWriter fw = new FileWriter(new File(minecraftDirectory+"/start_minecraft.sh"));
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("cd " + minecraftDirectory +"\n");
			bw.write("java -Xmx2048M -Xms32M -jar minecraft_server.1.6.2.jar nogui & ");
			//For bukkit -- java -Xmx2048M -Xms32M -jar craftbukkit-1.5.2-R1.0.jar -o true & echo $! > ./pid.txt
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
