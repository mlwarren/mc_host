package com.mlwarren.mc;

import java.io.Console;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mlwarren.mc.db.ServerDAO;
import com.mlwarren.mc.utils.ShellUtils;


public class Main {
	
	private static Logger logger = LogManager.getLogger(Main.class);
	
	public static void main(String[] args) {
		logger.debug( "main >");

		ServerDAO serverDAO = new ServerDAO();
		serverDAO.createServerTable(); //Create table if it doesn't already exist
		ServerProvisioner serverProvisioner = new ServerProvisioner();
		ServerController serverController = new ServerController();
		ServerDecomissioner serverDecomissioner = new ServerDecomissioner();
		
		Console console = System.console();
		String input="";
		while(!(input.equals("q")||input.equals("Q"))){
			System.out.println("(C)heck server inventory");
			System.out.println("(P)rovision new server");
			System.out.println("(S)top server");
			System.out.println("S(t)art server");
			System.out.println("(E)dit server");
			System.out.println("(D)ecomission server");
			System.out.println("(Q)uit");
			input=console.readLine();
			if(input.equals("C") || input.equals("c")){
				System.out.println("Server inventory check:");
				List<Server> serverList = serverDAO.getAllServers();
				if(serverList==null){
					System.out.println("No servers provisoined.");
				}
				else{
					System.out.println(serverList.toString());
				}
				continue;
			}
			if(input.equals("P") || input.equals("p")){
				System.out.println("Provisioning new server:");
				String serverSourceAbsolutePath = console.readLine("Enter server source absolute path, remember to include trailing /\n");
				String zipFileName = console.readLine("Enter zip file name, omit .zip extension\n");
				String serverDestinationAbsolutePath = console.readLine("Enter server destiantion absolute path, remember to include trailing /\n");
				String serverDirectory = console.readLine("Enter server directory name (relative path), remember to include trailing /\n");
				Server server = serverProvisioner.createNewServer(serverSourceAbsolutePath, zipFileName, serverDirectory, serverDestinationAbsolutePath);
				server=serverController.startServer(server);
				System.out.println("Server provisoned. Server information: ");
				System.out.println(server);
				continue;
			}
			if(input.equals("S") || input.equals("s")){
				System.out.println("Stopping server:");
				String id = console.readLine("Enter ID of server to stop\n");
				Server server = serverDAO.getServerByID(Integer.parseInt(id));
				if(server==null){
					System.out.println("ID not found, cannot stop that server.");
					continue;
				}
				serverController.stopServer(server);
				continue;
			}
			if(input.equals("T") || input.equals("t")){
				System.out.println("Starting server:");
				String id = console.readLine("Enter ID of server to start\n");
				Server server = serverDAO.getServerByID(Integer.parseInt(id));
				if(server==null){
					System.out.println("ID not found, cannot start that server.");
					continue;
				}
				server=serverController.startServer(server);
				continue;
			}
			if(input.equals("E") || input.equals("e")){
				System.out.println("Editing server:");
				String id = console.readLine("Enter ID of server to edit\n");
				Server server = serverDAO.getServerByID(Integer.parseInt(id));
				if(server==null){
					System.out.println("ID not found, cannot edit that server.");
					continue;
				}
				serverProvisioner.editServer(Integer.parseInt(id));
				System.out.println("Server edited, please stop and restart server for edits to take effect.");
				continue;
			} //TODO: Create option for upgrading/downgrading server jar, will need to copy jar and edit start_minecraft.sh script
			if(input.equals("D") || input.equals("d")){
				System.out.println("Decomissioning server:");
				String idString = console.readLine("Enter ID of server to decomission.\n");
				Server server = serverDecomissioner.removeServer(Integer.parseInt(idString));
				if(server!=null){
					serverDecomissioner.deleteServerFilesFromFileSystem(server.getServerContainerAbsolutePath());
					System.out.println("Server with ID " + idString + " has been decomissioned.");
					continue;
				}
				else{
					System.out.println("Can't decomission that server, it's not in our catalogue");
				}
				continue;
			}
		}
		
		logger.debug( "main <");
	}

}
