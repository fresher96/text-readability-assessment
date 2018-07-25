package prototype;


import shared.StringHelper;

import java.util.*;



class FeatureExtractor
{
	public List<FeatureSet> featureSetList = new ArrayList<>();
}

class Feature
{
	public int ordinal;
	public String name;
	public double value;
	
	public Feature(int ordinal, String name, double value) {
		this.ordinal = ordinal;
		this.name = name;
		this.value = value;
	}
}

abstract class FeatureSet
{
	private String name;
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	protected FeatureSet(){
		setName(this.getClass().getSimpleName());
	}
	
	protected FeatureSet(String name){
		setName(name);
	}
	
	public abstract List<Feature> extract(String document);
	
	public abstract List<String> addedFeatures();
}

class TraditionalFeatureSet extends FeatureSet
{
	public TraditionalFeatureSet(){
		super();
	}
	
	public TraditionalFeatureSet(String name){
		super(name);
	}
	
	public enum Features
	{
		CharacterCount,
		WordCount,
	}
	
	public EnumSet<Features> features = EnumSet.noneOf(Features.class);
	
	public boolean addAllFeatures() {
		return features.addAll(EnumSet.allOf(Features.class));
	}
	
	public boolean removeAllFeatures() {
		return features.removeAll(EnumSet.allOf(Features.class));
	}
	
	public boolean addWordCountFeature() {
		return features.add(Features.WordCount);
	}
	
	public void removeWordCountFeature() {
		features.remove(Features.WordCount);
	}
	
	public void addCharacterCountFeature() {
		features.add(Features.CharacterCount);
	}
	
	public void removeCharacterCountFeature() {
		features.remove(Features.CharacterCount);
	}
	
	@Override
	public List<Feature> extract(String document) {
		
		int wordCount = 0;
		int characterCount = 0;
		
		String[] tokenArray = document.split("\\s");
		Set<String> uniqueWords = new HashSet<>();
		for (String token : tokenArray)
		{
			if (StringHelper.isNullOrEmpty(token)) continue;
			
			uniqueWords.add(token);
			wordCount++;
			characterCount += token.length();
		}
		
		
		List<Feature> ret = new ArrayList<>();
		
		if (features.contains(Features.CharacterCount))
			ret.add(new Feature(Features.CharacterCount.ordinal(), Features.CharacterCount.name(), characterCount));
		
		if (features.contains(Features.WordCount))
			ret.add(new Feature(Features.WordCount.ordinal(), Features.WordCount.name(), wordCount));
		
		return ret;
	}
	
	@Override
	public List<String> addedFeatures() {
		List<String> ret = new ArrayList<>();
		for(Features feature : features)
		{
			ret.add(feature.name());
		}
		return ret;
	}
}
