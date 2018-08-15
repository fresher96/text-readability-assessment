package nlp.stanford;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import nlp.*;

public class StanfordNlpTokenAdapter implements NlpToken
{
	
	private CoreLabel stanfordToken;
	
	public StanfordNlpTokenAdapter(CoreLabel stanfordToken) {
		this.stanfordToken = stanfordToken;
	}
	
	
	@Override
	public String getRaw() {
		return stanfordToken.get(CoreAnnotations.TextAnnotation.class);
	}
}

