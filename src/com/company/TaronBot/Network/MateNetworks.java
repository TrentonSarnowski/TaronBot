package com.company.TaronBot.Network;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//import org.apache.commons.math3.distribution.NormalDistribution;
//import org.apache.commons.math3.random.ISAACRandom;
//import org.apache.commons.math3.random.RandomGenerator;
//import org.apache.commons.math3.stat.descriptive.moment.Mean;
//import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

public class MateNetworks {

	/**
	 * joins the networks together based on a normal distribution random selection for each of the mutator weights.
	 * 
	 * @param numNetworks
	 *            int number of new networks to generate.
	 * @param networks
	 *            List<TakNetwokrs> a list of tak networks that wish to be mutated together
	 * @param rand
	 *            Random random number generator used for the mating
	 * @return TakNetwork a single network based on the the group mating process.
	 */
	public static ArrayList<TakNetwork> GroupMateNetworks(List<TakNetwork> networks, Random rand, int numNetworks, int generation) {
		/*
		 * RandomGenerator random = null;
		 * 
		 * TakNetwork net0 = networks.get(0); ArrayList<Double> workingArray = new ArrayList<>(networks.size()); double fromCurve = 0; double[] statsArray = null;
		 * 
		 * ArrayList<TakNetwork> outputNetworks = new ArrayList<TakNetwork>(numNetworks); for(int i = 0; i < numNetworks; i++){
		 * 
		 * outputNetworks.add( new TakNetwork(net0.getHeight(), net0.getWidth(), net0.getDepth(), net0.getLayers(),generation , i)); } Mean mean = new Mean(); StandardDeviation std = new StandardDeviation(); //generate a blank network for the mutation.
		 * 
		 * 
		 * for(int a = 0; a < net0.getTotalNumberOfLayers(); a++){
		 * 
		 * //loop through every dimension of the mutator for this specific dimension ArrayList<NetworkLayer> layers = new ArrayList<NetworkLayer>(numNetworks); for(int i = 0; i < numNetworks; i++){ layers.add(new NetworkLayer(net0.getLayer(a).getPreviousLayerDimensions(), net0.getLayer(a).getOutputLayerDimensions(), net0.getLayer(a).getFunction())); } //for each output of the network layer for(int q = 0; q < layers.get(0).getOutputLayerSize(); q++){ for(int w = 0; w < layers.get(0).getInputLayerSize(); w++){
		 * 
		 * //a = layer in question //qwer = output of layer (arrayList) //ijkl = input from previous layer (mutator array) workingArray.clear(); for(TakNetwork currentNet: networks){ //add the values in the other networks to the list of numbers in the array //TODO FIGURE OUT WHY THE PROGRAM IS CRASHING //System.out.println(a+":"+q+":"+w+":"+e+":"+r+":"+i+":"+j+":"+k+":"+l); workingArray.add(currentNet.getLayer(a).getMutatorArray(q)[w]); }
		 * 
		 * 
		 * statsArray = new double[workingArray.size()]; for(int n = 0; n < workingArray.size() ; n ++ ){ statsArray[n] = workingArray.get(n); }
		 * 
		 * random = new ISAACRandom(); random.setSeed(rand.nextInt()); NormalDistribution n; if (std.evaluate(statsArray) < .0001) { //todo add Insertion n = new NormalDistribution(random, mean.evaluate(statsArray), std.evaluate(statsArray));
		 * 
		 * } else { n = new NormalDistribution(random, mean.evaluate(statsArray), std.evaluate(statsArray)); } for(int layer = 0; layer < numNetworks; layer++){
		 * 
		 * layers.get(layer).getMutatorArray(q)[w] = n.sample(); }
		 * 
		 * } }
		 * 
		 * 
		 * for(int i = 0; i < numNetworks; i++){ outputNetworks.get(i).setLayer(a, layers.get(i)); }
		 * 
		 * //newNetwork.setLayer(a, layer); }
		 * 
		 * 
		 * return outputNetworks;
		 */
		return null;

	}

	/**
	 * 
	 * @param net
	 *            TakNetwork base network to return from
	 * @param rand
	 *            Random Random number generator
	 * @param changePercentage
	 *            Double percentage to change each single random mutator value
	 * @return TakNetwork the network that is being returned.
	 */
	public static TakNetwork SingleNetworkMutate(TakNetwork net, Random rand, double changePercentage, int species) {
		return net.returnAnotherMutatedNetwork(rand, changePercentage, species);
	}
}
