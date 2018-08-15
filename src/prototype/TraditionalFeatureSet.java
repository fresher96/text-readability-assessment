package prototype;

import shared.utils.StringUtils;

import java.util.*;

public class TraditionalFeatureSet extends FeatureSet
{
	public TraditionalFeatureSet() {
		super();
	}
	
	public TraditionalFeatureSet(String name) {
		super(name);
	}
	
	public EnumSet<Features> features = EnumSet.allOf(Features.class);
	
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
	
	private void addFeature(List<Feature> featureList, List<Integer> featureValueList, Features feature) {
		if (features.contains(feature))
		{
			featureList.add(new Feature(feature.ordinal(), feature.name(), featureValueList.get(feature.ordinal())));
		}
	}
	
	public enum Features
	{
		CharacterCount,
		WordCount,
		AverageWordLength,
		UniqueWordsCount,
		TypeTokenRatio,
		RootTypeTokenRatio,
		CorrectedTypeTokenRatio,
	}
	
	@Override
	public List<Feature> extract(String document) {
		
		int wordCount = 0;
		int characterCount = 0;
		int uniqueWordsCount = 0;
		int linesCount = 0;
		
		String[] tokenArray = document.split("\\s");
		Set<String> uniqueWords = new HashSet<>();
		
		for (String token : tokenArray)
		{
			if (StringUtils.isNullOrEmpty(token)) continue;
			
			if (!uniqueWords.contains(token))
			{
				uniqueWords.add(token);
				uniqueWordsCount++;
			}
			
			
			wordCount++;
			characterCount += token.length();
		}
		
		
		List<Feature> ret = new ArrayList<>();
		
		if (features.contains(Features.CharacterCount))
			ret.add(new Feature(Features.CharacterCount.ordinal(), Features.CharacterCount.name(), characterCount));
		
		if (features.contains(Features.WordCount))
			ret.add(new Feature(Features.WordCount.ordinal(), Features.WordCount.name(), wordCount));
		
		ret.add(new Feature(-1, "AverageWordCount", characterCount / (double) wordCount));
		ret.add(new Feature(-1, "", uniqueWordsCount));
		
		ret.add(new Feature(-1, "TTR", uniqueWordsCount / (double) wordCount));
		ret.add(new Feature(-1, "Root_TTR", uniqueWordsCount / Math.sqrt(wordCount)));
		ret.add(new Feature(-1, "Corrected_TTR", uniqueWordsCount / Math.sqrt(2 * wordCount)));
		
		
		return ret;
	}
	
	
	@Override
	public List<String> addedFeatures() {
		List<String> ret = new ArrayList<>();
		for (Features feature : features)
		{
			ret.add(feature.name());
		}
		return ret;
	}
}
