package featureengineering;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import javafx.beans.value.ObservableDoubleValue;
import javafx.collections.ObservableList;

import javax.xml.bind.Marshaller;
import java.awt.event.ActionListener;
import java.util.*;

class Test
{
	public static void main(String[] args) {
		
		
		
		
		
		Properties props = new Properties();
//		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, depparse, ner, parse, dcoref");
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma");
		
		StanfordCoreNLP stanfordCoreNLP = new StanfordCoreNLP(props);
		NlpParser nlpParser = new StanfordNlpParserAdapter(stanfordCoreNLP);
		
		
		TraFeatureExtractor extractor = new TraFeatureExtractor();
		extractor.setParser(nlpParser);
		
		extractor.addTokenObserver(new Observer<NlpToken>()
		{
			@Override
			public void update(Observable o, NlpToken arg) {
//				System.out.println("hi");
			}
		});
		extractor.addTokenObserver(new WordCount());
		extractor.addAnnotationObserver(new Observer<NlpAnnotation>()
		{
			@Override
			public void update(Observable o, NlpAnnotation arg) {
				System.out.println("helloo");
			}
		});
		extractor.addAnnotationObserver(new Observer<NlpAnnotation>()
		{
			@Override
			public void update(Observable o, NlpAnnotation arg) {
				System.out.println("woooorrrld");
			}
		});
		
		extractor.extract("hello my name is Sue.");
	}
}

public class TraFeatureExtractor implements FeatureExtractor
{
	private NlpParser nlpParser;
	
	private Observable<NlpAnnotation> annotationObservable = new Observable<>();
	private Observable<NlpToken> tokenObservable = new Observable<>();
	
	public void addTokenObserver(Observer<NlpToken> observer) {
		tokenObservable.addObserver(observer);
	}
	
	public void addAnnotationObserver(Observer<NlpAnnotation> observer) {
		annotationObservable.addObserver(observer);
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
			// not tree

//			SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
			// not depend
		}

//		Map<Integer, CorefChain> graph = document.get(CorefCoreAnnotations.CorefChainAnnotation.class);
		// not graph
		
		return null;
	}
	
	private <T> void notify(Observable<T> observable, T arg) {
		if(observable.countObservers() > 0)
		{
			observable.notifyObservers(arg);
		}
	}
}

interface Observer<T> {
	void update(Observable o, T arg);
}

class WordCount implements Observer<NlpToken>
{
	@Override
	public void update(Observable o, NlpToken arg) {
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
		if (!obs.contains(o)) {
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
		
		synchronized (this) {
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
		
		for (int i = arrLocal.length-1; i>=0; i--)
			((Observer)arrLocal[i]).update(this, arg);
	}
	
	public synchronized void deleteObservers() {
		obs.removeAllElements();
	}
	
	public synchronized int countObservers() {
		return obs.size();
	}
}














class StanfordNlpParserAdapter implements NlpParser
{
	
	private StanfordCoreNLP stanfordNLP;
	
	public StanfordNlpParserAdapter(StanfordCoreNLP stanfordCoreNLP) {
		this.stanfordNLP = stanfordCoreNLP;
	}
	
	@Override
	public NlpAnnotation annotate(String text) {
		Annotation doc = new Annotation(text);
		stanfordNLP.annotate(doc);
		NlpAnnotation stanfordAnnotation = new StanfordNlpAnnotationAdapter(doc);
		return stanfordAnnotation;
	}
}

class StanfordNlpAnnotationAdapter implements NlpAnnotation
{
	
	private Annotation document;
	
	public StanfordNlpAnnotationAdapter(Annotation document) {
		this.document = document;
	}
	
	@Override
	public List<NlpSentence> getSentenceList() {
		
		List<CoreMap> sentenceList = document.get(CoreAnnotations.SentencesAnnotation.class);
		List<NlpSentence> ret = new ArrayList<>(sentenceList.size());
		
		for (CoreMap sentence : sentenceList)
		{
			NlpSentence stanfordSentence = new StanfordNlpSentenceAdapter(sentence);
			ret.add(stanfordSentence);
		}
		
		return ret;
	}
}

class StanfordNlpSentenceAdapter implements NlpSentence
{
	
	private CoreMap stanfordSentence;
	
	public StanfordNlpSentenceAdapter(CoreMap stanfordSentence) {
		this.stanfordSentence = stanfordSentence;
	}
	
	@Override
	public List<NlpToken> getTokenList() {
		
		List<CoreLabel> tokenList = stanfordSentence.get(CoreAnnotations.TokensAnnotation.class);
		List<NlpToken> ret = new ArrayList<>(tokenList.size());

		for (CoreLabel token : tokenList)
		{
			NlpToken stanfordToken = new StanfordNlpTokenAdapter(token);
			ret.add(stanfordToken);
		}
		
		return ret;
	}
	
	@Override
	public NlpParseTree getParseTree() {
		Tree tree = stanfordSentence.get(TreeCoreAnnotations.TreeAnnotation.class);
		NlpParseTree stanfordTree = new StanfordNlpParseTreeAdapter(tree);
		return stanfordTree;
	}
}

class StanfordNlpTokenAdapter implements NlpToken
{
	
	private CoreLabel stanfordToken;
	
	public StanfordNlpTokenAdapter(CoreLabel stanfordToken) {
		this.stanfordToken = stanfordToken;
	}
	
	
	@Override
	public String getRaw(){
		return stanfordToken.get(CoreAnnotations.TextAnnotation.class);
	}
}

class StanfordNlpParseTreeAdapter implements NlpParseTree
{
	private Tree stanfordTree;
	
	public StanfordNlpParseTreeAdapter(Tree stanfordTree) {
		this.stanfordTree = stanfordTree;
	}
}


interface NlpParser
{
	
	NlpAnnotation annotate(String text);
}

interface NlpAnnotation
{
	List<NlpSentence> getSentenceList();
}

interface NlpSentence
{
	List<NlpToken> getTokenList();
	
	NlpParseTree getParseTree();
}

interface NlpToken
{
	String getRaw();
}

interface NlpParseTree
{

}

