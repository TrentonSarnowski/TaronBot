package com.company;

import static tech.deef.Tools.Tools.PrintColor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.company.TaronBot.ControllClass;
import com.company.TaronBot.Game.Board;
import com.company.TaronBot.Game.Move;
import com.company.TaronBot.Network.ComputeGeneration;
import com.company.TaronBot.Network.MateNetworks;
import com.company.TaronBot.Network.TakNetwork;

import tech.deef.Tools.StaticGlobals;

public class TestingMain {

    private static final boolean LOGGING_ENABLED = StaticGlobals.LOGGING_ENABLED; //work around to avoid having to rename 20+ lines.
    private static int numPerGeneration = 3;

    /**
     * main testing method. runs through all testing algorithims for
     * funcionalitly.
     *
     * @param args
     */
    public static void main(String[] args) {
        //networkGroupMutatorsTest();
        startControllThread();

        // NetTesting();
        // ThreadedTesting(20);
        // ThreadTimingTesting();
        // ThreadedTesting(1000);

        // ComputeGenerationTesting(15);
        //for (int i = 0; i < 16; i++) {
            //TestGnerationalGrowth(32, 256, 8, 5);

        /*
        System.out.println();

        //*/
        //}
        
        /*
        TakNetwork network1;
		TakNetwork network2;
		network1=loadTesting("C:\\Users\\sarnowskit\\Downloads\\TaronBot\\networks\\TestGnerationalGrowth\\output\\Network" + 0 + ".takNetwork");
		network2=loadTesting("C:\\Users\\sarnowskit\\Downloads\\TaronBot\\networks\\TestGnerationalGrowth\\output\\Network" + 1 + ".takNetwork");
		Board.playGame(network1,network2,8);
		Board.playGame(network2,network1,8);
		//*/

		/*
		List<TakNetwork> newNetworks=new ArrayList<>();
		List<TakNetwork> oldNetworks=new ArrayList<>();
		for (int i = 0; i <10 ; i++) {
			network2=loadTesting("C:\\Users\\sarnowskit\\Desktop\\Network Storage\\64Size64genRankedWinLoss"+"\\Network"+i+".takNetwork");
			network2.setWins(0);
			oldNetworks.add(network2);

		}
		for (int i = 0; i <10 ; i++) {

			network1=loadTesting("C:\\Users\\sarnowskit\\Downloads\\TaronBot\\networks\\TestGnerationalGrowth\\output\\Network" + i + ".takNetwork");
			network1.setWins(0);
			newNetworks.add(network1);
		}
		int winner=0;
		for (int i = 0; i <10 ; i++) {
			network1=newNetworks.get(i);
			for (int j = 0; j <10 ; j++) {
				network2= oldNetworks.get(j);
				winner=Board.playGame(network1,network2,8);
				if(winner>0){
					network1.setWins(network1.getWins()+1);
					network2.setLosses(network2.getLosses()+1);

				}else{
					network2.setWins(network2.getWins()+1);
					network1.setLosses(network1.getLosses()+1);
				}
				winner=Board.playGame(network2,network1,8);
				if(winner<0){
					network1.setWins(network1.getWins()+1);
					network2.setLosses(network2.getLosses()+1);

				}else{
					network2.setWins(network2.getWins()+1);
					network1.setLosses(network1.getLosses()+1);
				}

			}
		}
		Collections.sort(newNetworks,new Comparator<TakNetwork>() {
					public int compare(TakNetwork m1, TakNetwork m2) {
						int out = 0;
						if ((double) m1.getWins() < (double) m2.getWins()) {
							// 1 is winner
							return 1;
						}
						if ((double) m1.getWins() > (double) m2.getWins()) {
							// 2 is winner
							return -1;
						}
						return 0;

					}
				}
			);
		Collections.sort(oldNetworks,new Comparator<TakNetwork>() {
					public int compare(TakNetwork m1, TakNetwork m2) {
						int out = 0;
						if ((double) m1.getWins() < (double) m2.getWins()) {
							// 1 is winner
							return 1;
						}
						if ((double) m1.getWins() > (double) m2.getWins()) {
							// 2 is winner
							return -1;
						}
						return 0;

					}
				}
		);
		System.out.println(" old set");
		int oldwins=0;
		for (int i = 0; i <10 ; i++) {
			System.out.println(oldNetworks.get(i).toString()+" wins:" +oldNetworks.get(i).getWins()+" loss: "+oldNetworks.get(i).getLosses());
			oldwins+=oldNetworks.get(i).getWins();
		}
		System.out.println();
		System.out.println(" new set");
		int newwins=0;
		for (int i = 0; i <10 ; i++) {
			System.out.println(newNetworks.get(i).toString()+" wins:" +newNetworks.get(i).getWins()+" loss: "+newNetworks.get(i).getLosses());
			newwins+=newNetworks.get(i).getWins();
		}

		System.out.println();
		System.out.println("old: "+oldwins+", new: "+newwins);

		//*/
		/*System.out.print(Board.playGame(
				loadTesting(
						"C:\\Users\\sarnowskit\\Downloads\\TaronBot\\networks\\TestGnerationalGrowth\\output\\Network2.takNetwork"),
				loadTesting("C:\\Users\\sarnowskit\\Desktop\\64 gened networks\\Network0.takNetwork"), 8));*/
        //TestGnerationalGrowth(64,100,8);
        //TestGnerationalGrowth(64,1000,8,5);

        // TestSingleGame();
        // TestGeneration();
        // networkGenerationCalculationTest();
        // saveTesting();
        // loadTesting();
        // networkmutatorsTest();
        // testing();

    }

