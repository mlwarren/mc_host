package com.mlwarren.mc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.mlwarren.mc.utils.LoggingUtils;

public class Main {
	
	public static void main(String[] args) {
		LoggingUtils.log("DEBUG", "main >");
		Provision provision = new Provision();
		provision.createNewServer("/Users/mlwarren/mc_host/mc.host/extras/", "MCTemplate.zip", "/Users/mlwarren/TEST/");
		LoggingUtils.log("DEBUG", "main <");
	}

}
