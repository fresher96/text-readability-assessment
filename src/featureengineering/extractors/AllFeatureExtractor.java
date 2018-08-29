package featureengineering.extractors;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import featureengineering.featuresets.LinguisticFeatureSet;
import featureengineering.featuresets.SampleFeatureSet;
import nlp.NlpParser;
import nlp.stanford.StanfordNlpParserAdapter;

import java.util.List;
import java.util.Properties;

public class AllFeatureExtractor implements FeatureExtractor
{
	private ObservableFeatureExtractor extractor;
	
	public AllFeatureExtractor() {
		
		// setting the parser
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, depparse, ner, parse, dcoref");
		
		StanfordCoreNLP stanfordCoreNLP = new StanfordCoreNLP(props);
		NlpParser nlpParser = new StanfordNlpParserAdapter(stanfordCoreNLP);
		
		extractor = new ObservableFeatureExtractor(nlpParser);
		
		
		//
		extractor.addSentenceFeatureSet(new LinguisticFeatureSet());
		
	}
	
	
	
	@Override
	public List<String> getFeatureList() {
		return extractor.getFeatureList();
	}
	
	@Override
	public List<Object> extract(String text) {
		return extractor.extract(text);
	}
}
