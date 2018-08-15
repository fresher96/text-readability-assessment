package featureengineering.features;

import edu.stanford.nlp.trees.Tree;
import nlp.NlpParseTree;
import nlp.stanford.StanfordNlpParseTreeAdapter;
import shared.Pair;
import shared.observer.Observable;
import shared.observer.Observer;
import shared.utils.TregexUtils;

public class ClauseCountFeature implements Feature, Observer<NlpParseTree>
{
	
	private int clauseCount;
	private int wordCount;
	
	public int getClauseCount() {
		return clauseCount;
	}
	
	public int getWordCount() {
		return wordCount;
	}
	
	public ClauseCountFeature() {
		clauseCount = 0;
		wordCount = 0;
	}
	
	
	@Override
	public void update(Observable<NlpParseTree> o, NlpParseTree arg) {
//		nClause += count("S|SINV|SQ < (VP <# MD|VBD|VBP|VBZ)", tree, tpc) + count("FRAG > ROOT !<< VP", tree, tpc);
		
		Tree tree = ((StanfordNlpParseTreeAdapter)arg).getTree();
		Pair<Integer, Integer> res = TregexUtils.count("S|SINV|SQ < (VP <# MD|VBD|VBP|VBZ)", tree);
		clauseCount += res.getFirst();
		wordCount += res.getSecond();
	}
}
