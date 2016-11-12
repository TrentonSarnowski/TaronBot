package com.company.TaronBot.Harness;

import com.company.TaronBot.Game.Board;
import com.company.TaronBot.Game.Move;
import com.company.TaronBot.TaronBot;

import java.util.ArrayList;

/**
 * Created by sarnowskit on 10/30/2016.
 */
public class SelfBattle {

    public SelfBattle(String aiID1, String aiID2,int sideLength){
        Board board1=new Board(sideLength,new ArrayList<>(), true);
        Board board2=new Board(sideLength,new ArrayList<>(), false);
        //todo build AI
        Move V1=null;
        Move V2=null;
        int victor;
        //todo start moves
        while(true){

            if(null==(V1=board1.checkForVictory())){
                //passAI board
                //todo
            }else{
                victor=1;
                break;
            }
            if(null==(V2=board1.checkForVictory())){
                //passAI board
                //todo
            }else{
                victor=2;
                break;
            }
        }
    }
}
