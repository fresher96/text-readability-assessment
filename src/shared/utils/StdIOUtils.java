package shared.utils;

import java.util.Scanner;

public class StdIOUtils
{
	private StdIOUtils(){
	
	}
	
	private static Scanner scanner = new Scanner(System.in);
	
	public static String readString() {
		return scanner.next();
	}
	
	public static char readChar() {
		return readString().charAt(0);
	}
	
	public static int readInt() {
		return scanner.nextInt();
	}
}
