package com.company.TaronBot.Network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import tech.deef.Tools.Tools;

/**
 * the individual layer of a network.
 * @author deef0000dragon1
 *
 */
public class NetworkLayer implements Serializable{
	int[] previousLayerDimensions, outputLayerDimensions;
	int inputLayerSize, outputLayerSize;
	nonLinearFunction function;
	
	/**
	 * 
	 * @return int[] containing the dimensions for the previous layer of the network
	 */
	public int[] getPreviousLayerDimensions() {
		return previousLayerDimensions;
	}

	
	/**
	 * 
	 * @return int[] containing the dimensions for the output of the network
	 */
	public int[] getOutputLayerDimensions() {
		return outputLayerDimensions;
	}

	/**
	 * 
	 * @return nonLinearFunction that this layer uses as the 
 	 * 					function for each sum calculation. 
	 */
	public nonLinearFunction getFunction() {
		return function;
	}

	double[] inputLayer, outputLayer;
	ArrayList<double[]> mutators;

	/**
	 * 
	 * @param previousLayerDimensions int[4] expected input dimensions
	 * @param outputLayerDimensions int[4] expected output dimensions
	 * @param function non linear function that can be used as the sum function. 
	 */
	public NetworkLayer(int[] previousLayerDimensions, int[] outputLayerDimensions, nonLinearFunction function) {

		this.previousLayerDimensions = previousLayerDimensions;
		this.outputLayerDimensions = outputLayerDimensions;
		this.function = function;

		// TODO error handeling for less than 4 dimensions

		inputLayerSize = 0;
		outputLayerSize = 0;
		
		for(int i : previousLayerDimensions){
			inputLayerSize = inputLayerSize*i;
		}
		for(int i : outputLayerDimensions){
			outputLayerSize = outputLayerSize*i;
		}
		
		inputLayer = new double[inputLayerSize];
		outputLayer = new double[outputLayerSize];

		//all created. 
		
		// the problem here is that the program is not creating the correct
		// series of things to add.

		//create an arraylist of mutators that is the size of the output that contains a double array for each of the mutators. 
		mutators = new ArrayList<double[]>(outputLayerSize);

		//go through and create a double array for every position in the mutator array.
		double[] m1;
		for (int j = 0; j < outputLayerDimensions[0]; j++) {
			m1 = new double[inputLayerSize];
			mutators.add(m1);
		}

	}

	/**
	 * causes the layer to run through its mutators and nonlinear functions to calculate the output
	 * @param input double[][][][] the input values from the last layer.
	 * @return double[][][][] output of the network calculation
	 */
	public double[] calculate(double[] input) {
		// TODO error checking to confirm that the input is the right size.
		double[] output = new double[outputLayerSize];

		for (int i = 0; i < outputLayerSize; i++) {
			output[i] = function.operation(corolate(mutators.get(i),input));
		}

		return output;
	}

	/**
	 * returns the sum of every weight multiplied by it's respective input. 
	 * @param weights
	 *            double [][][][] that holds the weighting numbers to be applied
	 *            to the inputs
	 * @param input
	 *            double[][][][] that holds the input values from the previous
	 *            layer
	 * @return the sum for a specific location (handled externally) based on the
	 *         weights and the inputs.
	 */
	private double corolate(double[] weights, double[] input) {

		double sum = 0;

		for (int i = 0; i < inputLayerSize; i++) {
			//System.err.println(i+" "+j+" "+k+" "+l);
			sum += weights[i] * input[i];
		}
		return sum;
	}

	/**
	 * randomized every mutator weight in the layer.
	 * @param rand Random random number generator used to randomize the network. 
	 */
	public void randomize(Random rand) {
		// TODO Auto-generated method stub

		for (int i = 0; i < outputLayerSize; i++) {
			for (int j = 0; j < inputLayerSize; j++) {
				mutators.get(i)[j] = rand.nextDouble() * 2 - 1;
			}
		}

	}

	/**
	 * returns a randomized network layer based on the current layer where the 
	 * randomization is based on a give percentage 
	 * @param rand Random random number generator for the mutation randomization
	 * @param changePrecentage Double 0.0-1.0 that represents the percentage of weights to change
	 * @return the network layer. 
	 */
	public NetworkLayer changePercentageSingleLayerMutate(Random rand, double changePrecentage) {
		// TODO Auto-generated method stub
		
		NetworkLayer newLayer = new NetworkLayer(previousLayerDimensions, outputLayerDimensions, function);
		
		for (int i = 0; i < outputLayerSize; i++) {
			for (int j = 0; j < inputLayerSize; j++) {
				if (rand.nextDouble() <= changePrecentage) {
					newLayer.getMutatorArray(i)[j] = rand.nextDouble() * 2 - 1;
				}
				else {
					newLayer.getMutatorArray(i)[j] = getMutatorArray(i)[j];
				}
			}
		}

		return newLayer;
	}
	
	/**
	 * given four output dimensional inputs, returns the mutator array
	 * @param i 
	 * @return double[][][][] containing the mutator weights for that output
	 */
	public double[] getMutatorArray(int i){
		
		return mutators.get(i);
	}


	public int getOutputLayerSize() {
		// TODO Auto-generated method stub
		return outputLayerSize;
	}


	public int getInputLayerSize() {
		// TODO Auto-generated method stub
		return inputLayerSize;
	}
}
