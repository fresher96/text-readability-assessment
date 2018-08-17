package featureengineering.extractors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SampleFeatureExtractor implements FeatureExtractor
{
	List<String> featureList;
	
	public SampleFeatureExtractor(){
		featureList = Arrays.asList(new String[]{"char_count", "word_count", "par_count", "length_category"});
	}
	
	@Override
	public List<String> getFeatureList() {
		return new ArrayList<>(featureList);
	}
	
	@Override
	public List<Object> extract(String text) {
		
		int charCount = 0;
		int wordCount = 0;
		int parCount = 0;
		
		for(int i=0; i<text.length(); i++)
		{
			char ch = text.charAt(i);
			if(ch == '\n')
			{
				parCount++;
				continue;
			}
			else if(ch == ' ')
			{
				wordCount++;
				continue;
			}
			
			charCount++;
		}
		
		String lengthCategory;
		if(parCount <= 5)
			lengthCategory = "short";
		else if(parCount <= 11)
			lengthCategory = "medium";
		else
			lengthCategory = "long";
		
		
		
		List<Object> ret = new ArrayList<>();
		ret.add(charCount);
		ret.add(wordCount);
		ret.add(parCount);
		ret.add(lengthCategory);
		return ret;
	}
}
