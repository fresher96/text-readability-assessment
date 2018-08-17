package featureengineering.featuresets;

import featureengineering.features.ClauseCountFeature;
import nlp.NlpItem;
import nlp.NlpParseTree;
import nlp.NlpSentence;

import java.util.ArrayList;
import java.util.List;

public class LinguisticFeatureSet implements FeatureSet<NlpSentence>
{
	
	ClauseCountFeature clauses = new ClauseCountFeature();
	
	
	@Override
	public List<String> getFeatureList() {
		List<String> ret = new ArrayList<>();
		ret.add(clauses.getName());
		return ret;
	}
	
	@Override
	public List<Object> getFeatures() {
		List<Object> ret = new ArrayList<>();
		ret.add(clauses.getClauseCount());
		return ret;
	}
	
	@Override
	public void update(NlpSentence arg) {
		NlpParseTree tree = arg.getParseTree();
		clauses.update(null, tree);
	}
}
