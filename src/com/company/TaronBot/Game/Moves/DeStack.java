package com.company.TaronBot.Game.Moves;

import com.company.TaronBot.Game.Move;

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
    public DeStack(String move){

    }
    public DeStack(int x, int y, List<Integer> left, int pickup, boolean verticalAxis, boolean positiveDirection){
        this.x=x;
        this.y=y;
        this.leftBehind=left;
        this.pickUpC=pickup;
        this.up=verticalAxis;
        this.positive =positiveDirection;

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
        if(up&& positive) {
            if(leftBehind.size()+y>map.length){
                return null;
                //edge check
            }
            //wall/cap check
            for (int i = 0; i < leftBehind.size(); i++) {
                if(Math.abs(map[x][y+i].get(map[x][y+i].size()))==3){
                    return null;
                }else if(Math.abs(map[x][y+i].get(map[x][y+i].size()))==2){
                    if(Math.abs(pickUp.get(pickUp.size()))==3 && leftBehind.get(leftBehind.size())==1){
                        map[x][y+i].set(map[x][y+i].size(),map[x][y+i].get(map[x][y+i].size())/2);
                    }else{
                        return null;
                    }
                }else{

                }
            }
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
            if(y-leftBehind.size()<0){
                return map;
                //edge check
            }
            //wall/cap check
            for (int i = 0; i < leftBehind.size(); i++) {
                if(Math.abs(map[x][y-i].get(map[x][y-i].size()))==3){
                    return map;
                }else if(Math.abs(map[x][y-i].get(map[x][y-i].size()))==2){
                    if(pickUp.get(pickUp.size())==3 && leftBehind.get(leftBehind.size())==1){

                    }else{
                        return map;
                    }
                }else{

                }
            }
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

            if(leftBehind.size()+x>map.length){
                return map;
                //edge check
            }
            //wall/cap check
            for (int i = 0; i < leftBehind.size(); i++) {
                if(Math.abs(map[x+i][y].get(map[x+i][y].size()))==3){
                    return map;
                }else if(Math.abs(map[x+i][y].get(map[x+i][y].size()))==2){
                    if(pickUp.get(pickUp.size())==3 && leftBehind.get(leftBehind.size())==1){

                    }else{
                        return map;
                    }
                }else{

                }
            }
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
            if(x-leftBehind.size()<0){
                return map;
                //edge check
            }
            //wall/cap check
            for (int i = 0; i < leftBehind.size(); i++) {
                if(Math.abs(map[x-i][y].get(map[x-i][y].size()))==3){
                    return map;
                }else if(Math.abs(map[x-i][y].get(map[x-i][y].size()))==2){
                    if(pickUp.get(pickUp.size())==3 && leftBehind.get(leftBehind.size())==1){

                    }else{
                        return map;
                    }
                }else{

                }
            }
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
}
