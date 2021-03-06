package featureengineering.features.linguistic;

import featureengineering.features.Feature;
import nlp.NlpParseTree;
import nlp.NlpSentence;
import nlp.stanford.StanfordNlpParseTreeAdapter;
import shared.Pair;

public class DependentClauseCountFeature implements Feature<NlpSentence>
{
	private int count;
	private int nWord;
	
	public int count() {
		return count;
	}
	
	public int wordCount() {
		return nWord;
	}
	
	public DependentClauseCountFeature() {
		reset();
	}
	
	@Override
	public void reset() {
		count = 0;
		nWord = 0;
	}
	
	@Override
	public void update(NlpSentence arg) {
		NlpParseTree nlpParseTree = arg.getParseTree();
		
		if(!(nlpParseTree instanceof StanfordNlpParseTreeAdapter))
			throw new UnsupportedOperationException();
		
		
		StanfordNlpParseTreeAdapter treeAdapter = (StanfordNlpParseTreeAdapter)nlpParseTree;
		
		apply(treeAdapter, "SBAR < (S|SINV|SQ < (VP <# MD|VBD|VBP|VBZ))");
	}
	
	private void apply(StanfordNlpParseTreeAdapter treeAdapter, String pattern) {
		Pair<Integer, Integer> res = treeAdapter.runTregex(pattern);
		count += res.getFirst();
		nWord += res.getSecond();
	}
}
