package featureengineering.featuresets;

import featureengineering.features.CharCountFeature;
import featureengineering.features.WordCountFeature;
import nlp.NlpToken;
import shared.Debugger;

public class SampleFeatureSet implements FeatureSet<NlpToken>
{
	private CharCountFeature chars = new CharCountFeature();
	WordCountFeature words = new WordCountFeature();
	private CharCountFeature chars2 = new CharCountFeature();
	protected String test = "hi";
	String testt;
	String ss = null;
}
