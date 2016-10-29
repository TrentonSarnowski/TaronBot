package com.company.TaronBot.network;

import java.util.ArrayList;

public class NetworkLayer {
	int[] previousLayerDimensions, outputLayerDimensions;
	nonLinearFunction function;
	
	double[][][][] inputLayer, outputLayer;
	ArrayList<ArrayList<ArrayList<ArrayList<double[][][][]>>>> mutators;
	
	
	public NetworkLayer(int[] previousLayerDimensions, int[] outputLayerDimensions, nonLinearFunction function){
		
		this.previousLayerDimensions = previousLayerDimensions;
		this.outputLayerDimensions = outputLayerDimensions;
		this.function = function;
		
		//TODO error handeling for less than 4 dimensions
		
		inputLayer = new double[previousLayerDimensions[0]][previousLayerDimensions[1]][previousLayerDimensions[2]][previousLayerDimensions[3]];
		outputLayer = new double[outputLayerDimensions[0]][outputLayerDimensions[1]][outputLayerDimensions[2]][outputLayerDimensions[3]];
		
		mutators = new ArrayList<ArrayList<ArrayList<ArrayList<double[][][][]>>>>(outputLayerDimensions[0]);
		
	}
	
	
	/**
	 * 
	 * @param input double[][][][] the input values from the last layer.
	 * @return
	 */
	public double[][][][] calculate(double[][][][] input){
		//TODO error checking to confirm that the input is the right size.
		double[][][][] output = new double[outputLayerDimensions[0]][outputLayerDimensions[1]][outputLayerDimensions[2]][outputLayerDimensions[3]];
		
		
		for(int i = 0; i < outputLayerDimensions[0]; i++){
			for(int j = 0; j < outputLayerDimensions[1]; j++){
				for(int k = 0; k < outputLayerDimensions[2]; k++){
					for(int l = 0; l < outputLayerDimensions[3]; l++){
						
						output[i][j][k][l] = function.operation(corolate(mutators.get(i).get(j).get(k).get(l), input));
					}
				}
			}
		}
		
		return null;
	}

	
	/**
	 * 
	 * @param weights double [][][][] that holds the weighting numbers to be applied to the inputs
	 * @param input double[][][][] that holds the input values from the previous layer
	 * @return the sum for a specific location (handled externally) based on the weights and the inputs.
	 */
	private double corolate(double[][][][] weights, double[][][][] input) {
		
		double sum = 0;
		
		for(int i = 0; i < previousLayerDimensions[0]; i++){
			for(int j = 0; j < previousLayerDimensions[1]; j++){
				for(int k = 0; k < previousLayerDimensions[2]; k++){
					for(int l = 0; l < previousLayerDimensions[3]; l++){
						
						sum += weights[i][j][k][l]*input[i][j][k][l];
					}
				}
			}
		}
		
		
		return sum;
	}
}
