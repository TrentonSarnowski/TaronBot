package com.company;

import java.util.Random;

import com.company.TaronBot.Network.Network;

import tech.deef.Tools.Tools;

public class Main {

    public static void main(String[] args) {
    	// write your code here
    	//Tools.PrintColor("This is a triumph", "green");
    	
    	//goals of the testing.
    	// create a series of networks.
    	// randomly generate a network from a specifc input.
    	//compare the output to the output of other networks. 
    	//calculate run time. 
    	
    	int data = (int) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    	long time = System.nanoTime();
    	System.out.println(data/1024 + " kB");
    	
    	Network testNetwork = new Network(8,8,9,8);
    	Random rand = new Random(1);
    	testNetwork.randomize(rand);
    	
    	//total memory usage
    	
    	data = (int) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    	time = System.nanoTime()-time;
    	System.out.println(data/1024 + " kB");
    	System.out.println(time/1000.0/1000.0 + " mS");
    	
    	
    	
    	
    	
    	
    }
}
