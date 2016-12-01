package com.company.TaronBot;

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
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.company.TaronBot.Game.Board;
import com.company.TaronBot.Game.Move;
import com.company.TaronBot.Network.TakNetwork;

public class Main {

	
	private static boolean LOGGING_ENABLED = false;
	private static int numPerGeneration = 20;
	
	public static void main(String[] args){
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
		
		for(int i = 0; i < numPerGeneration; i++){
			for(int j = 0; j < numPerGeneration; j++){
				TakNetwork net1 = load("networks\\gen" + generation + "\\Network"+ i + ".takNetwork");
				TakNetwork net2 = load("networks\\gen" + generation + "\\Network"+ j + ".takNetwork");
				
				System.out.println("game " + i + ":" + j + " " +  play(net1, net2, 8));
				
			}
		}
		
		
	}
	
	private static int play(TakNetwork net1, TakNetwork net2, int sideLegth){
		
		return Board.playGame(net1, net2, sideLegth);
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

