package tests;

import featureengineering.cleaners.ExampleTextCleaner;
import featureengineering.cleaners.MakeLowerCaseCleaner;
import featureengineering.cleaners.TextCleaner;
import datasets.*;
import datasets.corpora.LevelSeparatedTextCorpus;
import datasets.corpora.ListBasedTextCorpus;
import datasets.corpora.OneStopEnglishCorpus;
import datasets.corpora.WeeBitOriginalCorpus;
import datasets.writers.CSVFileWriter;
import datasets.writers.DefaultCSVFeatureFile;
import datasets.writers.FeatureFileWriter;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import featureengineering.extractors.FeatureExtractor;
import featureengineering.extractors.PrototypeFeatureExtractorAdapter;
import featureengineering.extractors.SampleFeatureExtractor;
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
//		FeatureExtractor extractor = new SampleFeatureExtractor();
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
		
		TextCleaner cleaner;
		cleaner = new ExampleTextCleaner();
		cleaner = new MakeLowerCaseCleaner(cleaner);
//		cleaner = new SomeOtherCleaner(cleaner);
		
		TextFeatureEngineer tfe = new TextFeatureEngineer();
		
		OneStopEnglishCorpus corpus = new OneStopEnglishCorpus();
		corpus.setRandom(new Random(123));
		corpus.setClassLimit(10);
		
		
		tfe.setTextCorpus(corpus);
		tfe.setFeatureFileWriter(new DefaultCSVFeatureFile());
		tfe.setFeatureExtractor(new SampleFeatureExtractor());
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
