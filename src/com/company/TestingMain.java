package com.company;

import static tech.deef.Tools.Tools.PrintColor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.amd.aparapi.Kernel;
import com.amd.aparapi.Range;
import com.amd.aparapi.device.Device;
import com.amd.aparapi.device.OpenCLDevice;
import com.amd.aparapi.internal.opencl.OpenCLPlatform;
import com.company.TaronBot.Game.Board;
import com.company.TaronBot.Game.Move;
import com.company.TaronBot.Network.MateNetworks;
import com.company.TaronBot.Network.TakNetwork;

import tech.deef.Tools.StaticGlobals;

public class TestingMain {

	private static boolean LOGGING_ENABLED = false;
	private static int numPerGenerationMethod = 3;
	
	/**
	 * main testing method. runs through all testing algorithims for funcionalitly. 
	 * @param args
	 */
	public static void main(String[] args) {
		
		test();
		//OUTPUT();
		
		//NetTesting();
		//ThreadedTesting(20);
		//ThreadTimingTesting();
		
		//ThreadedTesting(10);
		//GPUTesting(10);
		//TestSingleGame();
		//TestGeneration();
		//networkGenerationCalculationTest();
		//saveTesting();
		//loadTesting();
		//networkmutatorsTest();
		//testing();
		
	}

	
	private static void test() {
		// TODO Auto-generated method stub
		
		int num = 3;
		
		double[] input = {1.0,2.0,3.0};
		
		double[][] mutators = new double[num][num];
		
		double[] output = new double[num];

		double[] output2 = new double[num];
		Random rand = new Random(1);
		
		for(int i = 0; i < num; i++)
		{
			//input[i] = rand.nextDouble();
			for(int j = 0; j < num; j++){
				mutators[i][j] = rand.nextDouble();
			}
		}
		
		final double[] inputT = input;
		
		final double[][] mutatorsT = mutators;
		
		final double[] outputT = output;
		
		long singleThreadedStart = System.nanoTime();
		
		int sum = 0;;
		for(int i = 0; i < num; i++)
		{
			sum = 0;
			for(int j = 0; j < num; j++){
				sum += mutators[i][j] * input[j];
			}
			output[i] = 2/(1+Math.exp(-2*sum))-1;
			System.out.println(i + ":" + output[i] );
		}
		
		long singleThreadedEnd = System.nanoTime();

		
		long multiThreadedStart = System.nanoTime();

		
		
		
		Kernel t = new Kernel(){

			@Override
			public void run() {
				int i= getGlobalId(); 
				int sum = 0;
				for(int j = 0; j < num; j++){
					sum += mutatorsT[i][j] * inputT[j];
				}
				outputT[i] = 2/(1+Math.exp(-2*sum))-1;
			}
			
		};
		
		
		
		
		Range range = Range.create(output.length); 
		t.execute(range);
		
		for(int i = 0;i < num; i++){
			System.out.println(i + ":" + output[i] );
		}
		
		long multiThreadedEnd = System.nanoTime();
		
		input[0] = 2.0;
		
		input[1] = 4.0;
		input[2] = 3.0;
		
		System.out.println("thuing");
		t.execute(range);
		System.out.println("thuing");
		
		for(int i = 0;i < num; i++){
			System.out.println(i + ":" + output[i] );
		}
		
		System.out.println("Thread: " + (multiThreadedEnd-multiThreadedStart));
		System.out.println("Single: " + (singleThreadedEnd-singleThreadedStart));
		
		
	}


