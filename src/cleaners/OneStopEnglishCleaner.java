package cleaners;

import shared.MyUtils;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.*;
import java.util.*;

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


//		groupText();

//		buildCharsMap();

//		cleanCharacters();
		
//		lastCheck();
		
		/**
		 * @caution
		 * after all of this cleaning, some hand cleaning contained:
		 * 1) `WNL-World-Cup-*.txt` line 11: 've' -> 'five'
		 *
		 */
	}
	
	private static void lastCheck() throws IOException {
		
		String[] levelDirs = new String[]{"0_Elementary\\", "1_Intermediate\\", "2_Advanced\\"};
		String dir = "D:\\work space\\datasets\\OSE_cleaned\\5_texts_final\\";
		int nLevel = 3;
		
		Set<Character> visited = new HashSet<>();
		for (int level = 0; level < nLevel; level++)
		{
			List<File> fileList = MyUtils.getFiles(dir + levelDirs[level]);
			
			for (File file : fileList)
			{
				String text = MyUtils.readAllText(file);
				int line = 1, col = 0;
				
				for (int i = 0; i < text.length(); i++)
				{
					char ch = text.charAt(i);
					col++;
					
					if (ch == ' ') continue;
					else if (ch == '\n')
					{
						col = 0;
						line++;
						continue;
					}
					else if (33 <= ch && ch <= 126) continue;
					else if(visited.contains(ch)) continue;
					
					visited.add(ch);
					System.out.printf("strange character \"%c\" (%d), \t in file %s, \t line: %d, col: %d\n", ch, (int) ch, file.getName(), line, col);
				}
			}
		}
	}
	
	
	private static void cleanCharacters() throws IOException {
		Map<Character, String> charMap = new HashMap<>();
		loadMap(charMap);
		
		String outputDir = "D:\\work space\\datasets\\OSE_cleaned\\5_texts_final\\";
		String[] levelDirs = new String[]{"0_Elementary\\", "1_Intermediate\\", "2_Advanced\\"};
		String dir = "D:\\work space\\datasets\\OSE_cleaned\\4_texts\\";
		
		int nLevel = 3;
		
		for (int level = 0; level < nLevel; level++)
		{
			List<File> fileList = MyUtils.getFiles(dir + levelDirs[level]);
			
			for (File file : fileList)
			{
				String text = MyUtils.readAllText(file);
				StringBuilder stringBuilder = new StringBuilder();
				
				for (int i = 0; i < text.length(); i++)
				{
					char ch = text.charAt(i);
					
					String str = ch + "";
					if (charMap.containsKey(ch))
						str = charMap.get(ch);
					
					stringBuilder.append(str);
				}
				
				String newFile = outputDir + levelDirs[level] + file.getName();
				MyUtils.writeAllText(stringBuilder.toString(), new File(newFile));
			}
		}
	}
	
	private static void buildCharsMap() throws IOException {

//		for (int i = 0; i < 256; i++)
//		{
//			System.out.println(i + " ");
//			MyUtils.debug((char)i);
//		}
		
		Map<Character, String> charMap = new HashMap<>();
		
		loadMap(charMap);
		
		String outputFile = "D:\\work space\\datasets\\OSE_cleaned\\5_texts\\";
		
		String[] levelDirs = new String[]{"0_Elementary\\", "1_Intermediate\\", "2_Advanced\\"};
		
		String dir = "D:\\work space\\datasets\\OSE_cleaned\\4_texts\\";
		
		int nLevel = 3;
		
		List<File> fileList = new ArrayList<>();
		for (int i = 0; i < nLevel; i++)
		{
			fileList.addAll(MyUtils.getFiles(dir + levelDirs[i]));
		}
		Collections.reverse(fileList);
		
		
		boolean halt = false;
		for (File file : fileList)
		{
			String text = MyUtils.readAllText(file);
			int line = 1, col = 0;
			
			for (int i = 0; i < text.length(); i++)
			{
				char ch = text.charAt(i);
				col++;
				
				if (ch == ' ') continue;
				else if (ch == '\n')
				{
					col = 0;
					line++;
					continue;
				}
				else if (33 <= ch && ch <= 126) continue;
				else if (charMap.containsKey(ch)) continue;
				
				System.out.printf("strange character \"%c\" (%d), \t in file %s, \t line: %d, col: %d\n", ch, (int) ch, file.getName(), line, col);
				System.out.printf("want to map it? ");
				
				char in = MyUtils.readChar();
				if (in == 'x') // halt, and save map
				{
					halt = true;
					break;
				}
				else if (in == 'n') continue; // don't map
				else if (in == 'i') // map ch to itself
				{
					charMap.put(ch, ch + "");
					continue;
				}
				else if (in == 's') break; // skip file
				
				System.out.printf("enter new string: ");
				String nw = MyUtils.readString();
				
				charMap.put(ch, nw);
			}
			
			if (halt) break;
		}
		
		
		storeMap(charMap);
	}
	
	private static void loadMap(Map<Character, String> charMap) throws IOException {
		Properties prop = new Properties();
		InputStream input = new FileInputStream("etc/charMap_OSE.properties");
		
		prop.load(input);
		
		charMap.clear();
		for (String key : prop.stringPropertyNames())
		{
			String value = prop.getProperty(key);
//			System.out.printf("\"%c\" (%d) => \"%s\"\n", key.charAt(0), (int)key.charAt(0), value);
			
			charMap.put(key.charAt(0), value);
		}
//		System.exit(1);
	}
	
	private static void storeMap(Map<Character, String> charMap) throws IOException {
		Properties prop = new Properties();
		OutputStream output = new FileOutputStream("etc/charMap_OSE.properties");
		
		for (Map.Entry<Character, String> entry : charMap.entrySet())
		{
			prop.setProperty(entry.getKey().toString(), entry.getValue());
		}
		
		prop.store(output, "character map for OSE dataset");
	}
	
	private static void groupText() throws Exception {
		String outputDir = "D:\\work space\\datasets\\OSE_cleaned\\4_texts\\";
		
		String[] levelDirs = new String[]{"0_Elementary\\", "1_Intermediate\\", "2_Advanced\\"};
		String[] suffixArray = new String[]{"-ele.txt", "-int.txt", "-adv.txt"};
		
		String dir = "D:\\work space\\datasets\\OSE_cleaned\\2_csv_readable\\";
		
		int nLevel = 3;
		
		
		List<File> fileList = MyUtils.getFiles(dir);
		
		for (File file : fileList)
		{
			Instances instances = ConverterUtils.DataSource.read(file.getPath());
//			MyUtils.debug(instances.size());
			
			for (int level = 0; level < nLevel; level++)
			{
				StringBuilder stringBuilder = new StringBuilder();
				for (int line = 0; line < instances.size(); line++)
				{
					Instance instance = instances.get(line);
					if (instance.isMissing(level)) continue;
					
					String str = instance.stringValue(level);
					
					stringBuilder.append(str);
					stringBuilder.append("\n");
				}

//				System.out.println(stringBuilder.toString());
				
				String filePath = outputDir + levelDirs[level] +
						file.getName()
								.replaceAll(" ", "-")
								.replaceAll("\\.csv", suffixArray[level]);
				
				MyUtils.writeAllText(stringBuilder.toString(), new File(filePath));
			}
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
