package prototype;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main
{
	public static void main(String[] args) throws IOException {
		
		String path = "../datasets/OneStopEnglishCorpus/Texts-SeparatedByReadingLevel/test-dir/";
		
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		
		for (File file : listOfFiles)
		{
			if (file.isFile() && file.getName().endsWith(".txt"))
			{
				//System.out.println(file.getName());
				//System.out.println(file.getPath());
				
				FileReader fileReader = new FileReader(file);
				//List<String> lines = Files.readAllLines(Paths.get("file"), StandardCharsets.UTF_8);
				List<String> lines = Files.readAllLines(Paths.get(file.getPath()));
				for (String line : lines)
				{
					//System.out.println("\"" + line + "\"");
					
				}
			}
			
			
		}
		
		
	}
}

