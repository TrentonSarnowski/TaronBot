package tech.deef.Tools;

public class Tools {
	
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_BLACK = "\u001B[30m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_BLUE = "\u001B[34m";
	private static final String ANSI_PURPLE = "\u001B[35m";
	private static final String ANSI_CYAN = "\u001B[36m";
	private static final String ANSI_WHITE = "\u001B[37m";
	
	public static void Print(String string, String escape){
		System.out.print(escape + string + ANSI_RESET);
	}
	
	public static void PrintColor(String string, String color){
		switch(color.toLowerCase()){
		case "black":
			System.out.print(ANSI_BLACK + string + ANSI_RESET);
			break;
		case "red":
			System.out.print(ANSI_BLACK + string + ANSI_RESET);
			break;
		case "green":
			System.out.print(ANSI_BLACK + string + ANSI_RESET);
			break;
		case "yellow":
			System.out.print(ANSI_BLACK + string + ANSI_RESET);
			break;
		case "blue":
			System.out.print(ANSI_BLACK + string + ANSI_RESET);
			break;
		case "purple":
			System.out.print(ANSI_BLACK + string + ANSI_RESET);
			break;
		case "cyan":
			System.out.print(ANSI_BLACK + string + ANSI_RESET);
			break;
		case "white":
			System.out.print(ANSI_BLACK + string + ANSI_RESET);
			break;
		default:
			System.out.print(string + ANSI_RESET);
		}
	}
	
	
	
	
	
	
}
