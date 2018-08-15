package featureengineering;

import datasets.*;

import javax.xml.soap.Text;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

public class TextFeatureEngineer
{
	private TextCorpus textCorpus;
	private FeatureWriter featureWriter;
	private FeatureExtractor featureExtractor;
	private TextCleaner textCleaner;
	
	//region constructors
	
	public TextFeatureEngineer() {
		this(null, null, null, null);
	}
	
	public TextFeatureEngineer(TextCorpus textCorpus, FeatureWriter featureWriter, FeatureExtractor featureExtractor) {
		this(textCorpus, featureWriter, featureExtractor, null);
	}
	
	public TextFeatureEngineer(TextCorpus textCorpus, FeatureWriter featureWriter, FeatureExtractor featureExtractor, TextCleaner textCleaner) {
		setTextCorpus(textCorpus);
		setFeatureFileWriter(featureWriter);
		setFeatureExtractor(featureExtractor);
		setTextCleaner(textCleaner);
	}
	
	//endregion
	
	//region setters
	
	public void setTextCleaner(TextCleaner textCleaner) {
		this.textCleaner = textCleaner;
	}
	
	public void setTextCorpus(TextCorpus textCorpus) {
		this.textCorpus = textCorpus;
	}
	
	public void setFeatureExtractor(FeatureExtractor featureExtractor) {
		this.featureExtractor = featureExtractor;
	}
	
	public void setFeatureFileWriter(FeatureWriter featureWriter) {
		this.featureWriter = featureWriter;
	}
	
	//endregion
	
	//region methods
	
	public void run() throws FileNotFoundException {
		
		int index = 0;
		int total = textCorpus.size();
		
		featureWriter.writeHeaders(featureExtractor.getFeatureList());
		
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
				featureWriter.process(document, features);
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
		
		featureWriter.writeHeaders(featureExtractor.getFeatureList());
		
		for (Document document : textCorpus)
		{
			index++;
			String spaces = "    ";
			System.out.printf("processing (%d/%d): %-30.30s %s [%s]\n", index, total, document.getName(), spaces, document.getPath());
			
			if (textCleaner != null) textCleaner.clean(document);
//			System.out.println(document.getText());
			
			List<Object> features = featureExtractor.extract(document.getText());
			featureWriter.process(document, features);
		}
	}
	
	//endregion
}
