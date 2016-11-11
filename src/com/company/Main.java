package com.company;

import java.util.ArrayList;
import java.util.Random;

import com.company.TaronBot.Network.Network;

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
    	
    	Network testNetwork = new Network(8,8,9,8);
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
    	
    	ArrayList<Network> networks = new ArrayList<Network>(1000);
    	long startTime = System.nanoTime();
    	startData = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    	for(int i = 0; i < 1000; i++){
    		PrintColor("Network " + i + "\n","green");
    		if(i == 341){
    			
    			System.out.println("stuff");
    		}
    		startupTime = System.nanoTime();
    		startupData = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    		testNetwork = new Network(8,8,9,8);
    		networks.add(testNetwork);
    		Random random = new Random(i);
    		testNetwork.randomize(random);
    		genTime = System.nanoTime();
    		genData = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    		testNetwork.calculate(blank);
    		endTime = System.nanoTime();
    		endData = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    		
    		PrintColor(("\tTotal Data data " + (endData-startData)/1024/1024.0 + "Mb\n") ,"cyan");

    		//gen time
    		//gen data size
    		//run time
    		//run dataSize
    		//PrintColor(("\tGen  Time " + (genTime-startupTime)/1000000.0 + "ms\n") ,"yellow");
    		//PrintColor(("\tGen  data " + (genData-startData)/1024 + "kB\n") ,"cyan");
    		//PrintColor(("\tCalc Time " + (endTime-genTime)/1000000.0 + "ms\n") ,"yellow");
    		//PrintColor(("\tCalc data " + (endData-genData)/1024 + "Kb\n") ,"cyan");//expected 0
    	}
    	
    	PrintColor(("\tTotal Time " + (startupTime-endTime)/1000000.0 + "ms\n") ,"yellow");
		PrintColor(("\tCalc data " + (startupData-endData)/1024 + "Kb\n") ,"cyan");
    	
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
