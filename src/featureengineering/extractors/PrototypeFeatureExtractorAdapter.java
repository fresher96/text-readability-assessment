package featureengineering.extractors;

import java.util.ArrayList;
import java.util.List;

public class PrototypeFeatureExtractorAdapter implements FeatureExtractor
{
	private prototype.FeatureExtractor featureExtractor;
	
	public PrototypeFeatureExtractorAdapter(prototype.FeatureExtractor featureExtractor) {
		this.featureExtractor = featureExtractor;
	}
	
	@Override
	public List<String> getFeatureList() {
		List<String> ret = new ArrayList<>();
		for (prototype.FeatureSet featureSet : featureExtractor.featureSetList)
		{
			List<String> addedFeatures = featureSet.addedFeatures();
			for (String feature : addedFeatures)
			{
				ret.add(feature);
			}
		}
		return ret;
	}
	
	@Override
	public List<Object> extract(String text) {
		List<Object> ret = new ArrayList<>();
		for (prototype.FeatureSet featureSet : featureExtractor.featureSetList)
		{
			List<prototype.Feature> featureList = featureSet.extract(text);
			for (prototype.Feature feature : featureList)
			{
				ret.add(feature.value);
			}
		}
		return ret;
	}
}
