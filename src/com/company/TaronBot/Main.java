package com.company.TaronBot;

import tech.deef.Tools.StaticGlobals;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {
	public static void main(String[] args){
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
}

