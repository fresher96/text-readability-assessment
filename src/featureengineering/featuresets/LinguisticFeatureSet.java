package featureengineering.featuresets;

import featureengineering.features.linguistic.*;
import nlp.NlpSentence;

import java.util.ArrayList;
import java.util.List;

public class LinguisticFeatureSet implements FeatureSet<NlpSentence>
{
	ClauseCountFeature clauses = new ClauseCountFeature();
	ComplexNominalCountFeature nominals = new ComplexNominalCountFeature();
	ComplexTUnitCountFeature cTUnits = new ComplexTUnitCountFeature();
	CoordinatePhraseCountFeature coordinateClauses = new CoordinatePhraseCountFeature();
	DependentClauseCountFeature depClauses = new DependentClauseCountFeature();
	TUnitCountFeature TUnits = new TUnitCountFeature();
	VerbPhraseCountFeature verbPhrases = new VerbPhraseCountFeature();
	
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
		
		ret.add((double) clauses.wordCount() / (double) clauses.count());
		ret.add((double) TUnits.wordCount() / (double) TUnits.count());
		
		return ret;
	}
	
}
