package com.company.TaronBot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class ServerCommunication {

	public static void main(String[] args) {
		// TODO Create examples of how to communicate with the server

		try {
			Socket socket = new Socket("www.playtak.com", 10000);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			
			
			Thread readinThread = new Thread(){
				@Override
				public void run(){
					String s = "";
					while(true){
						try {
							s = in.readLine();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						if(!s.equals("")){
							System.out.println(s);
							s = "";
						}
					}
				}
				
			};
			
			readinThread.start();
			
			
			
			String userInput;
			while ((userInput = stdIn.readLine()) != null  && !userInput.toLowerCase().equals("quit")) {
			    out.println(userInput);
			}
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}



/*
 * 
	Thanks to Zach C. for creating this list for interaction with the API and PlatTak
	
	# Example connection to tak server.
	
	# Connect to server
	Server says: Welcome!
	Server says: Login or Registers
	Client says: Login Username Password
	Server says: Welcome Username!
	Server says (For all): Seek new num name boardsize time W\B
	Client says: Accept num
	
	# Begining of game
	
	# During game
	
	# For place
	Client says: Game#num P Sq (C/W)
	# Note that Sq is formated letterNumber, for example A1 A2 H5 H8. Should be formated as string
	# Also, C is for capstone, w is for wall.
	# P is for place M is for move
	Server says: Game#num P Sq 
	
	# For move
	Client says: Game#num M Sq Sq num...
	# Original square, destination square, num of pices to drop, num of pics to drop ... etc
	
	# Other
	
	Client says: List
	# Sends list of seeks
	
	Client says: GameList
	# Sends list of games in progress
	
	Client says: Observe num
	# Observe this game.
	
	Client says: quit
	
	Server says: OK
	# Good move
	
	Server says: NOK
	# Bad move

 */
