package fileio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DirectoryFilesReader implements DataFileReader
{
	private FileHandler fileHandler;
	
	@Override
	public void open() throws IOException {
		String path = "../datasets/OneStopEnglishCorpus/Texts-SeparatedByReadingLevel/test-dir/";
		
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		
		for (File file : listOfFiles) {
		
		}
	}
	
	@Override
	public void close() {
	
	}
	
	@Override
	public boolean hasNext() {
		return false;
	}
	
	@Override
	public String next() {
		return null;
	}
}
