package com.company.TaronBot.Network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.ISAACRandom;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

public class MateNetworks {

	// takes in a list of networks and produces a single network that has been
	// mutated and generated from the given network

	
	//mutates EVERY value to be along the same lines as the other values.
	public static TakNetwork MateNetworks(List<TakNetwork> networks, Random rand){
		TakNetwork net0 = networks.get(0);
		ArrayList<Double> workingArray = new ArrayList<>(networks.size());
		double fromCurve = 0;
		double[] statsArray = null;
		TakNetwork newNetwork = new TakNetwork(net0.getHeight(), net0.getWidth(), net0.getDepth(), net0.getLayers());
		Mean mean = new Mean();
		StandardDeviation std = new StandardDeviation();
		RandomGenerator random = null;
		//generate a blank network for the mutation. 
		//TODO fix this section of code to mutate properly
		
		
		for(int a = 0; a < net0.getTotalNumberOfLayers(); a++){
			
			//loop through every dimension of the mutator for this specific dimension
			NetworkLayer layer = new NetworkLayer(net0.getLayer(a).getPreviousLayerDimensions(), net0.getLayer(a).getOutputLayerDimensions(), net0.getLayer(a).getFunction());
			
			//for each output of the network layer
			for(int q = 0; q < layer.getOutputLayerDimensions()[0]; q++){
				for(int w = 0; q < layer.getOutputLayerDimensions()[1]; w++){
					for(int e = 0; q < layer.getOutputLayerDimensions()[2]; e++){
						for(int r = 0; q < layer.getOutputLayerDimensions()[3]; r++){
							
							//go through each input for that output
							for(int i = 0; i < layer.getPreviousLayerDimensions()[0]; i++){
								for(int j = 0; j < layer.getPreviousLayerDimensions()[1]; j++){
									for(int k = 0; k < layer.getPreviousLayerDimensions()[2]; k++){
										for(int l = 0; l < layer.getPreviousLayerDimensions()[3]; l++){
											//a = layer in question
											//qwer = output of layer (arrayList)
											//ijkl = input from previous layer (mutator array)
											workingArray.clear();
											for(TakNetwork currentNet: networks){
												//add the values in the other networks to the list of numbers in the array
												//TODO FIGURE OUT WHY THE PROGRAM IS CRASHING
												
												
												System.out.println(a+":"+q+":"+w+":"+e+":"+r+":"+i+":"+j+":"+k+":"+l);
												workingArray.add(currentNet.getLayer(a).getMutatorArray(q, w, e, r)[i][j][k][l]);
											}
											
											
											statsArray = new double[workingArray.size()];
											for(int n = 0; n < workingArray.size() ; n ++ ){
												statsArray[n] = workingArray.get(n);
											}
											
											random = new ISAACRandom();
											random.setSeed(rand.nextInt());
											NormalDistribution n = new NormalDistribution(random, mean.evaluate(statsArray), std.evaluate(statsArray));
											
											fromCurve = n.sample();
											
											
											layer.getMutatorArray(q,w,e,r)[i][j][k][l] = fromCurve;
										}
									}
								}
							}
							
							
						}
					}
				}
			}
			
			
			newNetwork.setLayer(a, layer);
		}
		
		
		return newNetwork;
	}
}
