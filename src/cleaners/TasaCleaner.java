package cleaners;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TasaCleaner
{
	public static void main(String[] args) throws IOException {
		TasaCleaner cleaner = new TasaCleaner();
		cleaner.clean();
	}
	
	private void clean() throws IOException {
		String path;
		
		path = "../datasets/TASA/tasa/tasa.pt1.txt";
		clean(path);
		
		path = "../datasets/TASA/tasa/tasa.pt2.txt";
		clean(path);
	}
	
	private void clean(String path) throws IOException {
		List<String> lineList = Files.readAllLines(Paths.get(path));
		PrintStream ps;
		
		int fileIndex = 0;
		for(String line : lineList)
		{
			line = line.trim();
			if(line.isEmpty()) continue;
			
			if(line.startsWith("[") && line.endsWith("]"))
			{
				
				ps = new PrintStream(new FileOutputStream(""));
				continue;
			}
			
		}
	}
}
