package com.company.Testing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.company.TaronBot.NEAT.Network;

import tech.deef.Tools.RNGTool;

public class RNGTesting {
	
	//the purpose of this class is to test and reverse engineer the the value from a 
	public static void main(String[] args){
		ArrayList<Network> networks = new ArrayList<Network>(100);
		
		
		for(int i = 0; i < 100; i++){
			networks.set(i, generateNetwork());
		}
		
		ArrayList<Double> results = new ArrayList<Double>(100);
		for(int i = 0; i < 1000 ; i++){
			
			//run generation for 
			for(int j = 0; j < 100; j++){
				results.set(j, DetermineCorrectPercentage(networks.get(j)));
			}
			
			
			
			
			
		}
	}
	
	//TODO generate the networks for 16 inputs and 16 outputs.
	private static Network generateNetwork(){
		return null;
		
	}
	
	private static double DetermineCorrectPercentage(Network net){
		Random random = new Random();
		int games = 100;
		double correctPercentage = 0;
		
		int input = 0;
		int output = 0;
		double inputArr[];
		double outputArr[];
		double netOutput[];
		int correct = 0;
		//run 100 trials
		for(int i = 0; i < games; i++){
			//get the numbers and arrays required to run the trials
			input = (random.nextInt()&0xffff);
			output = RNGTool.RNGInput(input);
			inputArr = getBinaryArray(input);
			outputArr = getBinaryArray(output);
			
			//run the net
			netOutput = net.RunNetwork(inputArr);
			correct = 0;
			
			for(int j = 0; j < 16; j++){
				if(netOutput[j]*outputArr[j] >0){
					correct++; //test the same sign
				}
			}
			
			correctPercentage += correct/16.0;
			//increase the correct percentage by the number that were correct. 
			
		}
		
		//return the correct percentage. 
		return correctPercentage/games;
	}
	
	private static double[] getBinaryArray(int in){
		double array[] = new double[16];
		
		for(int i = 0; i < 16; i++){
			if(((in >> i) & 1) == 1){
				array[i] = 1.0;
			}else{
				array[i] = -1.0;
			}
		}
		return array;
	}
	
}
