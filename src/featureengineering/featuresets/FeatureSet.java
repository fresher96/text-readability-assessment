package featureengineering.featuresets;

import featureengineering.FeatureExtractor;
import nlp.NlpItem;
import shared.observer.Observable;
import shared.observer.Observer;

import java.util.List;

public interface FeatureSet <T extends NlpItem>
{
	List<String> getFeatureList();
	
	List<Object> getFeatures();
	
	void update(T arg);
}
