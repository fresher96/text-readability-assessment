package cleaners.automatic;

import datasets.Document;

public interface TextCleaner
{
	void clean(Document document);
}
