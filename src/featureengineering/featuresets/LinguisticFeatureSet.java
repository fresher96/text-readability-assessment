package featureengineering.featuresets;

import featureengineering.features.ClauseCountFeature;
import nlp.NlpParseTree;
import shared.Debugger;
import shared.observer.Observable;
import shared.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class LinguisticFeatureSet implements Observer<NlpParseTree>
{
	
	ClauseCountFeature clauses = new ClauseCountFeature();
	
	@Override
	public void update(Observable<NlpParseTree> o, NlpParseTree arg) {
		clauses.update(o, arg);
	}
	
	public List<String> getFeatureNames() {
//		List<String> ret =
		return null;
	}
	
	public List<Object> getFeatures() {
		List<Object> ret = new ArrayList<>();
		ret.add(clauses.getClauseCount());
		ret.add(clauses.getWordCount() / (double) clauses.getClauseCount());
		
		Debugger.debug(ret);
		
		return ret;
	}
}
