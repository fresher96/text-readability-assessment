package featureengineering;

import datasets.LabeledDocument;
import datasets.TextCorpus;
import fileio.DataFileReader;
import fileio.FeatureFileWriter;
import javafx.util.Pair;
import prototype.NlpFeatureSet;

import java.util.List;

public class TextFeatureEngineer
{
	private TextCorpus textCorpus;
	private FeatureExtractor featureExtractor;
	private FeatureFileWriter featureFileWriter;
	
	public void run()
	{
		int index = 0;
		int total = textCorpus.size();
		
		featureFileWriter.writeHeaders(featureExtractor.getFeatureList());
		
		for(LabeledDocument labeledDocument : textCorpus)
		{
			index++;
			
			System.out.printf("processing (%d/%d): %s\n", index, total, document.getPath());
			List <Object> features = featureExtractor.extract(document.getText());
			
			featureFileWriter.process(document);
			featureFileWriter.process(features);
		}
	}
	
}


class FeatureEngineer
{
//	private DataSet dataSet;
//	private FeatureFileWriter featureFileWriter;
//	private FeatureExtractor featureExtractor;
//
//	public FeatureEngineer() {
//
//	}
//
//	public void run() {
//		while(dataSet.hasNext())
//		{
//		}
//	}
}
