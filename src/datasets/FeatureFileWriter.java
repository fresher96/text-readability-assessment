package datasets;

import java.io.FileNotFoundException;
import java.util.List;

public abstract class FeatureFileWriter
{
	private String path;
	
	public FeatureFileWriter() {
		setPath(null);
	}
	
	public FeatureFileWriter(String path) {
		setPath(path);
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public abstract void writeHeaders(List<String> featureList) throws FileNotFoundException;
	
	public abstract void process(Document document);
	
	public abstract void process(List<Object> features);
}
