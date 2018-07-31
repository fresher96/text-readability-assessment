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
	
	public TextCorpus getTextCorpus() {
		return textCorpus;
	}
	
	public void setTextCorpus(TextCorpus textCorpus) {
		this.textCorpus = textCorpus;
	}
	
	public FeatureExtractor getFeatureExtractor() {
		return featureExtractor;
	}
	
	public void setFeatureExtractor(FeatureExtractor featureExtractor) {
		this.featureExtractor = featureExtractor;
	}
	
	public FeatureFileWriter getFeatureFileWriter() {
		return featureFileWriter;
	}
	
	public void setFeatureFileWriter(FeatureFileWriter featureFileWriter) {
		this.featureFileWriter = featureFileWriter;
	}
	
	public void run() throws FileNotFoundException {
		
		int index = 0;
		int total = textCorpus.size();
		
		featureFileWriter.writeHeaders(featureExtractor.getFeatureList());
		
		for(Document document : textCorpus)
		{
			index++;
			String spaces = "    ";
			System.out.printf("processing (%d/%d): %-30.30s %s [%s]\n", index, total, document.getName(), spaces, document.getPath());
			
			List<Object> features = featureExtractor.extract(document.getText());
			featureFileWriter.process(document, features);
		}
	}
}
