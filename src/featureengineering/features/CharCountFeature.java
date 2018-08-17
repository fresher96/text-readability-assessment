package featureengineering.features;

import nlp.NlpToken;

public class CharCountFeature implements Feature<NlpToken>
{
	int value = 0;
	
	@Override
	public void update(NlpToken arg) {
		value += arg.getRaw().length();
	}
	
	@Override
	public Object getValue() {
		return value;
	}
}
