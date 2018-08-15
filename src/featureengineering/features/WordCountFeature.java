package featureengineering.features;

import nlp.NlpToken;
import shared.observer.Observable;
import shared.observer.Observer;

public class WordCountFeature implements Feature, Observer<NlpToken>
{
	@Override
	public void update(Observable<NlpToken> o, NlpToken arg) {
		System.out.println(arg.getRaw());
	}
}
