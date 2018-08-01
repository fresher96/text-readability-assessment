package tests;

import datasets.*;
import featureengineering.FeatureExtractor;
import featureengineering.SimpleFeatureExtractor;
import featureengineering.TextFeatureEngineer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

public class TextFeatureEngineerTest
{
	public static void main(String[] args) throws IOException {
//		testTextCorpus();
//		testCSVWriter();
		
//		testEngineer();
		
		
		LevelSeparatedTextCorpus corpus = new WeeBitOriginalCorpus();
		corpus.setClassLimit(600);
//		corpus.setRandom(null);


		FeatureExtractor extractor = new SimpleFeatureExtractor();


		TextFeatureEngineer tfe = new TextFeatureEngineer(corpus, new DefaultCSVFeatureFile(), extractor);
		tfe.run();
	}
	
	private static void testEngineer() throws IOException {
		
		TextCleanerDecorator cleaner = null;
		cleaner = new TextCleanerDecorator(new MakeLowerCaseCleaner());
//		cleaner = new TextCleanerDecorator(cleaner);
		
		
		TextFeatureEngineer tfe = new TextFeatureEngineer();
		
		OneStopEnglishCorpus corpus = new OneStopEnglishCorpus();
		corpus.setRandom(new Random(123));
		corpus.setClassLimit(10);
		
		
		tfe.setTextCorpus(corpus);
		tfe.setFeatureFileWriter(new DefaultCSVFeatureFile());
		tfe.setFeatureExtractor(new SimpleFeatureExtractor());
		tfe.setTextCleaner(cleaner);
		
		tfe.run();
	}
	
	private static void testCSVWriter() {
		
		FeatureFileWriter ffw = new CSVFileWriter("etc/test.csv");
		
		String str = "do\\cu'me\"n$t\"\\";
		String res = ((CSVFileWriter) ffw).prepare(str);
		System.out.println(res);
	}
	
	private static void testTextCorpus() {
		TextCorpus textCorpus = new OneStopEnglishCorpus();
		for (Document doc : textCorpus)
		{
			System.out.println(doc.getName() + " " + doc.getLabel() + " " + doc.getPath());
			System.out.println(doc.getText());
		}
	}
}
