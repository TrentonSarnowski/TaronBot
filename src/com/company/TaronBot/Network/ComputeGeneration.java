package com.company.TaronBot.Network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import com.company.TaronBot.Game.Board;

import com.company.TaronBot.Game.RunGames;
import com.company.TestingMain;
import tech.deef.Tools.StaticGlobals;

public class ComputeGeneration {
	private static ArrayList<TakNetwork> randNets=new ArrayList<>();

	public static List<TakNetwork> compute(List<TakNetwork> networks, int Threads, Logger logger) {
		int valueArray[][]=new int[networks.size()][networks.size()];
		StaticGlobals.roadCount=0;
		int totalGames = networks.size()*networks.size();
		int GamesPerThread = (int) Math.floor((double)totalGames/((double)Threads))+1;
		
		//clear losses and wins for games.
		for(TakNetwork net: networks){
			net.setWins(0);
			net.setLosses(0);
		}

		/*
		long RandomNunber=0;
		Random random=new Random();
		randNets=new ArrayList<>();
		for(int j = 0; j < networks.size(); j++){
			TakNetwork testNetwork = new TakNetwork(9, 8, 8, 3);
			RandomNunber = random.nextInt();
			Random rand = new Random(RandomNunber);
			testNetwork.randomize(rand);

			randNets.add(testNetwork);

			//all 100 networks generate
		}//*/

		RunGames games = new RunGames((ArrayList) networks);

		for (int i = 0; i < networks.size(); i++) {
			for (int j = 0; j < networks.size() / 4; j++) {
				games.addGame(i, j);
			}
		}
		games.playGamesSetThreadsBlocks(Threads);


		//System.out.println(StaticGlobals.roadCount + " / " + (games.getCount()));
		return games.GetSortedNetworks(RunGames.victoryType.WIN_ROAD_ONLY);



	}

	private static void RunGamesForEigenValues(int threadNum,List<TakNetwork> networks, int startingPoint, int endPoint, Logger logger , int[][] valueArray){
		for(int i=startingPoint;i<endPoint;i++){
			for (int j = 0; j <networks.size() ; j++) {
				try {
					while(StaticGlobals.PAUSED){
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					int score = Board.playGame(networks.get(i), networks.get(j), networks.get(i).getWidth());


					if(score!=0) {
						networks.get(i).setWins(networks.get(i).getWins() + score/Math.abs(score));

						networks.get(j).setWins(networks.get(j).getWins() + (-1*score)/Math.abs(score));
						if(score==-32) {
							valueArray[j][i] += networks.get(i).getWidth();
						}else if(score<0){
							valueArray[j][i] += 1;
						}
						if(score==32) {
							valueArray[i][j] += networks.get(i).getWidth();
						}else if(score>0){
							valueArray[i][j] += 1;
						}
						//valueArray[i][j] -= score/Math.abs(score);
					}
				}catch (Exception e){
					//System.err.println(e.getStackTrace());
					e.printStackTrace();
					System.err.println("Thread: "+threadNum+ " game: "+ i+" vs. "+j);

				}
			}
		}

	}
	private static void RunSelectionOfGames(int threadNum, List<TakNetwork> networks, int GamesPerThread, Logger logger){
		try{
			int net1num = 0;
			int net2num = 0;
			//int net3num = 0;
			int atGame = 0;
			TakNetwork net1 = null;
			TakNetwork net2 = null;
			//TakNetwork net3 = null;
			int winner = 0;
	
			for(int i = 0; i < GamesPerThread; i++){
				
				while(StaticGlobals.PAUSED){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	
				atGame = threadNum * GamesPerThread + i;
				net1num = atGame/networks.size();
				net2num = atGame%networks.size();
				//net3num = atGame%networks.size();
	
				
				long start=System.currentTimeMillis();
				try{
					net1 = networks.get(net1num);
					net2 = networks.get(net2num);
					//net3 = randNets.get(net3num);
				}catch(IndexOutOfBoundsException e){
					//last network run. exit. 
					return;
				}
	
				try{
					winner = Board.playGame(net1, net2, net1.getWidth());
				}catch(ArrayIndexOutOfBoundsException e){
					
					if(StaticGlobals.LOGGING_ENABLED){
					
						logger.severe(Arrays.toString(e.getStackTrace()));
					}
					e.printStackTrace();
					System.out.println("Error caught in game " +net1num+ " : " + net2num);
				}
				
				//if(Math.abs(winner)==32){
					//System.err.println("Road");

				net1.setWins(net1.getWins() + winner);

				net2.setWins(net2.getWins() + (-1*winner));
				//todo


				//winner = Board.playGame(net1, net3, 8);
	
				//if(Math.abs(winner)==32){
				//	System.err.println("Road");
				//}
				//net1.setWins(net1.getWins() + winner);
					//net3.setLosses(net3.getLosses() + -1*winner);
	
	
				if(StaticGlobals.PRINT_GAME_WINNER){
					System.out.println("game " + net1num + ":" + net2num + " Winner: "
					+  winner + " Time: "+(System.currentTimeMillis()-start)/1000.0 + " S");
				}
	
			}
			
		}
		catch(Exception e){
			if(StaticGlobals.LOGGING_ENABLED){
				logger.severe(Arrays.toString(e.getStackTrace()));
			}
			e.printStackTrace();
			throw e;
		}
	}
}
