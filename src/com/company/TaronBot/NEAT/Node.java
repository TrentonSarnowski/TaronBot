package com.company.TaronBot.NEAT;

import java.util.ArrayList;

public class Node {
	final int uniqueUniversalNodeIdentificationNumber; //for node comparison in mating
	int nodeOrderNumber; // for use in calculation networks. Prevent backtracking
	ArrayList<Node> inputNodes;  //to get previous node values
	ArrayList<Node> outputNodes; //for tracking purposes only
	ArrayList<Double> inputNodeWeights;
	double value; //updated by caluclate
	
	
	
	public int getUniqueUniversalNodeIdentificationNumber() {
		return uniqueUniversalNodeIdentificationNumber;
	}
	
	//sets the node order number. To be used if necessary in network restructuring
	public void setNodeOrderNumber(int nodeOrderNumber){
		this.nodeOrderNumber = nodeOrderNumber;
	}
	
	//returns the order number of the node. 
	public int getNodeOrderNumber() {
		return nodeOrderNumber;
	}

	
	
	//creates a node with an id number and a UUID number
	public Node(int nodeUUNIN, int nodeOrderNumber){
		uniqueUniversalNodeIdentificationNumber = nodeUUNIN;
		this.nodeOrderNumber = nodeOrderNumber;
		inputNodes = new ArrayList<Node>();
		outputNodes = new ArrayList<Node>();
		inputNodeWeights = new ArrayList<>();
		value = 0;
	}
	
	//adds a node to the input nodes of this node. 
	//this node will set itself as an output node of Node n 
	//sets weight as weight
	public void addNode(Node node, double weight){
		inputNodes.add(node);
		inputNodeWeights.add(weight);
		node.addOutputNode(this);
	}
	
	//adds a node to the input nodes of this node. 
	//this node will set itself as an output node of Node n 
	//default weight 0
	public void addNode(Node node){
		addNode(node, 0.0);
		//adds the node with a weight of 0;
	}
	
	//will add a node to the list of nodes referencing this node.
	//to be used in conjunction with mating and pruning algorithms. 
	private void addOutputNode(Node node) {
		outputNodes.add(node);
	}
	
	//removes a node from the list of nodes to use. 
	public void removeInputNode(Node node){
		int nodeNumber = inputNodes.indexOf(node);
		inputNodes.remove(nodeNumber);
		inputNodeWeights.remove(nodeNumber);
		node.removeOutputNode(this);
	}
	
	//remove a node from the list of nodes that it is used by
	private void removeOutputNode(Node node) {
		outputNodes.remove(node);
	}	
	
	//goes through every node in the list and adds it multiplied by it's respective weight to the sum
	//this sum is then run through the tansig function.
	public void calculateNode(){
		double sum = 0;
		
		for(int i = 0; i< inputNodes.size(); i++){
			sum+= inputNodes.get(i).getValue()*inputNodeWeights.get(i);
		}
		value = Math.tanh(sum);
	}
	
	
	
	public double getValue(){
		return value;
	}


	public void changeNodeWeight(Node node, double newWeight){
		inputNodeWeights.set(inputNodes.indexOf(node), newWeight);
	}
	
	public ArrayList<Node> getReferencingNodes(){
		return outputNodes;
	}
	
	public ArrayList<Node> getReferencedByNodes(){
		return inputNodes;
	}
	
	public int getReferencingNodesSize(){
		return outputNodes.size();
	}
	
	public int getReferencedByNodesSize(){
		return inputNodes.size();
	}
	
}
