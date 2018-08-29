package featureengineering.features.traditional;

import featureengineering.features.Feature;
import nlp.NlpToken;

public class CharCountFeature implements Feature<NlpToken>
{
	int value = 0;
	
	@Override
	public void update(NlpToken arg) {
		value += arg.raw().length();
	}
}
