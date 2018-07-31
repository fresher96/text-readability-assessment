package shared;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MyUtils
{
	private MyUtils() {
	
	}
	
	private static Scanner sc = new Scanner(System.in);
	
	public static String readString() {
		return sc.next();
	}
	
	public static char readChar() {
		return readString().charAt(0);
	}
	
	public static int readInt() {
		return sc.nextInt();
	}
	
	public static void debug(Object obj) {
		System.out.println("\"" + obj.toString() + "\"");
	}
	
	public static List<File> getFiles(String dirPath){
		File dir = new File(dirPath);
		List<File> fileList = Arrays.asList(dir.listFiles());
		return fileList;
	}
	
	public static List<String> getLines(File file) throws IOException {
		return Files.readAllLines(Paths.get(file.getPath()));
	}
	
	public static String readAllTextIgnoreEmptyLines(File file) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		List<String> lines = getLines(file);
		for(String line : lines)
		{
			if(line.equals("")) continue;
			stringBuilder.append(line);
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}
	
	public static String readAllText(File file) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		List<String> lines = getLines(file);
		for(String line : lines)
		{
			stringBuilder.append(line);
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}
	
	public static void writeAllText(String newText, File file) throws FileNotFoundException {
		PrintStream printStream = new PrintStream(file);
		printStream.print(newText);
	}
}
