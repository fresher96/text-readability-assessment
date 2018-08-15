package nlp.stanford;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;
import nlp.*;

import java.util.ArrayList;
import java.util.List;

public class StanfordNlpAnnotationAdapter implements NlpAnnotation
{
	
	private Annotation document;
	
	public StanfordNlpAnnotationAdapter(Annotation document) {
		this.document = document;
	}
	
	@Override
	public List<NlpSentence> getSentenceList() {
		
		List<CoreMap> sentenceList = document.get(CoreAnnotations.SentencesAnnotation.class);
		List<NlpSentence> ret = new ArrayList<>(sentenceList.size());
		
		for (CoreMap sentence : sentenceList)
		{
			NlpSentence stanfordSentence = new StanfordNlpSentenceAdapter(sentence);
			ret.add(stanfordSentence);
		}
		
		return ret;
	}
}
