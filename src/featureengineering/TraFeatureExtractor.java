package featureengineering;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import featureengineering.featuresets.FeatureSet;
import featureengineering.featuresets.LinguisticFeatureSet;
import nlp.*;
import nlp.stanford.NlpTemp;
import nlp.stanford.StanfordNlpParserAdapter;
import shared.observer.Observable;
import shared.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.ToLongBiFunction;

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
		
		
		extractor.getFeatureList();
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
	
	private Observable[] observables = new Observable[]{annotationObservable, sentenceObservable, tokenObservable, parseTreeObservable};
	
	
	private List<FeatureSet<NlpTemp>> annotationFeatureSets;
	
//	private List<FeatureSet> featureSets = new ArrayList<List<FeatureSet>>(annotationFeatureSets);
	
	
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
	
	//region methods
	
	<T> void doit(Observable<T> observable) {
		for (Observer<T> observer : observable)
		{
			System.out.println(observer.getClass().getSimpleName());
		}
	}
	
	@Override
	public List<String> getFeatureList() {
		for (Observable observable : observables)
		{
			for (Object observer : observable)
			{
			
			}
		}
		
		for (Observable observable : observables)
		{
			doit(observable);
		}
		
		
		for(FeatureSet fs : annotationFeatureSets)
		{
			fs.getFeatureList();
		}
		
		return null;
	}
	
	@Override
	public List<Object> extract(String text) {
		
		NlpAnnotation document = nlpParser.annotate(text);
		annotationObservable.notifyObservers(document);
		for(FeatureSet fs : annotationFeatureSets)
		{
		
		}
		
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
	
	//endregion
}
