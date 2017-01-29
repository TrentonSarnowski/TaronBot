package com.company.TaronBot;

import com.company.TaronBot.Game.Board;
import com.company.TaronBot.Game.Move;
import com.company.TaronBot.Game.Moves.DeStack;
import com.company.TaronBot.Game.Moves.Placement;
import com.company.TaronBot.Game.RunGames;
import com.company.TaronBot.Network.TakNetwork;
import sun.security.krb5.internal.crypto.Des;
import tech.deef.Tools.StaticGlobals;

import java.io.*;
import java.net.Socket;
import java.util.*;

import static java.lang.Thread.sleep;

public class ServerCommunication {


	public static Boolean cont = true;

	public static void online(RunGames.bot[] bots) {
		try {
			Socket socket = new Socket("www.playtak.com", 10000);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			cont = true;
			Thread t = new Thread() {
				@Override
				public void run() {

					while (cont) {
						try {
							sleep(5000);

						} catch (Exception e) {
							e.printStackTrace();
						}
						playSelf(bots);
					}

				}
			};

			//	commands.start();
			out.println("Login sTAKbot " + StaticGlobals.Password);

			t.start();
			Thread ts = new Thread() {
				@Override
				public void run() {
					while (true) {
						try {
							if (!cont) {
								return;
							}
							out.println("PING");

							sleep(15000);
						} catch (Exception e) {

						}
					}
				}
			};
			ts.start();

			while (cont) {

				WaitForGame(bots[0].getNet(), in, out, "sTAKbot");
			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
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
						try {
							sleep(5000);

						} catch (Exception e) {
							e.printStackTrace();
						}
						playSelf(l);
					}

				}
			};

			//	commands.start();
			out.println("Login sTAKbot " + StaticGlobals.Password);

			t.start();
			Thread ts = new Thread() {
				@Override
				public void run() {

						try {
							if (!cont) {
								return;
							}
							out.println("PING");

						} catch (Exception e) {

						}

				}
			};
			ts.start();

