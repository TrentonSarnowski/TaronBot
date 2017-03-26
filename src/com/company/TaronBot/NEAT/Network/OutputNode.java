package com.company.TaronBot.NEAT.Network;

import java.util.ArrayList;

/**
 * THe tail and exit nodes of a network
 */
public class OutputNode extends Node{
	
	public OutputNode(int nodeUUNIN, int nodeOrderNumber){
		super(nodeUUNIN, nodeOrderNumber);
	}
	
	@Override
	public double getValue(){
		return super.getValue();
	}

	/**
	 * TODO enforce documentation
	 * @return
	 */
	@Override
	public ArrayList<Node> getReferencedByNodes(){
		return null;
	}
	
	@Override
	public int getReferencedByNodesSize(){
		return 0;
	}
	
	
}
