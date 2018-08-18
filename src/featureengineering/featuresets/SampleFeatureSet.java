package featureengineering.featuresets;

import featureengineering.features.traditional.CharCountFeature;
import featureengineering.features.traditional.WordCountFeature;
import nlp.NlpToken;

public class SampleFeatureSet implements FeatureSet<NlpToken>
{
	private CharCountFeature chars = new CharCountFeature();
	WordCountFeature words = new WordCountFeature();
	private CharCountFeature chars2 = new CharCountFeature();
	protected String test = "hi";
	String testt;
	String ss = null;
}
