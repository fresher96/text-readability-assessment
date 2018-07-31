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
		
		featureFileWriter.writeHeaders(featureExtractor);
		
		for(LabeledDocument labeledDocument : textCorpus)
		{
			index++;
			
			System.out.printf("processing (%d/%d): %s\n", index, total, document.getName());
			
			
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
