package tests;

import datasets.Document;
import datasets.LevelSeparatedTextCorpus;
import datasets.TextCorpus;

public class Test
{
	public static void main(String[] args){
		TextCorpus textCorpus = new LevelSeparatedTextCorpus();
		for(Document doc : textCorpus)
		{
			System.out.println(doc.getName() + " " + doc.getLabel() + " " + doc.getPath());
			System.out.println(doc.getText());
		}
	}
}
