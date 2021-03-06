package featureengineering.features.linguistic;

import featureengineering.features.Feature;
import nlp.NlpParseTree;
import nlp.NlpSentence;
import nlp.stanford.StanfordNlpParseTreeAdapter;
import shared.Pair;

public class CoordinatePhraseCountFeature implements Feature<NlpSentence>
{
	private int count;
	private int nWord;
	
	public int count() {
		return count;
	}
	
	public int wordCount() {
		return nWord;
	}
	
	public CoordinatePhraseCountFeature() {
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
		
		apply(treeAdapter, "ADJP|ADVP|NP|VP < CC");
	}
	
	private void apply(StanfordNlpParseTreeAdapter treeAdapter, String pattern) {
		Pair<Integer, Integer> res = treeAdapter.runTregex(pattern);
		count += res.getFirst();
		nWord += res.getSecond();
	}
}
