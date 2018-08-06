package featureengineering;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class NlpFeatureSet
{
	
}

class NlpFeatureSet1
{
	Observable sentences;
	Observable parseTrees;
	TokenObservable tokens;
	
	public List<Object> extract(String document) {
		
		Annotation doc = new Annotation(document);
		
		StanfordCoreNLP nlpParser = new StanfordCoreNLP();
		nlpParser.annotate(doc);
		
		List<CoreMap> sentenceList = doc.get(CoreAnnotations.SentencesAnnotation.class);
		
		for (CoreMap sentence : sentenceList)
		{
			Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
			
			List<CoreLabel> tokenList = sentence.get(CoreAnnotations.TokensAnnotation.class);
			for (CoreLabel token : tokenList)
			{
				tokens.notifyObservers(token);
			}
		}
		
		
		/* returning results */
		
		List<Object> ret = new ArrayList<>();
		return ret;
	}
	
	public void addTokenObserver(Observer tokenFeature) {
		tokens.addObserver(tokenFeature);
	}
}

class TokenObservable extends Observable
{
	@Override
	public void notifyObservers(Object arg) {
		setChanged();
		super.notifyObservers(arg);
	}
}

class WordCountFeature implements Observer
{
	int wordCount = 0;
	int adjCount = 0;
	
	@Override
	public void update(Observable o, Object arg) {
		wordCount++;
		
		CoreLabel token = (CoreLabel)arg;
		if(token.get(CoreAnnotations.PartOfSpeechAnnotation.class).equals("JJ"))
			adjCount++;
	}
	
	public List<Object> getFeatures(){
		// return wordCount, adjCount
		return null;
	}
}

class WordCountFeatureSub extends WordCountFeature
{
	public List<Object> getFeatures(){
		// return adjCount
		return null;
	}
}

class Main
{
	public void main() {
		NlpFeatureSet1 nlp = new NlpFeatureSet1();
		nlp.addTokenObserver(new WordCountFeature());
	}
}
