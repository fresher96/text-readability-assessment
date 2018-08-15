package featureengineering;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import featureengineering.featuresets.LinguisticFeatureSet;
import nlp.*;
import nlp.stanford.StanfordNlpParserAdapter;
import shared.observer.Observable;
import shared.observer.Observer;

import java.util.List;
import java.util.Properties;

class Test
{
	public static void main(String[] args) {
		
		
		Properties props = new Properties();
//		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, depparse, ner, parse, dcoref");
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse");
		
		StanfordCoreNLP stanfordCoreNLP = new StanfordCoreNLP(props);
		NlpParser nlpParser = new StanfordNlpParserAdapter(stanfordCoreNLP);
		
		
		TraFeatureExtractor extractor = new TraFeatureExtractor();
		extractor.setParser(nlpParser);
		
		LinguisticFeatureSet linguistic = new LinguisticFeatureSet();
		extractor.addParseTreeObserver(linguistic);
		
		extractor.extract("We use it when a girl in our dorm is acting like a spoiled child.");
		
		linguistic.getFeatures();
	}
}

public class TraFeatureExtractor implements FeatureExtractor
{
	//region fields
	
	private NlpParser nlpParser;
	
	private Observable<NlpAnnotation> annotationObservable = new Observable<>();
	private Observable<NlpSentence> sentenceObservable = new Observable<>();
	private Observable<NlpToken> tokenObservable = new Observable<>();
	private Observable<NlpParseTree> parseTreeObservable = new Observable<>();
	
	//endregion
	
	//region addObserver methods
	
	public void addAnnotationObserver(Observer<NlpAnnotation> observer) {
		annotationObservable.addObserver(observer);
	}
	
	public void addSentenceObservaber(Observer<NlpSentence> observer) {
		sentenceObservable.addObserver(observer);
	}
	
	public void addTokenObserver(Observer<NlpToken> observer) {
		tokenObservable.addObserver(observer);
	}
	
	public void addParseTreeObserver(Observer<NlpParseTree> observer) {
		parseTreeObservable.addObserver(observer);
	}
	
	//endregion
	
	//region constructors
	
	public TraFeatureExtractor() {
		setParser(null);
	}
	
	public void setParser(NlpParser nlpParser) {
		this.nlpParser = nlpParser;
	}
	
	//endregion
	
	@Override
	public List<String> getFeatureList() {
		return null;
	}
	
	@Override
	public List<Object> extract(String text) {
		
		NlpAnnotation document = nlpParser.annotate(text);
		annotationObservable.notifyObservers(document);
		
		List<NlpSentence> sentenceList = document.getSentenceList();
		for (NlpSentence sentence : sentenceList)
		{
			sentenceObservable.notifyObservers(sentence);
			
			List<NlpToken> tokenList = sentence.getTokenList();
			for (NlpToken token : tokenList)
			{
				tokenObservable.notifyObservers(token);
			}
			
			NlpParseTree tree = sentence.getParseTree();
			parseTreeObservable.notifyObservers(tree);


//			SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
			// not depend
		}

//		Map<Integer, CorefChain> graph = document.get(CorefCoreAnnotations.CorefChainAnnotation.class);
		// not graph
		
		return null;
	}
}