	private static void OUTPUT() {

		 System.out.println("com.amd.aparapi.sample.info.Main");
	      List<OpenCLPlatform> platforms = (new OpenCLPlatform()).getOpenCLPlatforms();
	      System.out.println("Machine contains " + platforms.size() + " OpenCL platforms");
	      int platformc = 0;
	      for (OpenCLPlatform platform : platforms) {
	         System.out.println("Platform " + platformc + "{");
	         System.out.println("   Name    : \"" + platform.getName() + "\"");
	         System.out.println("   Vendor  : \"" + platform.getVendor() + "\"");
	         System.out.println("   Version : \"" + platform.getVersion() + "\"");
	         List<OpenCLDevice> devices = platform.getOpenCLDevices();
	         System.out.println("   Platform contains " + devices.size() + " OpenCL devices");
	         int devicec = 0;
	         for (OpenCLDevice device : devices) {
	            System.out.println("   Device " + devicec + "{");
	            System.out.println("       Type                  : " + device.getType());
	            System.out.println("       GlobalMemSize         : " + device.getGlobalMemSize());
	            System.out.println("       LocalMemSize          : " + device.getLocalMemSize());
	            System.out.println("       MaxComputeUnits       : " + device.getMaxComputeUnits());
	            System.out.println("       MaxWorkGroupSizes     : " + device.getMaxWorkGroupSize());
	            System.out.println("       MaxWorkItemDimensions : " + device.getMaxWorkItemDimensions());
	            System.out.println("   }");
	            devicec++;
	         }
	         System.out.println("}");
	         platformc++;
	      }

	      Device bestDevice = OpenCLDevice.best();
	      if (bestDevice == null) {
	         System.out.println("OpenCLDevice.best() returned null!");
	      } else {
	         System.out.println("OpenCLDevice.best() returned { ");
	         System.out.println("   Type                  : " + bestDevice.getType());
	         System.out.println("   GlobalMemSize         : " + ((OpenCLDevice) bestDevice).getGlobalMemSize());
	         System.out.println("   LocalMemSize          : " + ((OpenCLDevice) bestDevice).getLocalMemSize());
	         System.out.println("   MaxComputeUnits       : " + ((OpenCLDevice) bestDevice).getMaxComputeUnits());
	         System.out.println("   MaxWorkGroupSizes     : " + ((OpenCLDevice) bestDevice).getMaxWorkGroupSize());
	         System.out.println("   MaxWorkItemDimensions : " + ((OpenCLDevice) bestDevice).getMaxWorkItemDimensions());
	         System.out.println("}");
	      }

	      Device firstCPU = OpenCLDevice.firstCPU();
	      if (firstCPU == null) {
	         System.out.println("OpenCLDevice.firstCPU() returned null!");
	      } else {
	         System.out.println("OpenCLDevice.firstCPU() returned { ");
	         System.out.println("   Type                  : " + firstCPU.getType());
	         System.out.println("   GlobalMemSize         : " + ((OpenCLDevice) firstCPU).getGlobalMemSize());
	         System.out.println("   LocalMemSize          : " + ((OpenCLDevice) firstCPU).getLocalMemSize());
	         System.out.println("   MaxComputeUnits       : " + ((OpenCLDevice) firstCPU).getMaxComputeUnits());
	         System.out.println("   MaxWorkGroupSizes     : " + ((OpenCLDevice) firstCPU).getMaxWorkGroupSize());
	         System.out.println("   MaxWorkItemDimensions : " + ((OpenCLDevice) firstCPU).getMaxWorkItemDimensions());
	         System.out.println("}");
	      }

	      Device firstGPU = OpenCLDevice.firstGPU();
	      if (firstGPU == null) {
	         System.out.println("OpenCLDevice.firstGPU() returned null!");
	      } else {
	         System.out.println("OpenCLDevice.firstGPU() returned { ");
	         System.out.println("   Type                  : " + firstGPU.getType());
	         System.out.println("   GlobalMemSize         : " + ((OpenCLDevice) firstGPU).getGlobalMemSize());
	         System.out.println("   LocalMemSize          : " + ((OpenCLDevice) firstGPU).getLocalMemSize());
	         System.out.println("   MaxComputeUnits       : " + ((OpenCLDevice) firstGPU).getMaxComputeUnits());
	         System.out.println("   MaxWorkGroupSizes     : " + ((OpenCLDevice) firstGPU).getMaxWorkGroupSize());
	         System.out.println("   MaxWorkItemDimensions : " + ((OpenCLDevice) firstGPU).getMaxWorkItemDimensions());
	         System.out.println("}");
	      }
	}


