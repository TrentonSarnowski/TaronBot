package com.company.TaronBot;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.logging.Logger;

import com.company.TaronBot.Game.Board;
import com.company.TaronBot.Game.RunGames;
import com.company.TaronBot.Network.ComputeGeneration;
import com.company.TaronBot.Network.MateNetworks;
import com.company.TaronBot.Network.TakNetwork;
import com.company.TestingMain;
//import com.sun.org.apache.xpath.internal.operations.String;
import tech.deef.Tools.StaticGlobals;

import static com.company.TestingMain.loadTesting;

public class ControlClass {

    /**
     * TODO read in networks
     * mutate a list of networks
     * adjust verbosity of output
     * active Human game out puts weight
     * generation data
     * <p>
     * generate a series of networks
     * <p>
     * generate
     * -name/-n <default>required</default>
     * -s/-size <size>default 5</size>
     * -d/-depth <depth>default 8 </depth>
     * -q/-quantity/-num <quantity>default 64</quantity>
     * -complexity/ -c <complexity> default size*4</complexity>
     * -seed <seed>recorded in log default no seed</seed>
     * <p>
     * select mutation method
     * select mutation percentage
     * help files
     * log into playtak
     * -name
     * -password
     * automate games on playtak
     */

    /**
     * This begins the Remote Control Structure: Reduced functionality to prevent harm to computer because my secruity is low
     * stakbot
     *      ping: Sends a ping message to chat
     *      online: Begins generating AI(see generate for AI specs), Brings up Demo games(vs self) for playTak, Brings up sTAKbot for challenge on Playtak
     *      cleanup: Brings the AI down from online play. does not stop generation
     *      pause: pauses offline generation
     *      unpause: unpuases offline generation
     *      depth i: sets the depth of the networks generated  with i
     *      rungens i: sets the gens to be run to i
     *      size i: sets the size of the board to be run
     *      gensize i: sets the size of the generation to i:

     * @param r: Scanner of the output from the site
     * @param out: output to the site
     */
    public static void StartOnlineControl(Scanner r, PrintWriter out) {
        Map<String, Object> objects = new HashMap<String, Object>();

        Thread ping = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {


                        out.println("PING");
                        sleep(15000);
                    } catch (InterruptedException e) {
                        System.err.println("did not ping");
                    }
                }
            }
        };
        ping.start();
        String net1 = "0";
        String net2 = "1";
        int size = 5;
        int genSize = 128;
        int runGens = 128;
        int coreCount = 8;

        String input = "";

        while (true) {
            //System.out.print("\nWaiting for input: ");
            input = r.nextLine();
            System.out.println(input);
            Scanner reader = new Scanner(input);
            input = reader.next();
            if (input.toLowerCase().equals("shout")) {
                System.err.println("read shout");

                input = reader.next();
                if (input.equals("<Tarontos>") || input.equals("<Deef0000dragon1>")) {
                    System.err.println("read Tarontos");

                    input = reader.next();
                    if (input.toLowerCase().equals("stakbot")) {
                        System.err.println("read stakbot");

                        input = reader.next();
                        System.err.println("");
                        //***********************************START OF INPUT SWITCH*******************************
                        switch (input.toLowerCase()) {

                            case "ping":
                                out.println("PING");
                                out.println("Shout I Pinged");
                                break;
                            //***********************************ENABLE DIFFERNET GLOBAL BOOLEAN SWITCHES************
                            case "weighttype"://todo add others
                                switch (reader.next().toLowerCase()) {
                                    case "nw":
                                        RunGames.weightType = RunGames.victoryType.WIN_NO_WEIGHT;
                                        break;
                                    case "pnw":
                                        RunGames.weightType = RunGames.victoryType.PAGE_WEIGHTED_WIN_NO_WEIGHT;
                                        break;
                                    case "pagewinloss":
                                        RunGames.weightType = RunGames.victoryType.PAGE_WEIGHTED_WIN_LOSS_NO_WEIGHT;
                                        break;
                                    case "page12":
                                        RunGames.weightType = RunGames.victoryType.PAGE_WEIGHTED_WIN_ONE_TWO_WEIGHT;
                                        break;
                                    case "12":
                                        RunGames.weightType = RunGames.victoryType.WIN_ONE_TWO_WEIGHT;
                                        break;
                                    case "pagewinlossweighted":
                                        RunGames.weightType = RunGames.victoryType.PAGE_WEIGHTED_WIN_LOSS_WEIGHTED;
                                        break;

                                }
                                break;
                            case "cleanup":
                                out.println("Shout OK i'll remove the games");

                                ServerCommunication.cont = false;
                                break;

                            case "online":
                                out.println("Shout OK i'll bring stakbot Online");
                                final int genSizes = genSize;
                                final int runGenst = runGens;
                                final int coreCounts = coreCount;
                                final int sizes = size;

                                Thread t = new Thread() {

                                    @Override
                                    public void run() {
                                        for (int i = 0; i < 100; i++) {
                                            if (ServerCommunication.cont) {
                                                GenerateNetworksOnline(genSizes, runGenst, coreCounts, sizes);
                                            }
                                        }
                                    }
                                };
                                t.start();
                                break;
                            case "pause":
                                StaticGlobals.PAUSED = true;
                                System.out.println("Paused");
                                break;


                            case "gensize":
                                genSize = reader.nextInt();
                                break;
                            case "rungens":
                                runGens = reader.nextInt();
                                break;

                            case "depth":
                                StaticGlobals.DEPTH = reader.nextInt();
                                break;


                            //**************************GENERATE A NEW NETWORK*************************
                            case "unpause":
                                StaticGlobals.PAUSED = false;
                                System.out.println("Unpaused");
                                break;

                            case "size":

                                size = reader.nextInt();
                                out.println("Shout OK Next time I'm brought online, i'll play size " + size);

                                break;

                            default:
                                System.out.println(input + " not recognized as command");
                        }

                    }
                }
            }
        }

    }

    /**
     * This begins the Local Control Structure
     * enable/disable:
     * load: Loads game from last run
     * moves: shows moves made--Testing
     * board: shows the top level boardstate each turn--Testing
     * online: Begins generating AI(see generate for AI specs), Brings up Demo games(vs self) for playTak, Brings up sTAKbot for challenge on Playtak
     * cleanup: Brings the AI down from online play. does not stop generation
     * generate: Begins AI generation with rungens generations of size gensize with thread count of cores and Edge length of Board
     * continue: Enables load and generates(see generate) then disables load after load complete.
     * pause: pauses offline generation
     * unpause: unpuases offline generation
     * save: saves networks currently being run, halts generation
     * depth i: sets the depth of the networks generated  with i
     * rungens i: sets the gens to be run to i
     * size i: sets the size of the board to be run
     * gensize i: sets the size of the generation to i:
     * core i: sets the numbers of threads to run(defaults to 8)
     * player1 i: sets the player 1 for local test game(from saved networks)
     * player2 i: sets the player 2 for local test game(from saved networks)
     * play: plays a game using player 1 and 2 with size size: Will fail if no saved network of size size with the player id
     */
    public static void StartControl() {
        TakNetwork testNetwork;
        int RandomNumber;
        ArrayList<TakNetwork> n = new ArrayList<>();
        Random random = new Random();
        Map<String, Object> objects = new HashMap<String, Object>();


        String net1 = "0";
        String net2 = "1";
        int size = 5;
        int genSize = 128;
        int runGens = 128;
        int coreCount = 8;

        String input = "";
        Scanner reader=new Scanner(System.in);
        while (true) {
            System.out.println("\nWaiting for input: ");
            input = reader.next();

            //***********************************START OF INPUT SWITCH*******************************
            switch (input.toLowerCase()) {


                //***********************************ENABLE DIFFERNET GLOBAL BOOLEAN SWITCHES************
                case "test":
                    n = new ArrayList<>();
                    for (int i = 0; i < genSize; i++) {
                        try {


                            if (StaticGlobals.LOAD_FROM_LAST_RUN) {
                                testNetwork = loadTesting("networks\\TestNeat\\outputstats\\Network" + size + "x" + size + i + ".takNetwork");
                                testNetwork.setWins(0);
                                testNetwork.setLosses(0);
                            } else {

                                testNetwork = new TakNetwork(size + 1, size, size, StaticGlobals.DEPTH, 0, i);
                                RandomNumber = random.nextInt();
                                Random rand = new Random(RandomNumber);
                                testNetwork.randomize(rand);
                            }
                        }catch (Exception e){

                            testNetwork = new TakNetwork(size + 1, size, size, StaticGlobals.DEPTH, 0, i);
                            RandomNumber = random.nextInt();
                            Random rand = new Random(RandomNumber);
                            testNetwork.randomize(rand);
                        }
                        n.add(testNetwork);
                    }
                    System.err.println("Nets loaded");
                    ArrayList listOfItems=n;
                    Thread NEAT = new Thread() {
                        @Override
                        public void run() {
                            RunGames.NEATGAMEPLAY(listOfItems, 5, 6000000, "stats", listOfItems.size());
                        }
                    };
                    NEAT.start();
                    break;
                case "loadonline":{

                    n = new ArrayList<>();
                    for (int i = 0; i < genSize; i++) {




                                testNetwork = loadTesting("networks\\TestNeat\\output\\Network" + size + "x" + size + i + ".takNetwork");
                                testNetwork.setWins(0);
                                testNetwork.setLosses(0);


                        n.add(testNetwork);
                    }

                }
                ArrayList inner = n;
                (new Thread(){
                    @Override
                    public void run() {
                        ServerCommunication.playGame(inner);

                    }
                }).start();
                break;
                case "gamecount":
                    input = reader.next();
                    StaticGlobals.GAMESTOPLAY = Long.parseLong(input);
                    break;
                case "enable":
                    input = reader.next();
                    switch (input.toLowerCase()) {
                        case "moves":
                            StaticGlobals.PRINT_GAME_MOVES = true;
                            break;
                        case "board":
                            StaticGlobals.PRINT_GAME_BOARD = true;
                            break;
                        case "load":
                            StaticGlobals.LOAD_FROM_LAST_RUN = true;
                            break;
                        default:
                            System.out.println(input + " not recognized as boolean Switch");
                    }
                    break;


                //***********************************Disable DIFFERNET GLOBAL BOOLEAN SWITCHES***********
                case "disable":
                    input = reader.next();
                    switch (input.toLowerCase()) {
                        case "moves":
                            StaticGlobals.PRINT_GAME_MOVES = false;
                            break;
                        case "load":
                            StaticGlobals.LOAD_FROM_LAST_RUN = false;
                            break;
                        case "board":
                            StaticGlobals.PRINT_GAME_BOARD = true;
                            break;
                        default:
                            System.out.println(input + " not recognized as boolean Switch");
                    }
                    break;

                case "cleanup":
                    ServerCommunication.cont = false;
                    break;
                case "save":
                    StaticGlobals.SAVE_NETWORKS_OUT_AND_EXIT = true;
                    System.out.println("Saving after next generation");
                    break;
                case "online":
                    final int genSizes = genSize;
                    final int runGenst = runGens;
                    final int coreCounts = coreCount;
                    final int sizes = size;
                    Thread t = new Thread() {

                        @Override
                        public void run() {
                            for(int i=0;i<100;i++){
                                if(ServerCommunication.cont) {
                                    GenerateNetworksOnline(genSizes, runGenst, coreCounts, sizes);
                                }
                            }
                        }
                    };
                    t.start();
                    break;
                case "pause":
                    StaticGlobals.PAUSED = true;
                    System.out.println("Paused");
                    break;


                case "continue": {
                    boolean previous = StaticGlobals.LOAD_FROM_LAST_RUN;
                    StaticGlobals.LOAD_FROM_LAST_RUN = true;
                    final int innerSize = size;
                    final int innerGen = runGens;
                    final int innerRunGenSize = genSize;
                    final int innerDepth = coreCount;

                    Thread thread = new Thread() {

                        @Override
                        public void run() {
                            TestingMain.TestGenerationalGrowth(innerRunGenSize, innerGen, innerDepth, innerSize);

                        }

                    };
                    thread.start();
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        System.out.println("Bahhh: how did this break!!!");
                    }
                    StaticGlobals.LOAD_FROM_LAST_RUN = previous;
                }
                break;
                case "gensize":
                    genSize = reader.nextInt();
                    break;
                case "rungens":
                    runGens = reader.nextInt();
                    break;
                case "core":
                    coreCount = reader.nextInt();

                    break;
                case "depth":
                    StaticGlobals.DEPTH = reader.nextInt();
                    break;


                //**************************GENERATE A NEW NETWORK*************************
                case "generate":
                    GenerateNetworks(genSize, runGens, coreCount, size);
                    //{
                    //size = generateCommand(objects, size, genSize, runGens, coreCount, reader);
                    //}

                    break;

                case "unpause":
                    StaticGlobals.PAUSED = false;
                    System.out.println("Unpaused");
                    break;


                case "player1":
                    net1 = reader.next();
                    System.out.println(net1);
                    break;
                case "player2":
                    net2 = reader.next();
                    System.out.println(net2);
                    break;
                case "size":
                    size = reader.nextInt();
                    break;
                case "play":
                    //TakNetwork network1=null;
                    //TakNetwork network2=null;
                    //String line =reader.nextLine();
                    StaticGlobals.PRINT_GAME_MOVES = true;
                    //	Scanner r=new Scanner(line);

                    System.out.println("Player1: " + net1 + " Player2: " + net2);
                    Board.playGame(loadTesting(".\\networks\\TestGenerationalGrowth\\output\\Network" + size + "x" + size + net1 + ".taknetwork"), loadTesting("networks\\TestGenerationalGrowth\\output\\Network" + size + "x" + size + net2 + ".taknetwork"), size);
                    StaticGlobals.PRINT_GAME_MOVES = false;
                    break;
                default:
                    System.out.println(input + " not recognized as command");
            }

        }

    }

    /**
     * In progress will replace generate eventually working on good format for it
     * @param objects
     * @param size
     * @param genSize
     * @param runGens
     * @param coreCount
     * @param reader
     * @return
     */
    public static int generateCommand(Map<String, Object> objects, int size, int genSize, int runGens, int coreCount, Scanner reader) {
        //format without arguments right now to set formula.


        String line = reader.nextLine();
        String[] commands = line.split("-");
        String s1 = "", s2 = "";
        HashMap<String, String> commandVal = new HashMap<String, String>();

        //set default values

        int GameSize = StaticGlobals.SIZE;
        int NetworkDepth = StaticGlobals.DEPTH;
        int generationQuantity = 64;
        int complexity = GameSize*GameSize * 4;
        int seed = (new Random()).nextInt();
        String name = "";
                /*
                -name/-n <default>required</default>a
				-s/-size <size>default 5</size>
				-d/-depth <depth>default 8 </depth>
				-q/-quantity/-num <quantity>default 64</quantity>
				-complexity/ -c <complexity> default size*4</complexity>
				-seed <seed>recorded in log default no seed</seed>
				*/

        for (String command : commands) {
            try {
                System.out.println(command);
                s1 = command.split(" ")[0];
                s2 = command.substring(command.indexOf(" ") + 1);
                System.out.println("s1:" + s1 + "\ns2:" + s2);
                commandVal.put(s1, s2);
            } catch (IndexOutOfBoundsException e) {
//do nothing, possibly output that command is not recognized and break...
            }
            final int innerSize = size;
            final int innerGen = runGens;
            final int innerRunGenSize = genSize;
            final int cores = coreCount;

            Thread t = new Thread() {

                @Override
                public void run() {
                    TestingMain.TestGenerationalGrowth(innerRunGenSize, innerGen, cores, innerSize);

                }
            };

            if (!(commandVal.containsKey("name") || commandVal.containsKey("n"))) {
                System.out.println("network must be named to continue");
                break;
            }

            if (commandVal.containsKey("n")) {
                name = (commandVal.get("n"));
            }
            if (commandVal.containsKey("name")) {
                name = (commandVal.get("name"));
            }


            if (commandVal.containsKey("s")) {
                size = Integer.parseInt(commandVal.get("s"));
            }
            if (commandVal.containsKey("size")) {
                size = Integer.parseInt(commandVal.get("size"));
            }


            if (commandVal.containsKey("d")) {
                NetworkDepth = Integer.parseInt(commandVal.get("d"));
            }
            if (commandVal.containsKey("depth")) {
                NetworkDepth = Integer.parseInt(commandVal.get("depth"));
            }

            if (commandVal.containsKey("q")) {
                generationQuantity = Integer.parseInt(commandVal.get("q"));
            }
            if (commandVal.containsKey("quantity")) {
                generationQuantity = Integer.parseInt(commandVal.get("quantity"));
            }
            if (commandVal.containsKey("num")) {
                generationQuantity = Integer.parseInt(commandVal.get("num"));
            }

            if (commandVal.containsKey("complexity")) {
                complexity = Integer.parseInt(commandVal.get("complexity"));
            }
            if (commandVal.containsKey("c")) {
                complexity = Integer.parseInt(commandVal.get("c"));
            }

            if (commandVal.containsKey("seed")) {
                seed = Integer.parseInt(commandVal.get("seed"));
            }
        }
        System.out.println("generating networks");
        ArrayList<TakNetwork> newNetwork = new ArrayList<TakNetwork>();
        for (int i = 0; i < generationQuantity; i++) {
            newNetwork.add(new TakNetwork(size, size, size + 1, NetworkDepth, 0, i));
        }
        objects.put(name, newNetwork);
        return size;
    }

    /**
     * Generates networks offline
     * @param generationSize
     * @param generations
     * @param cores
     * @param dimns side length
     */
    private static void GenerateNetworks(int generationSize, int generations, int cores, int dimns) {


        Logger logger = Logger.getLogger("MyLog");


        Random random = new Random();
        ArrayList<TakNetwork> networks = new ArrayList<>();
        NumberFormat formatter = new DecimalFormat("#0.0000");

        // gen networks


        int RandomNumber = 0;
        for (int j = 0; j < generationSize; j++) {
            TakNetwork testNetwork;
            if (StaticGlobals.LOAD_FROM_LAST_RUN) {
                testNetwork = loadTesting("networks\\TestGenerationalGrowth\\output\\Network" + dimns + "x" + dimns + j + ".takNetwork");
                testNetwork.setWins(0);
                testNetwork.setLosses(0);
            } else {

                testNetwork = new TakNetwork(dimns + 1, dimns, dimns, StaticGlobals.DEPTH, 0, j);
                RandomNumber = random.nextInt();
                Random rand = new Random(RandomNumber);
                testNetwork.randomize(rand);
            }

            // System.out.println((testNetwork.toString()));

            networks.add(testNetwork);

            // all 100 networks generate
        }
        System.out.println("Base Generated");
        //*/

        try {
            for (int i = 0; i < generations; i++) {


                if (StaticGlobals.SAVE_NETWORKS_OUT_AND_EXIT || i % 100 == 99) {

                    String output = "networks\\TestGenerationalGrowth\\output";


                    System.out.println("Saving Networks");

                    new File(output).mkdirs();
                    for (int s = 0; s < networks.size(); s++) {

                        FileOutputStream fout;
                        try {
                            fout = new FileOutputStream(output + "\\"+"Network"+networks.get(s).getWidth()+"x"+networks.get(s).getWidth() + s + ".takNetwork");
                            ObjectOutputStream oos = new ObjectOutputStream(fout);
                            oos.writeObject(networks.get(s));
                            oos.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    System.out.println("Networks Saved");
                    if (StaticGlobals.SAVE_NETWORKS_OUT_AND_EXIT) {
                        StaticGlobals.SAVE_NETWORKS_OUT_AND_EXIT=false;
                        return;
                    }
                }


                System.out.println("\n\nGENERATION: " + i + "\n");


                networks = (ArrayList) ComputeGeneration.compute(networks, cores, logger);


                if (StaticGlobals.PRINT_NETWORK_STATS) {
                    StringBuffer t = new StringBuffer();
                    for (int j = 0; j < networks.size(); j++) {


                        t.append("NET: "
                                + networks.get(j).toString().substring((networks.get(j).toString().indexOf("@") + 1), networks.get(j).toString().length())
                                + "\t   Generation: " + networks.get(j).getGeneration()
                                + "\t   Wins: " + networks.get(j).getWins() + "\n"); //newline not appended in logging.
                    }
                    System.out.println(t.toString());


                }

                ArrayList<TakNetwork> remove = new ArrayList<TakNetwork>();


                try {
                    remove.clear();
                    for (int j = 0; j < (generationSize); j++) {

                        if (random.nextDouble() > Math.cos(j * Math.PI / 2.0 / generationSize)) {
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


                for (TakNetwork net : remove) {
                    networks.remove(net);
                }

                if (StaticGlobals.GENERATIONAL_NOTIFIERS) {
                    // System.out.println("Starting new network generation");
                }

                networks.addAll(MateNetworks.GroupMateNetworks(networks, random, generationSize - networks.size(), i));


                // direct dupe top net

                //for (int j=0;networks.size() < generationSize;j++) {
//					int toDupe = (int) Math.floor(Math.cos(random.nextDouble() * Math.PI / 2.0) * networks.size());
//					networks.add(networks.get(toDupe).returnAnotherMutatedNetwork(random, 0.00001,j));
                //System.out.println("Duped: " + toDupe);
                //}

                //networks.set(generationSize, networks.get(0).returnAnotherMutatedNetwork(random, 0.00001));


            }

            String output = "networks\\TestGenerationalGrowth\\output";


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


        } catch (Exception E) {
            E.printStackTrace();

            logger.severe(Arrays.toString(E.getStackTrace()));

            String output = "networks\\TestGenerationalGrowth\\output";


            System.out.println("Saving Networks");

            new File(output).mkdirs();
            for (int s = 0; s < networks.size(); s++) {

                FileOutputStream fout;
                try {
                    fout = new FileOutputStream(output + "\\Network" + dimns + "x" + dimns + s + ".takNetwork");
                    ObjectOutputStream oos = new ObjectOutputStream(fout);
                    oos.writeObject(networks.get(s));
                    oos.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            System.out.println("Networks Saved");

            return;


        }

    }
    /**
     * Generates networks offline while bringing online functionality up(demo games and bot seeks games)
     * @param generationSize
     * @param generations
     * @param cores
     * @param dimns side length
     */
    private static void GenerateNetworksOnline(int generationSize, int generations, int cores, int dimns) {


        Logger logger = Logger.getLogger("MyLog");


        Random random = new Random();
        ArrayList<TakNetwork> networks = new ArrayList<>();
        NumberFormat formatter = new DecimalFormat("#0.0000");

        // gen networks


        int RandomNumber = 0;
        for (int j = 0; j < generationSize; j++) {
            TakNetwork testNetwork;
            if (StaticGlobals.LOAD_FROM_LAST_RUN) {
                testNetwork = loadTesting("networks\\TestGenerationalGrowth\\output\\Network" + dimns + "x" + dimns + j + ".takNetwork");
                testNetwork.setWins(0);
                testNetwork.setLosses(0);
            } else {

                testNetwork = new TakNetwork(dimns + 1, dimns, dimns, StaticGlobals.DEPTH, 0, j);
                RandomNumber = random.nextInt();
                Random rand = new Random(RandomNumber);
                testNetwork.randomize(rand);
            }

            // System.out.println((testNetwork.toString()));

            networks.add(testNetwork);

            // all 100 networks generate
        }
        System.out.println("Base Generated");
        //*/
        final List<TakNetwork> network2 = new ArrayList<>();
        network2.add(networks.get(0));
        network2.add(networks.get(1));
        Thread thread = new Thread() {

            @Override
            public void run() {
                ServerCommunication.playGame(network2);

            }

        };
        thread.start();
        try {
            for (int i = 0; i < generations; i++) {


                if (StaticGlobals.SAVE_NETWORKS_OUT_AND_EXIT || i % 100 == 99) {

                    String output = "networks\\TestGenerationalGrowth\\output";
                    System.out.println("Saving Networks");

                    new File(output).mkdirs();
                    for (int s = 0; s < networks.size(); s++) {

                        FileOutputStream fout;
                        try {
                            fout = new FileOutputStream(output + "\\Network" + dimns + "x" + dimns + i + ".takNetwork");
                            ObjectOutputStream oos = new ObjectOutputStream(fout);
                            oos.writeObject(networks.get(s));
                            oos.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    System.out.println("Networks Saved");
                    if (StaticGlobals.SAVE_NETWORKS_OUT_AND_EXIT) {
                        StaticGlobals.SAVE_NETWORKS_OUT_AND_EXIT=false;

                        return;
                    }
                }


                // System.out.println("\n\nGENERATION: " + i + "\n");
                //todo
                networks = (ArrayList) ComputeGeneration.compute(networks, cores, logger);


                if (StaticGlobals.PRINT_NETWORK_STATS) {
                    StringBuffer t = new StringBuffer();
                   /*
                    for (int j = 0; j < networks.size(); j++) {


                        t.append("NET: "
                                + networks.get(j).toString().substring((networks.get(j).toString().indexOf("@") + 1), networks.get(j).toString().length())
                                + "\t   Generation: " + networks.get(j).getGeneration()
                                + "\t   Wins: " + networks.get(j).getWins() + "\n"); //newline not appended in logging.
                    }
                    //*/
                    System.out.println(t.toString());


                }

                ArrayList<TakNetwork> remove = new ArrayList<TakNetwork>();


                try {
                    remove.clear();
                    for (int j = 0; j < (generationSize); j++) {

                        if (random.nextDouble() > Math.cos(j * Math.PI / 2.0 / generationSize)) {
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


                for (TakNetwork net : remove) {
                    networks.remove(net);
                }

                if (StaticGlobals.GENERATIONAL_NOTIFIERS) {
                    System.out.println("Starting new network generation");
                }

                networks.addAll(MateNetworks.GroupMateNetworks(networks, random, generationSize - networks.size(), i));


                // direct dupe top net

                //for (int j=0;networks.size() < generationSize;j++) {
//					int toDupe = (int) Math.floor(Math.cos(random.nextDouble() * Math.PI / 2.0) * networks.size());
//					networks.add(networks.get(toDupe).returnAnotherMutatedNetwork(random, 0.00001,j));
                //System.out.println("Duped: " + toDupe);
                //}

                //networks.set(generationSize, networks.get(0).returnAnotherMutatedNetwork(random, 0.00001));

                network2.set(0, networks.get(0));
                network2.set(1, networks.get(1));

            }

            String output = "networks\\TestGenerationalGrowth\\output";
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


        } catch (Exception E) {
            E.printStackTrace();

            logger.severe(Arrays.toString(E.getStackTrace()));

            String output = "networks\\TestGenerationalGrowth\\output";


            System.out.println("Saving Networks");

            new File(output).mkdirs();
            for (int s = 0; s < networks.size(); s++) {

                FileOutputStream fout;
                try {
                    fout = new FileOutputStream(output + "\\Network" + dimns + "x" + dimns + s + ".takNetwork");
                    ObjectOutputStream oos = new ObjectOutputStream(fout);
                    oos.writeObject(networks.get(s));
                    oos.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            System.out.println("Networks Saved");
            while (thread.isAlive())
            return;


        }

    }

}
