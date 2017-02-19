package com.company.TaronBot.NEAT;

import tech.deef.Tools.StaticGlobals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Network {

	int intputNodes;
	int outputNodes;
	
	protected ArrayList<Node> NetworkNodes;
	static Random rand = new Random();

	public Network(int inputNodes, int outputNodes) {
		this(inputNodes, outputNodes, 0);
	}

	public Network(int inputNodes, int outputNodes, int startingNodes) {
		NetworkNodes = new ArrayList<Node>();

		for (int i = 0; i < inputNodes; i++) {
			// bad coding here. Have to do something about rand
			NetworkNodes.add(new InputNode(rand.nextInt(), i));
		}

		if (startingNodes != 0) {
			for (int i = 0; i < startingNodes; i++) {
				// bad coding here. Have to do something about rand
				NetworkNodes.add(new Node(rand.nextInt(), inputNodes + i));
			}
		}

		for (int i = 0; i < outputNodes; i++) {
			// bad coding here. Have to do something about rand
			NetworkNodes.add(new OutputNode(rand.nextInt(), inputNodes + startingNodes + i));
		}
	}

	public Network(Network one, Network two){
		for (int i = 0; i <one.NetworkNodes.size()||i<two.NetworkNodes.size() ; i++) {
			Node nodeOne=one.NetworkNodes.get(i);
			Node nodeTwo=two.NetworkNodes.get(i);
			if(!nodeOne.equals(nodeTwo)){
				//perform mutation involving both
				int r=StaticGlobals.RANDOM.nextInt(100);
				if(r<90){
					//find path
				}else{
					//mutate affected connection randomly
					//may need to break this up further
					//add include both at half weight

				}
				//get simlar paths if they exist(provide depth variable)

			}else{
				//check for random mutation
			}
		}
	}
	
	public double[] RunNetwork(double[] inputValues){
		Collections.sort(NetworkNodes, (Node n1, Node n2) -> {
			return n1.getNodeOrderNumber()-n2.getNodeOrderNumber();
		});
		
		double[] output = new double[outputNodes]; 
		//this may need to be a plus one. I dont think so tho. 
		
		//set input
		InputNode middleman = null;
		for(int i = 0; i < intputNodes; i++){
			middleman = (InputNode) NetworkNodes.get(i);
			middleman.setOutputValue(inputValues[i]);
		}
		
		//calculate all nodes
		for(Node node: NetworkNodes){
			node.calculateNode();
		}
		
		//set output
		for(int i = 0; i < outputNodes; i++){
			
			output[i] = NetworkNodes.get(NetworkNodes.size()-i-1).getValue();
		}
		
		return output;
	}
	
	//TODO anything to do with setting a nodes weighting values. 
	//geting the number of nodes in the network
	//connesting the nodes. 
	
	
}