    private static void startControllThread() {
        Thread t = new Thread() {

            @Override
            public void run() {
                ControllClass.StartControll();

            }

        };
        t.start();
    }

    public static void TestGnerationalGrowth(int generationSize, int generations, int cores, int dimns) {


        Logger logger = Logger.getLogger("MyLog");

        if (LOGGING_ENABLED) {
            FileHandler fh;

            try {

                // This block configure the logger with handler and formatter
                // Time time = new Time(System.currentTimeMillis());

                Date date = new Date(System.currentTimeMillis());
                DateFormat formattert = new SimpleDateFormat("MM-dd-YYYY HH_mm_ss");
                String dateFormatted = formattert.format(date);
                String location = ("logfiles\\");

                new File(location).mkdirs();
                System.out.println(dateFormatted);
                fh = new FileHandler(location + dateFormatted + " output.log");
                FileHandler f = new FileHandler();

                logger.addHandler(fh);
                SimpleFormatter formatter = new SimpleFormatter();
                fh.setFormatter(formatter);

                // the following statement is used to log any messages
                // logger.info("My first log");

            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            logger.setLevel(Level.ALL);
            // logger.info("Hi How r u?");
            // logger.config("Hi How r u?");
            // logger.config("msg2");

        }


        Random random = new Random();
        ArrayList<TakNetwork> networks = new ArrayList<>();
        NumberFormat formatter = new DecimalFormat("#0.0000");

        // gen networks


        int RandomNunber = 0;
        for (int j = 0; j < generationSize; j++) {
            TakNetwork testNetwork;
            if (StaticGlobals.LOAD_FROM_LAST_RUN) {
                testNetwork = loadTesting("networks\\TestGnerationalGrowth\\output\\Network"+ dimns+"x"+dimns+ j + ".takNetwork");
                testNetwork.setWins(0);
                testNetwork.setLosses(0);
            } else {

                testNetwork = new TakNetwork(dimns + 1, dimns, dimns, 16,0,j);
                RandomNunber = random.nextInt();
                Random rand = new Random(RandomNunber);
                testNetwork.randomize(rand);
            }

            // System.out.println((testNetwork.toString()));

            networks.add(testNetwork);

            // all 100 networks generate
        }
        System.out.println("Base Generated");
        //*/
        if (LOGGING_ENABLED) {
            logger.config("Beginning Generation");
        }
        try {
            for (int i = 0; i < generations; i++) {

                if (LOGGING_ENABLED) {
                    logger.config("Beginning Generation: " + i);
                }

                if (StaticGlobals.SAVE_NETWORKS_OUT_AND_EXIT || i % 100 == 99) {

                    String output = "networks\\FORCEEXIT\\TestGnerationalGrowth\\output";

                    if (LOGGING_ENABLED) {
                        logger.config("SavingNetworks to " + output);
                    }

                    System.out.println("Saving Networks");

                    new File(output).mkdirs();
                    for (int s = 0; s < networks.size(); s++) {

                        FileOutputStream fout;
                        try {
                            fout = new FileOutputStream(output + "\\GEN" + i + "Network" + s + ".takNetwork");
                            ObjectOutputStream oos = new ObjectOutputStream(fout);
                            oos.writeObject(networks.get(s));
                            oos.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (LOGGING_ENABLED) {
                            logger.config("Saved Network " + s);
                        }
                    }

                    System.out.println("Networks Saved");
                    if (StaticGlobals.SAVE_NETWORKS_OUT_AND_EXIT) {
                        return;
                    }
                }


                System.out.println("\n\nGENERATION: " + i + "\n");
                if (LOGGING_ENABLED) {
                    logger.config("Calculating Generation" + i);
                }

                ComputeGeneration.compute(networks, cores, logger);

                if (LOGGING_ENABLED) {
                    logger.config("Sorting Generation " + i);
                }
                if(true){

                }else {
                    Collections.sort(networks, new Comparator<TakNetwork>() {
                        public int compare(TakNetwork m1, TakNetwork m2) {
                            int out = 0;
                            if ((double) m1.getWins() < (double) m2.getWins()) {
                                // 1 is winner
                                return 1;
                            }
                            if ((double) m1.getWins() > (double) m2.getWins()) {
                                // 2 is winner
                                return -1;
                            }
                            return 0;

                        }
                    });
                }

                if (StaticGlobals.PRINT_NETWORK_STATS) {
                    StringBuffer t = new StringBuffer();
                    for (int j = 0; j < networks.size(); j++) {


                        t.append("NET: "
                                + networks.get(j).toString().substring((networks.get(j).toString().indexOf("@") + 1), networks.get(j).toString().length())
                                + "\t   Generation: "+networks.get(j).getGeneration()
                                + "\t   Wins: " + networks.get(j).getWins() + "\n"); //newline not appended in logging.
                    }
                    System.out.println(t.toString());

                    if (LOGGING_ENABLED) {
                        logger.config(t.toString());
                    }
                }

                ArrayList<TakNetwork> remove = new ArrayList<TakNetwork>();


                if (LOGGING_ENABLED) {
                    logger.config("Beginning Mutation/network addition of generation  " + i);
                }
                try {
                    remove.clear();
                    for (int j = 0; j < (generationSize); j++) {

                            if(random.nextDouble()>Math.cos(j * Math.PI / 2.0 / generationSize)) {
                                remove.add(networks.get(j));
                            }

                    }

                    int temp = 1;
                    while (remove.size() < generationSize / 8) {
                        remove.add(networks.get(networks.size() - temp));
                        temp++;
                    }

                } catch (IndexOutOfBoundsException e) {
                    //System.out.println("ExpectedIndexOutOfBoudsError");
                }

                if (LOGGING_ENABLED) {
                    logger.config("Removing " + remove.size() + " networks");
                }

                for (TakNetwork net : remove) {
                    networks.remove(net);
                }

                if (StaticGlobals.GENERATIONAL_NOTIFIERS) {
                    System.out.println("Starting new network generation");
                }
                if (LOGGING_ENABLED) {
                    logger.config("Starting new network generation");
                }
                networks.addAll(MateNetworks.GroupMateNetworks(networks, random, generationSize - networks.size(), i));


                if (LOGGING_ENABLED) {
                    logger.config("Networks Generated.");
                }

                // direct dupe top net

			//for (int j=0;networks.size() < generationSize;j++) {
//					int toDupe = (int) Math.floor(Math.cos(random.nextDouble() * Math.PI / 2.0) * networks.size());
//					networks.add(networks.get(toDupe).returnAnotherMutatedNetwork(random, 0.00001,j));
					//System.out.println("Duped: " + toDupe);
				//}

                //networks.set(generationSize, networks.get(0).returnAnotherMutatedNetwork(random, 0.00001));


            }

            String output = "networks\\TestGnerationalGrowth\\output";

            if (LOGGING_ENABLED) {
                logger.config("Learning complete!");
                logger.config("Saving Networks to " + output);
            }

            new File(output).mkdirs();
            for (int i = 0; i < networks.size(); i++) {

                FileOutputStream fout;
                try {
                    fout = new FileOutputStream(output + "\\Network" + dimns + "x" + dimns + i + ".takNetwork");
                    ObjectOutputStream oos = new ObjectOutputStream(fout);
                    oos.writeObject(networks.get(i));
                    oos.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            if (LOGGING_ENABLED) {
                logger.config("Networks Saved. ");
                logger.config("Generating Random Testing Networks for comparison");
            }
		/*
		ArrayList<TakNetwork> randNets = new ArrayList<>();

		for (int j = 0; j < 100; j++) {
			TakNetwork testNetwork = new TakNetwork(dimns+1, dimns, dimns, 3);
			RandomNunber = random.nextInt();
			Random rand = new Random(RandomNunber);
			testNetwork.randomize(rand);

			randNets.add(testNetwork);

			// all 100 networks generate
		}

		if (LOGGING_ENABLED) {
			logger.config("Random Network Generation Complete. Beginning testing");
		}

		networks.get(0).setWins(0);
		networks.get(0).setLosses(0);

		StringBuffer buffer = new StringBuffer();
		String s = "";

		int win = 0;
		for (int j = 0; j < networks.size(); j++) {
			networks.get(j).setWins(0);
			networks.get(j).setLosses(0);

			for (int i = 0; i < 100; i++) {
				try {


					win = Board.playGame(networks.get(j), randNets.get(i), dimns);
					// System.out.println("Win: " + win);
					if (win > 0) {
						networks.get(j).setWins(networks.get(j).getWins() + 1);
						// System.out.println("Win: " + i);

					} else {
						networks.get(j).setLosses(networks.get(j).getLosses() + 1);
						// System.out.println("Loss: " + i);
					}
				}catch (Exception e){

				}

			}

			s = ("NET: "
					+ networks.get(j).toString().substring((networks.get(j).toString().indexOf("@") + 1),
							networks.get(j).toString().length())
					+ "\t   Wins: " + networks.get(j).getWins() + "\tLosses: " + networks.get(j).getLosses()
					+ "\tPlayed total: " + (networks.get(j).getWins() + networks.get(j).getLosses())
					+ " \tWin/Loss Ration: "
					+ formatter.format((double) networks.get(j).getWins() / (double) networks.get(j).getLosses())
					+ "  \tWin Percentage: " + formatter.format((double) networks.get(j).getWins()
							/ (double) (networks.get(j).getLosses() + networks.get(j).getWins())) + "\n");
			System.out.println(s);
			buffer.append(s);
		}


		if (LOGGING_ENABLED) {
			logger.config("Testing Complete");
			logger.config(buffer.toString());
		}
		//*/
        } catch (Exception E) {
            E.printStackTrace();

            logger.severe(Arrays.toString(E.getStackTrace()));

            String output = "networks\\ERROREXIT\\TestGnerationalGrowth\\output";

            if (LOGGING_ENABLED) {
                logger.config("SavingNetworks to " + output);
            }

            System.out.println("Saving Networks");

            new File(output).mkdirs();
            for (int s = 0; s < networks.size(); s++) {

                FileOutputStream fout;
                try {
                    fout = new FileOutputStream(output + "\\Network" + s + ".takNetwork");
                    ObjectOutputStream oos = new ObjectOutputStream(fout);
                    oos.writeObject(networks.get(s));
                    oos.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (LOGGING_ENABLED) {
                    logger.config("Saved Network " + s);
                }
            }

            System.out.println("Networks Saved");

            return;


        }

    }

    private static void ComputeGenerationTesting(int numToTest) {
        // TODO Auto-generated method stub

        Random random = new Random();
        ArrayList<TakNetwork> networks = new ArrayList<>();

        // gen networks
        int RandomNunber = 0;
        for (int i = 0; i < numToTest; i++) {
            TakNetwork testNetwork = new TakNetwork(9, 8, 8, 8, 0, i);
            RandomNunber = random.nextInt();
            Random rand = new Random(RandomNunber);
            testNetwork.randomize(rand);

            networks.add(testNetwork);

            // all 100 networks generate
        }
        // System.out.println("Generated Threads");
        long startTime = System.nanoTime();

        ComputeGeneration.compute(networks, 8, null);

        NumberFormat formatter = new DecimalFormat("#0.0000");

        for (int i = 0; i < networks.size(); i++) {

            System.out.println("NET: " + i + "\t   Wins: " + networks.get(i).getWins() + "\tLosses: "
                    + networks.get(i).getLosses() + "\tPlayed total: "
                    + (networks.get(i).getWins() + networks.get(i).getLosses()) + "\tWin/Loss Ration: "
                    + formatter.format((double) networks.get(i).getWins() / (double) networks.get(i).getLosses())
                    + "  \tWin Percentage: " + formatter.format((double) networks.get(i).getWins()
                    / (double) (networks.get(i).getLosses() + networks.get(i).getWins())));

        }

    }

    /**
     * runs a series of tests from 5-95 to test timings of the generation.
     */
    private static void ThreadTimingTesting() {

        for (int i = 5; i < 100; i = i + 5) {
            ThreadedTesting(i);
        }
    }

    /**
     * creates a series of threads networks and tests the output of the wins
     * loss
     */
    private static long ThreadedTesting(int numPerGeneration) {

        Random random = new Random();
        int[] wins = new int[numPerGeneration];
        int[] losses = new int[numPerGeneration];
        ArrayList<TakNetwork> networks = new ArrayList<>();

        // gen networks
        int RandomNunber = 0;
        for (int i = 0; i < numPerGeneration; i++) {
            TakNetwork testNetwork = new TakNetwork(9, 8, 8, 8,0,i);
            RandomNunber = random.nextInt();
            Random rand = new Random(RandomNunber);
            testNetwork.randomize(rand);

            networks.add(testNetwork);

            // all 100 networks generate
        }
        // System.out.println("Generated Threads");
        long startTime = System.nanoTime();

        // create threads and thread arrayList
        ArrayList<Thread> threads = new ArrayList<Thread>(numPerGeneration);
        for (int i = 0; i < numPerGeneration; i++) {

            final int start = i;
            Thread t = new Thread() {

                @Override
                public void run() {
                    RunSelectionOfGames(start, networks, wins, losses);

                }

            };
            threads.add(t);
            // System.out.println(i);
        }

        // run all threads.
        for (Thread thread : threads) {
            // System.out.println(thread.toString());
            thread.start();
            // System.out.println(thread.isAlive());
        }

        for (int i = 0; i < threads.size(); i++) {
            try {
                // System.out.println(i);
                threads.get(i).join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        long endTime = System.nanoTime();

        // deprecated for wins and losses in network.
        double[] ratio = new double[wins.length];
        double[] percentage = new double[wins.length];
        for (int i = 0; i < wins.length; i++) {
            ratio[i] = (double) wins[i] / (double) losses[i];
            percentage[i] = (double) wins[i] / ((double) wins[i] + (double) losses[i]);
        }

        if (StaticGlobals.PRINT_THREAD_WINNER_OUTPUT) {
            System.out.println("Wins   : " + Arrays.toString(wins));
            System.out.println("Losses : " + Arrays.toString(losses));
            System.out.println("Ratio  : " + Arrays.toString(ratio));
            System.out.println("Percent: " + Arrays.toString(percentage));
        }

        System.out.println(
                "total Time for " + numPerGeneration + " Threads is: " + (endTime - startTime) / 1000000000.0 + " S");
        // System.out.println(numPerGeneration +" "+
        // (endTime-startTime)/1000000000.0);
        return endTime - startTime;
    }

    /**
     * takes in a single int and a list of networks, along with the locations of
     * the wins and losses arrays, and using that data, the network of the given
     * int against every other network in the list. This can be used for
     * threading the network running.
     *
     * @param i        int network to run against all other networks
     * @param networks List<TakNetworks> the list of networks that are to be run
     * @param wins     int[] the array for tracking wins
     * @param losses   int[] the array for tracking losses
     */
    private static void RunSelectionOfGames(int i, List<TakNetwork> networks, int[] wins, int[] losses) {

        for (int j = 0; j < networks.size(); j++) {
            long start = System.currentTimeMillis();
            TakNetwork net1 = networks.get(i);
            TakNetwork net2 = networks.get(j);

            int winner = Board.playGame(net1, net2, 8);
            if (winner == 1) {
                net1.setWins(net1.getWins() + 1);
                net2.setLosses(net1.getLosses() + 1);
            } else {
                net2.setWins(net2.getWins() + 1);
                net1.setLosses(net1.getLosses() + 1);
            }
            if (StaticGlobals.PRINT_GAME_WINNER) {
                System.out.println("game " + i + ":" + j + " Winner: " + winner + " Time: "
                        + (System.currentTimeMillis() - start) / 1000.0 + " S");
            }

        }

    }

    /**
     * Tests network access.
     */
    @SuppressWarnings("unused")
    private static void NetTesting() {

		/*
		 * URL url; InputStream inputStream = null; BufferedReader
		 * bufferedReader; String data = null;
		 * 
		 * try { url = new URL("playtak.com"); URLConnection c =
		 * url.openConnection(); c.setRequestProperty("Client-ID",
		 * "login guest"); c.
		 * 
		 * inputStream = c.getInputStream(); // throws an IOException
		 * bufferedReader = new BufferedReader(new
		 * InputStreamReader(inputStream));
		 * 
		 * 
		 * data = bufferedReader.readLine();
		 * 
		 * } catch (MalformedURLException mue) { mue.printStackTrace(); } catch
		 * (IOException ioe) { //ioe.printStackTrace();
		 * System.out.println("ERROR: IO Exception in Data Puller"); } finally {
		 * try { if (inputStream != null) inputStream.close(); } catch
		 * (IOException ioe) { // nothing to see here } }
		 */
    }

    /**
     * tests a single game created from two randomly generated networks.
     */
    @SuppressWarnings("unused")
    private static void TestSingleGame() {

        TakNetwork net1 = new TakNetwork(9, 8, 8, 8,0,0);
        TakNetwork net2 = new TakNetwork(9, 8, 8, 8,0,1);
        Random rand = null;

        rand = new Random();
        net1.randomize(rand);
        rand = new Random();
        net2.randomize(rand);

        System.out.println("game 1:2 " + Board.playGame(net1, net2, 8));

    }

    /**
     * tests the generation of numPerGeneration networks has each network saved,
     * loaded, and fight against itself and every other network
     */
    public static void TestGeneration() {
        Random random = new Random();
        int generation = 0;

        Logger logger = Logger.getLogger("MyLog");

        if (LOGGING_ENABLED) {
            FileHandler fh;

            try {

                // This block configure the logger with handler and formatter
                // Time time = new Time(System.currentTimeMillis());

                Date date = new Date(System.currentTimeMillis());
                DateFormat formattert = new SimpleDateFormat("MM-dd-YYYY HH_mm_ss");
                String dateFormatted = formattert.format(date);

                System.out.println(dateFormatted);
                fh = new FileHandler(("logfiles\\" + dateFormatted + " output.log"));
                logger.addHandler(fh);
                SimpleFormatter formatter = new SimpleFormatter();
                fh.setFormatter(formatter);

                // the following statement is used to log any messages
                // logger.info("My first log");

            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            logger.setLevel(Level.ALL);
            // logger.info("Hi How r u?");
            // logger.config("Hi How r u?");
            // logger.config("msg2");

        }

        // gen networks

        new File("networks\\gen0").mkdirs();
        int RandomNunber = 0;
        for (int i = 0; i < numPerGeneration; i++) {
            TakNetwork testNetwork = new TakNetwork(9, 8, 8, 8,0,i);
            RandomNunber = random.nextInt();
            Random rand = new Random(RandomNunber);
            testNetwork.randomize(rand);

            if (LOGGING_ENABLED) {
                logger.config("Created network with random seed:" + RandomNunber + "\nArgs 8, 8, 9, 8");
            }

            FileOutputStream fout;
            try {
                fout = new FileOutputStream("networks\\gen0\\Network" + i + ".takNetwork");
                ObjectOutputStream oos = new ObjectOutputStream(fout);
                oos.writeObject(testNetwork);
                oos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        // create win loss
        int[] wins = new int[numPerGeneration];
        int[] losses = new int[numPerGeneration];
        ArrayList<TakNetwork> networks = new ArrayList<>();
        for (int i = 0; i < numPerGeneration; i++) {
            System.err.println(i);
            networks.add(load("networks\\gen" + generation + "\\Network" + i + ".takNetwork"));
        }
        for (int i = 0; i < numPerGeneration; i++) {

            RunSelectionOfGames(i, networks, wins, losses);

        }

        double[] ratio = new double[wins.length];
        double[] percentage = new double[wins.length];
        for (int i = 0; i < wins.length; i++) {
            ratio[i] = (double) wins[i] / (double) losses[i];
            percentage[i] = (double) wins[i] / ((double) wins[i] + (double) losses[i]);
        }

        System.out.println("Wins   : " + Arrays.toString(wins));
        System.out.println("Losses : " + Arrays.toString(losses));
        System.out.println("Ratio  : " + Arrays.toString(ratio));
        System.out.println("Percent: " + Arrays.toString(percentage));

    }

    /**
     * Load a network
     *
     * @param location String File location to load the network from.
     * @return TakNetwork network that exists at file location, or null
     */
    private static TakNetwork load(String location) {

        FileInputStream fin;
        TakNetwork testNetwork = null;
        try {
            fin = new FileInputStream(location);
            ObjectInputStream ois = new ObjectInputStream(fin);
            testNetwork = (TakNetwork) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return testNetwork;
    }

    /**
     * tests a series of statistics methods and classes for other parts of the
     * program specifically the mutation method that accepts a list of networks.
     */
    @SuppressWarnings("unused")
    private static void StatsTesting() {

		/*
		 * //mean 10, std dev 1 //Random rand = new Random(1); Random rand = new
		 * Random(); // double[] vals =
		 * {-3,-2.5,-2,-1.5,-1,-.5,0,.5,1,1.5,2,2.5,3}; // double fromCurve = 0;
		 * //
		 * 
		 * 
		 * //at the beginning Mean mean = new Mean(); StandardDeviation std =
		 * new StandardDeviation();
		 * 
		 * RandomGenerator random = new ISAACRandom();
		 * random.setSeed(rand.nextInt()); NormalDistribution n = new
		 * NormalDistribution(random, mean.evaluate(vals), std.evaluate(vals));
		 * 
		 * fromCurve = n.sample(); System.out.println( n.sample());
		 * System.out.println( n.sample()); System.out.println( n.sample());
		 * System.out.println( n.sample()); System.out.println( n.sample());
		 * System.out.println( n.sample()); System.out.println( n.sample());
		 * System.out.println( n.sample());
		 * 
		 * 
		 * double total = 0;
		 * 
		 * for(int i = 0; i < 100000000; i++){ total += n.sample(); }
		 * System.out.println("average: " + total/(100000000));
		 */
        // setCurve

    }

    /**
     * tests the load Network Function. May error if saveTesting is not used
     * directally prior.
     */
    @SuppressWarnings("unused")
    public static TakNetwork loadTesting(String s) {
        FileInputStream fin;
        try {
            fin = new FileInputStream(s);
            ObjectInputStream ois = new ObjectInputStream(fin);
            TakNetwork testNetwork = (TakNetwork) ois.readObject();
            ois.close();

            int[][][] blank = createTestBlank();

            return testNetwork;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * tests the saving of networks
     */
    @SuppressWarnings("unused")
    private static void saveTesting() {

        TakNetwork testNetwork = new TakNetwork(8, 8, 9, 8,0,0);
        Random rand = new Random(1);
        testNetwork.randomize(rand);

        FileOutputStream fout;
        try {
            fout = new FileOutputStream("networks\\testNet.net");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(testNetwork);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * tests the mutation of multiple networks at the same time. tests with 20
     * networks.
     */
    public static void networkGroupMutatorsTest() {
        // int[][][] blank = createTestBlank();
        // generate a blank network to use as the calculation network
        TakNetwork testNetwork;
        Random rand = new Random(1);

		
		/* gen and test single net mutation
		TakNetwork testNetwork = new TakNetwork(8, 8, 9, 8);
		@SuppressWarnings("unused")
		testNetwork.randomize(rand);
		// randomize a new network base on the number 1

		newNetwork = testNetwork.returnAnotherMutatedNetwork(rand, 0.001);
		 */


        // create an array of 20 networks.
        // the set of these networs will be used to test the group generation
        ArrayList<TakNetwork> takNetworks = new ArrayList<TakNetwork>(20);
        for (int i = 0; i < 20; i++) {
            testNetwork = new TakNetwork(9, 8, 8, 8,0,i);
            takNetworks.add(testNetwork);
            Random random = new Random(i);
            testNetwork.randomize(random);
        }

        System.out.println("Beginning Generation");
        @SuppressWarnings("unused")
        TakNetwork mate = MateNetworks.GroupMateNetworks(takNetworks, rand, 1, 1).get(0);
        System.out.println("Ended Generation");

    }

    /**
     * tests the generation of a series of networks, it prints the ammount of
     * ram before and after a network is generated the time it took to build
     * said network the time it took for the network to calculate with that
     * output and then data for a series of other networks current data gen time
     * gen data calc time.
     * <p>
     * finally prints total calc time total calc data and a list of output moves
     * from the last generated network.
     */
    public static void networkGenerationCalculationTest() {
        // write your code here
        // Tools.PrintColor("This is a triumph", "green");

        // goals of the testing.
        // create a series of networks.
        // randomly generate a network from a specifc input.
        // compare the output to the output of other networks.
        // calculate run time.

        int data = (int) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
        long time = System.nanoTime();
        System.out.println(data / 1024 + " kB");

        TakNetwork testNetwork = new TakNetwork(8, 8, 9, 8,0,0);
        Random rand = new Random(1);
        testNetwork.randomize(rand);

        // total memory usage

        data = (int) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
        time = System.nanoTime() - time;
        System.out.println(data / 1024 + " kB");
        PrintColor(time / 1000.0 / 1000.0 + " mS in a build\n", "green");

        time = System.nanoTime();
        testNetwork.calculate(createTestBlank());
        time = System.nanoTime() - time;
        PrintColor(time / 1000.0 / 1000.0 + " mS in a calculate", "green");
        PrintColor("\n\nTESTING DATA BELOW\n", "blue");

        int[][][] blank = createTestBlank();

        long startupTime = 0, genTime = 0, endTime = 0, startData = 0, genData = 0, endData = 0;
        // long startupData;
        ArrayList<TakNetwork> takNetworks = new ArrayList<TakNetwork>(20);
        long startTime = System.nanoTime();
        startData = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
        List<Move> moves = new ArrayList<Move>(1);
        for (int i = 0; i < 20; i++) {
            PrintColor("Network " + i + "\n", "green");

            startupTime = System.nanoTime();
            // startupData = (Runtime.getRuntime().totalMemory() -
            // Runtime.getRuntime().freeMemory());
            testNetwork = new TakNetwork(8, 8, 9, 10,0,i);
            takNetworks.add(testNetwork);
            Random random = new Random(i);
            testNetwork.randomize(random);
            genTime = System.nanoTime();
            genData = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
            moves.clear();
            moves = testNetwork.calculate(blank);
            endTime = System.nanoTime();
            endData = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());

            PrintColor(("\tCurrent data " + (endData - startData) / 1024 / 1024.0 + "Mb\n"), "red");

            // gen time
            // gen data size
            // run time
            // run dataSize
            PrintColor(("\tGen  Time " + (genTime - startupTime) / 1000000.0 + "ms\n"), "yellow");
            PrintColor(("\tGen  data " + (genData - startData) / 1024 + "kB\n"), "blue");
            PrintColor(("\tCalc Time " + (endTime - genTime) / 1000000.0 + "ms\n"), "yellow");
        }

        PrintColor(("Total Time " + (endTime - startTime) / 1000000.0 + "ms\n"), "red");
        PrintColor(("Calc data " + (endData - startData) / 1024 + "Kb\n"), "red");

        for (Move move : moves) {

            PrintColor("   " + move.toString() + "\n", "blue");
        }

    }

    /**
     * creates a [8][8][9] integer network to use for testing
     *
     * @return double[][][] to be used for basic testing.
     */
    @SuppressWarnings("unused")
    private static int[][][] createTestBlank889() {
        int[][][] data = {
                {{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}},
                {{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}},
                {{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}},
                {{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}},
                {{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}},
                {{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}},
                {{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}},
                {{0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0}}};

        return data;
    }

    /**
     * creates a [9][8][8] integer network to use for testing
     *
     * @return double[][][] to be used for basic testing.
     */
    private static int[][][] createTestBlank() {

        int[][][] data = {
                {{0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}},
                {{0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}},
                {{0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}},
                {{0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}},
                {{0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}},
                {{0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}},
                {{0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}},
                {{0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}},
                {{0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}},};

        return data;

    }

}