	private static void GPUTesting(int numPerGeneration) {
		
		Random random = new Random();
		int[] wins = new int[numPerGeneration];
		int[] losses = new int[numPerGeneration];
		ArrayList<TakNetwork> networks=new ArrayList<>();
		
		
		//gen networks
		int RandomNunber = 0;
		for(int i = 0; i < numPerGeneration; i++){
			TakNetwork testNetwork = new TakNetwork(9, 8, 8, 8);
			RandomNunber = random.nextInt();
			Random rand = new Random(RandomNunber);
			testNetwork.randomize(rand);
			
			networks.add(testNetwork);
			
			//all 100 networks generate
		}
		//System.out.println("Generated Threads");
		long startTime = System.nanoTime();
		
		
		  
		//create threads and thread arrayList
		
		Kernel t = new Kernel(){

			@Override
			public void run() {
				RunSelectionOfGames(getGlobalId(),networks,wins,losses);
				
			}
			
		};
		
			
		Range r = new Range(null, 1);
		t.execute(r);
		
		
	
		
		long endTime = System.nanoTime();
		
		
		//deprecated for wins and losses in network.
		double[] ratio = new double[wins.length];
		double[] percentage = new double[wins.length];
		for(int i = 0; i < wins.length; i++){
			ratio[i] = (double)wins[i]/(double)losses[i];
			percentage[i] = (double) wins[i]/((double)wins[i]+(double)losses[i]);
		}
		
		if(StaticGlobals.PRINT_THREAD_WINNER_OUTPUT){
			System.out.println("Wins   : " + Arrays.toString(wins));
			System.out.println("Losses : " + Arrays.toString(losses));
			System.out.println("Ratio  : " + Arrays.toString(ratio));
			System.out.println("Percent: " + Arrays.toString(percentage));
		}
		
		System.out.println("total Time for " + numPerGeneration +" Threads is: " + (endTime-startTime)/1000000000.0 + " S");
		//System.out.println(numPerGeneration +" "+  (endTime-startTime)/1000000000.0);
		
	}


	/**
	 * runs a series of tests from 5-95 to test timings of the generation. 
	 */
	private static void ThreadTimingTesting() {
		
		for(int i = 5; i < 100; i = i + 5)
		{
			ThreadedTesting(i);
		}
	}



