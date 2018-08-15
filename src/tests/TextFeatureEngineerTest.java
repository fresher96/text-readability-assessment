package tests;

import datasets.*;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import featureengineering.FeatureExtractor;
import featureengineering.PrototypeFeatureExtractorAdapter;
import featureengineering.SimpleFeatureExtractor;
import featureengineering.TextFeatureEngineer;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class TextFeatureEngineerTest
{
	public static void main(String[] args) throws IOException {
//		testTextCorpus();
//		testCSVWriter();

//		testEngineer();
		
		
		runWeeBitPrototypeFeatures();

//		LevelSeparatedTextCorpus corpus = new WeeBitOriginalCorpus();
//		corpus.setClassLimit(3);
////		corpus.setRandom(null);
//
//
//		FeatureExtractor extractor = new SimpleFeatureExtractor();
//
//
//		TextFeatureEngineer tfe = new TextFeatureEngineer(corpus, new DefaultCSVFeatureFile(), extractor);
//		tfe.run();
	}
	
	private static void runWeeBitPrototypeFeatures() throws IOException {
		
		prototype.FeatureExtractor prototypeFeatures = new prototype.FeatureExtractor();
		
		prototype.TraditionalFeatureSet traditionalFeatureSet = new prototype.TraditionalFeatureSet();
		traditionalFeatureSet.addAllFeatures();
		prototypeFeatures.featureSetList.add(traditionalFeatureSet);
		
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		prototype.NlpFeatureSet nlpFeatureSet = new prototype.NlpFeatureSet(pipeline);
		prototypeFeatures.featureSetList.add(nlpFeatureSet);
		
		FeatureExtractor extractor = new PrototypeFeatureExtractorAdapter(prototypeFeatures);
		
		
		
		
		LevelSeparatedTextCorpus corpus = new WeeBitOriginalCorpus();
		corpus.setClassLimit(3);
//		corpus.setRandom(new Random(18));
		
		FeatureFileWriter writer = new DefaultCSVFeatureFile();
		
		TextCleaner cleaner = new MakeLowerCaseCleaner();
		
		
		TextFeatureEngineer engineer = new TextFeatureEngineer(corpus, writer, extractor, cleaner);
		engineer.run();
	}
	
	private static void testEngineer() throws IOException {
		
		TextCleanerDecorator cleaner = null;
		cleaner = new TextCleanerDecorator(new MakeLowerCaseCleaner());
//		cleaner = new TextCleanerDecorator(cleaner);
		
		
		TextFeatureEngineer tfe = new TextFeatureEngineer();
		
		OneStopEnglishCorpus corpus = new OneStopEnglishCorpus();
		corpus.setRandom(new Random(123));
		corpus.setClassLimit(10);
		
		
		tfe.setTextCorpus(corpus);
		tfe.setFeatureFileWriter(new DefaultCSVFeatureFile());
		tfe.setFeatureExtractor(new SimpleFeatureExtractor());
		tfe.setTextCleaner(cleaner);
		
		tfe.run();
	}
	
	private static void testCSVWriter() {
		
		FeatureFileWriter ffw = new CSVFileWriter("etc/test.csv");
		
		String str = "do\\cu'me\"n$t\"\\";
		String res = ((CSVFileWriter) ffw).prepare(str);
		System.out.println(res);
	}
	
	private static void testTextCorpus() {
		ListBasedTextCorpus textCorpus = new OneStopEnglishCorpus();
		for (Document doc : textCorpus)
		{
			System.out.println(doc.getName() + " " + doc.getLabel() + " " + doc.getPath());
			System.out.println(doc.getText());
		}
	}
}
