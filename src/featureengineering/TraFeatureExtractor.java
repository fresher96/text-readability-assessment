package featureengineering;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import nlp.*;
import nlp.stanford.*;
import shared.Debugger;
import shared.Pair;
import shared.utils.TregexUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

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
	private NlpParser nlpParser;
	
	private Observable<NlpAnnotation> annotationObservable = new Observable<>();
	private Observable<NlpToken> tokenObservable = new Observable<>();
	private Observable<NlpParseTree> parseTreeObservable = new Observable<>();
	
	private <T> void addObserver(Observable<T> observable, Observer<T> observer) {
		observable.addObserver(observer);
	}
	
	public void addTokenObserver(Observer<NlpToken> observer) {
		tokenObservable.addObserver(observer);
	}
	
	public void addAnnotationObserver(Observer<NlpAnnotation> observer) {
		annotationObservable.addObserver(observer);
	}
	
	public void addParseTreeObserver(Observer<NlpParseTree> observer) {
		addObserver(parseTreeObservable, observer);
	}
	
	public TraFeatureExtractor() {
		setParser(null);
	}
	
	public void setParser(NlpParser nlpParser) {
		this.nlpParser = nlpParser;
	}
	
	@Override
	public List<String> getFeatureList() {
		return null;
	}
	
	@Override
	public List<Object> extract(String text) {
		
		NlpAnnotation document = nlpParser.annotate(text);
		notify(annotationObservable, document);
		
		List<NlpSentence> sentenceList = document.getSentenceList();
		for (NlpSentence sentence : sentenceList)
		{
			// not sen
			
			List<NlpToken> tokenList = sentence.getTokenList();
			for (NlpToken token : tokenList)
			{
				tokenObservable.notifyObservers(token);
			}
			
			NlpParseTree tree = sentence.getParseTree();
			notify(parseTreeObservable, tree);

//			SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
			// not depend
		}

//		Map<Integer, CorefChain> graph = document.get(CorefCoreAnnotations.CorefChainAnnotation.class);
		// not graph
		
		return null;
	}
	
	private <T> void notify(Observable<T> observable, T arg) {
		if (observable.countObservers() > 0)
		{
			observable.notifyObservers(arg);
		}
	}
}


interface Feature
{
	
	default String getName() {
		return this.getClass().getSimpleName();
	}
	
}

class ClauseCountFeature implements Feature, Observer<NlpParseTree>
{
	
	private int clauseCount;
	private int wordCount;
	
	public int getClauseCount() {
		return clauseCount;
	}
	
	public int getWordCount() {
		return wordCount;
	}
	
	public ClauseCountFeature() {
		clauseCount = 0;
		wordCount = 0;
	}
	
	
	@Override
	public void update(Observable<NlpParseTree> o, NlpParseTree arg) {
//		nClause += count("S|SINV|SQ < (VP <# MD|VBD|VBP|VBZ)", tree, tpc) + count("FRAG > ROOT !<< VP", tree, tpc);
		
		Tree tree = ((StanfordNlpParseTreeAdapter)arg).getTree();
		Pair<Integer, Integer> res = TregexUtils.count("S|SINV|SQ < (VP <# MD|VBD|VBP|VBZ)", tree);
		clauseCount += res.getFirst();
		wordCount += res.getSecond();
	}
}


class LinguisticFeatureSet implements Observer<NlpParseTree>
{
	
	ClauseCountFeature clauses = new ClauseCountFeature();
	
	@Override
	public void update(Observable<NlpParseTree> o, NlpParseTree arg) {
		clauses.update(o, arg);
	}
	
	public List<String> getFeatureNames() {
//		List<String> ret =
		return null;
	}
	
	public List<Object> getFeatures() {
		List<Object> ret = new ArrayList<>();
		ret.add(clauses.getClauseCount());
		ret.add(clauses.getWordCount() / (double) clauses.getClauseCount());
		
		Debugger.debug(ret);
		
		return ret;
	}
}






interface Observer<T>
{
	void update(Observable<T> o, T arg);
}

class WordCount implements Observer<NlpToken>
{
	@Override
	public void update(Observable<NlpToken> o, NlpToken arg) {
		System.out.println(arg.getRaw());
	}
}

class Observable<T>
{
	private Vector<Observer<T>> obs;
	
	public Observable() {
		obs = new Vector<>();
	}
	
	public synchronized void addObserver(Observer<T> o) {
		if (o == null)
			throw new NullPointerException();
		if (!obs.contains(o))
		{
			obs.addElement(o);
		}
	}
	
	public synchronized void deleteObserver(Observer<T> o) {
		obs.removeElement(o);
	}
	
	public void notifyObservers(T arg) {
		/*
		 * a temporary array buffer, used as a snapshot of the state of
		 * current Observers.
		 */
		Object[] arrLocal;
		
		synchronized (this)
		{
			/* We don't want the Observer doing callbacks into
			 * arbitrary code while holding its own Monitor.
			 * The code where we extract each Observable from
			 * the Vector and store the state of the Observer
			 * needs synchronization, but notifying observers
			 * does not (should not).  The worst result of any
			 * potential race-condition here is that:
			 * 1) a newly-added Observer will miss a
			 *   notification in progress
			 * 2) a recently unregistered Observer will be
			 *   wrongly notified when it doesn't care
			 */
			arrLocal = obs.toArray();
		}
		
		for (int i = arrLocal.length - 1; i >= 0; i--)
			((Observer) arrLocal[i]).update(this, arg);
	}
	
	public synchronized void deleteObservers() {
		obs.removeAllElements();
	}
	
	public synchronized int countObservers() {
		return obs.size();
	}
}
