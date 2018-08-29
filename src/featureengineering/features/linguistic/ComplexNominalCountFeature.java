package featureengineering.features.linguistic;

import featureengineering.features.Feature;
import nlp.NlpParseTree;
import nlp.NlpSentence;
import nlp.stanford.StanfordNlpParseTreeAdapter;
import shared.Pair;

public class ComplexNominalCountFeature implements Feature<NlpSentence>
{
	private int count;
	private int nWord;
	
	public int count() {
		return count;
	}
	
	public int wordCount() {
		return nWord;
	}
	
	public ComplexNominalCountFeature() {
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
		
		apply(treeAdapter, "NP !> NP [<< JJ|POS|PP|S|VBG |<< (NP $++ NP !$+ CC)]");
		apply(treeAdapter, "SBAR [$+ VP | > VP] & [<# WHNP |<# (IN < That|that|For|for) |<, S]");
		apply(treeAdapter, "S < (VP <# VBG|TO) $+ VP");
	}
	
	private void apply(StanfordNlpParseTreeAdapter treeAdapter, String pattern) {
		Pair<Integer, Integer> res = treeAdapter.runTregex(pattern);
		count += res.getFirst();
		nWord += res.getSecond();
	}
}
