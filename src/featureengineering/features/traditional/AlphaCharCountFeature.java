package featureengineering.features.traditional;

import featureengineering.features.Feature;
import nlp.NlpToken;

public class AlphaCharCountFeature implements Feature<NlpToken>
{
	int value = 0;
	
	@Override
	public void update(NlpToken arg) {
		String word = arg.raw();
		for(int i=0; i<word.length(); i++)
		{
			if(Character.isAlphabetic(word.charAt(i)))
				value++;
		}
	}
}