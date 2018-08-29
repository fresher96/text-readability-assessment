package featureengineering.featuresets;

import featureengineering.features.linguistic.ClauseCountFeature;
import featureengineering.features.traditional.AlphaCharCountFeature;
import featureengineering.features.traditional.CharCountFeature;
import featureengineering.features.traditional.WordCountFeature;
import nlp.NlpSentence;

import java.util.ArrayList;
import java.util.List;

public class SuperficialFeatureSet implements FeatureSet<NlpSentence>
{
	CharCountFeature nRawChar = new CharCountFeature();
	AlphaCharCountFeature nChar = new AlphaCharCountFeature();
	WordCountFeature nWord = new WordCountFeature();
	
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
		
		ret.add((double) nChar.getValue() / (double) nWord.getValue());
		ret.add((double) nChar.getValue() / (double) nWord.getValue());
		
		return ret;
	}
}
