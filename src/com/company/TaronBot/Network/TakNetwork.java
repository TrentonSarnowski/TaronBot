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

/**
 * TakNetwork class specifically designed for the creation of a square board with n layers. 
 * @author deef0000dragon1
 *
 */
public class TakNetwork implements Serializable{
	private int width;
	private int height;
	private int depth;
	private int layers;
	private int wins = 0;
	private int losses = 0;
	
	
	public int getWins() {
		return wins;
	}

	public int getLosses() {
		return losses;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

	/**
	 * 
	 * @return int width of the network
	 */
	public int getWidth() {
		return width;
	}

	/** 
	 * 
	 * @return int height of the network
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * 
	 * @return int depth of the network
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * 
	 * @return int number of middle layers in the network
	 */
	public int getLayers() {
		return layers;
	}


	ArrayList<NetworkLayer> network = new ArrayList<NetworkLayer>();
	nonLinearFunction function = ((double d) -> ((6/(1+(Math.pow(Math.E,(-.5*d)))))-2.99));
	
	//function is slightly modified version of tanh
	//-3 to 3, and the input range is ~-10 to ~10
	
	/**
	 * constructs a blank network with the given dimensions and middle layers of 1,1
	 * 
	 * @param height int
	 * @param width int
	 * @param depth int 
	 * @param layers int
	 */
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
	
	/**
	 *  randomizes the entire network
	 * @param rand Random
	 */
	public void randomize(Random rand){
		for(NetworkLayer layer : network){
			layer.randomize(rand);
			
		}
	}
	
	/**
	 * returns a network that has been mutated from the original. 
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
	
	
	/**
	 * sets the specific layer of the network
	 * @param int i
	 * @param changePercentageSingleLayerMutate NetworkLayer
	 */
	public void setLayer(int i, NetworkLayer changePercentageSingleLayerMutate) {
		// TODO set the layer at i with the given layer
		network.set(i, changePercentageSingleLayerMutate);
	}

	/**
	 * calculates a list of all placements and a single movements for each location on 
	 * the board and orders best to worst. 
	 * Does not correct for impossible moves or impossible placements.
	 * Only calculates output of the network. 
	 * @param board int[][][] input 3d board
	 * @return list<Move> containing all possible moves ordered best to worst. 
	 */
	public List<Move> calculate(int[][][] board){
				
		double[][][][] output;
		
		//inital calculation. 
		output = network.get(0).calculate(convertArray(board));
		for(int i = 1; i < network.size(); i++){
			output = network.get(i).calculate(output); //do all other layers but the last 2
		}
		
		
		return sortedMoves(output);
				
	}

	/**
	 * converts the array input into the calculation system into a double array of the correct size
	 * converts the 3d board into a 4d calc matrix. 
	 * @param board int[][][]
	 * @return double[][][]
	 */
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

	/**
	 * takes the output from the network and converts it into a sorted series of moves
	 * all moves are added to this list, including those that would make the bot immediately lose or win. 
	 * 
	 * retuns the moves after being given an output 4d array from the calculation.
	 * @param placements double[][][][] output from the final layer
	 * @return ArrayList<Move>
	 */
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
				Move m=createPlacement(i,j, Arrays.copyOfRange(placements[0][i][j], 4, placements[0][i][j].length), placements[0][i][j][3]);
				if(m!=null) {
					moves.add(m);
				}
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


	/**
	 * creates a placement for a specific location
	 * @param XInput int x location
	 * @param YInput int y location
	 * @param moveOutput double[] the output array of the network
	 * @param weight double weight of the specific move. 
	 * @return Move the movement created
	 */
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
		try {


			movement = DeStack.DeStack(XInput, YInput, left, (int) d, (moveOutput[0] > 0 ? true : false), (moveOutput[1] > 0 ? true : false), weight);
		}catch (Exception e){
			movement =null;
		}
		return movement;
	}
	
	/**
	 * returns a layer of a specific number in the network. 
	 * @param i int the layer wanted
	 * @return NetworkLayer the network layer at that layer
	 */
	public NetworkLayer getLayer(int i){
		return network.get(i);
	}
	
	/**
	 * returns the total nimber of layers in the network.
	 * @return int total number of layers in the network. 
	 */
	public int getTotalNumberOfLayers(){
		return network.size();
	}
	
}
