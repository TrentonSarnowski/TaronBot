package com.company.Testing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import com.company.TaronBot.NEAT.Mating.AlterNetworkHelper;
import com.company.TaronBot.NEAT.Mating.RandomlyAlterNetwork;
import com.company.TaronBot.NEAT.Network.Network;

import tech.deef.Tools.RNGTool;

public class RNGTesting {

	// the purpose of this class is to test and reverse engineer the the value
	// from a
	public static void main(String[] args) {
		RNGToolTest();
		
		
		//RNGTest();
	}



	private static void RNGToolTest() {
		int num = 0;
		num = RNGTool.RNGInput(num);
		int first = num;
		
		for (int i = 0; i < 100000;i++){
			num = RNGTool.RNGInput(num);
			System.out.println(num + " : " + Arrays.toString(getBinaryArray(num)));
			if(num ==first){
				System.out.println("Looped after " + i + " runs.");
				break;
			}
		}
	}


	
	private static void RNGTest() {
		//network lists
		ArrayList<Network> networks = new ArrayList<Network>(100);
		ArrayList<Network> network = new ArrayList<Network>(100);
		Network temp;
		//result list
		ArrayList<Double> results = new ArrayList<Double>(100);
		
		//single network mutator
		AlterNetworkHelper helper = new AlterNetworkHelper();
		
		//create inital networks
		for (int i = 0; i < 100; i++) {
			networks.set(i, generateNetwork());
		}

		//main loop
		for (int i = 0; i < 1000; i++) {

			// run generation for
			for (int j = 0; j < 100; j++) {
				results.set(j, DetermineCorrectPercentage(networks.get(j)));
			}

			
			//output results
			double sum = 0;
			for(int j = 0; j < 100; j++){
				sum += results.get(j);
			}
			
			System.out.println("Round " + i + ": " + (sum/100.0));
			
			
			
			//sor networks based on accuracy
			networks = sortNetworks(results, networks);
			
			//randomly remove based on the result of sorting. prioritize removing worse nets
			for (int j = 0; j < 50; j++){
				//remove the nth 0object where n is calculated by
				//finding a number on a cosine curve, where the lower number 
				networks.remove(((int)Math.cos(Math.random()*Math.PI/2))*networks.size());
			}
			
			
			network.clear();
			//randomly duplicate based on the results of sorting. prioritize duplicating better nets
			for (int j = 0; j < 50; j++){
				//mutating the nth 0object where n is calculated by
				//finding a number on a sin curve, where a random number has a higher 
				//chance of being closer to 1 than anything else, with 0 being the lowest probability
				temp = networks.get(((int)Math.sin(Math.random()*Math.PI/2))*networks.size());
				network.add(RandomlyAlterNetwork.AlterNewNetwork(temp , helper));
			}
			
			//add new nets.
			networks.addAll(network);
			
			
			
		}
	}

	
	public static ArrayList<Network> sortNetworks(ArrayList<Double> results, ArrayList<Network> nets) {
		Network tmp2;
		Double tmp;
		for (int k = 0; k < nets.size() - 1; k++) {

			boolean isSorted = true;
			for (int i = 1; i < nets.size() - k; i++) {

				if (results.get(i) > results.get(i - 1)) {
					tmp = results.get(i);
					results.set(i, results.get(i - 1));
					results.set(i - 1, tmp);

					tmp2 = nets.get(i);
					nets.set(i, nets.get(i - 1));
					nets.set(i - 1, tmp2);

					isSorted = false;
				}
			}
			if (isSorted)
				break;
		}
		return nets;

	}

	// TODO generate the networks for 16 inputs and 16 outputs.
	private static Network generateNetwork() {
		return null;

	}

	private static double DetermineCorrectPercentage(Network net) {
		Random random = new Random();
		int games = 100;
		double correctPercentage = 0;

		int input = 0;
		int output = 0;
		double inputArr[];
		double outputArr[];
		double netOutput[];
		int correct = 0;
		// run 100 trials
		for (int i = 0; i < games; i++) {
			// get the numbers and arrays required to run the trials
			input = (random.nextInt() & 0xffff);
			output = RNGTool.RNGInput(input);
			inputArr = getBinaryArray(input);
			outputArr = getBinaryArray(output);

			// run the net
			netOutput = net.RunNetwork(inputArr);
			correct = 0;

			for (int j = 0; j < 16; j++) {
				if (netOutput[j] * outputArr[j] > 0) {
					correct++; // test the same sign
				}
			}

			correctPercentage += correct / 16.0;
			// increase the correct percentage by the number that were correct.

		}

		// return the correct percentage.
		return correctPercentage / games;
	}

	private static double[] getBinaryArray(int in) {
		double array[] = new double[16];

		for (int i = 0; i < 16; i++) {
			if (((in >> i) & 1) == 1) {
				array[i] = 1.0;
			} else {
				array[i] = -1.0;
			}
		}
		return array;
	}

}
