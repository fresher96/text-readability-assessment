package nlp;

public interface NlpToken extends NlpItem
{
	default String raw(){
		throw new UnsupportedOperationException();
	}
	
	default String tag(){
		throw new UnsupportedOperationException();
	}
	
	default String lemma(){
		throw new UnsupportedOperationException();
	}
	
	default String ner(){
		throw new UnsupportedOperationException();
	}
}
