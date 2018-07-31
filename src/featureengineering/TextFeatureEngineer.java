package featureengineering;

import datasets.Document;
import datasets.FeatureFileWriter;
import datasets.TextCorpus;

import java.io.FileNotFoundException;
import java.util.List;

public class TextFeatureEngineer
{
	private TextCorpus textCorpus;
	private FeatureExtractor featureExtractor;
	private FeatureFileWriter featureFileWriter;
	
	public void run() throws FileNotFoundException {
		
		int index = 0;
		int total = textCorpus.size();
		
		featureFileWriter.writeHeaders(featureExtractor.getFeatureList());
		
		for(Document document : textCorpus)
		{
			index++;
			
			System.out.printf("processing (%d/%d): %s [%s]\n", index, total, document.getName(), document.getPath());
			List<Object> features = featureExtractor.extract(document.getText());
			
			featureFileWriter.process(document, features);
		}
	}
}
