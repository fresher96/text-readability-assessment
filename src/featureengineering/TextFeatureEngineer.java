package featureengineering;

import datasets.Document;
import datasets.FeatureFileWriter;
import datasets.TextCleaner;
import datasets.TextCorpus;

import java.io.FileNotFoundException;
import java.util.List;

public class TextFeatureEngineer
{
	private TextCorpus textCorpus;
	private FeatureFileWriter featureFileWriter;
	private FeatureExtractor featureExtractor;
	private TextCleaner textCleaner;
	
	/* constructors */
	
	public TextFeatureEngineer(){
		this(null, null, null, null);
	}
	
	public TextFeatureEngineer(TextCorpus textCorpus, FeatureFileWriter featureFileWriter, FeatureExtractor featureExtractor) {
		this(textCorpus, featureFileWriter, featureExtractor, null);
	}
	
	public TextFeatureEngineer(TextCorpus textCorpus, FeatureFileWriter featureFileWriter, FeatureExtractor featureExtractor, TextCleaner textCleaner) {
		setTextCorpus(textCorpus);
		setFeatureFileWriter(featureFileWriter);
		setFeatureExtractor(featureExtractor);
		setTextCleaner(textCleaner);
	}
	
	/* setters & getters */
	
	public TextCleaner getTextCleaner() {
		return textCleaner;
	}
	
	public void setTextCleaner(TextCleaner textCleaner) {
		this.textCleaner = textCleaner;
	}
	
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
	
	/* methods */
	
	public void run() throws FileNotFoundException {
		
		int index = 0;
		int total = textCorpus.size();
		
		featureFileWriter.writeHeaders(featureExtractor.getFeatureList());
		
		for(Document document : textCorpus)
		{
			index++;
			String spaces = "    ";
			System.out.printf("processing (%d/%d): %-30.30s %s [%s]\n", index, total, document.getName(), spaces, document.getPath());
			
			if(textCleaner != null) textCleaner.clean(document);
//			System.out.println(document.getText());
			
			List<Object> features = featureExtractor.extract(document.getText());
			featureFileWriter.process(document, features);
		}
	}
}
