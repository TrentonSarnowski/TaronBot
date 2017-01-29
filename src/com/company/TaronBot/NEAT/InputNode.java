package com.company.TaronBot.NEAT;

import java.util.ArrayList;

public class InputNode extends Node{
	
	public void setOutputValue(double value){
		this.value = value;
	}
	
	//calculate does nothing.
	@Override
	public void calculateNode(){
		return;
	}
	
	//prevent a node from being added to the list of nodes. 
	@Override
	public void addNode(Node node){
		return;
	}
	
	
	public InputNode(int nodeUUNIN, int nodeOrderNumber){
		super(nodeUUNIN, nodeOrderNumber);
	}
	
	//create an input node with a constant output form calculate. 
	public InputNode(int nodeUUNIN, int nodeOrderNumber, double value){
		super(nodeUUNIN, nodeOrderNumber);
		this.value = value;
	}
	
	
	//as an input node outputs a constant, there are no referenced nodes
	@Override
	public ArrayList<Node> getReferencedByNodes(){
		return null;
	}
	
	//as an input node outputs a constant, there are no referenced nodes
	@Override
	public int getReferencingNodesSize(){
		return 0;
	}
	
	
	
}
