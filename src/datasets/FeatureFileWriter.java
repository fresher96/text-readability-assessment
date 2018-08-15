package datasets;

import java.io.FileNotFoundException;
import java.util.List;

public abstract class FeatureFileWriter implements FeatureWriter
{
	private String path;
	
	public FeatureFileWriter() {
		this(null);
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
}
