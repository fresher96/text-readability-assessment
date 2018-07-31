package tests;

import datasets.*;

public class TextFeatureEngineerTest
{
	public static void main(String[] args) {
//		testTextCorpus();
		testCSVWriter();
	}
	
	private static void testCSVWriter() {
		
		FeatureFileWriter ffw = new CSVFileWriter("etc/test.csv");
		
//		String str = "do\\cu'me\"n$t\"\\";
//		String res = ((CSVFileWriter) ffw).prepare(str);
//		System.out.println(res);
	
	
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
