package datasets;

import java.util.List;

public interface FeatureFileWriter
{
	void writeHeaders(List<String> featureList);
	
	void process(Document document);
	
	void process(List<Object> features);
}
