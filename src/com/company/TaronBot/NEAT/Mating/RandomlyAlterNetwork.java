package com.company.TaronBot.NEAT.Mating;

import com.company.TaronBot.NEAT.Network.Network;

public class RandomlyAlterNetwork {

	public static Network AlterAsNewNetwork(Network network, AlterNetworkHelper helper) {
		try {
			return AlterNetwork(DuplicateNetwork.Duplicate(network), helper);
		} catch (OperationFailedException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static Network AlterNetwork(Network network, AlterNetworkHelper helper) {

		return network;

	}
}
