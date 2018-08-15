package nlp.stanford;

import edu.stanford.nlp.trees.Tree;
import nlp.*;

public class StanfordNlpParseTreeAdapter implements NlpParseTree
{
	private Tree stanfordTree;
	
	public StanfordNlpParseTreeAdapter(Tree stanfordTree) {
		this.stanfordTree = stanfordTree;
	}
	
	public Tree getTree(){
		return stanfordTree;
	}
}


