package tests;

import cleaners.automatic.MakeLowerCaseCleaner;
import cleaners.automatic.TextCleaner;
import datasets.corpora.LevelSeparatedTextCorpus;
import datasets.corpora.OneStopEnglishCorpus;
import datasets.corpora.TextCorpus;
import datasets.writers.DefaultCSVFeatureFile;
import datasets.writers.FeatureFileWriter;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import featureengineering.TextFeatureEngineer;
import featureengineering.extractors.TraFeatureExtractor;
import featureengineering.featuresets.LinguisticFeatureSet;
import nlp.NlpParser;
import nlp.stanford.StanfordNlpParserAdapter;

import java.io.IOException;
import java.util.Properties;

public class TraFeatureExtractorTest
{
	public static void main(String[] args) throws IOException {
		
		LevelSeparatedTextCorpus corpus = new OneStopEnglishCorpus();
		corpus.setClassLimit(3);
//		corpus.setRandom(new Random(18));
		
		FeatureFileWriter writer = new DefaultCSVFeatureFile();
		TextCleaner cleaner = new MakeLowerCaseCleaner();
		
		
		Properties props = new Properties();
//		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, depparse, ner, parse, dcoref");
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse");
		StanfordCoreNLP stanfordCoreNLP = new StanfordCoreNLP(props);
		NlpParser nlpParser = new StanfordNlpParserAdapter(stanfordCoreNLP);
		TraFeatureExtractor extractor = new TraFeatureExtractor(nlpParser);
		
		
		
		extractor.addSentenceFeatureSet(new LinguisticFeatureSet());
		
		
		
		
		TextFeatureEngineer engineer = new TextFeatureEngineer(corpus, writer, extractor, cleaner);
		engineer.run();
	}
}
