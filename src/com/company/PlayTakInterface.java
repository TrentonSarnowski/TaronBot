package com.company;



import com.company.TaronBot.Game.Move;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by sarnowskit on 10/28/2016.
 */
public class PlayTakInterface {
    public PlayTakInterface(){
        URL playTak;
        try {
           playTak = new URL("www.playtak.com");
        }catch(MalformedURLException e){
            playTak=null;
        }

    }
    public boolean makeMove(String move){
        //code here from playtak communication
        return true;
    }
    public List<Move> getMoves(int game){
        //code here from playtak communication

        return null;
    }

}
