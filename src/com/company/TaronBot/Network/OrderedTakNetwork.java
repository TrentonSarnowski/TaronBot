package com.company.TaronBot.Network;

import java.util.List;
import java.util.*;
import java.io.*;

/**
 * Created by sarnowskit on 12/14/2016.
 */
public class OrderedTakNetwork {
    private static int path[][] = new int[10][10];
    private static double pagerank[] = new double[10];

    public static class sort {
        public double weight = 0;
        public TakNetwork item = null;

        public sort(double weight, TakNetwork item) {
            this.weight = weight;
            this.item = item;
        }
    }

    static List<TakNetwork> PageRankOrdering(List<TakNetwork> networks, int[][] weights) {
        //System.err.println(" into ordering");
        pagerank = new double[networks.size()];
        path = weights;
        calc(networks.size());
        ArrayList<sort> sortlist = new ArrayList<>();
        for (int i = 0; i < pagerank.length; i++) {
            sortlist.add(new sort(pagerank[i], networks.get(i)));

        }
        Collections.sort(sortlist, (o1, o2) -> {


            if (o1 == null || o2 == null) {
                return 0;
            }

            if (((sort)o1).weight > ((sort)o2).weight) {
                return 1;
            } else if(((sort)o1).weight == ((sort)o2).weight ){
                return 0;
            }else {
                return -1;
            }

        });

        for (int i = 0; i < networks.size(); i++) {
            //System.out.println(i+": Network weight: "+sortlist.get(i).weight);
            networks.set(i, sortlist.get(i).item);
        }
        //System.err.println(" into ordering");

        return networks;
    }


    /**
     * code found online at
     * http://codispatch.blogspot.com/2015/12/java-program-implement-google-page-rank-algorithm.html
     *
     * @param totalNodes
     */

    static public void calc(double totalNodes) {

        double InitialPageRank;
        double OutgoingLinks = 0;
        double DampingFactor = .99;
        double TempPageRank[] = new double[pagerank.length];

        int ExternalNodeNumber;
        int InternalNodeNumber;
        int k = 0; // For Traversing
        int ITERATION_STEP = 1;

        InitialPageRank = 1 / totalNodes;
        //System.out.printf(" Total Number of Nodes :" + totalNodes + "\t Initial PageRank  of All Nodes :" + InitialPageRank + "\n");

// 0th ITERATION  _ OR _ INITIALIZATION PHASE
        for (k = 0; k < totalNodes; k++) {
            pagerank[k] = InitialPageRank;
        }

        //System.out.printf("\n Initial PageRank Values , 0th Step \n");
        for (k = 0; k < totalNodes; k++) {
            // System.out.printf(" Page Rank of " + k + " is :\t" + pagerank[k] + "\n");
        }

        while (ITERATION_STEP <= 2) // Iterations
        {
            // Store the PageRank for All Nodes in Temporary Array
            for (k = 0; k < totalNodes; k++) {
                TempPageRank[k] = pagerank[k];
                pagerank[k] = 0;
            }

            for (InternalNodeNumber = 0; InternalNodeNumber < totalNodes; InternalNodeNumber++) {
                for (ExternalNodeNumber = 0; ExternalNodeNumber < totalNodes; ExternalNodeNumber++) {
                    if (path[ExternalNodeNumber][InternalNodeNumber] == 1) {
                        k = 0;
                        OutgoingLinks = 0;  // Count the Number of Outgoing Links for each ExternalNodeNumber
                        while (k < totalNodes) {
                            if (path[ExternalNodeNumber][k] == 1) {
                                OutgoingLinks = OutgoingLinks + 1; // Counter for Outgoing Links
                            }
                            k = k + 1;
                        }
                        // Calculate PageRank
                        pagerank[InternalNodeNumber] += TempPageRank[ExternalNodeNumber] * (1 / OutgoingLinks);
                    }
                }
            }

            //System.out.printf("\n After " + ITERATION_STEP + "th Step \n");

            for (k = 0; k <= totalNodes; k++)
                //  System.out.printf(" Page Rank of " + k + " is :\t" + pagerank[k] + "\n");

                ITERATION_STEP = ITERATION_STEP + 1;
        }

// Add the Damping Factor to PageRank
        for (k = 0; k < totalNodes; k++) {
            pagerank[k] = (1 - DampingFactor) + DampingFactor * pagerank[k];
        }

// Display PageRank
        //System.out.printf("\n Final Page Rank : \n");
        for (k = 0; k < totalNodes; k++) {
            //  System.out.printf(" Page Rank of " + k + " is :\t" + pagerank[k] + "\n");
        }

    }


}



