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

public class TUnitCountFeature implements Feature<NlpSentence>
{
	private int count;
	private int nWord;
	
	public int count() {
		return count;
	}
	
	public int wordCount() {
		return nWord;
	}
	
	public TUnitCountFeature() {
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
		
		if (!(nlpParseTree instanceof StanfordNlpParseTreeAdapter))
			throw new UnsupportedOperationException();
		
		
		StanfordNlpParseTreeAdapter treeAdapter = (StanfordNlpParseTreeAdapter) nlpParseTree;
		
		apply(treeAdapter, "S|SBARQ|SINV|SQ > ROOT | [$-- S|SBARQ|SINV|SQ !>> SBAR|VP]");
		apply(treeAdapter, "FRAG > ROOT");
	}
	
	private void apply(StanfordNlpParseTreeAdapter treeAdapter, String pattern) {
		Pair<Integer, Integer> res = treeAdapter.runTregex(pattern);
		count += res.getFirst();
		nWord += res.getSecond();
	}
}
