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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TraFeatureExtractor implements FeatureExtractor
{
	private NlpParser nlpParser;
	
	
	public TraFeatureExtractor(){
		setParser(null);
	}
	
	public void setParser(NlpParser nlpParser){
		this.nlpParser = nlpParser;
	}
	
	@Override
	public List<String> getFeatureList() {
		return null;
	}
	
	@Override
	public List<Object> extract(String text) {

		NlpAnnotation document = nlpParser.annotate(text);
		// not doc
		
		List<NlpSentence> sentenceList = document.getSentenceList();
		for (NlpSentence sentence : sentenceList)
		{
			// not sen
			
			List<NlpToken> tokenList = sentence.getTokenList();
			for (NlpToken token : tokenList)
			{
				// not token
				
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
}

class StanfordNlpParserAdapter implements NlpParser{
	
	private StanfordCoreNLP stanfordNLP;
	
	public StanfordNlpParserAdapter(StanfordCoreNLP stanfordCoreNLP){
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

class StanfordNlpAnnotationAdapter implements NlpAnnotation{
	
	private Annotation document;
	
	public StanfordNlpAnnotationAdapter(Annotation document){
		this.document = document;
	}
	
	@Override
	public List<NlpSentence> getSentenceList() {
		
		List<CoreMap> sentenceList = document.get(CoreAnnotations.SentencesAnnotation.class);
		List<NlpSentence> ret = new ArrayList<>(sentenceList.size());
		
		int index = -1;
		for(CoreMap sentence : sentenceList)
		{
			index++;
			NlpSentence stanfordSentence = new StanfordNlpSentenceAdapter(sentence);
			ret.set(index, stanfordSentence);
		}
		
		return ret;
	}
}

class StanfordNlpSentenceAdapter implements NlpSentence{
	
	private CoreMap stanfordSentence;
	
	public StanfordNlpSentenceAdapter(CoreMap stanfordSentence){
		this.stanfordSentence = stanfordSentence;
	}
	
	@Override
	public List<NlpToken> getTokenList() {
		
		List<CoreLabel> tokenList = stanfordSentence.get(CoreAnnotations.TokensAnnotation.class);
		List<NlpToken> ret = new ArrayList<>(tokenList.size());
		
		int index = -1;
		for(CoreLabel token : tokenList)
		{
			index++;
			NlpToken stanfordToken = new StanfordNlpTokenAdapter(token);
			ret.set(index, stanfordToken);
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

class StanfordNlpTokenAdapter implements NlpToken{
	
	private CoreLabel stanfordToken;
	
	public StanfordNlpTokenAdapter(CoreLabel stanfordToken) {
		this.stanfordToken = stanfordToken;
	}
}

class StanfordNlpParseTreeAdapter implements NlpParseTree
{
	private Tree stanfordTree;
	
	public StanfordNlpParseTreeAdapter(Tree stanfordTree){
		this.stanfordTree = stanfordTree;
	}
}


interface NlpParser{
	
	NlpAnnotation annotate(String text);
}

interface NlpAnnotation{
	
	List<NlpSentence> getSentenceList();
}

interface NlpSentence{
	
	List<NlpToken> getTokenList();
	
	NlpParseTree getParseTree();
}

interface NlpToken{

}

interface NlpParseTree{

}

