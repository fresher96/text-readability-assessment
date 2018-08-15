package shared.utils;

import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import edu.stanford.nlp.trees.tregex.TregexPatternCompiler;
import shared.Debugger;
import shared.Pair;

import java.util.List;

public class TregexUtils
{
	private static TregexPatternCompiler tregexCompiler = new TregexPatternCompiler();
	
	private TregexUtils() {
	
	}
	
	public static Pair<Integer, Integer> count(String pattern, Tree tree) {
		
		TregexPattern tp = tregexCompiler.compile(pattern);
		TregexMatcher tm = tp.matcher(tree);
		
		Pair<Integer, Integer> ret = new Pair<>(0, 0);
		while (tm.find())
		{
			Tree subTree = tm.getMatch();
			int wordCount = subTree.getLeaves().size();
			
			Debugger.debug(subTree.getLeaves());
			
			ret.setFirst(ret.getFirst() + 1);
			ret.setSecond(ret.getSecond() + wordCount);
		}
		
		return ret;
	}
}
