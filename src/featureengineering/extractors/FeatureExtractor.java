package featureengineering.extractors;

import java.util.List;

public interface FeatureExtractor
{
	List<String> getFeatureList();
	
	List<Object> extract(String text);
}
