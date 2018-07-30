package cleaners;

import shared.MyUtils;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class OneStopEnglishCleaner
{
	
	public static void main(String[] args) throws Exception {

//		convertToUTF8();
		
		/**
		 * @caution
		 * after running this method,
		 * `WNL JMW Turner.csv` was cleaned by hand
		 * it contains an extra ',' at the end of each line
		 */
//		fixLineFeeds();

//		checkReadability();

//		checkReadability("D:\\work space\\datasets\\OSE_cleaned\\2_csv_readable\\0test.csv", true);
		
		
		
		
		cleanCharacters();
		
//		groupText();
	}
	
	private static void cleanCharacters() {
		
		String outputDir;
		outputDir = "D:\\work space\\datasets\\OSE_cleaned\\3_csv_final\\";
		
		String dir;
		dir = "D:\\work space\\datasets\\OSE_cleaned\\2_csv_readable\\";
		
		List<File> fileList = MyUtils.getFiles(dir);
		
		for(File file : fileList)
		{
		
		}
	}
	
	private static void fixLineFeeds() throws IOException {
		
		String outputDir;
		outputDir = "D:\\work space\\datasets\\OSE_cleaned\\2_csv_readable\\";
		
		String dir;
		dir = "D:\\work space\\datasets\\OSE_cleaned\\1_csv_utf8\\";
		
		List<File> fileList = MyUtils.getFiles(dir);
		
		for (File file : fileList)
		{
			String text = MyUtils.readAllText(file);
			StringBuilder stringBuilder = new StringBuilder();
			
			stringBuilder.append("Elementary,Intermediate,Advanced\n");
			int cnt = 0;
			int idx = 0;
			while (text.charAt(idx) != '\n')
			{
				if (text.charAt(idx) == ',') cnt++;
				idx++;
			}
			idx++;

//			if(cnt != 2)
//			{
//				System.out.println(file.getName());
//			}
			
			boolean open = false;
			boolean skip = false;
			for (; idx < text.length(); idx++)
			{
				char ch = text.charAt(idx);
				
				if (ch == '\n')
				{
					if (open)
					{
						stringBuilder.append(' ');
						continue;
					}
				}
				else if (skip)
				{
					skip = false;
				}
				else if (ch == '\"')
				{
					if (!open)
					{
						open = true;
					}
					else if (idx + 1 < text.length() && text.charAt(idx + 1) == '\"')
					{
						skip = true;
						stringBuilder.append('\\');
						continue;
					}
					else
					{
						open = false;
					}
				}
				
				stringBuilder.append(ch);
			}
			
			String newText = stringBuilder.toString();
//			System.out.println(newText);
			MyUtils.writeAllText(newText, new File(outputDir + file.getName()));
		}
	}
	
	private static void checkReadability() throws Exception {
		
		String dirPath;
		dirPath = "D:\\work space\\datasets\\OSE_cleaned\\2_csv_readable\\";
		
		File dir = new File(dirPath);
		List<File> fileList = Arrays.asList(dir.listFiles());
		
		for (File file : fileList)
		{
			try
			{
				checkReadability(file.getPath(), false);
			}
			catch (Exception ex)
			{
				System.out.println("problem with: " + file.getName());
				System.out.println(ex.getMessage());
				
				System.out.println("continue?");
				char ch = MyUtils.readChar();
				if (ch != 'y') break;
			}
		}
	}
	
	private static void checkReadability(String filePath, boolean debug) throws Exception {
		
		Instances data = ConverterUtils.DataSource.read(filePath);
		
		if (debug) System.out.println(data);
		
		String str = data.get(0).stringValue(0);
		
		for (int i = 0; i < str.length(); i++)
		{
			if (debug) System.out.println("\"" + str.charAt(i) + "\" " + (int) (str.charAt(i)));
			
			if (str.charAt(i) == '’')
			{
				//System.out.println("yeaah");
			}
		}
	}
	
	private static void convertToUTF8() throws IOException {
		String baseDir = "..\\datasets\\OneStopEnglishCorpus\\Texts-Together-OneCSVperFile\\";
		String outputDir = "..\\datasets\\OSE_cleaned\\";
		
		UTF8Converter converter = new UTF8Converter();
		converter.convertDirectory(baseDir, outputDir);
	}
	
	
	public static void stuff() throws Exception {

//		for(int i=0; i<256; i++)
//		{
//			System.out.println(i + " " + (char)i);
//		}
		
		String dir = "";
		Instances data = ConverterUtils.DataSource.read(dir + "java.csv");
		
		System.out.println(data);

//		System.out.println("\n\n" + data.get(1).stringValue(0));
		
		String str = data.get(0).stringValue(0);


//		List<String> lines = Files.readAllLines(Paths.get(dir + "Everest.csv"), StandardCharsets.UTF_8);
//		String str = lines.get(2);
		
		
		for (int i = 0; i < str.length(); i++)
		{
			System.out.println("\"" + str.charAt(i) + "\" " + (int) (str.charAt(i)));
			
			if (str.charAt(i) == '’')
			{
				System.out.println("yeaah");
			}
		}


//		StanfordAPI nlp = new StanfordAPI();
//		nlp.run(str);
//
//		str = "what's \"amazon\" doing ...";
//		nlp.run(str);
	}
	
}
