package com.company.TaronBot.network;

import java.util.List;

public class CalulateNetwork {
	
	
	/**
	 * 
	 * @param gameBoard The input game board in the form of -1, 0 and 1 repersenting 
	 * 			opponent piece, no piece, and our piece
	 * 			1 is a flat, 2 is a wall, and 3 is a capstone. 
	 * @param network The network that will be calculated using the input game board.
	 * 
	 * @return List of moves in order of best to worst. 
	 */
	public List<String> CalculateMoves(int[][] gameBoard, NodalNetwork network){
		
		
		
		return null;
	}
	
	
	
	/**
	 * 
	 * @param placement double array that contains the output of the network when run.
	 * 			sorts through all of the moves in order of highest priority to lowest priority
	 * 			and returns the proper name for the move. 
	 * 
	 * @param movement secondary array containing the movement order should it be determined that
	 *			that a movement is what is wanted.
	 *
	 * @return a list from best to worst of the moves in standard string format. 
	 */
	private List<String> sortMoves(double[][] placement,  double[] movement){
		
		
		return null;
	}
}
