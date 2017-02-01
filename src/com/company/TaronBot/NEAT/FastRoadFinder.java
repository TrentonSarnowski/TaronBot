package com.company.TaronBot.NEAT;

import tech.deef.Tools.StaticGlobals;

/**
 * Created by sarnowskit on 1/31/2017.
 */
public class FastRoadFinder {

    public static boolean RoadCheck(int[] Map){
        int passToNext=0;

        if((Map[0]&-1)!=0&&(Map[Map.length]&-1)!=0){//check to see if either the first or last integers are empty as fast cal
            passToNext=-1;// set pass to next to be =-1 to demonstrate being next to the edge
            for (int i = 0; i <Map.length ; i++) {// loop through the diferent integers checking for roads
                if((passToNext&Map[i])!=0){//checks if the pass to next has anything incommon with the next element if not it should break
                    int connections=passToNext&Map[i]; // determines point at with pass to next is the same as this elemen
                    int connectionsR=connections;
                    while((Map[i]&connections)!=0&&(Map[i]&connectionsR)!=0){//checks if the connections are not empty
                        connections=Map[i]&(connections<<1); //finds left connections
                        connectionsR=Map[i]&(connectionsR>>>1); //find right connections >>> equals 0 feed
                        passToNext=passToNext|connections|connectionsR; // sets up pass to next adding any connections
                    }
                }else{
                    break;
                }
            }
        }
        return passToNext!=0;
    }

    /**
     * returns true if a map does not contain a road
     * may return false if a map does contain a road or does not contain a road
     * @param Map
     * @return
     */
    public static boolean NotRoadCheck(int[] Map){
        for (int i = 0; i <Map.length-1 ; i++) {
            if((Map[i]&Map[i+1])==0){
                return true;
            }
        }
        return true;
    }
    public static boolean RoadChecker(int[] b){
        int[] empty=new int[b.length];//makes empty array with extra space for a -1 value
        empty[0]=b[0];
        for (int i = 0; i <b.length*4 ; i++) {// iterates through the process length squared times to ensure road discovery
            for (int j = 0; j <empty.length ; j++) {// iterates performing transformations over list
                empty[j]=empty[j]|((empty[j]>>>1)&b[j])|((empty[j]<<1)&b[j]);//setting visted adjacent squares
                if(j<b.length-1) {
                    empty[j + 1] = empty[j + 1] | (empty[j] & b[j + 1]);//checking above and bellow for simularities
                }
                if(j>0) {
                    empty[j - 1] = empty[j - 1] | (empty[j] & b[j - 1]);
                }
            }

        }
        if(empty[empty.length-1]!=0){
            StaticGlobals.roadCount++;
        }
        return empty[empty.length-1]!=0;// will equal zero if empty
    }

    /**
     * returns an int describing the furthest road
     * @param b
     * @return
     */
    public static int RoadChecker(long[] b){
        long[] empty=new long[b.length];//makes empty array with extra space for a -1 value
        empty[0]=b[0];
        for (int i = 0; i <b.length*4 ; i++) {// iterates through the process length squared times to ensure road discovery
            for (int j = 0; j <empty.length ; j++) {// iterates performing transformations over list
                empty[j]=empty[j]|((empty[j]>>>1)&b[j])|((empty[j]<<1)&b[j]);//setting visted adjacent squares
                if(j<b.length-1) {
                    empty[j + 1] = empty[j + 1] | (empty[j] & b[j + 1]);//checking above and bellow for simularities
                }
                if(j>0) {
                    empty[j - 1] = empty[j - 1] | (empty[j] & b[j - 1]);
                }
            }

        }
        if(empty[empty.length-1]!=0){
            StaticGlobals.roadCount++;
        }
        int i=0;
        while(empty[i]!=0){
            i++;
        }
        return i;// will equal zero if empty
    }
}
