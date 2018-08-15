package nlp.stanford;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import nlp.*;

import java.util.ArrayList;
import java.util.List;

public class StanfordNlpSentenceAdapter implements NlpSentence
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

