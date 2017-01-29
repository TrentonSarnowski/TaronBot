package com.company.TaronBot.NEAT;

import java.util.ArrayList;

public class OutputNode extends Node{
	
	public OutputNode(int nodeUUNIN, int nodeOrderNumber){
		super(nodeUUNIN, nodeOrderNumber);
	}
	
	@Override
	public double getValue(){
		return 0;
	}
	
	@Override
	public ArrayList<Node> getReferencedByNodes(){
		return null;
	}
	
	@Override
	public int getReferencedByNodesSize(){
		return 0;
	}
	
	
}
