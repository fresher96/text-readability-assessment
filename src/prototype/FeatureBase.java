package prototype;

import java.util.ArrayList;
import java.util.List;

class FeatureExtractorOld
{
	public List<FeatureBase> featureList = new ArrayList<>();

	public List<Integer> extract(String document) {

		List<Integer> ret = new ArrayList<>();
		for (FeatureBase feature : featureList)
		{
			ret.add(feature.extract(document));
		}

		return ret;
	}
}

public abstract class FeatureBase
{
	public abstract int extract(String document);
}


class WordCountFeature extends FeatureBase
{
	@Override
	public int extract(String document) {
		int ret = 0;
		String[] tokenArray = document.split("\\s");
		for (String token : tokenArray)
		{
			if (token.length() > 0)
				ret++;
		}
		return ret;
	}
}

class CharacterCountFeature extends FeatureBase
{
	@Override
	public int extract(String document) {
		int ret = 0;
		for (int i = 0; i < document.length(); i++)
		{
			if (!Character.isWhitespace(document.charAt(i)))
				ret++;
		}
		return ret;
	}
}

