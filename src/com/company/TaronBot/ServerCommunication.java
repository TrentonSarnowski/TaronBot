package com.company.TaronBot;

import com.company.TaronBot.Game.Board;
import com.company.TaronBot.Game.Move;
import com.company.TaronBot.Game.Moves.DeStack;
import com.company.TaronBot.Game.Moves.Placement;
import com.company.TaronBot.Network.TakNetwork;
import com.company.TestingMain;
import org.apache.commons.math3.ml.neuralnet.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

import static java.lang.Thread.sleep;

public class ServerCommunication {


	public static Boolean cont = true;
	public static void playGame(List<TakNetwork> l) {
		// TODO Create examples of how to communicate with the server

		try {
			Socket socket = new Socket("www.playtak.com", 10000);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			cont = true;
			Thread t = new Thread() {
				@Override
				public void run() {
					while (cont) {
						playSelf(l);
					}

				}
			};
			t.start();
			while (cont) {
				Thread ts = new Thread() {
					@Override
					public void run() {
						try {

							out.println("PING");
							sleep(30000);
						} catch (Exception e) {

						}
					}
				};
				ts.start();
				playGame(l.get(0), in, out, "sTAKbot");
			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
//Game Start 128608 5 Guest5068 vs Guest5069 white 600

	public static Board parseToGame(String s, PrintWriter out, String playerName) {
		Scanner sc = new Scanner(s);
		try {
			sleep(100);
		} catch (Exception e) {

		}
		System.err.println("Parsing Game: " + s);
		if (s.contains(playerName)) {
			if (sc.hasNext() && sc.next().equals("Game")) {
				if (sc.hasNext() && sc.next().equals("Start")) {
					int no = sc.nextInt();
					int size = sc.nextInt();
					if (size > 9 || size < 5) {
						out.println("Game" + no + " Resign");
						//Game#no Resign
					} else {
						sc.next();
						sc.next();
						sc.next();
						String color = sc.next();
						if (color.equals("white")) {

							return Board.PlayTakBoard(size, new LinkedList<>(), true, no);
						} else if (color.equals("black")) {
							return Board.PlayTakBoard(size, new LinkedList<>(), false, no);

						}
					}
				}
			}
		}
		return null;
	}

	private static void playGame(TakNetwork player, BufferedReader in, PrintWriter out, String id) {

		Board b = seekGame(player, in, out, id);


		if (b.control()) {


			Game:
			while (true) {
				hi:
				for (Move m : player.calculate(b.getAIMap(true))) {

					if (m.checkFeasible(b, true)) {
						if (!cont) {
							out.println("Game#" + b.boardNumber + " Resign");
							return;
						}
						out.println("Game#" + b.boardNumber + " " + m.toPlayTakString());
						String input = "";
						while (true) {
							try {

								input = in.readLine();
							} catch (Exception e) {
							}

							if (input.contains("NOK")) {
								break;
							} else if (input.contains("OK")) {
								m.performMove(b, true);
								try {


									sleep(750);
								} catch (Exception e) {

								}
								break hi;

							} else if (input.toLowerCase().contains("over")) {
								Scanner sn = new Scanner(input);
								sn.next();
								sn.next();
								switch (sn.next().toLowerCase().charAt(0)) {
									case 'r':
									case '1':
									case 'f':
										player.addWins();
										break Game;
									case '0':
										break Game;
									default:

								}

							}
						}

					}

				}

				Move m = null;
				while (m == null) {
					if (!cont) {
						out.println("Game#" + b.boardNumber + " Resign");
						return;
					}
					try {
						String ss = in.readLine();

						m = parseMove(ss, b.boardNumber);

					} catch (GameOverException e) {
						switch (e.getMessage().toLowerCase().charAt(0)) {
							case 'r':
							case '1':
							case 'f':
								player.addWins();
								break;
							default:

						}
						break Game;
					} catch (Exception e) {
						//System.err.println("Err message");
					}
				}

				m.performMove(b, false);

			}

		} else {
			System.out.println("Play as Black");

			boolean gameOver = false;
			Game:
			while (!gameOver) {

				Move l = null;
				while (l == null) {
					if (!cont) {
						out.println("Game#" + b.boardNumber + " Resign");
						return;
					}
					try {
						String ss = in.readLine();
						//System.out.println("try parse: " + ss);

						l = parseMove(ss, b.boardNumber);

					} catch (GameOverException e) {
						//System.err.println("over " + e.getMessage());

						switch (e.getMessage().toLowerCase().charAt(0)) {
							case 'r':
							case '1':
							case 'f':
								break;
							default:
								player.addWins();
								break;

						}
						break Game;
					} catch (Exception e) {
						System.err.println("Err message");
					}
				}

				l.performMove(b, false);
				Boolean exit = false;
				hi:
				for (Move m : player.calculate(b.getAIMap(true))) {

					if (m.checkFeasible(b, true)) {
						if (!cont) {
							out.println("Game#" + b.boardNumber + " Resign");
							return;
						}
						out.println("Game#" + b.boardNumber + " " + m.toPlayTakString());
						String input = "";
						while (true) {
							try {

								input = in.readLine();
							} catch (Exception e) {
							}

							if (input.contains("NOK")) {
								break;
							} else if (input.contains("OK")) {
								try {
									sleep(750);
								} catch (Exception e) {

								}
								m.performMove(b, true);

								break hi;
							} else if (input.toLowerCase().contains("over")) {
								Scanner sn = new Scanner(input);
								sn.next();
								sn.next();
								switch (sn.next().toLowerCase().charAt(0)) {
									case 'r':
									case '1':
									case 'f':
										break;
									default:
										player.addWins();
										break;

								}
								break Game;

							}
						}

					}

				}
				try {
					//Thread.sleep(1500);
				} catch (Exception e) {

				}
			}

		}
		System.err.println("Game Over");
	}

	private static int findSeek(BufferedReader in, String o) throws Exception {
		Integer i = null;
		while (true) {
			String s = in.readLine();
			sleep(100);
			if (s.toLowerCase().contains(o.toLowerCase())) {
				Scanner inner = new Scanner(s);
				if (inner.next().equals("Seek")) {
					if (inner.next().equals("new")) {
						i = inner.nextInt();
						if (inner.next().equals(o)) {
							return i;
						}
					}
				}
			}
		}
	}

	private static void acceptGame(TakNetwork player, BufferedReader in, PrintWriter out, String opponent) {
		int no;
		try {


			no = findSeek(in, opponent);

		} catch (Exception e) {
			no = 0;
		}
		System.out.println(no);
		out.println("Accept " + no);
		Board b = null;
		while (b == null) {
			try {
				String s = in.readLine();
				b = parseToGame(s, out, opponent);

			} catch (IOException e) {

			}
		}

		if (b.control()) {


			Game:
			while (true) {
				hi:
				for (Move m : player.calculate(b.getAIMap(true))) {

					if (m.checkFeasible(b, true)) {
						out.println("Game#" + b.boardNumber + " " + m.toPlayTakString());
						String input = "";
						while (true) {
							try {

								input = in.readLine();
							} catch (Exception e) {
							}

							if (input.contains("NOK")) {
								break;
							} else if (input.contains("OK")) {
								try {


									sleep(750);
								} catch (Exception e) {

								}
								m.performMove(b, true);
								break hi;
							} else if (input.toLowerCase().contains("over")) {
								Scanner sn = new Scanner(input);
								sn.next();
								sn.next();
								switch (sn.next().toLowerCase().charAt(0)) {
									case 'r':
									case '1':
									case 'f':
										player.addWins();
										break Game;
									case '0':
										break Game;
									default:

								}

							}
						}
						try {
							//Thread.sleep(1500);
						} catch (Exception e) {

						}

					}

				}
				Move m = null;
				while (m == null) {
					try {
						String ss = in.readLine();

						m = parseMove(ss, b.boardNumber);

					} catch (GameOverException e) {
						switch (e.getMessage().toLowerCase().charAt(0)) {
							case 'r':
							case '1':
							case 'f':
								player.addWins();
								break;
							default:

						}
						break Game;
					} catch (Exception e) {
					}
				}

				m.performMove(b, false);

			}

		} else {

			boolean gameOver = false;
			Game:
			while (!gameOver) {

				Move l = null;
				while (l == null) {
					try {
						String ss = in.readLine();

						l = parseMove(ss, b.boardNumber);

					} catch (GameOverException e) {

						switch (e.getMessage().toLowerCase().charAt(0)) {
							case 'r':
							case '1':
							case 'f':
								break;
							default:
								player.addWins();
								break;

						}
						break Game;
					} catch (Exception e) {
						System.err.println("Err message");
					}
				}

				l.performMove(b, false);
				Boolean exit = false;
				hi:
				for (Move m : player.calculate(b.getAIMap(true))) {

					if (m.checkFeasible(b, true)) {
						out.println("Game#" + b.boardNumber + " " + m.toPlayTakString());
						String input = "";
						while (true) {
							try {

								input = in.readLine();
							} catch (Exception e) {
							}

							if (input.contains("NOK")) {
								break;
							} else if (input.contains("OK")) {
								try {


									sleep(750);
								} catch (Exception e) {

								}
								m.performMove(b, true);

								break hi;
							} else if (input.toLowerCase().contains("over")) {
								Scanner sn = new Scanner(input);
								sn.next();
								sn.next();
								switch (sn.next().toLowerCase().charAt(0)) {
									case 'r':
									case '1':
									case 'f':
										break;
									default:
										player.addWins();
										break;

								}
								break Game;

							}
						}

					}

				}

			}

		}
		System.err.println("Game Over");

	}

	private static Board seekGame(TakNetwork player, BufferedReader in, PrintWriter out, String id) {
		String s = "";
		Board b = null;
		out.println("Seek " + player.getWidth() + " 300 10");
		while ((b) == null) {
			if (!cont) {
				return new Board(player.getWidth(), new LinkedList<Move>(), true);
			}
			try {

				s = in.readLine();

				b = parseToGame(s, out, id);

			} catch (Exception e) {

			}
		}
		return b;
	}


	public static Move parseMove(String s, int number) throws GameOverException {
		//called after game confirmed
		Scanner n = new Scanner(s);
		try {


		} catch (Exception e) {

		}
		if (n.hasNext() && n.next().equals("Game#" + number)) {

			if (!n.hasNext()) return null;
			switch (n.next().toLowerCase()) {
				case "abandoned":
				case "resign":
					//todo make abandoned move
					break;
				case "over":
					throw new GameOverException(n.next());
				case "p":
					return parsePlacement(n);
				case "m":
					return parseDestack(n);

				default:
					//todo add forfite on malformed move


			}
		}
		return null;
	}

	public static Placement parsePlacement(Scanner s) {
		position p = new position(s.next());
		int type = 1;
		if (s.hasNext()) {
			switch (s.next().toLowerCase()) {
				case "w":
					type = 2;
					break;
				case "c":
					type = 3;
					break;
				default:
					type = 1;
			}
		}
		return new Placement(p.x, p.y, type, 0);
	}

	public static DeStack parseDestack(Scanner s) {
		position p = new position(s.next());
		position e = new position(s.next());
		int direction = p.Direction(e);
		boolean positive;
		boolean vertical;
		switch (direction) {
			case 0:
				positive = true;
				vertical = true;
				break;
			case 1:
				positive = true;
				vertical = false;
				break;
			case 2:
				positive = false;
				vertical = true;
				break;
			case 3:
				positive = false;
				vertical = false;
				break;
			default:
				return null;
		}
		List<Integer> l = new ArrayList<>(10);
		while (s.hasNext()) {
			try {


				l.add(Integer.parseInt(s.next()));
			} catch (Exception exc) {
				//ignore
			}
		}
		int sum = 0;
		for (Integer i : l) {
			sum += i;
		}
		return new DeStack(p.x, p.y, l, sum, vertical, positive, 0);
	}

	public static class position {
		public int x;
		public int y;

		public position(String s) {
			s = s.toLowerCase();
			switch (s.charAt(0)) {
				case 'a':
					x = 0;
					break;
				case 'b':
					x = 1;
					break;
				case 'c':
					x = 2;
					break;
				case 'd':
					x = 3;
					break;
				case 'e':
					x = 4;
					break;
				case 'f':
					x = 5;
					break;
				case 'g':
					x = 6;
					break;
				case 'h':
					x = 7;
					break;
				default:
					x = -1;

			}
			String temp = s.replace(s.charAt(0), '0');
			y = Integer.parseInt(temp) - 1;

		}

		/**
		 * -1 = error
		 * 0=y+
		 * 1=x+
		 * 2=y-
		 * 3=x-
		 */
		public int Direction(position p) {
			if (x == p.x) {
				if (y > p.y) {
					return 2;
				} else if (y < p.y) {
					return 0;
				}
			} else if (y == p.y) {
				if (x > p.x) {
					return 3;
				} else if (x < p.x) {
					return 1;
				}
			}
			return -1;
		}
	}

	public static void playSelf(List<TakNetwork> nets) {
		Random r = new Random();

		Thread t = new Thread() {
			@Override
			public void run() {
				PrintWriter out;

				try {
					Socket socket = new Socket("www.playtak.com", 10000);
					out = new PrintWriter(socket.getOutputStream(), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

					playGame(nets.get(r.nextInt(nets.size())), in, out, "sTAKbot1");
					in.close();
					out.close();
					socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Thread t1 = new Thread() {
			@Override
			public void run() {
				PrintWriter out;

				try {
					Socket socket = new Socket("www.playtak.com", 10000);
					out = new PrintWriter(socket.getOutputStream(), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

					acceptGame(nets.get(r.nextInt(nets.size())), in, out, "sTAKbot1");
					in.close();
					out.close();
					socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};


		try {
			t.start();
			sleep(1000);
			t1.start();
			t.join();
			t1.join();
		} catch (Exception e) {
		}


	}

	public void PING(PrintWriter w) {

		w.println("PING");

	}
}







/*
 * 
	Thanks to Zach C. for creating this list for interaction with the API and PlatTak
	API Located Here: https://github.com/chaitu236/TakServer
	# Example connection to tak server.
	
	# Connect to server
	Server says: Welcome!
	Server says: Login or Registers
	Client says: Login Username Password
	Server says: Welcome Username!
	Server says (For all): Seek new num name boardsize time W\B
	Client says: Accept num
	
	# Begining of game
	
	# During game
	
	# For place
	Client says: Game#num P Sq (C/W)
	# Note that Sq is formated letterNumber, for example A1 A2 H5 H8. Should be formated as string
	# Also, C is for capstone, w is for wall.
	# P is for place M is for move
	Server says: Game#num P Sq 
	
	# For move
	Client says: Game#num M Sq Sq num...
	# Original square, destination square, num of pices to drop, num of pics to drop ... etc
	
	# Other
	
	Client says: List
	# Sends list of seeks
	
	Client says: GameList
	# Sends list of games in progress
	
	Client says: Observe num
	# Observe this game.
	
	Client says: quit
	
	Server says: OK
	# Good move
	
	Server says: NOK
	# Bad move

 */
