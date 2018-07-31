package cleaners;

import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class UTF8Converter
{
	public void convertDirectory(String path, String outputDirPath) throws IOException {
		File dir = new File(path);
		List<File> fileList = Arrays.asList(dir.listFiles());
		
		for(File file : fileList)
		{
			if (file.isFile() && file.getName().endsWith(".csv"))
			{
				byte[] sourceBytes = Files.readAllBytes(Paths.get(file.getPath()));
				
				String data = new String(sourceBytes , "Windows-1252");
				
				Files.write(Paths.get(outputDirPath + file.getName()), data.getBytes(StandardCharsets.UTF_8));
			}
		}
	}
}
