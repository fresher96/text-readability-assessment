package featureengineering.features.traditional;

import featureengineering.features.Feature;
import nlp.NlpToken;
import shared.observer.Observable;
import shared.observer.Observer;
import shared.utils.StringUtils;

public class WordCountFeature implements Feature<NlpToken>
{
	int value = 0;
	
	@Override
	public void update(NlpToken arg) {
		String word = arg.raw();
		if(StringUtils.isAWord(word))
			value++;
	}
	
}
