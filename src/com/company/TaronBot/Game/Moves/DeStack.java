package com.company.TaronBot.Game.Moves;

import com.company.TaronBot.Game.Move;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by sarnowskit on 10/28/2016.
 */
public class DeStack implements Move {

    List<Integer> leftBehind;// should not contain zero
    Integer pickUpC;//the amount initially taken of the stack
    boolean up; // if true goes along the y axis, if false along the x
    boolean positive;// positive goes up, else goes down
    int x;//inside of bounds
    int y;//inside of bounds
    int xend;
    int yend;
    double weight;
    public double getWeight(){
        return weight;
    }
    public DeStack(String move){

    }
    public static DeStack DeStack(int x, int y, Integer[] left, int pickup, boolean verticalAxis, boolean positiveDirection,double weight) {
        ArrayList<Integer> leftbehind=new ArrayList<>();
        for (Integer i:left) {
            leftbehind.add(i);
        }
        DeStack temp=new DeStack(x,y,leftbehind, pickup,verticalAxis,positiveDirection,weight);
        return temp;
    }
    public DeStack(int x, int y, List<Integer> left, int pickup, boolean verticalAxis, boolean positiveDirection,double weight){
        this.weight=weight;
        this.x=x;
        this.y=y;
        this.leftBehind=left;
        this.pickUpC=pickup;
        this.up=verticalAxis;
        this.positive =positiveDirection;
        int dif= left.size();
        if(verticalAxis&&positiveDirection){
            xend=x;
            yend=y+dif;
        }else if(verticalAxis){
            xend=x;
            yend=y-dif;
        }else if(positiveDirection){
            xend=x+dif;
            yend=y;
        }else{
            xend=x-dif;
            yend=y;
        }

    }
    public String toPlayTakString(){
        String ret="M ";
        switch (x){
            case 0:
                ret+="a";
                break;
            case 1:
                ret+="b";
                break;
            case 2:
                ret+="c";
                break;
            case 3:
                ret+="d";
                break;
            case 4:
                ret+="e";
                break;
            case 5:
                ret+="f";
                break;
            case 6:
                ret+="g";
                break;
            case 7:
                ret+="h";
                break;
            default:
                return "";
        }
        ret+=(y+1)+" ";
        switch (xend){
            case 0:
                ret+="a";
                break;
            case 1:
                ret+="b";
                break;
            case 2:
                ret+="c";
                break;
            case 3:
                ret+="d";
                break;
            case 4:
                ret+="e";
                break;
            case 5:
                ret+="f";
                break;
            case 6:
                ret+="g";
                break;
            case 7:
                ret+="h";
                break;
            default:
                return "";
        }
        ret+=(yend+1);
        for (Integer e: leftBehind) {
            ret+=" "+e;
        }
        return ret;
    }
    @Override
    public String toString() {
        String ret=""+pickUpC;
        switch (x){
            case 0:
                ret+="a";
                break;
            case 1:
                ret+="b";
                break;
            case 2:
                ret+="c";
                break;
            case 3:
                ret+="d";
                break;
            case 4:
                ret+="e";
                break;
            case 5:
                ret+="f";
                break;
            case 6:
                ret+="g";
                break;
            case 7:
                ret+="h";
                break;
            default:
                return "";
        }
        ret+=y;
        if(up){
            if(positive){
                ret+="+";
            }else{
                ret+="-";
            }
        }else{
            if(positive){
                ret+=">";
            }else{
                ret+="<";
            }
        }
        for (Integer i:
             leftBehind) {
            ret+=i;
        }
        return null;
    }

