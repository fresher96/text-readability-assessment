package prototype;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import shared.Debugger;
import shared.StringHelper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Prototype
{
	public static void main(String[] args) throws IOException {
		
		Prototype prototype = new Prototype();
		String path;

//		path = "../datasets/OneStopEnglishCorpus/Texts-SeparatedByReadingLevel/test-dir/";
//		prototype.processDir(path);
		
		//prototype.featureExtractorTest();
		//prototype.featureExtractorTest2();
		
		
		//prototype.processOSE();
		prototype.processWeeBit();
	}
	
	private void processWeeBit() throws IOException {
		
		maxNumberOfFilesToProcess = 50;
		
		System.out.printf("\n\n");
		
		long nanoPerSec = 1L * 1000 * 1000 * 1000;
		long startTime = System.nanoTime();
		String[] dirArray = {"WRLevel2", "WRLevel3", "WRLevel4", "BitKS3", "BitGCSE"};
		for (String dir : dirArray)
		{
			String path = String.format("../datasets/WeeBit/WeeBit-TextOnly/WeeBit-TextOnly/%s/", dir);
			
			long startTime2 = System.nanoTime();
			processDir(path);
			long elapsedTime = System.nanoTime() - startTime2;
			
			System.out.println("\n..........................................................................................................");
			System.out.printf("finished directory %s, time elapsed: %02d:%02d\n", dir, elapsedTime / nanoPerSec / 60, elapsedTime / nanoPerSec % 60);
			System.out.println("..........................................................................................................\n\n\n\n\n");
		}
		
		long elapsedTime = System.nanoTime() - startTime;
		System.out.printf("total time elapsed: %02d:%02d\n", elapsedTime / nanoPerSec / 60, elapsedTime / nanoPerSec % 60);
	}
	
	private void processOSE() throws IOException {
		
		maxNumberOfFilesToProcess = 30;
		
		System.out.printf("\n\n");
		
		long nanoPerSec = 1L * 1000 * 1000 * 1000;
		long startTime = System.nanoTime();
		String[] dirArray = {"Ele-Txt", "Int-Txt", "Adv-Txt"};
		for (String dir : dirArray)
		{
			String path = String.format("../datasets/OneStopEnglishCorpus/Texts-SeparatedByReadingLevel/%s/", dir);
			
			long startTime2 = System.nanoTime();
			processDir(path);
			long elapsedTime = System.nanoTime() - startTime2;
			
			System.out.println("\n..........................................................................................................");
			System.out.printf("finished directory %s, time elapsed: %02d:%02d\n", dir, elapsedTime / nanoPerSec / 60, elapsedTime / nanoPerSec % 60);
			System.out.println("..........................................................................................................\n\n\n\n\n");
		}
		
		long elapsedTime = System.nanoTime() - startTime;
		System.out.printf("total time elapsed: %02d:%02d\n", elapsedTime / nanoPerSec / 60, elapsedTime / nanoPerSec % 60);
	}
	
	private void featureExtractorTest2() {
		String test;
		test = "hi my name is Sue, how you do?\r\n" +
				"Increased cloud cover in periods of normally clear weather is closing Lukla Airport, the gateway to the Everest region, more often. A new road for 4x4s is being built to Lukla to guarantee the flow of tourists and their money, but Byers is worried that the rapid spread of the road network in Nepal is being done too cheaply, with disastrous consequences in terms of soil erosion and landslides.\r\n" +
				"Everest is the icon everyone knows, he says. Its the perfect laboratory for figuring out how";
		
		test = "hi my name is Sue, how you do? \r\n now is a good time!";
		
		TraditionalFeatureSet traditionalFeatureSet = new TraditionalFeatureSet();
		traditionalFeatureSet.addCharacterCountFeature();
		traditionalFeatureSet.addWordCountFeature();
		traditionalFeatureSet.addWordCountFeature();
		traditionalFeatureSet.addCharacterCountFeature();
		
		for (TraditionalFeatureSet.Features feature : traditionalFeatureSet.features)
		{
			System.out.println(feature.ordinal() + " " + feature);
		}
		
		traditionalFeatureSet.features.forEach(feature -> System.out.println(feature.ordinal() + " " + feature));
		
		traditionalFeatureSet.extract(test).forEach(arg -> System.out.println(arg));
	}
	
	private void featureExtractorTest() {
		String test;
		test = "hi my name is Sue, how you do?\r\n" +
				"Increased cloud cover in periods of normally clear weather is closing Lukla Airport, the gateway to the Everest region, more often. A new road for 4x4s is being built to Lukla to guarantee the flow of tourists and their money, but Byers is worried that the rapid spread of the road network in Nepal is being done too cheaply, with disastrous consequences in terms of soil erosion and landslides.\r\n" +
				"Everest is the icon everyone knows, he says. Its the perfect laboratory for figuring out how";
		
		test = "hi my name is Sue, how you do? \r\n now is a good time!";
		
		FeatureExtractorOld featureExtractor = new FeatureExtractorOld();
		
		featureExtractor.featureList.add(new WordCountFeature());
		featureExtractor.featureList.add(new WordCountFeature());
		featureExtractor.featureList.add(new CharacterCountFeature());
		featureExtractor.featureList.add(new CharacterCountFeature());
		
		// expected output: 13, 38
		List<Integer> featureVector = featureExtractor.extract(test);
		
		featureVector.forEach(integer -> System.out.println(integer));
//		for(int value : featureVector)
//		{
//			System.out.println(value);
//		}
		//featureVector.stream().filter(integer -> integer >= 0).forEachOrdered(System.out::println);
		
	}
	
	
	FeatureExtractor featureExtractor = new FeatureExtractor();
	PrintStream featuresCsvFile = new PrintStream("etc/features.csv");
	int maxNumberOfFilesToProcess = 3;
	
	public Prototype() throws FileNotFoundException {
		
		TraditionalFeatureSet traditionalFeatureSet = new TraditionalFeatureSet();
		traditionalFeatureSet.addAllFeatures();
		featureExtractor.featureSetList.add(traditionalFeatureSet);
		
		//TraditionalFeatureSet testFeatureSet = new TraditionalFeatureSet("test_feature_set");
		//testFeatureSet.addAllFeatures();
		//testFeatureSet.removeAllFeatures();
		//testFeatureSet.addWordCountFeature();
		//featureExtractor.featureSetList.add(testFeatureSet);
		
		
		addNlpFeatures();
		
		
		// writing CSV file headers
//		featuresCsvFile.print("document,");
//		for(FeatureSet featureSet : featureExtractor.featureSetList)
//		{
//			List<String> addedFeatures = featureSet.addedFeatures();
//			for(String feature : addedFeatures)
//			{
//				featuresCsvFile.print(featureSet.getName() + ",");
//			}
//		}
//		featuresCsvFile.println("label");
		
		
		featuresCsvFile.print("document,");
		for (FeatureSet featureSet : featureExtractor.featureSetList)
		{
			List<String> addedFeatures = featureSet.addedFeatures();
			for (String feature : addedFeatures)
			{
				featuresCsvFile.print(feature + ",");
			}
		}
		featuresCsvFile.println("label");
	}
	
	private void addNlpFeatures() {
		
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		
		NlpFeatureSet nlpFeatureSet = new NlpFeatureSet(pipeline);
		featureExtractor.featureSetList.add(nlpFeatureSet);
	}
	
	public void processDir(String path) throws IOException {
		
		
		//featuresCsvFile.println("hi \r\n how are you,nigga,wigga");
		
		File folder = new File(path);
		List<File> listOfFiles = Arrays.asList(folder.listFiles());
		
		//Collections.shuffle(listOfFiles);
		
		int index = 0;
		
		for (File file : listOfFiles)
		{
			if (file.isFile() && file.getName().endsWith(".txt"))
			{
				if (index == maxNumberOfFilesToProcess) break;
				index++;
				
				//System.out.println(file.getName());
				//System.out.println(file.getPath());
				
				//FileReader fileReader = new FileReader(file);
				System.out.printf("processing file %d: %s\n", index, file.getName());
				List<String> lines = Files.readAllLines(Paths.get(file.getPath()));
				StringBuilder stringBuilder = new StringBuilder();
				
				//String label = null;
				String label = file.getPath().split("\\\\")[5];
				
				for (String line : lines)
				{
					//System.out.println("\"" + line + "\"");
					
					if (label == null)
					{
						label = line.trim();
						continue;
					}
					
					stringBuilder.append(line);
					stringBuilder.append("\n");
				}
				
				
				String document = stringBuilder.toString();
				//Debugger.debug(label);
				//Debugger.debug(document);
				
				//System.out.printf("processing file %d: %s\n", index, file.getName());
				
				// writing features to features.csv
				featuresCsvFile.print(file.getName() + ",");
				for (FeatureSet featureSet : featureExtractor.featureSetList)
				{
					List<Feature> featureList = featureSet.extract(document);
					for (Feature feature : featureList)
					{
						featuresCsvFile.printf("%.6f,", feature.value);
					}
				}
				featuresCsvFile.println(label);
			}
		}
	}
}









