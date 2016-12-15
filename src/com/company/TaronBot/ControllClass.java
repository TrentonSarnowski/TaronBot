package com.company.TaronBot;

import java.util.Scanner;

import tech.deef.Tools.StaticGlobals;

public class ControllClass {

	/**
	 * TODO read in networks
	 * mutate a list of networks
	 * adjust verbosity of output
	 	active Human game out puts weight
	 	generation data

	 * generate a series of networks
	   generate
	 	-s/-size <size>default 5</size>
	 	-d/-depth <depth>default 8 </depth>
	 	-q/-quantity/-num <quantity>default 64</quantity>
	 	-complexity/ -c <complexity> default size*4</complexity>
	 	-seed <seed>recorded in log default no seed</seed>
	 	-name/-n <default>required</default>
	 * select mutation method
	 * select mutation percentage
	 * help files
	 * log into playtak
	 	-name
	 	-password
	 * automate games on playtak
	 * 
	 */
	
	
	public static void StartControll() {
		
		String input = "";
		Scanner reader = new Scanner(System.in);
		while(true){
			System.out.println("Waiting for input: ");
			input = reader.next();
			
			switch(input.toLowerCase()){
			case "enable":
				input = reader.next();
				switch(input.toLowerCase()){
				case "moves":
					StaticGlobals.PRINT_GAME_MOVES = true;
					break;
				default:
					System.out.println(input + " not recognized as boolean Switch");
				}	
				break;
			case "disable":
				input = reader.next();
				switch(input.toLowerCase()){
				case "moves":
					StaticGlobals.PRINT_GAME_MOVES = false;
					break;
				default:
					System.out.println(input + " not recognized as boolean Switch");
				}
				break;
			case "save": 
				StaticGlobals.SAVE_NETWORKS_OUT_AND_EXIT = true;
				System.out.println("Saving after next generation");
				return;
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
