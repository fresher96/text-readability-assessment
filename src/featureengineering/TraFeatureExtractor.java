package featureengineering;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import nlp.*;
import nlp.stanford.StanfordNlpParseTreeAdapter;
import nlp.stanford.StanfordNlpParserAdapter;
import shared.Debugger;
import shared.Pair;
import shared.observer.Observable;
import shared.observer.Observer;
import shared.utils.TregexUtils;

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






class WordCount implements Observer<NlpToken>
{
	@Override
	public void update(Observable<NlpToken> o, NlpToken arg) {
		System.out.println(arg.getRaw());
	}
}

