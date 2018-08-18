package featureengineering.features.traditional;

import featureengineering.features.Feature;
import nlp.NlpToken;
import shared.observer.Observable;
import shared.observer.Observer;

public class WordCountFeature implements Feature<NlpToken>
{
	int value = 0;
	
	@Override
	public void update(NlpToken arg) {
		value++;
	}
	
	@Override
	public Object getValue() {
		return value;
	}
}
