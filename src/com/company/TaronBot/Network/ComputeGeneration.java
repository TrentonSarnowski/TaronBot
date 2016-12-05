package com.company.TaronBot.Network;

import java.util.ArrayList;
import java.util.List;

import com.company.TaronBot.Game.Board;

import tech.deef.Tools.StaticGlobals;

public class ComputeGeneration {
	public static void compute(List<TakNetwork> networks, int Threads){
		
		int totalGames = networks.size()*networks.size();
		int GamesPerThread = (int) Math.floor((double)totalGames/((double)Threads))+1;
		
		//clear losses and wins for games.
		for(TakNetwork net: networks){
			net.setWins(0);
			net.setLosses(0);
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
		int atGame = 0;
		TakNetwork net1 = null;
		TakNetwork net2 = null;
		
		for(int i = 0; i < GamesPerThread; i++){
			
			atGame = threadNum * GamesPerThread + i;
			net1num = atGame/networks.size();
			net2num = atGame%networks.size();
			
			
			long start=System.currentTimeMillis();
			try{
				net1 = networks.get(net1num);
				net2 = networks.get(net2num);
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
			
			if(StaticGlobals.PRINT_GAME_WINNER){
				System.out.println("game " + net1num + ":" + net1num + " Winner: " 
				+  winner + " Time: "+(System.currentTimeMillis()-start)/1000.0 + " S");
			}

		}
		
		
		
	}
}
