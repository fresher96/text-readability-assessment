package nlp.stanford;

import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import edu.stanford.nlp.trees.tregex.TregexPatternCompiler;
import nlp.*;
import shared.Debugger;
import shared.Pair;

public class StanfordNlpParseTreeAdapter implements NlpParseTree
{
	private Tree stanfordTree;
	
	public StanfordNlpParseTreeAdapter(Tree stanfordTree) {
		this.stanfordTree = stanfordTree;
	}
	
	public Tree getTree(){
		return stanfordTree;
	}
	
	
	
	//region TregexUtils
	
	private static TregexPatternCompiler tregexCompiler = new TregexPatternCompiler();
	
	public Pair<Integer, Integer> runTregex(String pattern) {
		
		TregexPattern tp = tregexCompiler.compile(pattern);
		TregexMatcher tm = tp.matcher(stanfordTree);
		
		Pair<Integer, Integer> ret = new Pair<>(0, 0);
		while (tm.find())
		{
			Tree subTree = tm.getMatch();
			int wordCount = subTree.getLeaves().size();
			
			ret.setFirst(ret.getFirst() + 1);
			ret.setSecond(ret.getSecond() + wordCount);
		}
		
		return ret;
	}
	
	//endregion
}
