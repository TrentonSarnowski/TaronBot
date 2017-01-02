package com.company.TaronBot.Game;

import com.company.TaronBot.Network.OrderedTakNetwork;
import com.company.TaronBot.Network.TakNetwork;
import org.apache.commons.math3.ml.neuralnet.Network;
import tech.deef.Tools.StaticGlobals;

import java.util.*;

/**
 * Created by sarnowskit on 12/28/2016.
 */
public class RunGames {
    List<Integer> player1;
    List<Integer> player2;
    ArrayList<TakNetwork> networks;
    int winNoWeight[];
    int winWeighted[];
    int winRoadOnly[];
    int winOneTwoWeight[];
    int legalMoveDepth[];
    int winLossWeighted[];
    int winNoWeightPage[][];
    int winWeightedPage[][];
    int winRoadOnlyPage[][];
    int winLossWeightedPage[][];
    int winLossNoWeightPage[][];
    int winOneTwoWeightPage[][];

    private enum victoryType {
        WIN_NO_WEIGHT,//Wins all qualify as 1
        WIN_WEIGHTED,//Wins based on flat count difference or sideLength for road win
        WIN_ROAD_ONLY,//Wins based solly on road wins
        WIN_ONE_TWO_WEIGHT,//Wins with one for a flat win and two for a road win
        LEGAL_MOVE_DEPTH,//Average legal move depth, reverse ordered
        WIN_LOSS_WEIGHTED,//Wins are subtracted from losses for a weight
        PAGE_WEIGHTED_WIN_NO_WEIGHT,//Page weighted is based on giving each page a single vote and the above qualifiers
        PAGE_WEIGHTED_WIN_WEIGHTED,
        PAGE_WEIGHTED_WIN_ROAD_ONLY,
        PAGE_WEIGHTED_WIN_LOSS_WEIGHTED,
        PAGE_WEIGHTED_WIN_LOSS_NO_WEIGHT,//May not order properly
        // as giving some one a lower rate in the weighting system
        // means that winning against a loser is worth less
        PAGE_WEIGHTED_WIN_ONE_TWO_WEIGHT


    }

    int count;

    public RunGames(ArrayList<TakNetwork> networks) {
        this.networks = new ArrayList<>();
        for (TakNetwork n : networks) {
            this.networks.add(n);
        }
        player1 = new LinkedList<>();
        player2 = new LinkedList<>();
        int c = networks.size();

        winNoWeight = new int[c];
        winWeighted = new int[c];
        winRoadOnly = new int[c];
        winOneTwoWeight = new int[c];
        legalMoveDepth = new int[c];
        winLossWeighted = new int[c];
        winNoWeightPage = new int[c][c];
        winWeightedPage = new int[c][c];
        winRoadOnlyPage = new int[c][c];
        winLossWeightedPage = new int[c][c];
        winLossNoWeightPage = new int[c][c];
        winOneTwoWeightPage = new int[c][c];
    }

    public boolean addGame(int one, int two) {
        if (one >= 0 && one < networks.size() && two >= 0 && two < networks.size()) {
            player1.add(one);
            player2.add(two);
            return true;
        }
        return false;
    }

