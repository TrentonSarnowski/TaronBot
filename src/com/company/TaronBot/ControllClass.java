package com.company.TaronBot;

import java.util.Scanner;

import com.company.TaronBot.Game.Board;
import com.company.TaronBot.Network.TakNetwork;
import com.company.TestingMain;
//import com.sun.org.apache.xpath.internal.operations.String;
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
		String net1="0";
		String net2="1";
		int size=5;

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
				case "board":
					StaticGlobals.PRINT_GAME_BOARD=true;
					break;
				case "load":
					StaticGlobals.LOAD_FROM_LAST_RUN=true;
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
				case "load":
					StaticGlobals.LOAD_FROM_LAST_RUN=false;
					break;
				case "board":
					StaticGlobals.PRINT_GAME_BOARD=true;
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
			case "continue": {
				boolean previous = StaticGlobals.LOAD_FROM_LAST_RUN;
				StaticGlobals.LOAD_FROM_LAST_RUN = true;

				Thread t = new Thread() {

					@Override
					public void run() {
						TestingMain.TestGnerationalGrowth(32, 256, 8, 5);

					}

				};
				t.start();
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					System.out.println("Bahhh: how did this break!!!");
				}
				StaticGlobals.LOAD_FROM_LAST_RUN = previous;
			}
				break;
			case "generate": {
				Thread t = new Thread() {

					@Override
					public void run() {
						TestingMain.TestGnerationalGrowth(32, 256, 8, 5);

					}

				};
				t.start();
			}
				break;

			case "unpause":
				StaticGlobals.PAUSED = false;
				System.out.println("Unpaused");
				break;

			case "player1":
				net1=reader.next();
				System.out.println(net1);
				break;
			case "player2":
				net2=reader.next();
				System.out.println(net2);
				break;
			case "size":
				size =reader.nextInt();
				break;
			case "play":
				//TakNetwork network1=null;
				//TakNetwork network2=null;
					//String line =reader.nextLine();
				StaticGlobals.PRINT_GAME_MOVES=true;
					//	Scanner r=new Scanner(line);

				System.out.println("Player1: "+net1+" Player2: "+net2);
				Board.playGame(TestingMain.loadTesting("networks\\TestGnerationalGrowth\\output\\Network"+size+"x"+size+net1+".taknetwork"),TestingMain.loadTesting("networks\\TestGnerationalGrowth\\output\\Network"+size+"x"+size+net2+".taknetwork"),size);
				StaticGlobals.PRINT_GAME_MOVES =false;
				break;
			default: 
				System.out.println(input + " not recognized as command");
			}
			
		}
		
	}

}
