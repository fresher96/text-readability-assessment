package featureengineering;

import cleaners.automatic.TextCleaner;
import datasets.*;
import datasets.corpora.TextCorpus;
import datasets.writers.FeatureWriter;
import shared.Timer;

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
		
		System.out.print("\n\n\n");
		
		int index = -1;
		int total = textCorpus.size();
		int errors = 0;
		
		Timer chunkTimer = new Timer();
		Timer timer = new Timer();
		
		featureWriter.writeHeaders(featureExtractor.getFeatureList());
		Iterator<Document> iterator = textCorpus.iterator();
		
		chunkTimer.start();
		timer.start();
		
		while (iterator.hasNext())
		{
			index++;
			
			try
			{
				Document document = iterator.next();
				
				String spaces = "    ";
				System.out.printf("processing (%d/%d): %-30.30s %s [%s]\n", index+1, total, document.getName(), spaces, document.getPath());
				
				if (textCleaner != null) textCleaner.clean(document);
//				System.out.println(document.getText() + "\n\n\n");
				
				List<Object> features = featureExtractor.extract(document.getText());
				featureWriter.process(document, features);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				errors++;
			}
			
			if(textCorpus.chunk() != -1 && (index + 1)%textCorpus.chunk() == 0)
			{
				chunkTimer.stop();
				
				System.out.println("\n..........................................................................................................");
				System.out.printf("time elapsed: %s\n", chunkTimer.toString());
				System.out.println("..........................................................................................................\n\n\n\n\n");
				
				chunkTimer.start();
			}
		}
		
		timer.stop();
		
		System.out.println("\n\n\n.........................................");
		System.out.printf("time elapsed: %s\n", timer.toString());
		System.out.printf("there were %d/%d errors\n", errors, total);
	}
	
	//endregion
}
