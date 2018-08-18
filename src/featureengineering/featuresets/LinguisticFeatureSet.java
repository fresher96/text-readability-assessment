package featureengineering.featuresets;

import featureengineering.features.linguistic.ClauseCountFeature;
import nlp.NlpSentence;

import java.util.ArrayList;
import java.util.List;

public class LinguisticFeatureSet implements FeatureSet<NlpSentence>
{
	
	ClauseCountFeature clauses = new ClauseCountFeature();
	
	@Override
	public List<String> getFeatureList() {
		List<String> ret = new ArrayList<>();
		
		ret.add("ClauseCount");
		ret.add("AvgClauseLength");
		
		return ret;
	}
	
	@Override
	public List<Object> getFeatures() {
		List<Object> ret = new ArrayList<>();
		
		ret.add(clauses.clauseCount());
		ret.add(clauses.wordCount() / (double)clauses.clauseCount());
		
		return ret;
	}
	
}