	/**
	 * creates a series of threads networks and tests the output of the wins loss
	 */
	private static long ThreadedTesting(int numPerGeneration) {
		
		Random random = new Random();
		int[] wins = new int[numPerGeneration];
		int[] losses = new int[numPerGeneration];
		ArrayList<TakNetwork> networks=new ArrayList<>();
		
		
		//gen networks
		int RandomNunber = 0;
		for(int i = 0; i < numPerGeneration; i++){
			TakNetwork testNetwork = new TakNetwork(9, 8, 8, 8);
			RandomNunber = random.nextInt();
			Random rand = new Random(RandomNunber);
			testNetwork.randomize(rand);
			
			networks.add(testNetwork);
			
			//all 100 networks generate
		}
		//System.out.println("Generated Threads");
		long startTime = System.nanoTime();
		
		
		
		//create threads and thread arrayList
		ArrayList<Thread> threads = new ArrayList<Thread>(numPerGeneration);
		for(int i = 0; i < numPerGeneration; i++){
			
			final int start = i;
			Thread t = new Thread(){

				@Override
				public void run() {
					RunSelectionOfGames(start,networks,wins,losses);
					
				}
				
			};
			threads.add(t);
		//	System.out.println(i);
		}
		
		
		//run all threads. 
		for(Thread thread: threads){
			//System.out.println(thread.toString());
			thread.start();	
			//System.out.println(thread.isAlive());
		}
		
		
		for(int i = 0; i < threads.size(); i++){
			try {
				//System.out.println(i);
				threads.get(i).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		long endTime = System.nanoTime();
		
		
		//deprecated for wins and losses in network.
		double[] ratio = new double[wins.length];
		double[] percentage = new double[wins.length];
		for(int i = 0; i < wins.length; i++){
			ratio[i] = (double)wins[i]/(double)losses[i];
			percentage[i] = (double) wins[i]/((double)wins[i]+(double)losses[i]);
		}
		
		if(StaticGlobals.PRINT_THREAD_WINNER_OUTPUT){
			System.out.println("Wins   : " + Arrays.toString(wins));
			System.out.println("Losses : " + Arrays.toString(losses));
			System.out.println("Ratio  : " + Arrays.toString(ratio));
			System.out.println("Percent: " + Arrays.toString(percentage));
		}
		
		System.out.println("total Time for " + numPerGeneration +" Threads is: " + (endTime-startTime)/1000000000.0 + " S");
		//System.out.println(numPerGeneration +" "+  (endTime-startTime)/1000000000.0);
		return endTime-startTime;
	}
	
	/**
	 * takes in a single int and a list of networks, along with the locations of the wins and losses arrays, and 
	 * using that data, the network of the given int against every other network in the list. 
	 * This can be used for threading the network running. 
	 * 
	 * @param i int network to run against all other networks
	 * @param networks List<TakNetworks> the list of networks that are to be run
	 * @param wins int[] the array for tracking wins
	 * @param losses int[] the array for tracking losses
	 */
	private static void RunSelectionOfGames(int i, List<TakNetwork> networks, int[] wins, int[] losses){
		
		//for(int j = 0; j < networks.size(); j++){
			/*
			long start=System.currentTimeMillis();
			
			TakNetwork net1 = networks.get(i);
			TakNetwork net2 = networks.get(j);

			int winner = Board.playGame(net1, net2, 8);
			if(winner == 1){
				net1.setWins(net1.getWins() + 1);
				net2.setLosses(net1.getLosses() + 1);
			}else{
				net2.setWins(net2.getWins() + 1);
				net1.setLosses(net1.getLosses() + 1);
			}
			if(StaticGlobals.PRINT_GAME_WINNER){
				System.out.println("game " + i + ":" + j + " Winner: " 
				+  winner + " Time: "+(System.currentTimeMillis()-start)/1000.0 + " S");
			}*/

		//}
		
		
	}
	
	/**
	 * Tests network access. 
	 * 
	 */
	@SuppressWarnings("unused")
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
		}*/
	}

	/**
	 * tests a single game created from two randomly generated networks.
	 * 
	 */
	@SuppressWarnings("unused")
	private static void TestSingleGame() {
		
		TakNetwork net1 = new TakNetwork(9, 8, 8, 8);
		TakNetwork net2 = new TakNetwork(9, 8, 8, 8);
		Random rand = null;
		
		rand = new Random();
		net1.randomize(rand);
		rand = new Random();
		net2.randomize(rand);
		
		System.out.println("game 1:2 " +  Board.playGame(net1, net2, 8));

	}

