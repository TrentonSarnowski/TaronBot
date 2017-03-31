package com.company.TaronBot.Game;

import com.company.TaronBot.Game.Move;
import com.company.TaronBot.Game.Moves.Placement;
import tech.deef.Tools.StaticGlobals;

/**
 * Created by sarnowskit on 1/31/2017.
 */
public class FastRoadFinder {

	/**
	 * returns true if a map does not contain a road may return false if a map does contain a road or does not contain a road
	 * 
	 * @param Map
	 * @return
	 */
	public static boolean NotRoadCheck(int[] Map) {
		for (int i = 0; i < Map.length - 1; i++) {
			if ((Map[i] & Map[i + 1]) == 0) {
				return true;
			}
		}
		return true;
	}

	public static native boolean RoadFound();

	public static boolean RoadChecker(int[] b) {
		int[] empty = new int[b.length];// makes empty array with extra space for a -1 value
		empty[0] = b[0];
		// if(empty[0]==0||b[b.length-1]==0){
		// return false;
		// }
		// if(!NotRoadCheck(b)){
		// return false;
		// }
		for (int i = 0; i < b.length * 2; i++) {// iterates through the process length squared times to ensure road discovery
			for (int j = 0; j < empty.length; j++) {// iterates performing transformations over list

				empty[j] = empty[j] | ((empty[j] >>> 1) & b[j]) | ((empty[j] << 1) & b[j]);// setting visited adjacent
				if (j < b.length - 1) {
					empty[j + 1] = empty[j + 1] | (empty[j] & b[j + 1]);// checking above for similarities
				}
				if (j > 0) {
					empty[j - 1] = empty[j - 1] | (empty[j] & b[j - 1]);// checking bellow for similarities
				}
			}

		}

		return empty[empty.length - 1] != 0;// will equal zero if empty
	}

	/**
	 * returns a boolean describing victory
	 * 
	 * @param b
	 * @return
	 */
	public static boolean RoadChecker(long[] b) {
		long[] empty = new long[b.length];// makes empty array with extra space for a -1 value
		empty[0] = b[0];

		for (int i = 0; i < b.length * 2; i++) {// iterates through the process length squared times to ensure road discovery
			for (int j = 0; j < empty.length; j++) {// iterates performing transformations over list
				if (j < b.length - 1) {
					empty[j + 1] = empty[j + 1] | (empty[j] & b[j + 1]);// checking above and bellow for simularities
				}
				empty[j] = empty[j] | ((empty[j] >>> 1) & b[j]) | ((empty[j] << 1) & b[j]);// setting visted adjacent squares

				if (j > 0) {
					empty[j - 1] = empty[j - 1] | (empty[j] & b[j - 1]);
				}
			}

		}

		return empty[empty.length - 1] != 0;// will equal zero if empty
	}

	/**
	 * Not working don't use REturns a move that leads to victory if able
	 * 
	 * @param b
	 * @return
	 */
	public static Move RoadFinder(long[] b) {
		long mult2 = 1024;
		for (int i = 0; i < b.length; i++) {
			long mult = 1;

			for (int j = 0; j < b.length; j++) {
				if ((b[i] & mult) != 0) {
					long store1 = b[i];
					long store2 = b[j];
					b[i] = b[i] | mult;
					b[j] = b[j] | mult2;
					if (RoadChecker(b)) {
						return new Placement(j, i, 1, 0);
					}
					b[i] = store1;
					b[j] = store2;
				}
				mult = mult << 1;
			}
			mult2 = mult2 << 1;
		}

		return null;

	}
}
