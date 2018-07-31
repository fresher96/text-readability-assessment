package shared;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MyUtils
{
	private MyUtils() {
	
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
	
	public static void debug(Object obj) {
		System.out.println("\"" + obj.toString() + "\"");
	}
	
	public static List<File> getFiles(String dirPath) {
		File dir = new File(dirPath);
		List<File> fileList = Arrays.asList(dir.listFiles());
		return fileList;
	}
	
	public static List<String> getLines(File file) throws IOException {
		try
		{
			return Files.readAllLines(Paths.get(file.getPath()), StandardCharsets.UTF_8);
		}
		catch (Exception ex)
		{
			System.err.println(file.getPath() + " is not UTF8!!!");
			List <String> ret = new ArrayList<>();
			
			byte[] sourceBytes = Files.readAllBytes(Paths.get(file.getPath()));
			String text = new String(sourceBytes , "Windows-1252");
			String[] lines = text.split("\n");
			
			ret = Arrays.asList(lines);
			return ret;
		}
	}
	
	public static String readAllTextIgnoreEmptyLines(File file) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		List<String> lines = getLines(file);
		for (String line : lines)
		{
			if (line.equals("")) continue;
			stringBuilder.append(line);
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}
	
	public static String readAllText(File file) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		List<String> lines = getLines(file);
		for (String line : lines)
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
