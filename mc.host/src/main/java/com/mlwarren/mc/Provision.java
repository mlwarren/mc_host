package com.mlwarren.mc;

import java.io.IOException;

import com.mlwarren.mc.utils.ShellUtils;

public class Provision {
	public void createNewServer(String serverSourceAbsolutePath, String zipFileName, String serverDestinationAbsolutePath){
		try {
			Process p = Runtime.getRuntime().exec("cp " + serverSourceAbsolutePath+zipFileName + " " + serverDestinationAbsolutePath);
			ShellUtils.outputToConsole(p);
			p = Runtime.getRuntime().exec("unzip " + serverDestinationAbsolutePath+zipFileName + " -d " + serverDestinationAbsolutePath);
			ShellUtils.outputToConsole(p);
			p = Runtime.getRuntime().exec("rm " + serverDestinationAbsolutePath+zipFileName);
			ShellUtils.outputToConsole(p);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
