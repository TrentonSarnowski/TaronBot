package com.company.TaronBot.Network;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.company.TaronBot.Game.Board;

import com.company.TestingMain;
import tech.deef.Tools.StaticGlobals;

public class ComputeGeneration {
	private static ArrayList<TakNetwork> randNets=new ArrayList<>();
	public static void compute(List<TakNetwork> networks, int Threads){
		
		int totalGames = networks.size()*networks.size();
		int GamesPerThread = (int) Math.floor((double)totalGames/((double)Threads))+1;
		
		//clear losses and wins for games.
		for(TakNetwork net: networks){
			net.setWins(0);
			net.setLosses(0);
		}

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
		}
		
		ArrayList<Thread> threads = new ArrayList<Thread>(Threads);
		for(int i = 0; i < Threads; i++){
			
			final int threadNum = i;
			Thread t = new Thread(){

				@Override
				public void run() {
					RunSelectionOfGames(threadNum,networks,GamesPerThread);
					
				}
				
			};
			threads.add(t);
		//	System.out.println(i);
		}
		
		
		
		
		
		//start Threads
		for(Thread thread: threads){
			//System.out.println(thread.toString());
			thread.start();	
			//System.out.println(thread.isAlive());
		}
		
		
		//wait for threads to end
		for(int i = 0; i < threads.size(); i++){
			try {
				//System.out.println(i);
				threads.get(i).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	
	private static void RunSelectionOfGames(int threadNum, List<TakNetwork> networks, int GamesPerThread){
		int net1num = 0;
		int net2num = 0;
		int net3num = 0;
		int atGame = 0;
		TakNetwork net1 = null;
		TakNetwork net2 = null;
		TakNetwork net3 = null;
		
		for(int i = 0; i < GamesPerThread; i++){
			
			atGame = threadNum * GamesPerThread + i;
			net1num = atGame/networks.size();
			net2num = atGame%networks.size();
			net3num = atGame%networks.size();
			
			
			long start=System.currentTimeMillis();
			try{
				net1 = networks.get(net1num);
				net2 = networks.get(net2num);
				net3 = randNets.get(net3num);
			}catch(IndexOutOfBoundsException e){
				//last network run. exit. 
				return;
			}
			int winner = Board.playGame(net1, net2, 8);
			
			if(winner == 1){
				net1.setWins(net1.getWins() + 1);
				net2.setLosses(net2.getLosses() + 1);
			}else{
				net2.setWins(net2.getWins() + 1);
				net1.setLosses(net1.getLosses() + 1);
			}
			 winner = Board.playGame(net1, net3, 8);

			if(winner == 1){
				net1.setWins(net1.getWins() + 1);
				net3.setLosses(net3.getLosses() + 1);
			}else{
				net3.setWins(net3.getWins() + 1);
				net1.setLosses(net1.getLosses() + 1);
			}
			
			if(StaticGlobals.PRINT_GAME_WINNER){
				System.out.println("game " + net1num + ":" + net1num + " Winner: " 
				+  winner + " Time: "+(System.currentTimeMillis()-start)/1000.0 + " S");
			}

		}
		
		
		
	}
}
