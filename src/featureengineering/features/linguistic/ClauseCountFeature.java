package featureengineering.features.linguistic;

import edu.stanford.nlp.trees.Tree;
import featureengineering.features.Feature;
import nlp.NlpParseTree;
import nlp.NlpSentence;
import nlp.stanford.StanfordNlpParseTreeAdapter;
import shared.Pair;
import shared.observer.Observable;
import shared.observer.Observer;
import shared.utils.TregexUtils;

public class ClauseCountFeature implements Feature<NlpSentence>
{
	private int nClause;
	private int nWord;
	
	public int clauseCount() {
		return nClause;
	}
	
	public int wordCount() {
		return nWord;
	}
	
	public ClauseCountFeature() {
		reset();
	}
	
	@Override
	public void reset() {
		nClause = 0;
		nWord = 0;
	}
	
	@Override
	public void update(NlpSentence arg) {
		NlpParseTree nlpParseTree = arg.getParseTree();
		
		if(!(nlpParseTree instanceof StanfordNlpParseTreeAdapter))
			throw new UnsupportedOperationException();
		
		
		StanfordNlpParseTreeAdapter treeAdapter = (StanfordNlpParseTreeAdapter)nlpParseTree;
		
		apply(treeAdapter, "S|SINV|SQ < (VP <# MD|VBD|VBP|VBZ)");
		apply(treeAdapter, "FRAG > ROOT !<< VP");
	}
	
	private void apply(StanfordNlpParseTreeAdapter treeAdapter, String pattern) {
		Pair<Integer, Integer> res = treeAdapter.runTregex(pattern);
		nClause += res.getFirst();
		nWord += res.getSecond();
	}
}
