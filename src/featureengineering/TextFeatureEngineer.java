package featureengineering;

import datasets.Document;
import datasets.FeatureFileWriter;
import datasets.TextCleaner;
import datasets.TextCorpus;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

public class TextFeatureEngineer
{
	private TextCorpus textCorpus;
	private FeatureFileWriter featureFileWriter;
	private FeatureExtractor featureExtractor;
	private TextCleaner textCleaner;
	
	/* constructors */
	
	public TextFeatureEngineer() {
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
		
		Iterator<Document> iterator = textCorpus.iterator();
		
		long startTime = System.nanoTime();
		while (iterator.hasNext())
		{
			try
			{
				Document document = iterator.next();
				
				index++;
				String spaces = "    ";
				System.out.printf("processing (%d/%d): %-30.30s %s [%s]\n", index, total, document.getName(), spaces, document.getPath());
				
				if (textCleaner != null) textCleaner.clean(document);
//				System.out.println(document.getText());
				
				List<Object> features = featureExtractor.extract(document.getText());
				featureFileWriter.process(document, features);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		long elapsedTime = System.nanoTime() - startTime;
		long nanoPerSec = 1000 * 1000 * 1000;
		
		System.out.println("\n..........................................................................................................");
		System.out.printf("time elapsed: %02d:%02d\n", elapsedTime / nanoPerSec / 60, elapsedTime / nanoPerSec % 60);
		System.out.println("..........................................................................................................\n\n\n\n\n");
	}
	
	public void run1() throws FileNotFoundException {
		
		int index = 0;
		int total = textCorpus.size();
		
		featureFileWriter.writeHeaders(featureExtractor.getFeatureList());
		
		for (Document document : textCorpus)
		{
			index++;
			String spaces = "    ";
			System.out.printf("processing (%d/%d): %-30.30s %s [%s]\n", index, total, document.getName(), spaces, document.getPath());
			
			if (textCleaner != null) textCleaner.clean(document);
//			System.out.println(document.getText());
			
			List<Object> features = featureExtractor.extract(document.getText());
			featureFileWriter.process(document, features);
		}
	}
}
