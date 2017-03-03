package com.company.TaronBot.NEAT.Mating;

import com.company.TaronBot.NEAT.Network.Network;

public class RandomlyAlterNetwork {
	
	public static Network AlterNewNetwork(Network network, AlterNetworkHelper helper){
		try {
			return ReturnAlteredNetwork(DuplicateNetwork.Duplicate(network), helper);
		} catch (OperationFailedException e) {
			e.printStackTrace();
			//TODO deal with fallout of failure to duplicate. 
		}
		return null;
		
	}
	
	public static Network ReturnAlteredNetwork(Network network, AlterNetworkHelper helper){
		return network;
		//TODO write mutation system. 
		
	}
}
