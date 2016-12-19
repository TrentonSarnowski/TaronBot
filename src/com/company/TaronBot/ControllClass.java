package com.company.TaronBot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
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
		-name/-n <default>required</default>
		-s/-size <size>default 5</size>
		-d/-depth <depth>default 8 </depth>
		-q/-quantity/-num <quantity>default 64</quantity>
		-complexity/ -c <complexity> default size*4</complexity>
		-seed <seed>recorded in log default no seed</seed>

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
		Map<String, Object> objects = new HashMap<String, Object>();


		String net1="0";
		String net2="1";
		int size=5;
		int genSize=32;
		int runGens=64;
		int coreCount=8;

		String input = "";
		Scanner reader = new Scanner(System.in);
		while(true){
			System.out.print("\nWaiting for input: ");
			input = reader.next();
			
			//***********************************START OF INPUT SWITCH*******************************
			switch(input.toLowerCase()){


			//***********************************ENABLE DIFFERNET GLOBAL BOOLEAN SWITCHES************
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


			//***********************************Disable DIFFERNET GLOBAL BOOLEAN SWITCHES***********
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
				final int innerSize=size;
				final int innerGen=runGens;
				final int innerRunGenSize=genSize;
				final int innerDepth=coreCount;

				Thread t = new Thread() {

					@Override
					public void run() {
						TestingMain.TestGnerationalGrowth(innerRunGenSize, innerGen, innerDepth, innerSize);

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
			case "gensize":
				genSize=reader.nextInt();
				break;
			case "rungens":
				runGens=reader.nextInt();
				break;
			case "core":
				coreCount=reader.nextInt();

				break;
			case "depth":
				StaticGlobals.DEPTH=reader.nextInt();
				break;


				//**************************GENERATE A NEW NETWORK*************************
			case "generate": {
				//format without arguments right now to set formula.


				String line = reader.nextLine();
				String[] commands = line.split("-");
				String s1 = "",s2 = "";
				HashMap<String, String> commandVal = new HashMap<String, String>();

				//set default values

				int GameSize = 5;
				int NetworkDepth = 8;
				int generationQuantity = 64;
				int complexity = 5*4;
				int seed = (new Random()).nextInt();
				String name = "";
				/*
				-name/-n <default>required</default>
				-s/-size <size>default 5</size>
				-d/-depth <depth>default 8 </depth>
				-q/-quantity/-num <quantity>default 64</quantity>
				-complexity/ -c <complexity> default size*4</complexity>
				-seed <seed>recorded in log default no seed</seed>
				*/

				for(String command: commands) {
                    try {
                        System.out.println(command);
                        s1 = command.split(" ")[0];
                        s2 = command.substring(command.indexOf(" ") + 1);
                        System.out.println("s1:" + s1 + "\ns2:" + s2);
                        commandVal.put(s1, s2);
                    } catch (IndexOutOfBoundsException e) {
                        //do nothing, possibly output that command is not recognized and break...
                    }
                    final int innerSize = size;
                    final int innerGen = runGens;
                    final int innerRunGenSize = genSize;
                    final int cores = coreCount;

                    Thread t = new Thread() {

                        @Override
                        public void run() {
                            TestingMain.TestGnerationalGrowth(innerRunGenSize, innerGen, cores, innerSize);

                        }
                    };

                    if (!(commandVal.containsKey("name") || commandVal.containsKey("n"))) {
                        System.out.println("network must be named to continue");
                        break;
                    }

                    if (commandVal.containsKey("n")) {
                        name = (commandVal.get("n"));
                    }
                    if (commandVal.containsKey("name")) {
                        name = (commandVal.get("name"));
                    }


                    if (commandVal.containsKey("s")) {
                        size = Integer.parseInt(commandVal.get("s"));
                    }
                    if (commandVal.containsKey("size")) {
                        size = Integer.parseInt(commandVal.get("size"));
                    }


                    if (commandVal.containsKey("d")) {
                        NetworkDepth = Integer.parseInt(commandVal.get("d"));
                    }
                    if (commandVal.containsKey("depth")) {
                        NetworkDepth = Integer.parseInt(commandVal.get("depth"));
                    }

                    if (commandVal.containsKey("q")) {
                        generationQuantity = Integer.parseInt(commandVal.get("q"));
                    }
                    if (commandVal.containsKey("quantity")) {
                        generationQuantity = Integer.parseInt(commandVal.get("quantity"));
                    }
                    if (commandVal.containsKey("num")) {
                        generationQuantity = Integer.parseInt(commandVal.get("num"));
                    }

                    if (commandVal.containsKey("complexity")) {
                        complexity = Integer.parseInt(commandVal.get("complexity"));
                    }
                    if (commandVal.containsKey("c")) {
                        complexity = Integer.parseInt(commandVal.get("c"));
                    }

                    if (commandVal.containsKey("seed")) {
                        seed = Integer.parseInt(commandVal.get("seed"));
                    }
                }
                    System.out.println("generatong networks");
                    ArrayList<TakNetwork> newNetwork = new ArrayList<TakNetwork>();
                    for (int i = 0; i < generationQuantity; i++) {
                        newNetwork.add(new TakNetwork(size, size, size + 1, NetworkDepth, 0, i));
                    }
                    objects.put(name, newNetwork);
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