	/**
	 * tests the generation of numPerGeneration networks
	 * has each network saved, loaded, and fight against itself and every other network
	 */
	public static void TestGeneration(){
		Random random = new Random();
		int generation = 0;
		
		Logger logger = Logger.getLogger("MyLog");

		if(LOGGING_ENABLED)
		{
	    	FileHandler fh;  
	
		    try {  
	
		        // This block configure the logger with handler and formatter  
		    	//Time time = new Time(System.currentTimeMillis());
		    	
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
		for(int i = 0; i < numPerGenerationMethod; i++){
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
				oos.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
		
		//create win loss 
		int[] wins = new int[numPerGenerationMethod];
		int[] losses = new int[numPerGenerationMethod];
		ArrayList<TakNetwork> networks=new ArrayList<>();
		for (int i = 0; i < numPerGenerationMethod; i++) {
			System.err.println(i);
			networks.add(load("networks\\gen" + generation + "\\Network"+ i + ".takNetwork"));
		}
		for(int i = 0; i < numPerGenerationMethod; i++){
			
			RunSelectionOfGames(i,networks,wins,losses);
			
		}
		
		double[] ratio = new double[wins.length];
		double[] percentage = new double[wins.length];
		for(int i = 0; i < wins.length; i++){
			ratio[i] = (double)wins[i]/(double)losses[i];
			percentage[i] = (double) wins[i]/((double)wins[i]+(double)losses[i]);
		}
		
		
		System.out.println("Wins   : " + Arrays.toString(wins));
		System.out.println("Losses : " + Arrays.toString(losses));
		System.out.println("Ratio  : " + Arrays.toString(ratio));
		System.out.println("Percent: " + Arrays.toString(percentage));
		
	}
		
	/**
	 * Load a network
	 * @param location String File location to load the network from. 
	 * @return TakNetwork network that exists at file location, or null
	 */
	private static TakNetwork load(String location){
		
		FileInputStream fin;
		TakNetwork testNetwork = null;
		try {
			fin = new FileInputStream(location);
			ObjectInputStream ois = new ObjectInputStream(fin);
			testNetwork = (TakNetwork) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return testNetwork;
	}
	
	/**
	 * tests a series of statistics methods and classes for other parts of the program
	 * specifically the mutation method that accepts a list of networks. 
	 */
	@SuppressWarnings("unused")
	private static void StatsTesting() {

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

	/**
	 * tests the load Network Function. 
	 * May error if saveTesting is not used directally prior. 
	 */
	@SuppressWarnings("unused")
	private static void loadTesting() {
		FileInputStream fin;
		try {
			fin = new FileInputStream("networks\\testNet.net");
			ObjectInputStream ois = new ObjectInputStream(fin);
			TakNetwork testNetwork = (TakNetwork) ois.readObject();
			ois.close();
			
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

	/**
	 * tests the saving of networks
	 */
	@SuppressWarnings("unused")
	private static void saveTesting() {
				
		TakNetwork testNetwork = new TakNetwork(8, 8, 9, 8);
		Random rand = new Random(1);
		testNetwork.randomize(rand);
		
		FileOutputStream fout;
		try {
			fout = new FileOutputStream("networks\\testNet.net");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(testNetwork);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * tests the mutation of multiple networks at the same time. 
	 * tests with 20 networks. 
	 */
	public static void networkGroupMutatorsTest() {
		//int[][][] blank = createTestBlank();
		//generate a blank network to use as the calculation network
		
		TakNetwork testNetwork = new TakNetwork(8, 8, 9, 8);
		@SuppressWarnings("unused")
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
		
		
		@SuppressWarnings("unused")
		TakNetwork mate = MateNetworks.GroupMateNetworks(takNetworks, rand);
		
	}

	/**
	 * tests the generation of a series of networks,
	 * it prints the ammount of ram before and after a network is generated
	 * the time it took to build said network
	 * the time it took for the network to calculate with that output
	 * and then data for a series of other networks
	 * 		current data
	 * 		gen time
	 * 		gen data
	 * 		calc time. 
	 * 
	 * finally prints total calc time
	 * total calc data
	 * and a list of output moves from the last generated network.
	 */
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

		long startupTime = 0, genTime = 0, endTime = 0, startData = 0 , genData = 0, endData = 0;
		//long startupData;
		ArrayList<TakNetwork> takNetworks = new ArrayList<TakNetwork>(20);
		long startTime = System.nanoTime();
		startData = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
		List<Move> moves = new ArrayList<Move>(1);
		for (int i = 0; i < 20; i++) {
			PrintColor("Network " + i + "\n", "green");

			startupTime = System.nanoTime();
			//startupData = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
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

	/**
	 * creates a [8][8][9] integer network to use for testing
	 * @return double[][][] to be used for basic testing. 
	 */
	@SuppressWarnings("unused")
	private static int[][][] createTestBlank889(){
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
	
	/**
	 * creates a [9][8][8] integer network to use for testing
	 * @return double[][][] to be used for basic testing. 
	 */
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
