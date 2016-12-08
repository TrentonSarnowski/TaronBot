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

	

	
	/**
	 * joins the networks together based on a normal distribution random selection for each of the mutator weights. 
	 * @param numNetworks int number of new networks to generate. 
	 * @param networks List<TakNetwokrs> a list of tak networks that wish to be mutated together
	 * @param rand Random random number generator used for the mating
	 * @return TakNetwork a single network based on the the group mating process. 
	 */
	public static ArrayList<TakNetwork> GroupMateNetworks(List<TakNetwork> networks, Random rand, int numNetworks){
		
		RandomGenerator random = null;
		
		TakNetwork net0 = networks.get(0);
		ArrayList<Double> workingArray = new ArrayList<>(networks.size());
		double fromCurve = 0;
		double[] statsArray = null;
		
		ArrayList<TakNetwork> outputNetworks = new ArrayList<TakNetwork>(numNetworks);
		for(int i = 0; i < numNetworks; i++){
			
			outputNetworks.add( new TakNetwork(net0.getHeight(), net0.getWidth(), net0.getDepth(), net0.getLayers()));
		}
		Mean mean = new Mean();
		StandardDeviation std = new StandardDeviation();
		//generate a blank network for the mutation. 
		//TODO fix this section of code to mutate properly
		
		
		for(int a = 0; a < net0.getTotalNumberOfLayers(); a++){
			
			//loop through every dimension of the mutator for this specific dimension
			ArrayList<NetworkLayer> layers = new ArrayList<NetworkLayer>(numNetworks);
			for(int i = 0; i < numNetworks; i++){
				layers.set(i, new NetworkLayer(net0.getLayer(a).getPreviousLayerDimensions(), net0.getLayer(a).getOutputLayerDimensions(), net0.getLayer(a).getFunction()));
			}
			//for each output of the network layer
			for(int q = 0; q < layers.get(0).getOutputLayerDimensions()[0]; q++){
				for(int w = 0; w < layers.get(0).getOutputLayerDimensions()[1]; w++){
					for(int e = 0; e < layers.get(0).getOutputLayerDimensions()[2]; e++){
						for(int r = 0; r < layers.get(0).getOutputLayerDimensions()[3]; r++){
							
							//go through each input for that output
							for(int i = 0; i < layers.get(0).getPreviousLayerDimensions()[0]; i++){
								for(int j = 0; j < layers.get(0).getPreviousLayerDimensions()[1]; j++){
									for(int k = 0; k < layers.get(0).getPreviousLayerDimensions()[2]; k++){
										for(int l = 0; l < layers.get(0).getPreviousLayerDimensions()[3]; l++){
											//a = layer in question
											//qwer = output of layer (arrayList)
											//ijkl = input from previous layer (mutator array)
											workingArray.clear();
											for(TakNetwork currentNet: networks){
												//add the values in the other networks to the list of numbers in the array
												//TODO FIGURE OUT WHY THE PROGRAM IS CRASHING					
												//System.out.println(a+":"+q+":"+w+":"+e+":"+r+":"+i+":"+j+":"+k+":"+l);
												workingArray.add(currentNet.getLayer(a).getMutatorArray(q, w, e, r)[i][j][k][l]);
											}
											
											
											statsArray = new double[workingArray.size()];
											for(int n = 0; n < workingArray.size() ; n ++ ){
												statsArray[n] = workingArray.get(n);
											}
											
											random = new ISAACRandom();
											random.setSeed(rand.nextInt());
											NormalDistribution n = new NormalDistribution(random, mean.evaluate(statsArray), std.evaluate(statsArray));
											
											for(int layer = 0; layer < numNetworks; layer++){
												layers.get(layer).getMutatorArray(q,w,e,r)[i][j][k][l] = n.sample();
											}
										}
									}
								}
							}
							
							
						}
					}
				}
			}
			
			
			for(int i = 0; i < numNetworks; i++){
				outputNetworks.get(i).setLayer(a, layers.get(i));
			}
			
			//newNetwork.setLayer(a, layer);
		}
		
		
		return outputNetworks;
	}
	
	/**
	 * 
	 * @param net TakNetwork base network to return from
	 * @param rand Random Random number generator 
	 * @param changePercentage Double percentage to change each single random mutator value
	 * @return TakNetwork the network that is being returned. 
	 */
	public TakNetwork SingleNetworkMutate(TakNetwork net, Random rand, double changePercentage){
		return net.returnAnotherMutatedNetwork(rand, changePercentage);
	}
}
