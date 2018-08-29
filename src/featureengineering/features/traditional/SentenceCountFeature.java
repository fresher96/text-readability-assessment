package featureengineering.features.traditional;

import featureengineering.features.Feature;
import nlp.NlpSentence;
import nlp.NlpToken;
import shared.utils.StringUtils;

public class SentenceCountFeature implements Feature<NlpSentence>
{
	int value = 0;
	
	@Override
	public void update(NlpSentence arg) {
		value++;
	}
}
