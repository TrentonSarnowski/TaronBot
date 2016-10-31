package com.company.TaronBot.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.company.TaronBot.Game.Move;
import com.company.TaronBot.Game.Moves.Placement;

public class Network {
	int width;
	int height;
	int depth;
	int layers;
	
	ArrayList<NetworkLayer> network = new ArrayList<NetworkLayer>();
	nonLinearFunction function = ((double d) -> (6/(1+(Math.pow(Math.E,(-.5*d)))-2.99)));
	//function is slightly modified version of tanh
	//-3 to 3, and the input range is ~-10 to ~10
	
	
	//this constructor creates a fully blank network of the given size. 
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
	
	
	//fully radomizes the entire network.
	public void randomize(Random rand){
		for(NetworkLayer layer : network){
			layer.randomize(rand);
			
		}
	}
	
	/**
	 * 
	 * @param rand input random generator
	 * @param changePrecentage the percentage of nodes that should be changed. (0.0-1.0) expected
	 */
	public void mutate (Random rand, double changePrecentage){
		
		for(NetworkLayer layer : network){
			layer.mutate(rand, changePrecentage);
			
		}
	}
	
	
	public List<Move> calculate(int[][][] board){
				
		double[][][][] output;
		
		//inital calculation
		output = network.get(0).calculate(convertArray(board)); //TODO test if this needs to be a -2 or -1
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
		
		for(int i = 0; i < placements.length; i++){
			for(int j = 0; j < placements[0].length; j++){
				moves.add(new Placement(i,j,1,placements[0][i][j][0]));
			}
		}
		
		for(int i = 0; i < placements.length; i++){
			for(int j = 0; j < placements[0].length; j++){
				moves.add(new Placement(i,j,2,placements[1][i][j][0]));
			}
		}
		
		for(int i = 0; i < placements.length; i++){
			for(int j = 0; j < placements[0].length; j++){
				moves.add(new Placement(i,j,3,placements[2][i][j][0]));
			}
		}
		
		for(int i = 0; i < placements.length; i++){
			for(int j = 0; j < placements[0].length; j++){
				moves.add(createPlacement(i,j,0, moveOutput));// todo, figure out how the move output is created. set it up so that I can pass an array 3 times. 
				moves.add(createPlacement(i,j,1, moveOutput));
				moves.add(createPlacement(i,j,2, moveOutput));
			}
		}
		
		
		
		
		return null;
	}


	private Move createPlacement(int XInput, int YInput, int NumOfMove, double[][][][] moveOutput) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}
