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
	public String raw() {
		return stanfordToken.originalText();
	}
	
	@Override
	public String tag() {
		return stanfordToken.tag();
	}
	
	@Override
	public String lemma() {
		return stanfordToken.lemma();
	}
	
	@Override
	public String ner() {
		return stanfordToken.ner();
	}
}

