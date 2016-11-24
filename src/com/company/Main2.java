package com.company;

import static tech.deef.Tools.Tools.PrintColor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.company.TaronBot.Game.Move;
import com.company.TaronBot.Network.MateNetworks;
import com.company.TaronBot.Network.TakNetwork;

public class Main2 {

	public static void main2(String[] args) {
		networkGenerationCalculationTest();
		saveTesting();
		loadTesting();
		networkmutatorsTest();

		
	}

	
	private static void loadTesting() {
		FileInputStream fin;
		try {
			fin = new FileInputStream("networks\\testNet.net");
			ObjectInputStream ois = new ObjectInputStream(fin);
			TakNetwork testNetwork = (TakNetwork) ois.readObject();
			
			int[][][] blank = createTestBlank();
			List<Move> moves = testNetwork.calculate(blank);
			for (Move move : moves) {
				PrintColor("   " + move.toString() + "\n", "blue");
			}
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void saveTesting() {
				
		TakNetwork testNetwork = new TakNetwork(8, 8, 9, 8);
		Random rand = new Random(1);
		testNetwork.randomize(rand);
		
		FileOutputStream fout;
		try {
			fout = new FileOutputStream("networks\\testNet.net");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(testNetwork);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		
		
		
	}

	public static void networkmutatorsTest() {
		int[][][] blank = createTestBlank();
		//generate a blank network to use as the calculation network
		
		TakNetwork testNetwork = new TakNetwork(8, 8, 9, 8);
		TakNetwork newNetwork;
		Random rand = new Random(1);
		testNetwork.randomize(rand);
		//randomize a new network base on the number 1
		
		newNetwork = testNetwork.returnAnotherMutatedNetwork(rand, 0.001);
		
		//create an array of 20 networks.
		//the set of these networs will be used to test the group generation
		ArrayList<TakNetwork> takNetworks = new ArrayList<TakNetwork>(20);
		for (int i = 0; i < 20; i++) {
			testNetwork = new TakNetwork(8, 8, 9, 10);
			takNetworks.add(testNetwork);
			Random random = new Random(i);
			testNetwork.randomize(random);
		}
		
		TakNetwork mate = MateNetworks.MateNetworks(takNetworks);
		
	}

	public static void networkGenerationCalculationTest() {
		// write your code here
		// Tools.PrintColor("This is a triumph", "green");

		// goals of the testing.
		// create a series of networks.
		// randomly generate a network from a specifc input.
		// compare the output to the output of other networks.
		// calculate run time.

		int data = (int) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
		long time = System.nanoTime();
		System.out.println(data / 1024 + " kB");

		TakNetwork testNetwork = new TakNetwork(8, 8, 9, 8);
		Random rand = new Random(1);
		testNetwork.randomize(rand);

		// total memory usage

		data = (int) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
		time = System.nanoTime() - time;
		System.out.println(data / 1024 + " kB");
		PrintColor(time / 1000.0 / 1000.0 + " mS in a build\n", "green");

		time = System.nanoTime();
		testNetwork.calculate(createTestBlank());
		time = System.nanoTime() - time;
		PrintColor(time / 1000.0 / 1000.0 + " mS in a calculate", "green");
		PrintColor("\n\nTESTING DATA BELOW\n", "blue");

		int[][][] blank = createTestBlank();

		long startupTime = 0, genTime = 0, endTime = 0, startData = 0, startupData = 0, genData = 0, endData = 0;

		ArrayList<TakNetwork> takNetworks = new ArrayList<TakNetwork>(20);
		long startTime = System.nanoTime();
		startData = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
		List<Move> moves = new ArrayList<Move>(1);
		for (int i = 0; i < 20; i++) {
			PrintColor("Network " + i + "\n", "green");

			startupTime = System.nanoTime();
			startupData = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
			testNetwork = new TakNetwork(8, 8, 9, 10);
			takNetworks.add(testNetwork);
			Random random = new Random(i);
			testNetwork.randomize(random);
			genTime = System.nanoTime();
			genData = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
			moves.clear();
			moves = testNetwork.calculate(blank);
			endTime = System.nanoTime();
			endData = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());

			PrintColor(("\tCurrent data " + (endData - startData) / 1024 / 1024.0 + "Mb\n"), "red");

			// gen time
			// gen data size
			// run time
			// run dataSize
			PrintColor(("\tGen  Time " + (genTime - startupTime) / 1000000.0 + "ms\n"), "yellow");
			PrintColor(("\tGen  data " + (genData - startData) / 1024 + "kB\n"), "blue");
			PrintColor(("\tCalc Time " + (endTime - genTime) / 1000000.0 + "ms\n"), "yellow");
		}

		PrintColor(("Total Time " + (endTime - startTime) / 1000000.0 + "ms\n"), "red");
		PrintColor(("Calc data " + (endData - startData) / 1024 + "Kb\n"), "red");

		for (Move move : moves) {

			PrintColor("   " + move.toString() + "\n", "blue");
		}

	}

	private static int[][][] createTestBlank() {

		int[][][] data = { { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } }, { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } }, { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } }, { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } }, { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } }, { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } }, { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } }, { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } }, { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } }, };

		return data;

	}

}