    @Override
    //Map should be a square array
    public List<Integer>[][] performMove(List<Integer>[][] map, boolean control) {
        List<Integer> pickUp=map[x][y];
        int sum=pickUp.size()-pickUpC;
        if(!checkFeasible(map)){
            return null;
        }
        if(up&& positive) {

            for (int i = 0; i <leftBehind.size() ; i++) {
                for (int j = 0; j <leftBehind.get(i) ; j++) {
                    map[x][y+i].add(pickUp.get(sum));
                    sum++;
                }
            }
            for (int i = 0; i < pickUpC; i++) {
                map[x][y].remove(map[x][y].size()-i);
            }
        }else if(up&&!positive){

            for (int i = 0; i <leftBehind.size() ; i++) {
                for (int j = 0; j <leftBehind.get(i) ; j++) {
                    map[x][y-i].add(pickUp.get(sum));
                    sum++;
                }
            }
            for (int i = 0; i < pickUpC; i++) {
                map[x][y].remove(map[x][y].size()-i);
            }
        }else if(!up && positive){

            for (int i = 0; i <leftBehind.size() ; i++) {
                for (int j = 0; j <leftBehind.get(i) ; j++) {
                    map[x+i][y].add(pickUp.get(sum));
                    sum++;
                }
            }
            for (int i = 0; i < pickUpC; i++) {
                map[x][y].remove(map[x][y].size()-i);
            }

        }else if(!up && !positive){

            for (int i = 0; i <leftBehind.size() ; i++) {
                for (int j = 0; j <leftBehind.get(i) ; j++) {
                    map[x-i][y].add(pickUp.get(sum));
                    sum++;
                }
            }
            for (int i = 0; i < pickUpC; i++) {
                map[x][y].remove(map[x][y].size()-i);
            }
        }


        return map;
    }
    public boolean checkFeasible(List<Integer>[][] map){
        List<Integer> pickUp=map[x][y];
        int sum=pickUp.size()-pickUpC;
        if(up&& positive) {
            if(leftBehind.size()+y>map.length){
                return false;
                //edge check
            }
            //wall/cap check
            for (int i = 0; i < leftBehind.size(); i++) {
                if(Math.abs(map[x][y+i].get(map[x][y+i].size()))==3){
                    return false;
                }else if(Math.abs(map[x][y+i].get(map[x][y+i].size()))==2){
                    if(Math.abs(pickUp.get(pickUp.size()))==3 && leftBehind.get(leftBehind.size())==1){
                        map[x][y+i].set(map[x][y+i].size(),map[x][y+i].get(map[x][y+i].size())/2);
                    }else{
                        return false;
                    }
                }else{

                }
            }

        }else if(up&&!positive){
            if(y-leftBehind.size()<0){
                return false;
                //edge check
            }
            //wall/cap check
            for (int i = 0; i < leftBehind.size(); i++) {
                if(Math.abs(map[x][y-i].get(map[x][y-i].size()))==3){
                    return false;
                }else if(Math.abs(map[x][y-i].get(map[x][y-i].size()))==2){
                    if(pickUp.get(pickUp.size())==3 && leftBehind.get(leftBehind.size())==1){

                    }else{
                        return false;
                    }
                }else{

                }
            }

        }else if(!up && positive){

            if(leftBehind.size()+x>map.length){
                return false;
                //edge check
            }
            //wall/cap check
            for (int i = 0; i < leftBehind.size(); i++) {
                if(Math.abs(map[x+i][y].get(map[x+i][y].size()))==3){
                    return false;
                }else if(Math.abs(map[x+i][y].get(map[x+i][y].size()))==2){
                    if(pickUp.get(pickUp.size())==3 && leftBehind.get(leftBehind.size())==1){

                    }else{
                        return false;
                    }
                }else{

                }
            }


        }else if(!up && !positive){
            if(x-leftBehind.size()<0){
                return false;
                //edge check
            }
            //wall/cap check
            for (int i = 0; i < leftBehind.size(); i++) {
                if(Math.abs(map[x-i][y].get(map[x-i][y].size()))==3){
                    return false;
                }else if(Math.abs(map[x-i][y].get(map[x-i][y].size()))==2){
                    if(pickUp.get(pickUp.size())==3 && leftBehind.get(leftBehind.size())==1){

                    }else{
                        return false;
                    }
                }else{

                }
            }
        }

        return true;
    }

}
