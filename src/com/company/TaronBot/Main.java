package com.company.TaronBot;

import com.company.TaronBot.Game.RunGames;
import com.company.TaronBot.Network.TakNetwork;
import tech.deef.Tools.StaticGlobals;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static com.company.TestingMain.loadTesting;

public class Main {
	public static void main(String[] args){
        if(true){
            statGather();
            return;
        }
        Scanner s = null;
        try {
            s = new Scanner(new File("Password\\Password"));
        } catch (Exception e) {

        }
        StaticGlobals.Password = s.next();
        Thread commands = new Thread() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("www.playtak.com", 10000);
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out.println("Login sTAKbotControler " + StaticGlobals.Password);
                    ControlClass.StartOnlineControl(new Scanner(in), out);
                } catch (Exception e) {

                }

            }
        };
        commands.start();
        try {

            ControlClass.StartControl();
            commands.join();
        } catch (InterruptedException e) {

        }
    }
    private static void statGather(){
        Random random= new Random(0);
        TakNetwork testNetwork;
        ArrayList n = new ArrayList<>();
        for (int i = 0; i < 512; i++) {



            testNetwork = new TakNetwork(5 + 1, 5, 5, StaticGlobals.DEPTH, 0, i);
            int RandomNumber = random.nextInt();
            Random rand = new Random(RandomNumber);
            testNetwork.randomize(rand);

            n.add(testNetwork);
        }


        RunGames.NEATGAMEPLAY(n,5,100000,"Count8Stats",8);
        RunGames.NEATGAMEPLAY(n,5,100000,"Count16Stats",16);
        RunGames.NEATGAMEPLAY(n,5,100000,"Count32Stats",32);
        RunGames.NEATGAMEPLAY(n,5,100000,"Count64Stats",64);
        RunGames.NEATGAMEPLAY(n,5,100000,"Count128Stats",128);



    }
}

