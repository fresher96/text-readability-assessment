package datasets.writers;

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
