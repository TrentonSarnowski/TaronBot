package com.company.TaronBot.Network;

import java.util.List;

public class MateNetworks {

	// takes in a list of networks and produces a single network that has been
	// mutated and generated from the given network

	public TakNetwork MateNetworks(List<TakNetwork> networks){
		TakNetwork net0 = networks.get(0);
		
		//generate a blank network for the mutation. 
		TakNetwork newNetwork = new TakNetwork(net0.getHeight(), net0.getWidth(), net0.getDepth(), net0.getLayers());
		/*
		for(int a = 0; a < net0.getTotalNumberOfLayers(); a++){
			
			//loop through tevery dimension of the mutator for this specific dimension
			
			double[][][][] array = newNetwork.getLayer(a).getMutatorArray(q,w,e,r);
			for (int i = 0; i < array.length; i++) {
				for (int j = 0; j < array[0].length; j++) {
					for (int k = 0; k < array[0][0].length; k++) {
						for (int l = 0; l < array[0][0][0].length; l++) {
							for(int v = 0; v < networks.size(); v++){
								numbers[v] = networks.get(v).getLayer(a).getMutatorArray(q, w, e, r)[i][j][k][l]
										
								
							}
						}
					}
				}
			}
			
		}
		*/
		
		return null;
	}
}
