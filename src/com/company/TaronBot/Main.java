package com.company.TaronBot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.company.TaronBot.Game.Board;
import com.company.TaronBot.Network.TakNetwork;

public class Main {

	
	private static boolean LOGGING_ENABLED = false;
	private static int numPerGeneration = 20;
	
	public static void main(String[] args){
		NetTesting();
		//TestSingleGame();
		TestGeneration();
		
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
	
	private static int[][][] createTestBlank() {

		int[][][] data = { { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } }, { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } }, { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } }, { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } }, { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } }, { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } }, { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } }, { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } }, { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } }, };

		return data;

	}
	
	
}

