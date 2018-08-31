package main;

import featureengineering.cleaners.MakeLowerCaseCleaner;
import featureengineering.cleaners.TextCleaner;
import datasets.corpora.LevelSeparatedTextCorpus;
import datasets.corpora.OneStopEnglishCorpus;
import datasets.writers.DefaultCSVFeatureFile;
import datasets.writers.FeatureFileWriter;
import featureengineering.TextFeatureEngineer;
import featureengineering.extractors.AllFeatureExtractor;

import java.io.IOException;
import java.util.Random;

public class FeatureExtraction
{
	public static void main(String[] args) throws IOException {
		
		// corpus
		LevelSeparatedTextCorpus corpus;
		corpus = new OneStopEnglishCorpus();
		corpus.setClassLimit(2);
		corpus.setRandom(new Random(0));
		
		
		// feature file
		FeatureFileWriter writer = new DefaultCSVFeatureFile();
		
		
		// cleaner
		TextCleaner cleaner = new MakeLowerCaseCleaner();
		
		
		// extractor
		AllFeatureExtractor extractor = new AllFeatureExtractor();
		
		
		// engineer
		TextFeatureEngineer engineer = new TextFeatureEngineer(corpus, writer, extractor, cleaner);
		engineer.run();
	}
}
