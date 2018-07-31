package tests;

import datasets.Document;
import datasets.LevelSeparatedTextCorpus;
import datasets.OneStopEnglishCorpus;
import datasets.TextCorpus;

public class TextFeatureEngineerTest
{
	public static void main(String[] args) {
		testTextCorpus();
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
