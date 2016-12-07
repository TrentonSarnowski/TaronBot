package com.company.TaronBot;

import java.util.Scanner;

import tech.deef.Tools.StaticGlobals;

public class ControllClass {

	/**
	 * TODO read in networks
	 * mutate a list of networks
	 * adjust verbosity of output
	 * generate a series of networks
	 * select mutation method
	 * select mutation percentage
	 * help files
	 * log into playtak
	 * automate games on playtak
	 * 
	 */
	
	
	public static void StartControll() {
		
		String input = "";
		Scanner reader = new Scanner(System.in);
		while(true){
			System.out.println("Waiting for input: ");
			input = reader.nextLine();
			
			switch(input){
			
			case "pause": 
				StaticGlobals.PAUSED = true;
				System.out.println("Paused");
				break;
			case "unpause":
				StaticGlobals.PAUSED = false;
				System.out.println("Unpaused");
				break;
			default: 
				System.out.println(input + " not recognized as command");
			}
			
		}
		
	}

}
