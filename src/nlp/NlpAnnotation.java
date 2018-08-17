package nlp;

import java.util.List;

public interface NlpAnnotation extends NlpItem
{
	List<NlpSentence> getSentenceList();
}

