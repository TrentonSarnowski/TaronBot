package com.company.TaronBot.network;

import java.util.ArrayList;
import java.util.List;

import com.company.TaronBot.Game.Move;

public class Network {
	int width;
	int height;
	int depth;
	int layers;
	
	ArrayList<NetworkLayer> network = new ArrayList<NetworkLayer>();
	nonLinearFunction function = ((double d) -> Math.tanh(d));
	
	
	public Network(int height, int width, int depth, int layers){
		int[] inputDimensions = {width,depth,height,1};
		int[] middleDimensions = {width,height,1,1};
		int[] outputDimensions = {width,depth,4,1};
		int[] moveOutputDimensions = {width+3,3,1,1};
		
				
		network.add(new NetworkLayer(inputDimensions, middleDimensions, function));
		
		for(int i = 0; i < layers; i++){
			network.add(new NetworkLayer(middleDimensions, middleDimensions, function));
			
		}
		
		network.add(new NetworkLayer(middleDimensions, outputDimensions , function));
		network.add(new NetworkLayer(middleDimensions, moveOutputDimensions , function));
		
		
	}
	
	
	public List<Move> calculateMoves(int[][][] board){
		
		//TODO, convert the board to a 4 d array. 
		
		double[][][][] output;
		
		//inital calculation
		output = network.get(0).calculate(convertArray(board)); //TODO test if tgus needs to be a -2 or -1
		for(int i = 1; i < network.size()-1; i++){
			output = network.get(i).calculate(output); //do all other layers but the last 2
		}
		
		
		//output to 
		double[][][][] placements = network.get(network.size()-1).calculate(output);
		double[][][][] moveOutput = network.get(network.size()).calculate(output);
		
		return sortedMoves(placements, moveOutput);
				
	}

	//converts the array input into the calculation system into a double array of the correct size
	private double[][][][] convertArray(int[][][] board) {
		
		double[][][][] output = new double[board.length][board[0].length][board[0][0].length][1];
		//create an array of the correct size to house the input values.
		
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[0].length; j++){
				for(int k = 0; k < board[0][0].length; k++){
					output[i][j][k][0] = (double) board[i][j][k];
				}
			}
		}
		
		return null;
	}

	//takes the output from the network and converts it into a sorted series of moves
	//all moves are added to this list, including those that would make the bot immediately lose or win. 
	private List<Move> sortedMoves(double[][][][] placements, double[][][][] moveOutput) {		
		List<Move> moves = new ArrayList<Move>(placements[0].length * placements.length*(3+moveOutput.length));
		
		//TODO add the creation method. 
		
		
		return null;
	}
	
	
	
	
}
