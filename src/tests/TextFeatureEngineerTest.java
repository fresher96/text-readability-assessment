package tests;

import datasets.*;
import featureengineering.SimpleFeatureExtractor;
import featureengineering.TextFeatureEngineer;

import java.io.FileNotFoundException;

public class TextFeatureEngineerTest
{
	public static void main(String[] args) throws FileNotFoundException {
//		testTextCorpus();
//		testCSVWriter();
		
		testEngineer();
	}
	
	private static void testEngineer() throws FileNotFoundException {
		TextFeatureEngineer tfe = new TextFeatureEngineer();
		
		tfe.setTextCorpus(new OneStopEnglishCorpus());
		tfe.setFeatureExtractor(new SimpleFeatureExtractor());
		tfe.setFeatureFileWriter(new CSVFileWriter("etc/features.csv"));
		
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
