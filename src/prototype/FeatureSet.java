package prototype;

import java.util.List;

public abstract class FeatureSet
{
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	protected FeatureSet() {
		setName(this.getClass().getSimpleName());
	}
	
	protected FeatureSet(String name) {
		setName(name);
	}
	
	public abstract List<Feature> extract(String document);
	
	public abstract List<String> addedFeatures();
}
