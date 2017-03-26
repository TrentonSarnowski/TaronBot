package com.company.TaronBot.Network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.company.TaronBot.Game.Board;

import com.company.TaronBot.Game.RunGames;
import tech.deef.Tools.StaticGlobals;

public class ComputeGeneration {
	/**
	 * Plays games and returns a sorted list of networks
	 * @param networks
	 * @param Threads
	 * @param logger
	 * @return
	 */
	public static List<TakNetwork> compute(List<TakNetwork> networks, int Threads, Logger logger) {
		StaticGlobals.destackCount = 0;
		StaticGlobals.flatCount = 0;
		StaticGlobals.moveCount = 0;
		StaticGlobals.roadCount=0;

		for(TakNetwork net: networks){
			net.setWins(0);
			net.setLosses(0);
		}



		RunGames games = RunGames.RunGames((ArrayList) networks);

		for (int i = 0; i < networks.size(); i++) {
			for (int j = 0; j < networks.size() / 4; j++) {
				games.addGame(i, j);
			}
		}
		games.playGamesSetThreadsBlocks(Threads);


		System.out.println("Road Ratio: " + StaticGlobals.roadCount + " / " + (games.getCount()));
		System.out.println("Flat Count: " + StaticGlobals.flatCount);
		System.out.println("DeStack Count: " + StaticGlobals.destackCount);

		System.out.println("Move Count: " + StaticGlobals.moveCount);

		return games.GetSortedNetworks(RunGames.victoryType.WIN_NO_WEIGHT);



	}


}
