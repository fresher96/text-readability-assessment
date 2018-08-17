package nlp;

import java.util.List;

public interface NlpSentence extends NlpItem
{
	List<NlpToken> getTokenList();
	
	NlpParseTree getParseTree();
}