    /**
     * runs games that have been set, Should be called once, will wait until all threads have finished
     *
     * @param cores
     */
    public void playGamesSetThreadsBlocks(int cores) {

        List<Thread> threads = playGamesSetThreads(cores);
        for (Thread t: threads) {

            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * returns the threads to run currently selected
     * @param cores
     * @return
     */
    public List<Thread> playGamesSetThreads(int cores) {
        Iterator<Integer> player1 = this.player1.iterator();
        Iterator<Integer> player2 = this.player2.iterator();
        List<Thread> threads=new ArrayList<>();
        for (int i = 0; i <cores ; i++) {


            Thread t = new Thread() {

                @Override
                public void run() {
                    while (player1.hasNext() && player2.hasNext()) {
                        TakNetwork one;
                        TakNetwork two;
                        int net1Num;
                        int net2Num;
                        synchronized (player1) {
                            net1Num = player1.next();
                            net2Num = player2.next();
                            one = networks.get(net1Num);
                            two = networks.get(net2Num);
                        }

                        count++;
                        if (one.getWidth() == two.getWidth()) {
                            int result = Board.playGame(one, two, one.getWidth());
                            addValuesForSort(one, two, net1Num, net2Num, result);


                        }
                        count--;

                    }
                    notify();
                }


            };
            threads.add(t);
            t.start();
        }
        return threads;
    }

    /**
     * used to sort class with weights we have
     */
    public static class sort {
        public double weight = 0;
        public TakNetwork item = null;

        public sort(double weight, TakNetwork item) {
            this.weight = weight;
            this.item = item;
        }
    }

    /**
     * returns the sorted list of tak networks based on victory type
     *
     * @param victoryType
     * @return
     */
    public List<TakNetwork> GetSortedNetworks(victoryType victoryType) {
        List<TakNetwork> returnValue = new ArrayList<>();
        List<sort> sortList = new ArrayList<>();
        switch (victoryType) {
            case WIN_LOSS_WEIGHTED:
                for (int i = 0; i < networks.size(); i++) {
                    sortList.add(new sort(winLossWeighted[i], networks.get(i)));
                }
                Collections.sort(sortList, (o1, o2) -> {


                    if (o1 == null || o2 == null) {
                        return 0;
                    }

                    if (o1.weight > o2.weight) {
                        return 1;
                    } else if (o1.weight == o2.weight) {
                        return 0;
                    } else {
                        return -1;
                    }

                });
                for (int i = 0; i < returnValue.size(); i++) {
                    //System.out.println(i+": Network weight: "+sortlist.get(i).weight);
                    returnValue.set(i, sortList.get(i).item);
                }
                break;
            case WIN_NO_WEIGHT:
                for (int i = 0; i < networks.size(); i++) {
                    sortList.add(new sort(winNoWeight[i], networks.get(i)));
                }
                Collections.sort(sortList, (o1, o2) -> {


                    if (o1 == null || o2 == null) {
                        return 0;
                    }

                    if (o1.weight > o2.weight) {
                        return 1;
                    } else if (o1.weight == o2.weight) {
                        return 0;
                    } else {
                        return -1;
                    }

                });
                for (int i = 0; i < returnValue.size(); i++) {
                    //System.out.println(i+": Network weight: "+sortlist.get(i).weight);
                    returnValue.set(i, sortList.get(i).item);
                }
                break;
            case WIN_ONE_TWO_WEIGHT:
                for (int i = 0; i < networks.size(); i++) {
                    sortList.add(new sort(winOneTwoWeight[i], networks.get(i)));
                }
                Collections.sort(sortList, (o1, o2) -> {


                    if (o1 == null || o2 == null) {
                        return 0;
                    }

                    if (o1.weight > o2.weight) {
                        return 1;
                    } else if (o1.weight == o2.weight) {
                        return 0;
                    } else {
                        return -1;
                    }

                });
                for (int i = 0; i < returnValue.size(); i++) {
                    //System.out.println(i+": Network weight: "+sortlist.get(i).weight);
                    returnValue.set(i, sortList.get(i).item);
                }
                break;
            case WIN_ROAD_ONLY:
                for (int i = 0; i < networks.size(); i++) {
                    sortList.add(new sort(winRoadOnly[i], networks.get(i)));
                }
                Collections.sort(sortList, (o1, o2) -> {


                    if (o1 == null || o2 == null) {
                        return 0;
                    }

                    if (o1.weight > o2.weight) {
                        return 1;
                    } else if (o1.weight == o2.weight) {
                        return 0;
                    } else {
                        return -1;
                    }

                });
                for (int i = 0; i < returnValue.size(); i++) {
                    //System.out.println(i+": Network weight: "+sortlist.get(i).weight);
                    returnValue.set(i, sortList.get(i).item);
                }
                break;
            case WIN_WEIGHTED:
                for (int i = 0; i < networks.size(); i++) {
                    sortList.add(new sort(winWeighted[i], networks.get(i)));
                }
                Collections.sort(sortList, (o1, o2) -> {


                    if (o1 == null || o2 == null) {
                        return 0;
                    }

                    if (o1.weight > o2.weight) {
                        return 1;
                    } else if (o1.weight == o2.weight) {
                        return 0;
                    } else {
                        return -1;
                    }

                });
                for (int i = 0; i < returnValue.size(); i++) {
                    //System.out.println(i+": Network weight: "+sortlist.get(i).weight);
                    returnValue.set(i, sortList.get(i).item);
                }
                break;
            case PAGE_WEIGHTED_WIN_LOSS_NO_WEIGHT:
                returnValue = OrderedTakNetwork.PageRankOrdering(networks, winLossNoWeightPage);
                break;
            case PAGE_WEIGHTED_WIN_LOSS_WEIGHTED:
                returnValue = OrderedTakNetwork.PageRankOrdering(networks, winLossWeightedPage);

                break;
            case PAGE_WEIGHTED_WIN_NO_WEIGHT:
                returnValue = OrderedTakNetwork.PageRankOrdering(networks, winNoWeightPage);
                break;
            case PAGE_WEIGHTED_WIN_ONE_TWO_WEIGHT:
                returnValue = OrderedTakNetwork.PageRankOrdering(networks, winOneTwoWeightPage);

                break;
            case PAGE_WEIGHTED_WIN_ROAD_ONLY:
                returnValue = OrderedTakNetwork.PageRankOrdering(networks, winRoadOnlyPage);
                break;
            case PAGE_WEIGHTED_WIN_WEIGHTED:
                returnValue = OrderedTakNetwork.PageRankOrdering(networks, winWeightedPage);
                break;
            case LEGAL_MOVE_DEPTH:
                return networks;

            default:
                return networks;
        }
        return returnValue;
    }

    /**
     * adds in the values for sorting, compartamentalizes the adding
     *
     * @param one
     * @param two
     * @param net1Num
     * @param net2Num
     * @param result
     */
    public void addValuesForSort(TakNetwork one, TakNetwork two, int net1Num, int net2Num, int result) {
        //todo legalMoveDepth;

        if (result > 0) {
            one.addWins();
            two.addLosses();
            winNoWeight[net1Num]++;
            winWeighted[net1Num] += result;
            if (result >= one.getWidth()) {
                winRoadOnly[net1Num]++;
                winOneTwoWeight[net1Num] += 2;
                winRoadOnlyPage[net2Num][net1Num]++;
                winOneTwoWeightPage[net2Num][net1Num] += 2;
            } else {
                winOneTwoWeightPage[net2Num][net1Num]++;

                winOneTwoWeight[net1Num]++;
            }
            winNoWeightPage[net2Num][net1Num]++;
            winWeightedPage[net2Num][net1Num] += result;
            winLossNoWeightPage[net1Num][net2Num]--;
            winLossNoWeightPage[net2Num][net1Num]++;


        } else if (result < 0) {
            two.addWins();
            one.addLosses();
            winNoWeight[net2Num]++;
            winWeighted[net1Num] -= result;
            if (result <= one.getWidth()) {
                winRoadOnly[net2Num]++;
                winOneTwoWeight[net1Num] += 2;
                winRoadOnlyPage[net1Num][net2Num]++;

                winOneTwoWeightPage[net1Num][net2Num] += 2;
            } else {
                winOneTwoWeightPage[net1Num][net2Num]++;
                winOneTwoWeight[net1Num]++;
            }
            winLossNoWeightPage[net1Num][net2Num]++;
            winLossNoWeightPage[net2Num][net1Num]--;

            winNoWeightPage[net1Num][net2Num]++;
            winWeightedPage[net1Num][net2Num] -= result;

        }
        winLossWeighted[net1Num] += result;
        winLossWeighted[net2Num] -= result;
        winLossWeightedPage[net2Num][net1Num] += result;
        winLossWeightedPage[net1Num][net2Num] +=result;
    }
}
