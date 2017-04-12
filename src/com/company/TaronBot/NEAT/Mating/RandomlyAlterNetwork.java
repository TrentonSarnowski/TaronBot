package com.company.TaronBot.NEAT.Mating;

import java.util.ArrayList;

import com.company.TaronBot.NEAT.Network.InputNode;
import com.company.TaronBot.NEAT.Network.Network;
import com.company.TaronBot.NEAT.Network.Node;

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
		ArrayList<Node> nodes = new ArrayList<Node>();
		
		network.addNodesToListSorted(nodes,0);//TODO get a better OperationID Number
		
		
		for(Node node: nodes){
			//if it is a node that can have inputs, then loop through
			if(node.getClass()!=InputNode.class){
				for(Node input: node.getInputNodes()){
					if(helper.getChanceToChangeCurrentInputLinkage() > helper.getRandomGenerator().nextDouble()){
						//change the linkage to another node
					}
					
					if(helper.getChanceToRemoveLinkagePerNode() > helper.getRandomGenerator().nextDouble()){
						//remove this linkage
					}
				}
				
				if(helper.getChanceToAddLinkagePerNode() > helper.getRandomGenerator().nextDouble()){
					//add a linkage
				}
			}
		}
		
		
		//legend
		// *** fully implemented
		// ^^^ coding started
		// ### ready for implementation
		// @@@ not started
		
		//todolist							^^^
			//Lnkage modification			###
				//change current linkage	###
				//add linkage				###
				//remove linkage			###
		
			//mutator modification			@@@
				//chance to change value	@@@
					//maximum increase		@@@
					//maximun decrease		@@@
				//chance to randomize value	@@@
	
			//replace linkage with node		@@@
		
			//add new node to map			@@@
				//maximum num of new nodes	@@@
				//minimum num of new nodes	@@@
		
		return network;

	}
}
