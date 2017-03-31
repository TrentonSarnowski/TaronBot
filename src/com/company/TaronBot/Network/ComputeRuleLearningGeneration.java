package com.company.TaronBot.Network;

import com.company.TaronBot.Game.RunGames;
import tech.deef.Tools.StaticGlobals;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by sarnowskit on 2/8/2017.
 */
public class ComputeRuleLearningGeneration {
	public static List<TakNetwork> compute(List<TakNetwork> networks, int Threads, Logger logger) {
		StaticGlobals.destackCount = 0;
		StaticGlobals.flatCount = 0;
		StaticGlobals.moveCount = 0;
		int valueArray[][] = new int[networks.size()][networks.size()];
		StaticGlobals.roadCount = 0;
		int totalGames = networks.size() * networks.size();
		int GamesPerThread = (int) Math.floor((double) totalGames / ((double) Threads)) + 1;

		// clear losses and wins for games.
		for (TakNetwork net : networks) {
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
