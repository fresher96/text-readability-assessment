package nlp.stanford;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import nlp.*;

public class StanfordNlpParserAdapter implements NlpParser
{
	
	private StanfordCoreNLP stanfordNLP;
	
	public StanfordNlpParserAdapter(StanfordCoreNLP stanfordCoreNLP) {
		this.stanfordNLP = stanfordCoreNLP;
	}
	
	@Override
	public NlpAnnotation annotate(String text) {
		Annotation doc = new Annotation(text);
		stanfordNLP.annotate(doc);
		NlpAnnotation stanfordAnnotation = new StanfordNlpAnnotationAdapter(doc);
		return stanfordAnnotation;
	}
}
