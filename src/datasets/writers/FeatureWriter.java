package datasets.writers;

import datasets.Document;

import java.io.FileNotFoundException;
import java.util.List;

public interface FeatureWriter
{
	void writeHeaders(List<String> featureList) throws FileNotFoundException;
	
	void process(Document document, List<Object> features);
}
