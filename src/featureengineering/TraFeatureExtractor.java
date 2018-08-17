package featureengineering;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import featureengineering.featuresets.FeatureSet;
import featureengineering.featuresets.LinguisticFeatureSet;
import featureengineering.featuresets.LinguisticFeatureSett;
import nlp.*;
import nlp.NlpItem;
import nlp.stanford.StanfordNlpParserAdapter;
import org.omg.PortableServer.POAPackage.ObjectAlreadyActiveHelper;
import shared.Debugger;
import shared.observer.Observable;
import shared.observer.Observer;

import java.util.ArrayList;
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
		
		LinguisticFeatureSett linguistic = new LinguisticFeatureSett();
		extractor.addSentenceFeatureSet(linguistic);
		
		List<Object> features = extractor.extract("We use it when a girl in our dorm is acting like a spoiled child.");
		
		
		Debugger.debug(extractor.getFeatureList());
		Debugger.debug(features);
		
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
	
	
	private List<FeatureSet<NlpAnnotation>> annotationFeatureSets = new ArrayList<>();
	private List<FeatureSet<NlpSentence>> sentenceFeatureSets = new ArrayList<>();
	private List<FeatureSet<NlpToken>> tokenFeatureSets = new ArrayList<>();
	
	
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
	
	public void addAnnotationFeatureSet(FeatureSet<NlpAnnotation> featureSet) {
		annotationFeatureSets.add(featureSet);
	}
	
	public void addSentenceFeatureSet(FeatureSet<NlpSentence> featureSet) {
		sentenceFeatureSets.add(featureSet);
	}
	
	public void addTokenFeatureSet(FeatureSet<NlpToken> featureSet) {
		tokenFeatureSets.add(featureSet);
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
	
	private <T> void add(List<T> to, List<T> from) {
		to.addAll(from);
	}
	
	private <T extends NlpItem> void addFeatureList(List<String> to, List<FeatureSet<T>> TFeatureSets) {
		for (FeatureSet<T> featureSet : TFeatureSets)
		{
			add(to, featureSet.getFeatureList());
		}
	}
	
	private <T extends NlpItem> void addFeatures(List<Object> to, List<FeatureSet<T>> TFeatureSets) {
		for (FeatureSet<T> featureSet : TFeatureSets)
		{
			add(to, featureSet.getFeatures());
		}
	}
	
	
	private <T extends NlpItem> void update(List<FeatureSet<T>> TFeatureSets, T arg) {
		for (FeatureSet<T> featureSet : TFeatureSets)
		{
			featureSet.update(arg);
		}
	}
	
	@Override
	public List<String> getFeatureList() {
		List<String> ret = new ArrayList<>();
		
		addFeatureList(ret, annotationFeatureSets);
		addFeatureList(ret, sentenceFeatureSets);
		addFeatureList(ret, tokenFeatureSets);
		
		return ret;
	}
	
	@Override
	public List<Object> extract(String text) {
		
		NlpAnnotation document = nlpParser.annotate(text);
		update(annotationFeatureSets, document);
		
		if(sentenceFeatureSets.size() > 0 || tokenFeatureSets.size() > 0)
		{
			List<NlpSentence> sentenceList = document.getSentenceList();
			for (NlpSentence sentence : sentenceList)
			{
				update(sentenceFeatureSets, sentence);
				
				if(tokenFeatureSets.size() > 0)
				{
					List<NlpToken> tokenList = sentence.getTokenList();
					for (NlpToken token : tokenList)
					{
						update(tokenFeatureSets, token);
					}
				}
			}
		}
		
		
		
		List<Object> ret = new ArrayList<>();
		
		addFeatures(ret, annotationFeatureSets);
		addFeatures(ret, sentenceFeatureSets);
		addFeatures(ret, tokenFeatureSets);
		
		return ret;
	}
	
	public List<Object> extract11111(String text) {
		
		NlpAnnotation document = nlpParser.annotate(text);
		annotationObservable.notifyObservers(document);
		for (FeatureSet fs : annotationFeatureSets)
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
