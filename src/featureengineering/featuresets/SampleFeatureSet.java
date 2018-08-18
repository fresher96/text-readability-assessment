package featureengineering.featuresets;

import featureengineering.features.CharCountFeature;
import nlp.NlpToken;
import shared.Debugger;

public class SampleFeatureSet implements FeatureSet<NlpToken>
{
	private CharCountFeature chars = new CharCountFeature();
	featureengineering.features.WordCountFeature words = new featureengineering.features.WordCountFeature();
	private CharCountFeature chars2 = new CharCountFeature();
	protected String test = "hi";
	String testt;
	String ss = null;
}
