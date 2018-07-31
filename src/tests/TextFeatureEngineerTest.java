package tests;

import datasets.*;
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
		
		testEngineer();
		
		
		
//		TextFeatureEngineer tfe = new TextFeatureEngineer();
//
//		tfe.setTextCorpus(new OneStopEnglishCorpus());
//		tfe.setFeatureExtractor(new SimpleFeatureExtractor());
//		tfe.setFeatureFileWriter(new DefaultCSVFeatureFile());
//
//		tfe.run();
	}
	
	private static void testEngineer() throws IOException {
		TextFeatureEngineer tfe = new TextFeatureEngineer();
		
		OneStopEnglishCorpus corpus = new OneStopEnglishCorpus();
		corpus.setRandom(new Random(123));
		corpus.setClassLimit(10);
		
		tfe.setTextCorpus(corpus);
		tfe.setFeatureExtractor(new SimpleFeatureExtractor());
		tfe.setFeatureFileWriter(new DefaultCSVFeatureFile());
		
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
