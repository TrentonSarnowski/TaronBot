package com.company;

import java.util.ArrayList;
import java.util.Random;

import com.company.TaronBot.Network.TakNetwork;

import tech.deef.Tools.Tools;

import static tech.deef.Tools.Tools.*;

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
    	
    	TakNetwork testNetwork = new TakNetwork(8,8,9,8);
    	Random rand = new Random(1);
    	testNetwork.randomize(rand);
    	
    	//total memory usage
    	
    	data = (int) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    	time = System.nanoTime()-time;
    	System.out.println(data/1024 + " kB");
    	PrintColor(time/1000.0/1000.0 + " mS in a build\n", "green");
    	
    	
    	
    	time = System.nanoTime();
    	testNetwork.calculate(createTestBlank());
    	time = System.nanoTime()-time;
    	PrintColor(time/1000.0/1000.0 + " mS in a calculate", "green");
    	PrintColor("\n\nTESTING DATA BELOW\n", "blue");
    	
    	int[][][] blank = createTestBlank();
    	
    	long startupTime = 0, genTime=0, endTime=0,startData=0, startupData=0,genData=0,endData=0;
    	
    	ArrayList<TakNetwork> takNetworks = new ArrayList<TakNetwork>(20);
    	long startTime = System.nanoTime();
    	startData = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    	for(int i = 0; i < 20; i++){
    		PrintColor("Network " + i + "\n","green");
    		if(i == 341){
    			
    			System.out.println("stuff");
    		}
    		startupTime = System.nanoTime();
    		startupData = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    		testNetwork = new TakNetwork(8,8,9,10);
    		takNetworks.add(testNetwork);
    		Random random = new Random(i);
    		testNetwork.randomize(random);
    		genTime = System.nanoTime();
    		genData = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    		testNetwork.calculate(blank);
    		endTime = System.nanoTime();
    		endData = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    		
    		PrintColor(("\tCurrent data " + (endData-startData)/1024/1024.0 + "Mb\n") ,"red");

    		//gen time
    		//gen data size
    		//run time
    		//run dataSize
    		PrintColor(("\tGen  Time " + (genTime-startupTime)/1000000.0 + "ms\n") ,"yellow");
    		PrintColor(("\tGen  data " + (genData-startData)/1024 + "kB\n") ,"blue");
    		PrintColor(("\tCalc Time " + (endTime-genTime)/1000000.0 + "ms\n") ,"yellow");
    	}
    	
    	PrintColor(("Total Time " + (endTime-startTime)/1000000.0 + "ms\n") ,"red");
		PrintColor(("Calc data " + (endData-startData)/1024 + "Kb\n") ,"red");
    	
    }
    
    private static int[][][] createTestBlank(){
    	
    	int[][][] data = {
    			{{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0}},
    			{{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0}},
    			{{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0}},
    			{{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0}},
    			{{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0}},
    			{{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0}},
    			{{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0}},
    			{{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0}},
    			{{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0}},
    	};
    	
    	return data;
    	
    }
    
}
