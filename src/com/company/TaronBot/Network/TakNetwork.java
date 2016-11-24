package com.company.TaronBot.Network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.company.TaronBot.Game.Move;
import com.company.TaronBot.Game.Moves.DeStack;
import com.company.TaronBot.Game.Moves.Placement;

public class TakNetwork implements Serializable{
	private int width;
	private int height;
	private int depth;
	private int layers;
	
	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}


	public int getDepth() {
		return depth;
	}


	public int getLayers() {
		return layers;
	}


	ArrayList<NetworkLayer> network = new ArrayList<NetworkLayer>();
	nonLinearFunction function = ((double d) -> ((6/(1+(Math.pow(Math.E,(-.5*d)))))-2.99));
	
	//function is slightly modified version of tanh
	//-3 to 3, and the input range is ~-10 to ~10
	
	
	//this constructor creates a fully blank network of the given size. 
	public TakNetwork(int height, int width, int depth, int layers){
		int[] inputDimensions = {depth,width,height,1};
		int[] middleDimensions = {width,height,1,1};//depth may need to be adjusted. 
		int[] outputDimensions = {1,width,depth,width+7};
		 //make the move output dimenstions the last 2d array
		this.width = width;
		this.height = height;
		this.depth = depth;
		
				
		network.add(new NetworkLayer(inputDimensions, middleDimensions, function));
		
		for(int i = 0; i < layers; i++){
			network.add(new NetworkLayer(middleDimensions, middleDimensions, function));
			
		}
		
		network.add(new NetworkLayer(middleDimensions, outputDimensions , function));
		
		
		
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
	public TakNetwork returnAnotherMutatedNetwork (Random rand, double changePrecentage){
		
		TakNetwork newNetwork = new TakNetwork(getHeight(), getWidth(), getDepth(), getLayers());

		for(int i = 0 ; i < layers; i++){
			newNetwork.setLayer(i, this.getLayer(i).changePercentageSingleLayerMutate(rand, changePrecentage));
			
			
		}
		
		return newNetwork;
	}
	
	
	public void setLayer(int i, NetworkLayer changePercentageSingleLayerMutate) {
		// TODO set the layer at i with the given layer
		network.set(i, changePercentageSingleLayerMutate);
	}


	public List<Move> calculate(int[][][] board){
				
		double[][][][] output;
		
		//inital calculation. 
		output = network.get(0).calculate(convertArray(board));
		for(int i = 1; i < network.size(); i++){
			output = network.get(i).calculate(output); //do all other layers but the last 2
		}
		
		
		return sortedMoves(output);
				
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
		
		return output;
	}

	//takes the output from the network and converts it into a sorted series of moves
	//all moves are added to this list, including those that would make the bot immediately lose or win. 
	private List<Move> sortedMoves(double[][][][] placements) {		
		List<Move> moves = new ArrayList<Move>(width * height * 4);
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < depth; j++){
				moves.add(new Placement(i,j,1,placements[0][i][j][0]));
			}
		}
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < depth; j++){
				moves.add(new Placement(i,j,2,placements[0][i][j][1]));
			}
		}
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < depth; j++){
				moves.add(new Placement(i,j,3,placements[0][i][j][2]));
			}
		}
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < depth; j++){
				moves.add(createPlacement(i,j, Arrays.copyOfRange(placements[0][i][j], 4, placements[0][i][j].length), placements[0][i][j][3]));
				// todo, figure out how the move output is created. set it up so that I can pass an array 3 times. 
			}
		}
		
		Collections.sort(moves, 
						new Comparator<Move>() {
        					public int compare(Move m1, Move m2) {
        						return (m1.getWeight() < m2.getWeight() ? 1 : (m1.getWeight() == m2.getWeight() ? 0 : -1));
        					}
    					}
						);
		
		return moves;
	}


	//[verticle][direction][pickedUp][][][][][]
	private Move createPlacement(int XInput, int YInput, double[] moveOutput, double weight) {
		//x location input
		//y location input
		//what move it is
		//the output form the move function
		//the weight of the 
		Move movement = null;
		Integer[] left = new Integer[width];		
		double range = Math.abs(function.operation(-1000000) - function.operation(1000000)); //gets the range of the function outputs. 
		//System.out.println(range);
		
		//loop
		/*
		 * get the remaning total
		 * get the output number in the space
		 * get the percentage
		 * multiply by remaning peices
		 * round up
		 * */ 
		double d =  Math.abs(moveOutput[2]*width/(range/2));
		int pickedUp = (int) d;
		
		double remaningTotal = 0;
		double percentage;
		for(int i = 3; i < moveOutput.length; i++){
			remaningTotal = 0;
			for(int j = i; j < moveOutput.length; j++){
				remaningTotal += Math.abs((moveOutput[j])); 
			}
			
			percentage = Math.abs(moveOutput[i])/remaningTotal;
			left[i-3] = (int) (Math.floor(percentage*pickedUp)+1);
			
			pickedUp -= left[i-3];
			if(pickedUp <= 0 ){
				break;
			}
		} 
				
		movement = DeStack.DeStack(XInput, YInput, left, (int) d, (moveOutput[0] > 0?true:false), (moveOutput[1] > 0?true:false), weight );
		
		return movement;
	}
	
	public NetworkLayer getLayer(int i){
		return network.get(i);
	}
	
	public int getTotalNumberOfLayers(){
		return network.size();
	}
	
}
