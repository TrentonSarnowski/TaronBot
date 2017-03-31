package com.company.TaronBot.NEAT.Network;

import tech.deef.Tools.StaticGlobals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * A one directional network of nodes that calculates a known number of output based on a known number of inputs
 */
public class Network {

	int intputNodes;
	int outputNodes;

	protected ArrayList<Node> NetworkNodes;
	static Random rand = new Random();// TODO initilize in constructors

	/**
	 * creates Network with no middle nodes
	 * 
	 * @param inputNodes
	 * @param outputNodes
	 */
	public Network(int inputNodes, int outputNodes) {
		this(inputNodes, outputNodes, 0);
	}

	/**
	 * COnstructor for a network with staring number of middle nodes
	 * 
	 * @param inputNodes
	 * @param outputNodes
	 * @param startingNodes
	 */
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

	/**
	 * Calls the mating algorithm on two networks with default values
	 * 
	 * @param one
	 * @param two
	 * @return
	 */
	public Network Network(Network one, Network two) {
		return null;// TODO
	}

	/**
	 * Takes in an array of doubles representing a state and returns an array of output values
	 * 
	 * @param inputValues
	 * @return
	 */
	public double[] RunNetwork(double[] inputValues) {
		Collections.sort(NetworkNodes, (Node n1, Node n2) -> {
			return n1.getNodeOrderNumber() - n2.getNodeOrderNumber();
		});

		double[] output = new double[outputNodes];
		// this may need to be a plus one. I dont think so tho.

		// set input
		InputNode middleman = null;
		for (int i = 0; i < intputNodes; i++) {
			middleman = (InputNode) NetworkNodes.get(i);
			middleman.setOutputValue(inputValues[i]);
		}

		// calculate all nodes
		for (Node node : NetworkNodes) {
			node.calculateNode();
		}

		// set output
		for (int i = 0; i < outputNodes; i++) {

			output[i] = NetworkNodes.get(NetworkNodes.size() - i - 1).getValue();
		}

		return output;
	}

	// TODO anything to do with setting a nodes weighting values.
	// geting the number of nodes in the network
	// connesting the nodes.

	public void addNodeLinkage(Node giver, Node reciever, double weight) {
		// TODO generate method
	}

	public void removeNodeLinkage(Node giver, Node reciever) {
		// TODO generate method
	}

	public void adjustNodeLinkage(Node giver, Node reciever, double weight) {
		// TODO generate method
	}

	public void addNode(Node node) {
		// TODO generate method
	}

	public void removeNode(Node node) {
		// TODO generate method
	}

	public void cleanupNodeTree() {
		// TODO go through every node and if
		// the number of referencing nodes is zero, remove it.
	}
}
