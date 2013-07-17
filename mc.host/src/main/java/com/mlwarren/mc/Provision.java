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

import com.mlwarren.mc.utils.LoggingUtils;
import com.mlwarren.mc.utils.ShellUtils;

public class Provision implements Runnable{
	String serverSourceAbsolutePath;
	String zipFileName;
	String serverDestinationAbsolutePath;
	public Provision(String serverSourceAbsolutePath, String zipFileName, String serverDestinationAbsolutePath){
		this.serverSourceAbsolutePath=serverSourceAbsolutePath;
		this.zipFileName=zipFileName;
		this.serverDestinationAbsolutePath=serverDestinationAbsolutePath;
	}
	
	public void createNewServer(){
		LoggingUtils.log("DEBUG", "createNewServer >");
		copyZipToDestination(serverSourceAbsolutePath, zipFileName, serverDestinationAbsolutePath);
		configureServerProperties(serverDestinationAbsolutePath+zipFileName);
		LoggingUtils.log("DEBUG", "createNewServer <");
	}
	
	public void copyZipToDestination(String serverSourceAbsolutePath, String outputDirectory, String serverDestinationAbsolutePath){
		LoggingUtils.log("DEBUG", "copyZipToDestination >");
		try {
			Process p = Runtime.getRuntime().exec("cp " + serverSourceAbsolutePath+outputDirectory+".zip" + " " + serverDestinationAbsolutePath);
			LoggingUtils.log("DEBUG",ShellUtils.outputToString(p));
			p = Runtime.getRuntime().exec("unzip " + serverDestinationAbsolutePath+outputDirectory+".zip" + " -d " + serverDestinationAbsolutePath);
			LoggingUtils.log("DEBUG",ShellUtils.outputToString(p));
			p = Runtime.getRuntime().exec("rm " + serverDestinationAbsolutePath+outputDirectory);
			LoggingUtils.log("DEBUG",ShellUtils.outputToString(p));

		} catch (IOException e) {
			e.printStackTrace();
		}
		LoggingUtils.log("DEBUG", "copyZipToDestination <");
	}
	
	public void configureServerProperties(String serverProperties){
		LoggingUtils.log("DEBUG", "configureServerProperties >");
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
		LoggingUtils.log("DEBUG", "configureServerProperties <");
	}
	
	public void startServer(){
		LoggingUtils.log("DEBUG", "startServer >");
		try {
			buildStartMinecraftShellScript(serverDestinationAbsolutePath+zipFileName);
			Process p = Runtime.getRuntime().exec("chmod +x " + serverDestinationAbsolutePath+zipFileName+"/start_minecraft.sh");
			LoggingUtils.log("DEBUG",ShellUtils.outputToString(p));
			p = Runtime.getRuntime().exec(serverDestinationAbsolutePath+zipFileName+"/start_minecraft.sh");
			LoggingUtils.log("DEBUG",ShellUtils.outputToString(p));

		} catch (IOException e) {
			e.printStackTrace();
		}
		LoggingUtils.log("DEBUG", "startServer <");
	}
	
	public void buildStartMinecraftShellScript(String minecraftDirectory){
		try{
			FileWriter fw = new FileWriter(new File(minecraftDirectory+"/start_minecraft.sh"));
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("cd " + minecraftDirectory +"\n");
			bw.write("java -Xmx2048M -Xms32M -jar minecraft_server.1.6.2.jar nogui &");
			bw.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public void run() {
		startServer();
	}
}