			while (cont) {

				WaitForGame(l.get(0), in, out, "sTAKbot");
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
			//sleep(100);
		} catch (Exception e) {

		}
		if (s.contains(playerName)) {
			if (sc.hasNext() && sc.next().equals("Game")) {
				if (sc.hasNext() && sc.next().equals("Start")) {
					int no = sc.nextInt();
					int size = sc.nextInt();
					if (size > 9 || size < 5) {
						out.println("Game#" + no + " Resign");
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

	private static void WaitForGame(TakNetwork player, BufferedReader in, PrintWriter out, String id) {
		Board b = seekGame(player, in, out, id);

		if (b.control()) {

			playGame(player, in, out, b);

		} else {
			Move l = null;

			while (l == null) {
				String ss = "";
				try {
					ss = in.readLine();
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
					return;
				} catch (Exception e) {
					out.println("Game#" + b.boardNumber + " Resign");
					return;
				}
			}

			l.performMove(b, false);

			playGame(player, in, out, b);

		}
	}

	private static int findSeek(BufferedReader in, String o) throws Exception {
		Integer i = null;
		while (true) {
			String s = in.readLine();

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

			playGame(player, in, out, b);

		} else {
			Move l = null;

			while (l == null) {
				String ss = "";
				try {
					ss = in.readLine();
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
					return;
				} catch (Exception e) {
					out.println("Game#" + b.boardNumber + " Resign");
					return;
				}
			}

			l.performMove(b, false);

			playGame(player, in, out, b);

		}

	}

	private static void playGame(TakNetwork player, BufferedReader in, PrintWriter out, Board b) {
		System.err.println("Game Started");
		Move l;
		List<Move> move=new ArrayList<>();

		boolean checkOutput = false;
		Game:
		while (true) {
			if (checkOutput) {
				System.err.println("Generating Moves");
			}
			List<Move> moves = player.calculate(b.getAIMap(true),b);

			Move s = null;//b.checkForVictory(true);
			if (s != null) {
				moves.add(0, s);
				if (checkOutput) {
					System.err.println("victory found");
				}
			}
			if (checkOutput) {
				System.err.println("finished move generation: " + moves.size());
			}
			boolean ExitCheck = true;

			hi:
			for (Move m : moves) {

				if (!cont) {
					out.println("Game#" + b.boardNumber + " Resign");
					return;
				}
				if (m.checkFeasible(b, true)&&((move.size()<10)||!m.isEqual(move.get(move.size()-10)))) {

					if (checkOutput) {
						System.err.println("try Move" + m.toPlayTakString());
					}

					out.println("Game#" + b.boardNumber + " " + m.toPlayTakString());

					String input;

					moveFailed:
					do {

						try {

							input = in.readLine();
							if (checkOutput) {
								System.err.println("checking input" + input);
							}
							if (input.equals("NOK")) {
								if (checkOutput) {
									System.err.println("Move failed");
								}
								break moveFailed;
							} else if (input.contains("Over") && input.contains("Game#" + b.boardNumber)) {
								if (checkOutput) {
									System.err.println("Game Over");
								}
								Scanner sn = new Scanner(input);
								sn.next();
								sn.next();
								switch (sn.next().toLowerCase().charAt(0)) {
									case 'r':
									case '1':
									case 'f':
										player.addWins();
									case '0':
									default:

								}
								break Game;


							} else {
								l = parseMove(input, b.boardNumber);
								if (l != null) {
									if (checkOutput) {
										System.err.println("Move performed: " + b.control() + ": " + m.toString() + ": " + m.getWeight());
									}
									if (checkOutput) {
										System.err.println("Next move Reached: " + l.toPlayTakString());
									}

									m.performMove(b, true);
									move.add(m);
									l.performMove(b, false);

									sleep(500);

									ExitCheck = false;
									break hi;
								}
							}
						} catch (GameOverException e) {

							switch (e.getMessage().toLowerCase().charAt(0)) {
								case 'r':
								case '1':
								case 'f':
									break;
								default:
									player.addWins();


							}
							break Game;
						} catch (Exception e) {
							out.println("Game#" + b.boardNumber + " Resign");
							break Game;
						}
					} while (l == null);


				}
			}
			if (ExitCheck) {
				out.println("Game#" + b.boardNumber + " Resign");
				return;
			}

		}
	}

	private static Board seekGame(TakNetwork player, BufferedReader in, PrintWriter out, String id) {
		System.err.println("Seeking Game");
		String s = "";
		Board b = null;
		out.println("Seek " + player.getWidth() + " 600 0");
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

		Scanner n = new Scanner(s);
		try {


		} catch (Exception e) {

		}
		if (n.hasNext() && n.next().equals("Game#" + number)) {

			if (!n.hasNext()) return null;
			switch (n.next().toLowerCase()) {
				case "abandoned":
				case "resign":

					throw new GameOverException(n.next());
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
					out.println("Login sTAKbot1 " + StaticGlobals.Password);

					WaitForGame(nets.get(r.nextInt(nets.size())), in, out, "sTAKbot1");
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
					out.println("Login sTAKbot2 " + StaticGlobals.Password);


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
			t1.start();
			t.join();
			t1.join();
		} catch (Exception e) {
		}


	}

	public static void playSelf(RunGames.bot[] bots) {

		Random r = new Random();

		Thread t = new Thread() {
			@Override
			public void run() {
				PrintWriter out;

				try {
					Socket socket = new Socket("www.playtak.com", 10000);
					out = new PrintWriter(socket.getOutputStream(), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					out.println("Login sTAKbot1 " + StaticGlobals.Password);

					WaitForGame(bots[r.nextInt(bots.length - 1)].getNet(), in, out, "sTAKbot1");
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
					out.println("Login sTAKbot2 " + StaticGlobals.Password);


					acceptGame(bots[r.nextInt(bots.length - 1)].getNet(), in, out, "sTAKbot1");
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
