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
						//change the linkage to another node 1 TODO
					}
					
					if(helper.getChanceToRemoveLinkagePerNode() > helper.getRandomGenerator().nextDouble()){
						//remove this linkage 2 TODO
					}
					
					if(helper.getChanceToChangeMutatorValue() > helper.getRandomGenerator().nextDouble()){
						//change mutator value 4 TODO
						
						if(helper.getChanceToReRandomizeMutatorValue() > helper.getRandomGenerator().nextDouble()){
							//random change mutator value 5 TODO
							
						}else{
							//change to mutator value increase or decrease. percentage??? TODO
							
						}
					}
					
					if(helper.getChanceToReplaceLinkageWithNewNode()> helper.getRandomGenerator().nextDouble()){
						//replace linkage with new node. 6 TODO
					}
				}
				
				if(helper.getChanceToAddLinkagePerNode() > helper.getRandomGenerator().nextDouble()){
					//add a linkage 3 TODO
				}
			}
		}
		
		
		int numNodesToAdd = 1;
		int diff = helper.getMinimumNumberOfNewNodes() - helper.getMaximumNumberOfNewNodes();
		numNodesToAdd += helper.getMinimumNumberOfNewNodes();
		//add this number of nodes if the difference is more than 0;
		if(diff != 0){
			numNodesToAdd += helper.getRandomGenerator().nextInt(diff);
		}
		
		for(int i = 0; i < numNodesToAdd; i++){
			Node n1 = nodes.get(helper.getRandomGenerator().nextInt(nodes.size()));
			Node n2 = nodes.get(helper.getRandomGenerator().nextInt(nodes.size()));
			
			//if same depth, break appart. 
			if(n1.getNodeDepth() == n2.getNodeDepth()){
				i--;
				break;
			}
			//asumption,
			//n1 is higher in the list, and therefore has a smaller number. 
			//if it does not, switch them.
			if(n1.getNodeDepth() > n2.getNodeDepth()){
				Node temp = n1;
				n2 = n1;
				n2 = temp;
			}
			
			//TODO create a new node here. 
			
			
		}
		
		
		//legend
		// *** fully implemented
		// ^^^ coding started
		// ### ready for implementation
		// @@@ not started
		
		//todolist							^^^
			//Lnkage modification			###
				//change current linkage	###1
				//add linkage				###2
				//remove linkage			###3
		
			//mutator modification			###
				//chance to change value	###4
					//maximum increase		###
					//maximun decrease		###
					//randomize value		###
	
			//replace linkage with node		###6
		
			//add new node to map			###7
				//maximum num of new nodes	###
				//minimum num of new nodes	###
		
		return network;

	}
}
