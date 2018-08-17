package featureengineering.featuresets;

import nlp.stanford.NlpTemp;
import shared.observer.Observable;
import shared.observer.Observer;

import java.util.List;

public interface FeatureSet <T extends NlpTemp> extends Observer<T>
{
	List<String> getFeatureList();
	
	List<Object> getFeatures();
}


class LingFeatureSet implements FeatureSet<NlpTemp>
{
	
	@Override
	public List<String> getFeatureList() {
		return null;
	}
	
	@Override
	public List<Object> getFeatures() {
		return null;
	}
	
	@Override
	public void update(Observable<NlpTemp> o, NlpTemp arg) {
	
	}
}
