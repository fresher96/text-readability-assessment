package prototype;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import shared.Debugger;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class NlpFeatureSet extends FeatureSet
{
	private StanfordCoreNLP nlpParser;
	
	public NlpFeatureSet(StanfordCoreNLP nlpParser){
		this.nlpParser = nlpParser;
	}
	
	@Override
	public List<String> addedFeatures() { // rename it to getSelectedFeatures
		List<String> ret = new ArrayList<>();
		for (NlpFeatureSet.Features feature : selectedFeatures)
		{
			ret.add(feature.name());
		}
		return ret;
	}
	
	EnumSet<Features> selectedFeatures = EnumSet.allOf(Features.class);
	
	public enum Features
	{
		Test,
	}
	
	@Override
	public List<Feature> extract(String document) {
		
		Annotation doc = new Annotation(document);
		nlpParser.annotate(doc);
		
		
		
		List<Feature> ret = new ArrayList<>();
		ret.add(new Feature(-1, "", 777));
		return ret;
	}
	
}
