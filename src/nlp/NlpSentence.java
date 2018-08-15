package nlp;

import java.util.List;

public interface NlpSentence
{
	List<NlpToken> getTokenList();
	
	NlpParseTree getParseTree();
}

