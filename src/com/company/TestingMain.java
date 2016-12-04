package com.company;

import static tech.deef.Tools.Tools.PrintColor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.AbstractRandomGenerator;
import org.apache.commons.math3.random.BitsStreamGenerator;
import org.apache.commons.math3.random.ISAACRandom;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import com.company.TaronBot.Game.Board;
import com.company.TaronBot.Game.Move;
import com.company.TaronBot.Network.MateNetworks;
import com.company.TaronBot.Network.TakNetwork;

public class TestingMain {

	private static boolean LOGGING_ENABLED = false;
	private static int numPerGeneration = 20;
	
	public static void main(String[] args) {
		NetTesting();
		//TestSingleGame();
		TestGeneration();
		
		networkGenerationCalculationTest();
		//saveTesting();
		//loadTesting();
		//networkmutatorsTest();
		//testing();
		
	}

private static void NetTesting() {
		
		/*URL url;
		InputStream inputStream = null;
		BufferedReader bufferedReader;
		String data = null;
		
		try {
			url = new URL("playtak.com");
			URLConnection c = url.openConnection();
			c.setRequestProperty("Client-ID", "login guest");
			c.
			
			inputStream = c.getInputStream(); // throws an IOException
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

			
			data = bufferedReader.readLine();
			
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
		} catch (IOException ioe) {
			//ioe.printStackTrace();
			System.out.println("ERROR: IO Exception in Data Puller");
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException ioe) {
				// nothing to see here
			}
		}
		
		*/
	}

	private static void TestSingleGame() {
		
		TakNetwork net1 = new TakNetwork(9, 8, 8, 8);
		TakNetwork net2 = new TakNetwork(9, 8, 8, 8);
		Random rand = null;
		
		rand = new Random(154);
		net1.randomize(rand);
		rand = new Random(2);
		net2.randomize(rand);
		
		System.out.println("game 1:2 " +  Board.playGame(net1, net2, 8));

	}

	public static void TestGeneration(){
		Random random = new Random();
		int generation = 0;
		
		Logger logger = Logger.getLogger("MyLog");

		if(LOGGING_ENABLED)
		{
	    	FileHandler fh;  
	
		    try {  
	
		        // This block configure the logger with handler and formatter  
		    	Time time = new Time(System.currentTimeMillis());
		    	
		    	Date date = new Date(System.currentTimeMillis());
		    	DateFormat formattert = new SimpleDateFormat("MM-dd-YYYY HH_mm_ss");
		    	String dateFormatted = formattert.format(date);
		    	
		    	System.out.println(dateFormatted);
		        fh = new FileHandler(("logfiles\\" + dateFormatted + " output.log"));  
		        logger.addHandler(fh);
		        SimpleFormatter formatter = new SimpleFormatter();  
		        fh.setFormatter(formatter);  
	
		        // the following statement is used to log any messages  
		        //logger.info("My first log");  
	
		    } catch (SecurityException e) {  
		        e.printStackTrace();  
		    } catch (IOException e) {  
		        e.printStackTrace();  
		    }  
		    logger.setLevel(Level.ALL);
		    //logger.info("Hi How r u?");  
			//logger.config("Hi How r u?");
			//logger.config("msg2");
			
		}
		
		//gen networks
		
		new File("networks\\gen0").mkdirs();
		int RandomNunber = 0;
		for(int i = 0; i < numPerGeneration; i++){
			TakNetwork testNetwork = new TakNetwork(9, 8, 8, 8);
			RandomNunber = random.nextInt();
			Random rand = new Random(RandomNunber);
			testNetwork.randomize(rand);
			
			if(LOGGING_ENABLED){
				logger.config("Created network with random seed:" + RandomNunber + 
							  "\nArgs 8, 8, 9, 8");
			}
			
			FileOutputStream fout;
			try {
				fout = new FileOutputStream("networks\\gen0\\Network" + i + ".takNetwork");
				ObjectOutputStream oos = new ObjectOutputStream(fout);
				oos.writeObject(testNetwork);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		
		//create win loss 
		int[] wins = new int[numPerGeneration];
		int[] losses = new int[numPerGeneration];
		ArrayList<TakNetwork> networks=new ArrayList<>();
		for (int i = 0; i < numPerGeneration; i++) {
			System.err.println(i);
			networks.add(load("networks\\gen" + generation + "\\Network"+ i + ".takNetwork"));
		}
		for(int i = 0; i < numPerGeneration; i++){
			for(int j = 0; j < numPerGeneration; j++){
				long start=System.currentTimeMillis();
				TakNetwork net1 = networks.get(i);
				TakNetwork net2 = networks.get(j);
				long generate=System.currentTimeMillis();

				System.out.println("game " + i + ":" + j + " " +  Board.playGame(net1, net2, 8)+" "+(System.currentTimeMillis()-start)/1000.0);


			}
		}
		
		
	}
	
	
	
	private static TakNetwork load(String location){
		
		FileInputStream fin;
		TakNetwork testNetwork = null;
		try {
			fin = new FileInputStream(location);
			ObjectInputStream ois = new ObjectInputStream(fin);
			testNetwork = (TakNetwork) ois.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return testNetwork;
	}
	
	
	private static void testing() {

		/*
		//mean 10, std dev 1
		//Random rand = new Random(1);
		Random rand = new Random(); //
		double[] vals = {-3,-2.5,-2,-1.5,-1,-.5,0,.5,1,1.5,2,2.5,3}; //
		double fromCurve = 0; //
		
		
		//at the beginning
		Mean mean = new Mean();
		StandardDeviation std = new StandardDeviation();
		
		RandomGenerator random = new ISAACRandom();
		random.setSeed(rand.nextInt());
		NormalDistribution n = new NormalDistribution(random, mean.evaluate(vals), std.evaluate(vals));
		
		fromCurve = n.sample();
		System.out.println( n.sample());
		System.out.println( n.sample());
		System.out.println( n.sample());
		System.out.println( n.sample());
		System.out.println( n.sample());
		System.out.println( n.sample());
		System.out.println( n.sample());
		System.out.println( n.sample());
		
		
		double total = 0;
		
		for(int i = 0; i < 100000000; i++){
			total += n.sample();
		}
		System.out.println("average: " + total/(100000000));
		*/
		//setCurve
		
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
		
		
		TakNetwork mate = MateNetworks.MateNetworks(takNetworks, rand);
		
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

	private static int[][][] createTestBlank2(){
		int[][][] data ={{{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0}},
				{{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0}},
				{{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0}},
				{{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0}},
				{{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0}},
				{{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0}},
				{{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0}},
				{{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0}}};
		
				
				
				
		return data;
	}
	
	
	private static int[][][] createTestBlank() {

		int[][][] data = { { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } },
				{ { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } }, 
				{ { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } }, 
				{ { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } }, 
				{ { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } }, 
				{ { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } }, 
				{ { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } }, 
				{ { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } }, 
				{ { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } }, };

		return data;

	}

}
