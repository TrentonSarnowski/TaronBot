package com.company.TaronBot.Network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.company.TaronBot.Game.Board;
import com.company.TaronBot.Game.Move;
import com.company.TaronBot.Game.Moves.DeStack;
import com.company.TaronBot.Game.Moves.Placement;
import sun.security.krb5.internal.crypto.Des;

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
	private int generation=0;
	private int species=0;

	
	
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
	public int addWins(){
		return ++wins;
	}
	public int addLosses(){
		return ++losses;
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
	 * @param species 
	 * @param generation 
	 */
	public TakNetwork(int height, int width, int depth, int layers, int generation, int species){
		int[] inputDimensions = {depth,width,height,1};
		int[] middleDimensions = {width,height,1,1};//depth may need to be adjusted.
		int[] outputDimensions = {1,width,depth,width+7};
		 //make the move output dimenstions the last 2d array
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.layers = layers;
		this.generation = generation;
		this.species = species;
				
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
	public TakNetwork returnAnotherMutatedNetwork (Random rand, double changePrecentage, int species){
		
		TakNetwork newNetwork = new TakNetwork(getHeight(), getWidth(), getDepth(), getLayers(), getGeneration()+1, species);

		for(int i = 0 ; i < network.size(); i++){
			newNetwork.setLayer(i, this.getLayer(i).changePercentageSingleLayerMutate(rand, changePrecentage));
			
		}
		
		return newNetwork;
	}
	
	
	public int getSpecies() {
		// TODO Auto-generated method stub
		return species;
	}

	public void setSpecies(int species){
		this.species = species;
	}

	public int getGeneration() {
		// TODO Auto-generated method stub
		return generation;
	}

	public void setGeneration(int generation){
		this.generation = generation;
	}

	public int getUniqueID(int generationSize){
		return getGeneration()*generationSize+getSpecies();
	}

	/**
	 * sets the specific layer of the network
	 * @param i
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
	public List<Move> calculate(int[][][] board, Board b){
				
		double[] output;
		double[] output2;
		double[] output3;
		double[] output4;

		//inital calculation. 
		output = network.get(0).calculate(convertArray(board, true, true));
		output2 = network.get(0).calculate(convertArray(board, true, false));
		output3 = network.get(0).calculate(convertArray(board, false, false));
		output4 = network.get(0).calculate(convertArray(board, false, true));

		for(int i = 1; i < network.size(); i++){
			output = network.get(i).calculate(output); //do all other layers but the last 2
			output2 = network.get(i).calculate(output2); //do all other layers but the last 2
			output3 = network.get(i).calculate(output3); //do all other layers but the last 2
			output4 = network.get(i).calculate(output4); //do all other layers but the last 2

		}
		List<Move> midway=new ArrayList<>();
		midway.addAll(sortedMoves(ConvertToOutputMoveArray(output), b, true, true));
		midway.addAll(sortedMoves(ConvertToOutputMoveArray(output2), b, true, false));
		midway.addAll(sortedMoves(ConvertToOutputMoveArray(output3), b , false, false));
		midway.addAll(sortedMoves(ConvertToOutputMoveArray(output4), b, false, true));
		Collections.sort(midway, new Comparator<Move>() {
			@Override
			public int compare(Move o1, Move o2) {
				if(o1.getWeight()==o2.getWeight()){
					return 0;
				}
				if(o1.getWeight()>o2.getWeight()){
					return 1;
				}
				return -1;
			}
		});

		
		return midway;
				
	}

	private double[][][] ConvertToOutputMoveArray(double[] input) {
		// TODO Auto-generated method stub
		double[][][] output = new double[width][depth][input.length/width/depth];
		int tracker = 0;
		
		for(int i = 0; i < output.length; i++){
			for(int j = 0; j < output[0].length; j++){
				for(int k= 0; k < output[0][0].length; k++){
					output[i][j][k] = input[tracker];
					tracker ++;
				}
			}
		}
		
		
		return output;
	}

	/**
	 * converts the array input into the calculation system into a double array of the correct size
	 * converts the 3d board into a 4d calc matrix. 
	 * @param board int[][][]
	 * @return double[][][]
	 */
	private double[] convertArray(int[][][] board, boolean x, boolean y) {
		
		double[] output = new double[board.length *board[0].length*board[0][0].length];
		//create an array of the correct size to house the input values.

		int tracker = 0;
		if(x && y){
			for(int i = 0; i < board.length; i++){
				for(int j = 0; j < board[0].length; j++){
					for(int k = 0; k < board[0][0].length; k++){
						output[tracker] = (double) board[i][j][k];
						tracker++;
					}
				}
			}
		}else if(x){
			for(int i = board.length-1; i >=0; i--){
				for(int j = 0; j < board[0].length; j++){
					for(int k = 0; k < board[0][0].length; k++){
						output[tracker] = (double) board[i][j][k];
						tracker++;
					}
				}
			}
		}else if(y){
			for(int i = board.length-1; i >=0; i--){
				for(int j = board[0].length-1; j >= 0; j--){
					for(int k = 0; k < board[0][0].length; k++){
						output[tracker] = (double) board[i][j][k];
						tracker++;
					}
				}
			}
		}else{
			for(int i = 0; i < board.length; i++){
				for(int j = board[0].length-1; j >= 0; j--){
					for(int k = 0; k < board[0][0].length; k++){
						output[tracker] = (double) board[i][j][k];
						tracker++;
					}
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
	private ArrayList<Move> sortedMoves(double[][][] placements, Board board, boolean x, boolean y) {
		ArrayList<Move> moves = new ArrayList<Move>(width * height * 4);
		int xR=0;
		if(!x){
			xR=1;
		}
		int yR=0;
		if(!y){
			yR=1;
		}
		for(int i = width*xR; (xR*-2+1)*i < (xR*-2+1)*width; i+=(xR*-2+1)){
			for(int j = width*yR; (yR*-2+1)*j < (yR*-2+1)*width; j+=(yR*-2+1)){
				moves.add(new Placement(i,j,1,placements[i][j][0]));
			}
		}

		for(int i = 0; i < width; i++){
			for(int j = 0; j < depth; j++){
				moves.add(new Placement(i,j,2,placements[i][j][1]));
			}
		}

		for(int i = 0; i < width; i++){
			for(int j = 0; j < depth; j++){
				moves.add(new Placement(i,j,3,placements[i][j][2]));
			}
		}

		for(int i = 0; i < width; i++){
			for(int j = 0; j < depth; j++){
				Move m;
				try {
					m= createPlacement(i, j, true, true, placements[i][j][7], placements[i][j][6], board.getMap()[i][j].size());
				}catch (Exception e){
					System.err.println(i+", "+j);
					m=null;
				}
					if(m!=null) {
					moves.add(m);
				}
			}
		}
		for(int i = 0; i < width; i++){
			for(int j = 0; j < depth; j++){

				Move m=createPlacement(i,j, true, false, placements[i][j][1], placements[i][j][0], board.getMap()[i][j].size());
				if(m!=null) {
					moves.add(m);
				}
				// todo, figure out how the move output is created. set it up so that I can pass an array 3 times.
			}
		}
		for(int i = 0; i < width; i++){
			for(int j = 0; j < depth; j++){

				Move m=createPlacement(i,j, false, true, placements[i][j][3], placements[i][j][2], board.getMap()[i][j].size());
				if(m!=null) {
					moves.add(m);
				}
				// todo, figure out how the move output is created. set it up so that I can pass an array 3 times.
			}
		}
		for(int i = 0; i < width; i++){
			for(int j = 0; j < depth; j++){

				Move m=createPlacement(i,j, false, false, placements[i][j][5], placements[i][j][4], board.getMap()[i][j].size());
				if(m!=null) {
					moves.add(m);
				}
				// todo, figure out how the move output is created. set it up so that I can pass an array 3 times.
			}
		}
		Collections.shuffle(moves);
		Collections.sort(moves, 
						new Comparator<Move>() {
        					public int compare(Move m1, Move m2) {
								if(m1.getWeight() <m2.getWeight()){
									return 1;
								}else if(m1.getWeight()==m2.getWeight()){
									return 0;
								}else{
									return (m1.getWeight() < m2.getWeight() ? 1 : (m1.getWeight() == m2.getWeight() ? 0 : -1));

								}
        					}
    					}
						);
		
		return moves;
	}


	/**
	 * creates a placement for a specific location
	 * @param XInput int x location
	 * @param YInput int y location

	 * @param weight double weight of the specific move. 
	 * @return Move the movement created
	 */
	private Move createPlacement(int XInput, int YInput, boolean positive, boolean vertical, double move, double weight, int height) {
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
		 * multiply by remaning Fpeices
		 * round up
		 * */

		try {
			int number = 0; //needs to equal a number form 0 to 256.
			//currently using a random number from the moveoutput (6) do to uncertanty on how the move was created.
			boolean map[] = generateMoves.toArray((int)((move+3)*256/6), height);
			movement = new DeStack(map,vertical, positive, weight,XInput, YInput);

			//movement = DeStack.DeStack(XInput, YInput, left, (int) d, (moveOutput[0] > 0), (moveOutput[1] > 0), weight);
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
